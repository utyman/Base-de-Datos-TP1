package ubadb.components.bufferManager.bufferPool.replacementStrategies.lesser;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import ubadb.components.bufferManager.bufferPool.BufferFrame;
import ubadb.exceptions.PageReplacementStrategyException;
import ubadb.mocks.MockObjectFactory;

public class LesserReplacementStrategyTest {
	private LesserReplacementStrategy strategy;

	@Before
	public void setUp() {
		strategy = new LesserReplacementStrategy();
	}

	@Test
	public void testReplace() throws Exception {
		BufferFrame frame0 = strategy.createNewFrame(MockObjectFactory.PAGE);
		BufferFrame frame1 = strategy.createNewFrame(MockObjectFactory.PAGE_1);
		BufferFrame frame2 = strategy.createNewFrame(MockObjectFactory.PAGE_2);

		frame0.pin();
		frame0.unpin();
		frame1.pin();
		frame1.unpin();
		
		assertEquals(frame0,
				strategy.findVictim(Arrays.asList(frame1, frame0, frame2)));

	}

	@Test(expected=PageReplacementStrategyException.class)
	public void testNoPageToReplace() throws Exception {
		BufferFrame frame0 = strategy.createNewFrame(MockObjectFactory.PAGE);
		BufferFrame frame1 = strategy.createNewFrame(MockObjectFactory.PAGE_1);

		frame0.pin();
		frame1.pin();

		strategy.findVictim(Arrays.asList(frame0, frame1));
	}
}
