package ubadb.bench.tests;

import java.util.Random;

import ubadb.apps.bufferManagement.PageReference;
import ubadb.apps.bufferManagement.PageReferenceTrace;
import ubadb.apps.bufferManagement.PageReferenceType;
import ubadb.common.PageId;
import ubadb.common.TableId;

public class TraceUtil {

	private static final int FILE_SCAN = 0;
	
	private static final int INDEX_CLUSTERED_SCAN = 1;
	
	private static final int INDEX_UNCLUSTERED_SCAN = 2;
	
	private static final int BNLJ = 3;
	
	private static final int NUMBER_OF_DIFFERENT_STRATEGIES = 4;
	
	private static final int BLOCKS = 5;
	
	public PageReferenceTrace generateRandomTrace(int numberOfTracesToConcat, int pagesBySingleTrace, int bufferSize) {
		// generator used to create all-concatenated different kinds of traces
		Random gen = new Random(564);
		PageReferenceTrace concatenatedTrace = new PageReferenceTrace();
		for (int i = 0; i < numberOfTracesToConcat; i++) {
			int traceType = gen.nextInt(NUMBER_OF_DIFFERENT_STRATEGIES);
			concatenatedTrace.concatenate(obtainTrace(traceType, pagesBySingleTrace, bufferSize));
		}
		
		return concatenatedTrace; 
	}

	public PageReferenceTrace obtainTrace(int type, int pagesBySingleTrace, int bufferSize) {
		PageReferenceTrace trace = new PageReferenceTrace();
		Test test = null;
		switch(type) {
			case FILE_SCAN:
				test = new FileScanTest(bufferSize, pagesBySingleTrace, 1);
				trace.concatenate(test.createTraces());
				break;
			case INDEX_CLUSTERED_SCAN:
				test = new IndexScanClusteredTest(bufferSize, 3, pagesBySingleTrace, 1);
				trace.concatenate(test.createTraces());
				break;
			case INDEX_UNCLUSTERED_SCAN:
				Random randomPage = new Random(314);
				int pages = randomPage.nextInt(50);
				test = new IndexScanUnclusteredTest(bufferSize, 3, pagesBySingleTrace, pages, 1);
				trace.concatenate(test.createTraces());
				break;
			case BNLJ:
				Random randomFirstCount = new Random(315);
				Random randomSecondCount = new Random(316);
				
				test = new BNLJTest(bufferSize, randomFirstCount.nextInt(), randomSecondCount.nextInt(50), BLOCKS, 1);
				trace.concatenate(test.createTraces());
				break;
				
				
				
		}
		return trace;
	}
	
	
	protected PageReferenceTrace createTracesFileScan(int pages) {
		PageReferenceTrace trace = new PageReferenceTrace();
		addScanFileScan("A", 0, pages, trace);
		return trace;
	}

	protected PageReferenceTrace createTracesBNLJ(int firstCount, int secondCount, int blocks) {
		PageReferenceTrace trace = new PageReferenceTrace();
		for (int f = 0; f < firstCount; f++) {
			int block = Math.min(blocks, firstCount - f);
			addScanBNLJ("A", f, f + block, PageReferenceType.REQUEST, trace);
			addScanFileScan("B", 0, secondCount, trace);
			addScanBNLJ("A", f, f + block, PageReferenceType.RELEASE, trace);
		}
		return trace;
	}

	protected PageReferenceTrace createTraceScanClustered(int indexLevels, int resultSize) {
		PageReferenceTrace trace = new PageReferenceTrace();
		addScanFileScan("A_index", 0, indexLevels, trace);
		addScanFileScan("A", 0, resultSize, trace);
		return trace;
	}

	protected PageReferenceTrace createTraceScanUnclustered(int indexLevels, int resultSize, int resultPages) {
		Random random = new Random(314);
		PageReferenceTrace trace = new PageReferenceTrace();
		addScanFileScan("A_index", 0, indexLevels, trace);
		for (int i = 0; i < resultSize; i++) {
			int page = random.nextInt(resultPages);
			trace.addPageReference(new PageReference(new PageId(page,
					new TableId("A")), PageReferenceType.REQUEST));
			trace.addPageReference(new PageReference(new PageId(page,
					new TableId("A")), PageReferenceType.RELEASE));
		}
		return trace;
	}

	static void addScanFileScan(String table, int from, int to, PageReferenceTrace trace) {
		for (int page = from; page < to; page++) {
			trace.addPageReference(new PageReference(new PageId(page,
					new TableId(table)), PageReferenceType.REQUEST));
			trace.addPageReference(new PageReference(new PageId(page,
					new TableId(table)), PageReferenceType.RELEASE));
		}
	}

	static void addScanBNLJ(String table, int from, int to, PageReferenceType type,
			PageReferenceTrace trace) {
		for (int page = from; page < to; page++) {
			trace.addPageReference(new PageReference(new PageId(page,
					new TableId(table)), type));
		}
	}

	public static void main(String[] args) {
		try {
			TraceUtil util = new TraceUtil();
			System.out.println(util.generateRandomTrace(50, 10, 50));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}

 