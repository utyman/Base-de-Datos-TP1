package ubadb.bench.tests;

import ubadb.apps.bufferManagement.PageReference;
import ubadb.apps.bufferManagement.PageReferenceTrace;
import ubadb.apps.bufferManagement.PageReferenceType;
import ubadb.common.PageId;
import ubadb.common.TableId;

public class BNLJTest extends Test {

	private final int firstCount, secondCount, blocks;

	public BNLJTest(int bufferPoolSize, int firstCount, int secondCount,
			int blocks, int repeats) {
		super("BNLJ (buffer: " + bufferPoolSize + ", firstSize: " + firstCount
				+ ", secondSize: " + secondCount + ", block: " + blocks
				+ ", repeats: " + repeats + ")", bufferPoolSize, repeats);
		this.firstCount = firstCount;
		this.secondCount = secondCount;
		this.blocks = blocks;
	}

	@Override
	protected PageReferenceTrace createTraces() {
		return (new TraceUtil()).createTracesBNLJ(firstCount, secondCount, blocks);
	}

}