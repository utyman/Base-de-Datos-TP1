package ubadb.bench.tests;

import ubadb.apps.bufferManagement.PageReferenceTrace;

public class ConcatTest extends Test {

	private final int pages;

	private final int numberOfTracesToConcat;

	public ConcatTest(int numberOfTracesToConcat, int bufferSize, int pages,
			int repeats) {
		super("File Scan (buffer: " + bufferSize + ",pages: " + pages
				+ ", repeats:" + repeats + ")", bufferSize, repeats);
		this.pages = pages;
		this.numberOfTracesToConcat = numberOfTracesToConcat;
	}

	@Override
	protected PageReferenceTrace createTraces() {
		TraceUtil util = new TraceUtil();
		PageReferenceTrace trace = util.generateRandomTrace(
				numberOfTracesToConcat, pages);
		return trace;
	}

}
