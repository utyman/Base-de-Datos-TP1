package ubadb.bench.util;

import java.util.ArrayList;
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

	private static final String [] tables = { "A", "B", "C", "D", "E", "F"};
	/**
	 * file scan type index
	 */
	private static final int FILE_SCAN = 0;

	/**
	 * index clustered scan index
	 */
	private static final int INDEX_CLUSTERED_SCAN = 1;

	/**
	 * index unclustered scan index
	 */
	private static final int INDEX_UNCLUSTERED_SCAN = 2;

	/**
	 * Block Nested Loop Join index
	 */
	private static final int BNLJ = 3;

	/**
	 * Number of different strategies
	 */

	private static final int NUMBER_OF_DIFFERENT_STRATEGIES = 4;

	/**
	 * Number of blocks
	 */
	private static final int BLOCKS = 5;

	/**
	 * calculate the max number of different pages.
	 * Useful in order to calculate the relevant max for the buffer size.
	 * @param traces
	 * @return
	 */
	public int calculateNumberOfDifferentPages(PageReferenceTrace trace) {
		List<PageId> ids = new ArrayList<PageId>();
		int numberOfDifferentPages = 0;
		
		for (PageReference reference : trace.getPageReferences()) {
			if (!ids.contains(reference.getPageId())) {
				numberOfDifferentPages++;
				ids.add(reference.getPageId());
			}
		}
		return numberOfDifferentPages;
		
	}
	/**
	 * calculate the max number of page simultaneously pinned throughout the 
	 * trace evaluation
	 * 
	 * @param trace
	 * @return
	 */
	public int calculateMaxPinned(PageReferenceTrace trace) {
		List<PageReference> pageReferences = trace.getPageReferences();
		Map<PageId, Integer> pinsPerPages = generatePinsPerPages(pageReferences);
		int currentlyPinnedPages = 0; // variable to store the currently pinned pp throughout the the trace iteration
		int maxConcurrentlyPinnedPages = 0; // variable to store the temporary max
		
		for(PageReference pageReference : pageReferences) {
			if (pageReference.getType().equals(PageReferenceType.REQUEST)) {
				pinsPerPages.put(pageReference.getPageId(), pinsPerPages.get(pageReference.getPageId()) + 1);
				// in case the page had not been previously pinned, then we update the currentlyPinnedPages variable
				if (pinsPerPages.get(pageReference.getPageId()) == 1) {
					currentlyPinnedPages++;
					if (currentlyPinnedPages > maxConcurrentlyPinnedPages ) {
						maxConcurrentlyPinnedPages = currentlyPinnedPages;
					}
				}
			} else {
				pinsPerPages.put(pageReference.getPageId(), pinsPerPages.get(pageReference.getPageId()) - 1); 
				// in case every pin has been released, we update the currentlyPinnedPages variable.
				if (pinsPerPages.get(pageReference.getPageId()) == 0) {
					currentlyPinnedPages--;
				}
				
			}
		}
		return maxConcurrentlyPinnedPages;
	}
	/**
	 *  generate a map from a page id to the number of pins inflicted on the correspondent
	 *  page.
	 *  
	 * @param pageReferences the list of the pages forming the trace
	 * @return
	 */
	private Map<PageId, Integer> generatePinsPerPages(
			List<PageReference> pageReferences) {
		Map<PageId, Integer> pinsPerPages = new HashMap<PageId, Integer>();
		
		for (PageReference pageReference : pageReferences) {
			pinsPerPages.put(pageReference.getPageId(), new Integer(0));
		}
		return pinsPerPages;
	}
	
	/**
	 * generate a trace composed a sequence of randomly selected "pure" traces
	 * @param numberOfTracesToConcat
	 * @param pagesBySingleTrace
	 * @return
	 */
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

	/**
	 * generate a trace of a selected type (file scan, index scan, etc)
	 * passed as parameter
	 * 
	 * @param type
	 * @param pagesBySingleTrace
	 * @return
	 */
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

	/**
	 * generate a file scan trace
	 * 
	 * @param pages number of pages 
	 * @return
	 */
	protected PageReferenceTrace createTracesFileScan(int pages) {
		PageReferenceTrace trace = new PageReferenceTrace();
		addScanFileScan(getRandomTable(), 0, pages, trace);
		return trace;
	}

	/**
	 * generate a block nested loop join trace
	 * 
	 * @param pages number of pages 
	 * @return
	 */
	protected PageReferenceTrace createTracesBNLJ(int firstCount,
			int secondCount, int blocks) {
		PageReferenceTrace trace = new PageReferenceTrace();
		for (int f = 0; f < firstCount; f++) {
			int block = Math.min(blocks, firstCount - f);
			addScanBNLJ(getRandomTable(), f, f + block, PageReferenceType.REQUEST, trace);
			addScanFileScan("B", 0, secondCount, trace);
			addScanBNLJ(getRandomTable(), f, f + block, PageReferenceType.RELEASE, trace);
		}
		return trace;
	}

	/**
	 * generate an clustered index scan trace
	 * 
	 * @param pages number of pages 
	 * @return
	 */
	protected PageReferenceTrace createTraceScanClustered(int indexLevels,
			int resultSize) {
		PageReferenceTrace trace = new PageReferenceTrace();
		addScanFileScan(getRandomTable() + "_index", 0, indexLevels, trace);
		addScanFileScan(getRandomTable(), 0, resultSize, trace);
		return trace;
	}

	/**
	 * generate an unclustered index scan trace
	 * 
	 * @param pages number of pages 
	 * @return
	 */
	protected PageReferenceTrace createTraceScanUnclustered(int indexLevels,
			int resultSize, int resultPages) {
		Random random = new Random(314);
		PageReferenceTrace trace = new PageReferenceTrace();
		addScanFileScan(getRandomTable() + "_index", 0, indexLevels, trace);
		for (int i = 0; i < resultSize; i++) {
			int page = random.nextInt(resultPages);
			trace.addPageReference(new PageReference(new PageId(page,
					new TableId(getRandomTable())), PageReferenceType.REQUEST));
			trace.addPageReference(new PageReference(new PageId(page,
					new TableId(getRandomTable())), PageReferenceType.RELEASE));
		}
		return trace;
	}


	/**
	 * add a file scan
	 * 
	 * @param table
	 * @param from
	 * @param to
	 * @param trace
	 */
	static void addScanFileScan(String table, int from, int to,
			PageReferenceTrace trace) {
		for (int page = from; page < to; page++) {
			trace.addPageReference(new PageReference(new PageId(page,
					new TableId(table)), PageReferenceType.REQUEST));
			trace.addPageReference(new PageReference(new PageId(page,
					new TableId(table)), PageReferenceType.RELEASE));
		}
	}

	/**
	 * add a block nester loop join trace
	 * 
	 * @param table
	 * @param from
	 * @param to
	 * @param type
	 * @param trace
	 */
	static void addScanBNLJ(String table, int from, int to,
			PageReferenceType type, PageReferenceTrace trace) {
		for (int page = from; page < to; page++) {
			trace.addPageReference(new PageReference(new PageId(page,
					new TableId(table)), type));
		}

	}

	/**
	 * generate a random table name
	 * @return
	 */
	private String getRandomTable() {
		Random rand = new Random(5);
		
		int character = rand.nextInt(5);
		
		return tables[character];
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
