package ubadb.bench.tests;

import ubadb.apps.bufferManagement.PageReferenceTrace;

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
		return new TraceUtil().createTraceScanUnclustered(indexLevels,
				resultSize, resultPages);
	}

}