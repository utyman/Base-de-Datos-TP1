package ubadb.bench.tests;

import ubadb.apps.bufferManagement.PageReferenceTrace;

public class FileScanTest extends Test {

	private final int pages;

	public FileScanTest(int bufferSize, int pages, int repeats) {
		super("File Scan (buffer: " + bufferSize + ",pages: " + pages
				+ ", repeats:" + repeats + ")", bufferSize, repeats);
		this.pages = pages;
	}

	@Override
	protected PageReferenceTrace createTraces() {
		return new TraceUtil().createTracesFileScan(pages);

	}

}