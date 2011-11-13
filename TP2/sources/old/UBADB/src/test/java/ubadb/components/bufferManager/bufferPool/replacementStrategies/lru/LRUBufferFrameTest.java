package ubadb.components.bufferManager.bufferPool.replacementStrategies.lru;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ubadb.mocks.MockObjectFactory;
import ubadb.util.TestUtil;

public class LRUBufferFrameTest
{
	@Test
	public void testCreationDate() throws Exception
	{
		LRUBufferFrame bufferFrame0 = new LRUBufferFrame(MockObjectFactory.PAGE);
		LRUBufferFrame bufferFrame1 = new LRUBufferFrame(MockObjectFactory.PAGE);

		bufferFrame0.pin();
		Thread.sleep(TestUtil.PAUSE_INTERVAL); //Sleep to guarantee that the second frame is created some time after the first one
		bufferFrame1.pin();
		
		assertTrue(bufferFrame0.getLastUsed().before(bufferFrame1.getLastUsed()));
	}
}
