package ubadb.bench.tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestResult {
	private final int totalReads, diskReads;
	private final Exception error;

	public TestResult(Exception error) {
		super();
		this.error = error;
		this.totalReads = -1;
		this.diskReads = -1;
	}

	public TestResult(int totalReads, int diskReads) {
		super();
		this.totalReads = totalReads;
		this.diskReads = diskReads;
		this.error = null;
	}

	public int getTotalReads() {
		return totalReads;
	}

	public int getDiskReads() {
		return diskReads;
	}

	public Exception getError() {
		return error;
	}

	public double hitRate() {
		return ((double) (totalReads - diskReads)) / ((double) totalReads);
	}

	@Override
	public String toString() {
		if (error == null) {
			return String.format("Hit rate: %.0f%%", hitRate() * 100.0);
		} else {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			error.printStackTrace(new PrintStream(out));
			return "ERROR (" + out.toString() + ")";
		}
	}
}
