package ubadb.bench;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import ubadb.apps.bufferManagement.PageReferenceTrace;
import ubadb.bench.tests.Test;
import ubadb.bench.tests.TestResult;
import ubadb.bench.util.TraceUtil;

public class CompareStrategies {
	public static void main(String[] args) {
		try {
			new CompareStrategies().run(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final PageReferenceTrace TRACE_SMALL = TraceUtil
			.generateRandomTrace(100, 3);

	private static final PageReferenceTrace TRACE_BIG = TraceUtil
			.generateRandomTrace(1000, 10);

	private static final Test[] TESTS = new Test[] {
			new Test("SMALL", TRACE_SMALL), new Test("BIG", TRACE_BIG) };

	void run(String args[]) throws FileNotFoundException {
		PrintWriter csv = new PrintWriter(new FileOutputStream("output.csv"));
		csv.println("Test,BufferSize,Strategy,Rate");
		for (Test test : TESTS) {
			int minBufferSize = TraceUtil.calculateMaxPinned(test.getTrace());
			int maxBufferSize = TraceUtil.calculateNumberOfDifferentPages(test
					.getTrace());
			Set<Integer> bufferSizes = new HashSet<Integer>();
			bufferSizes.add(minBufferSize);
			bufferSizes.add(maxBufferSize);
			int count = 15;
			int delta = Math.max(1, (maxBufferSize - minBufferSize) / count);
			for (int b = minBufferSize; b < maxBufferSize; b += delta) {
				bufferSizes.add(b);
			}
			bufferSizes = new TreeSet<Integer>(bufferSizes);
			System.out.println("Testing \"" + test.getName() + "\" of size "
					+ test.getTrace().getPageReferences().size()
					+ " calls, with buffers: " + bufferSizes);
			for (Integer bufferSize : bufferSizes) {
				System.out.println("   BufferSize: " + bufferSize);
				for (Strategy strategy : Strategy.INSTANCES) {
					System.out.print("       Strategy: " + strategy.getName());
					long init = System.currentTimeMillis();
					TestResult result = test.test(strategy, bufferSize);
					System.out.println(". Result: " + result + " ("
							+ (System.currentTimeMillis() - init) + "ms)");

					csv.println("\"" + test.getName() + "\"," + bufferSize
							+ "," + strategy.getName() + "," + result.hitRate());
				}
			}
			System.out.println();
		}
		csv.flush();
		csv.close();
	}
}
