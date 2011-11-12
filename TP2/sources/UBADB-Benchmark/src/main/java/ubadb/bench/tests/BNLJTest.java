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
		PageReferenceTrace trace = new PageReferenceTrace();
		for (int f = 0; f < firstCount; f++) {
			int block = Math.min(blocks, firstCount - f);
			addScan("A", f, f + block, PageReferenceType.REQUEST, trace);
			FileScanTest.addScan("B", 0, secondCount, trace);
			addScan("A", f, f + block, PageReferenceType.RELEASE, trace);
		}
		return trace;
	}

	static void addScan(String table, int from, int to, PageReferenceType type,
			PageReferenceTrace trace) {
		for (int page = from; page < to; page++) {
			trace.addPageReference(new PageReference(new PageId(page,
					new TableId(table)), type));
		}
	}

}