package ubadb.bench.tests;

import ubadb.apps.bufferManagement.DiskManagerFaultCounterMock;
import ubadb.apps.bufferManagement.PageReference;
import ubadb.apps.bufferManagement.PageReferenceTrace;
import ubadb.bench.Strategy;
import ubadb.bench.util.BufferManagerCountImpl;
import ubadb.components.bufferManager.BufferManager;
import ubadb.components.bufferManager.bufferPool.BufferPool;
import ubadb.components.bufferManager.bufferPool.SingleBufferPool;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy;

public class Test {

	private static final long PAUSE_BETWEEN_REFERENCES = 2;

	private final String name;
	private final PageReferenceTrace trace;

	public Test(String name, PageReferenceTrace trace) {
		super();
		this.name = name;
		this.trace = trace;
	}

	public String getName() {
		return name;
	}

	public final TestResult test(Strategy strategy, int bufferPoolSize) {
		PageReplacementStrategy replacementStrategy = strategy
				.createStrategy(trace);
		DiskManagerFaultCounterMock diskManager = new DiskManagerFaultCounterMock();
		BufferPool bufferPool = new SingleBufferPool(bufferPoolSize,
				replacementStrategy);
		BufferManagerCountImpl bufferManager = new BufferManagerCountImpl(
				diskManager, bufferPool);

		try {
			simulateTraces(bufferManager, trace, strategy);
			int totalReads = bufferManager.getReadsCount();
			int diskReads = diskManager.getFaultsCount();
			return new TestResult(totalReads, diskReads);
		} catch (RuntimeException e) {
			return new TestResult(e);
		}

	}

	private void simulateTraces(BufferManager bufferManager,
			PageReferenceTrace trace, Strategy strategy) {
		try {
			for (PageReference pageReference : trace.getPageReferences()) {
				switch (pageReference.getType()) {
				case REQUEST: {
					bufferManager.readPage(pageReference.getPageId());
					break;
				}
				case RELEASE: {
					bufferManager.releasePage(pageReference.getPageId());
					break;
				}
				}
				strategy.executedRequest();
				Thread.sleep(PAUSE_BETWEEN_REFERENCES);
			}
		} catch (Exception e) {
			throw new RuntimeException("Error running test: " + e.getMessage(),
					e);
		}

	}

	public PageReferenceTrace getTrace() {
		return trace;
	}

}
