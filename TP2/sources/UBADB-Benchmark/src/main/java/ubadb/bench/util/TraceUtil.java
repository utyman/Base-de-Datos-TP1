package ubadb.bench.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import ubadb.apps.bufferManagement.PageReference;
import ubadb.apps.bufferManagement.PageReferenceTrace;
import ubadb.apps.bufferManagement.PageReferenceType;
import ubadb.common.PageId;
import ubadb.common.TableId;

public class TraceUtil {

	private static final String[] TABLES = { "A", "B" };

	private static enum TRACE_TYPE {
		FILE_SCAN, INDEX_CLUSTERED_SCAN, INDEX_UNCLUSTERED_SCAN, BNLJ
	}

	private static Random RANDOM = new Random(31415926);

	/**
	 * calculate the max number of different pages. Useful in order to calculate
	 * the relevant max for the buffer size.
	 * 
	 * @param traces
	 * @return
	 */
	public static int calculateNumberOfDifferentPages(PageReferenceTrace trace) {
		Set<PageId> ids = new HashSet<PageId>();
		for (PageReference reference : trace.getPageReferences()) {
			ids.add(reference.getPageId());
		}
		return ids.size();

	}

	/**
	 * calculate the max number of page simultaneously pinned throughout the
	 * trace evaluation
	 * 
	 * @param trace
	 * @return
	 */
	public static int calculateMaxPinned(PageReferenceTrace trace) {
		List<PageReference> pageReferences = trace.getPageReferences();
		Map<PageId, Integer> pinsPerPages = generatePinsPerPages(pageReferences);
		int currentlyPinnedPages = 0; // variable to store the currently pinned
										// pp throughout the the trace iteration
		int maxConcurrentlyPinnedPages = 0; // variable to store the temporary
											// max

		for (PageReference pageReference : pageReferences) {
			if (pageReference.getType().equals(PageReferenceType.REQUEST)) {
				pinsPerPages.put(pageReference.getPageId(),
						pinsPerPages.get(pageReference.getPageId()) + 1);
				// in case the page had not been previously pinned, then we
				// update the currentlyPinnedPages variable
				if (pinsPerPages.get(pageReference.getPageId()) == 1) {
					currentlyPinnedPages++;
					if (currentlyPinnedPages > maxConcurrentlyPinnedPages) {
						maxConcurrentlyPinnedPages = currentlyPinnedPages;
					}
				}
			} else {
				pinsPerPages.put(pageReference.getPageId(),
						pinsPerPages.get(pageReference.getPageId()) - 1);
				// in case every pin has been released, we update the
				// currentlyPinnedPages variable.
				if (pinsPerPages.get(pageReference.getPageId()) == 0) {
					currentlyPinnedPages--;
				}

			}
		}
		return maxConcurrentlyPinnedPages;
	}

	/**
	 * generate a map from a page id to the number of pins inflicted on the
	 * correspondent page.
	 * 
	 * @param pageReferences
	 *            the list of the pages forming the trace
	 * @return
	 */
	private static Map<PageId, Integer> generatePinsPerPages(
			List<PageReference> pageReferences) {
		Map<PageId, Integer> pinsPerPages = new HashMap<PageId, Integer>();

		for (PageReference pageReference : pageReferences) {
			pinsPerPages.put(pageReference.getPageId(), new Integer(0));
		}
		return pinsPerPages;
	}

	/**
	 * generate a trace composed a sequence of randomly selected "pure" traces
	 * 
	 * @param numberOfTracesToConcat
	 * @param pagesBySingleTrace
	 * @return
	 */
	public static PageReferenceTrace generateRandomTrace(
			int numberOfTracesToConcat, int pagesBySingleTrace) {
		// generator used to create all-concatenated different kinds of traces
		PageReferenceTrace result = new PageReferenceTrace();
		for (int i = 0; i < numberOfTracesToConcat; i++) {
			result.concatenate(obtainRandomTrace(pagesBySingleTrace));
		}

		return result;
	}

