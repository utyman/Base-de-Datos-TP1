package ubadb.bench.tests;

import java.util.Random;

import ubadb.apps.bufferManagement.PageReference;
import ubadb.apps.bufferManagement.PageReferenceTrace;
import ubadb.apps.bufferManagement.PageReferenceType;
import ubadb.common.PageId;
import ubadb.common.TableId;

public class IndexScanUnclusteredTest extends Test {
	private final int indexLevels, resultPages, resultSize;

	public IndexScanUnclusteredTest(int bufferPoolSize, int indexLevels,
			int resultPages, int resultSize, int repeats) {
		super("Index Scan Unclustered(buffer: " + bufferPoolSize + ",index: "
				+ indexLevels + ", pages: " + resultPages + ", size:"
				+ resultSize + ", repeats: " + repeats + ")", bufferPoolSize,
				repeats);
		this.indexLevels = indexLevels;
		this.resultPages = resultPages;
		this.resultSize = resultSize;
	}

	@Override
	protected PageReferenceTrace createTraces() {
		Random random = new Random(314);
		PageReferenceTrace trace = new PageReferenceTrace();
		FileScanTest.addScan("A_index", 0, indexLevels, trace);
		for (int i = 0; i < resultSize; i++) {
			int page = random.nextInt(resultPages);
			trace.addPageReference(new PageReference(new PageId(page,
					new TableId("A")), PageReferenceType.REQUEST));
			trace.addPageReference(new PageReference(new PageId(page,
					new TableId("A")), PageReferenceType.RELEASE));
		}
		return trace;
	}

}