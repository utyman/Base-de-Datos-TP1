package ubadb.bench;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import ubadb.bench.tests.ConcatTest;
import ubadb.bench.tests.Test;
import ubadb.bench.tests.TestResult;

public class CompareStrategies {
	public static void main(String[] args) {
		try {
			new CompareStrategies().run(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final Test[] TESTS = new Test[] {
	//
	// new FileScanTest(2, 5, 40), //
	// new FileScanTest(5, 5, 40), //
	// new FileScanTest(20, 50, 40),//
	// //
	// new IndexScanClusteredTest(3, 3, 3, 40), //
	// new IndexScanClusteredTest(6, 3, 3, 40), //
	// new IndexScanClusteredTest(10, 10, 10, 40), //
	// //
	// new IndexScanUnclusteredTest(3, 3, 3, 10, 40), //
	// new IndexScanUnclusteredTest(6, 3, 3, 10, 40), //
	// new IndexScanUnclusteredTest(5, 3, 10, 50, 40), //
	// //
	// new BNLJTest(5, 7, 2, 3, 40), //
	// new BNLJTest(10, 7, 2, 3, 40), //
	new ConcatTest(20, 20, 10, 1) };

	void run(String args[]) throws FileNotFoundException {
		PrintWriter csv = new PrintWriter(new FileOutputStream("output.csv"));
		csv.println("Test,Strategy,TargetRate");
		for (Test test : TESTS) {
			System.out.println("Testing " + test.getName());
			for (Strategy strategy : Strategy.INSTANCES) {
				System.out.print("   Strategy: " + strategy.getName());
				long init = System.currentTimeMillis();
				TestResult result = test.test(strategy);
				System.out.println(". Result: " + result + " ("
						+ (System.currentTimeMillis() - init) + "ms)");

				csv.println("\"" + test.getName() + "\"," + strategy.getName()
						+ "," + result.hitRate());
			}
			System.out.println();
		}
		csv.flush();
		csv.close();
	}
}