	/**
	 * generate a trace of a selected type (file scan, index scan, etc) passed
	 * as parameter
	 * 
	 * @param type
	 * @param pages
	 * @return
	 */
	private static PageReferenceTrace obtainRandomTrace(int pages) {
		TRACE_TYPE[] types = TRACE_TYPE.values();
		TRACE_TYPE type = types[RANDOM.nextInt(types.length)];
		int secondaryPages = RANDOM.nextInt(pages / 3) + pages / 3;
		switch (type) {
		case FILE_SCAN:
			return createTracesFileScan(pages);
		case INDEX_CLUSTERED_SCAN:
			return createTraceScanClustered(3, pages);
		case INDEX_UNCLUSTERED_SCAN:
			return createTraceScanUnclustered(3, pages, secondaryPages);
		case BNLJ:
			return createTracesBNLJ(secondaryPages, pages, 5);
		}
		return null;
	}

	/**
	 * generate a file scan trace
	 * 
	 * @param pages
	 *            number of pages
	 * @return
	 */
	private static PageReferenceTrace createTracesFileScan(int pages) {
		PageReferenceTrace trace = new PageReferenceTrace();
		addScanFileScan(getRandomTable(), 0, pages, trace);
		return trace;
	}

	/**
	 * generate a block nested loop join trace
	 * 
	 * @param pages
	 *            number of pages
	 * @return
	 */
	private static PageReferenceTrace createTracesBNLJ(int firstCount,
			int secondCount, int blocks) {
		PageReferenceTrace trace = new PageReferenceTrace();
		String tableA = getRandomTable();
		String tableB = getRandomTable(tableA);
		for (int f = 0; f < firstCount; f++) {
			int block = Math.min(blocks, firstCount - f);
			addScanBNLJ(tableA, f, f + block, PageReferenceType.REQUEST, trace);
			addScanFileScan(tableB, 0, secondCount, trace);
			addScanBNLJ(tableA, f, f + block, PageReferenceType.RELEASE, trace);
		}
		return trace;
	}

	/**
	 * generate an clustered index scan trace
	 * 
	 * @param pages
	 *            number of pages
	 * @return
	 */
	private static PageReferenceTrace createTraceScanClustered(int indexLevels,
			int resultSize) {
		PageReferenceTrace trace = new PageReferenceTrace();
		String table = getRandomTable();
		addScanFileScan(table + "_index", 0, indexLevels, trace);
		addScanFileScan(table, 0, resultSize, trace);
		return trace;
	}

	/**
	 * generate an unclustered index scan trace
	 * 
	 * @param pages
	 *            number of pages
	 * @return
	 */
	private static PageReferenceTrace createTraceScanUnclustered(
			int indexLevels, int resultSize, int resultPages) {
		PageReferenceTrace trace = new PageReferenceTrace();
		String table = getRandomTable();
		addScanFileScan(table + "_index", 0, indexLevels, trace);
		for (int i = 0; i < resultSize; i++) {
			int page = RANDOM.nextInt(resultPages);
			trace.addPageReference(new PageReference(new PageId(page,
					new TableId(table)), PageReferenceType.REQUEST));
			trace.addPageReference(new PageReference(new PageId(page,
					new TableId(table)), PageReferenceType.RELEASE));
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
	private static void addScanFileScan(String table, int from, int to,
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
	private static void addScanBNLJ(String table, int from, int to,
			PageReferenceType type, PageReferenceTrace trace) {
		for (int page = from; page < to; page++) {
			trace.addPageReference(new PageReference(new PageId(page,
					new TableId(table)), type));
		}

	}

	/**
	 * generate a random table name
	 * 
	 * @return
	 */
	private static String getRandomTable(String... excludes) {
		Set<String> available = new TreeSet<String>();
		for (String t : TABLES) {
			boolean exclude = false;
			for (String e : excludes) {
				if (t.equals(e)) {
					exclude = true;
					break;
				}
			}
			if (!exclude) {
				available.add(t);
			}
		}
		String[] tables = new ArrayList<String>(available)
				.toArray(new String[0]);
		int index = RANDOM.nextInt(tables.length);
		return tables[index];
	}

	public static void main(String[] args) {
		try {
			System.out.println(createTracesBNLJ(7, 2, 3));
			// PageReferenceTraceGenerator gen = new
			// PageReferenceTraceGenerator();
			// System.out.println(gen.generateFileScan("A",0,5));
			// System.out.println(gen.generateIndexScanClustered("A", 3, 3, 3));
			// System.out.println(gen.generateIndexScanUnclustered("A", 3, 3,
			// 10));
			// System.out.println(gen.generateBNLJ("A", 7, "B", 4, 3));
			// System.out.println("max: "
			// + calculateMaxPinned(gen.generateBNLJ("A", 7, "B", 4, 3)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
