package ubadb.bench.tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ubadb.apps.bufferManagement.PageReference;
import ubadb.apps.bufferManagement.PageReferenceTrace;
import ubadb.apps.bufferManagement.PageReferenceTraceGenerator;
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

	public int calculateMaxPinned(PageReferenceTrace trace) {
		List<PageReference> pageReferences = trace.getPageReferences();
		Map<PageId, Integer> pinsPerPages = generatePinsPerPages(pageReferences);
		int currentlyPinnedPages = 0;
		int maxConcurrentlyPinnedPages = 0;
		
		for(PageReference pageReference : pageReferences) {
			if (pageReference.getType().equals(PageReferenceType.REQUEST)) {
				pinsPerPages.put(pageReference.getPageId(), pinsPerPages.get(pageReference.getPageId()) + 1);
				currentlyPinnedPages++;
				if (currentlyPinnedPages > maxConcurrentlyPinnedPages) {
					maxConcurrentlyPinnedPages = currentlyPinnedPages;
				}
			} else {
				pinsPerPages.put(pageReference.getPageId(), pinsPerPages.get(pageReference.getPageId()) - 1); 
				currentlyPinnedPages--;
			}
		}
		return maxConcurrentlyPinnedPages;
	}
	private Map<PageId, Integer> generatePinsPerPages(
			List<PageReference> pageReferences) {
		Map<PageId, Integer> pinsPerPages = new HashMap<PageId, Integer>();
		
		for (PageReference pageReference : pageReferences) {
			pinsPerPages.put(pageReference.getPageId(), new Integer(0));
		}
		return pinsPerPages;
	}
	public PageReferenceTrace generateRandomTrace(int numberOfTracesToConcat,
			int pagesBySingleTrace) {
		// generator used to create all-concatenated different kinds of traces
		Random gen = new Random(564);
		PageReferenceTrace concatenatedTrace = new PageReferenceTrace();
		for (int i = 0; i < numberOfTracesToConcat; i++) {
			int traceType = gen.nextInt(NUMBER_OF_DIFFERENT_STRATEGIES);
			concatenatedTrace.concatenate(obtainTrace(traceType,
					pagesBySingleTrace));
		}

		return concatenatedTrace;
	}

	public PageReferenceTrace obtainTrace(int type, int pagesBySingleTrace) {
		Random random = new Random(314);
		PageReferenceTrace trace = new PageReferenceTrace();
		switch (type) {
		case FILE_SCAN:
			trace.concatenate(createTracesFileScan(pagesBySingleTrace));
			break;
		case INDEX_CLUSTERED_SCAN:
			trace.concatenate(createTraceScanClustered(3, pagesBySingleTrace));
			break;
		case INDEX_UNCLUSTERED_SCAN:
			int pages = random.nextInt(50);
			trace.concatenate(createTraceScanUnclustered(3, pagesBySingleTrace,
					pages));
			break;
		case BNLJ:
			trace.concatenate(createTracesBNLJ(random.nextInt(50),
					random.nextInt(50), BLOCKS));
			break;

		}
		return trace;
	}

	protected PageReferenceTrace createTracesFileScan(int pages) {
		PageReferenceTrace trace = new PageReferenceTrace();
		addScanFileScan("A", 0, pages, trace);
		return trace;
	}

	protected PageReferenceTrace createTracesBNLJ(int firstCount,
			int secondCount, int blocks) {
		PageReferenceTrace trace = new PageReferenceTrace();
		for (int f = 0; f < firstCount; f++) {
			int block = Math.min(blocks, firstCount - f);
			addScanBNLJ("A", f, f + block, PageReferenceType.REQUEST, trace);
			addScanFileScan("B", 0, secondCount, trace);
			addScanBNLJ("A", f, f + block, PageReferenceType.RELEASE, trace);
		}
		return trace;
	}

	protected PageReferenceTrace createTraceScanClustered(int indexLevels,
			int resultSize) {
		PageReferenceTrace trace = new PageReferenceTrace();
		addScanFileScan("A_index", 0, indexLevels, trace);
		addScanFileScan("A", 0, resultSize, trace);
		return trace;
	}

	protected PageReferenceTrace createTraceScanUnclustered(int indexLevels,
			int resultSize, int resultPages) {
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

	static void addScanFileScan(String table, int from, int to,
			PageReferenceTrace trace) {
		for (int page = from; page < to; page++) {
			trace.addPageReference(new PageReference(new PageId(page,
					new TableId(table)), PageReferenceType.REQUEST));
			trace.addPageReference(new PageReference(new PageId(page,
					new TableId(table)), PageReferenceType.RELEASE));
		}
	}

	static void addScanBNLJ(String table, int from, int to,
			PageReferenceType type, PageReferenceTrace trace) {
		for (int page = from; page < to; page++) {
			trace.addPageReference(new PageReference(new PageId(page,
					new TableId(table)), type));
		}

	}

	public static void main(String[] args) {
		try {
			TraceUtil util = new TraceUtil();
			PageReferenceTraceGenerator gen = new PageReferenceTraceGenerator();
//			System.out.println(gen.generateFileScan("A",0,5));
//			System.out.println(gen.generateIndexScanClustered("A", 3, 3, 3));
//			System.out.println(gen.generateIndexScanUnclustered("A", 3, 3, 10));
			System.out.println(gen.generateBNLJ("A", 7, "B", 4, 3));
			System.out.println("max: " + util.calculateMaxPinned(gen.generateBNLJ("A", 7, "B", 4, 3)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
