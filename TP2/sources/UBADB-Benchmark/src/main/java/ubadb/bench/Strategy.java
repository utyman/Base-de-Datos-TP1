package ubadb.bench;

import ubadb.apps.bufferManagement.PageReferenceTrace;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.beststrategy.BestReplacementStrategy;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.count.CountReplacementStrategy;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.fifo.FIFOReplacementStrategy;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.lru.LRUReplacementStrategy;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.mru.MRUReplacementStrategy;
import ubadb.components.bufferManager.bufferPool.replacementStrategies.random.RandomReplacementStrategy;

public class Strategy {

	private final String name;
	private final Class<? extends PageReplacementStrategy> clazz;

	private Strategy(String name, Class<? extends PageReplacementStrategy> clazz) {
		super();
		this.name = name;
		this.clazz = clazz;
	}

	public String getName() {
		return name;
	}

	public PageReplacementStrategy createStrategy(PageReferenceTrace trace) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Error creating strategy object", e);
		}
	}

	public void executedRequest() {

	}

	static final Strategy[] INSTANCES = new Strategy[] {
			//
			new Strategy("FIFO", FIFOReplacementStrategy.class),
			new Strategy("Random", RandomReplacementStrategy.class),
			new Strategy("MRU", MRUReplacementStrategy.class),
			new Strategy("LRU", LRUReplacementStrategy.class),
			new BestStrategy(),
			new Strategy("Count", CountReplacementStrategy.class)
	//
	};

	private static final class BestStrategy extends Strategy {

		private BestReplacementStrategy strategy;

		private BestStrategy() {
			super("Best", BestReplacementStrategy.class);
		}

		@Override
		public PageReplacementStrategy createStrategy(PageReferenceTrace trace) {
			return strategy = new BestReplacementStrategy(trace);
		}

		@Override
		public void executedRequest() {
			strategy.moveNextPositionInTrace();
		}
	}
}