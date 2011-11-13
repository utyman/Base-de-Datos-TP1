package ubadb.components.bufferManager.bufferPool.replacementStrategies.fifo;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ubadb.mocks.MockObjectFactory;
import ubadb.util.TestUtil;

public class FIFOBufferFrameTest
{
	@Test
	public void testCreationDate() throws Exception
	{
		FIFOBufferFrame bufferFrame0 = new FIFOBufferFrame(MockObjectFactory.PAGE);
		Thread.sleep(TestUtil.PAUSE_INTERVAL); //Sleep to guarantee that the second frame is created some time after the first one
		FIFOBufferFrame bufferFrame1 = new FIFOBufferFrame(MockObjectFactory.PAGE);
		
		assertTrue(bufferFrame0.getCreationDate().before(bufferFrame1.getCreationDate()));
	}
}
