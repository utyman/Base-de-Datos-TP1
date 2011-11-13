package ubadb.bench.tests;

import java.util.Random;

import ubadb.apps.bufferManagement.PageReference;
import ubadb.apps.bufferManagement.PageReferenceTrace;
import ubadb.apps.bufferManagement.PageReferenceType;
import ubadb.common.PageId;
import ubadb.common.TableId;

public class IndexScanClusteredTest extends Test {

	private final int indexLevels, resultSize;

	public IndexScanClusteredTest(int bufferSize, int indexLevels,
			int resultSize, int repeats) {
		super("Index Scan Clustered (buffer:" + bufferSize + ", levels: "
				+ indexLevels + ", results:" + resultSize + ", repeats: "
				+ repeats + ")", bufferSize, repeats);
		this.indexLevels = indexLevels;
		this.resultSize = resultSize;
	}

	@Override
	protected PageReferenceTrace createTraces() {
		return (new TraceUtil()).createTraceScanClustered(indexLevels, resultSize);
	}
}