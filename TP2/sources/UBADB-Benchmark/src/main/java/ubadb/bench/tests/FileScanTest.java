package ubadb.bench.tests;

import ubadb.apps.bufferManagement.PageReference;
import ubadb.apps.bufferManagement.PageReferenceTrace;
import ubadb.apps.bufferManagement.PageReferenceType;
import ubadb.common.PageId;
import ubadb.common.TableId;

public class FileScanTest extends Test {

	private final int pages;

	public FileScanTest(int bufferSize, int pages, int repeats) {
		super("File Scan (buffer: " + bufferSize + ",pages: " + pages
				+ ", repeats:" + repeats + ")", bufferSize, repeats);
		this.pages = pages;
	}

	@Override
	protected PageReferenceTrace createTraces() {
		PageReferenceTrace trace = new PageReferenceTrace();
		addScan("A", 0, pages, trace);
		return trace;
	}

	static void addScan(String table, int from, int to, PageReferenceTrace trace) {
		for (int page = from; page < to; page++) {
			trace.addPageReference(new PageReference(new PageId(page,
					new TableId(table)), PageReferenceType.REQUEST));
			trace.addPageReference(new PageReference(new PageId(page,
					new TableId(table)), PageReferenceType.RELEASE));
		}
	}
}