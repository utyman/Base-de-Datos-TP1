package ubadb.components.bufferManager.bufferPool.replacementStrategies.mru;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ubadb.mocks.MockObjectFactory;
import ubadb.util.TestUtil;

public class MRUBufferFrameTest
{
	@Test
	public void testCreationDate() throws Exception
	{
		MRUBufferFrame bufferFrame0 = new MRUBufferFrame(MockObjectFactory.PAGE);
		MRUBufferFrame bufferFrame1 = new MRUBufferFrame(MockObjectFactory.PAGE);

		bufferFrame0.pin();
		Thread.sleep(TestUtil.PAUSE_INTERVAL); //Sleep to guarantee that the second frame is created some time after the first one
		bufferFrame1.pin();
		
		assertTrue(bufferFrame0.getLastUsed().before(bufferFrame1.getLastUsed()));
	}
}
