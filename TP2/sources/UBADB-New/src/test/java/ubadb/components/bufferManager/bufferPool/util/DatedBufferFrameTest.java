package ubadb.components.bufferManager.bufferPool.util;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ubadb.mocks.MockObjectFactory;
import ubadb.util.TestUtil;

public class DatedBufferFrameTest
{
	@Test
	public void testCreationDate() throws Exception
	{
		DatedBufferFrame bufferFrame0 = new DatedBufferFrame(MockObjectFactory.PAGE, false);
		DatedBufferFrame bufferFrame1 = new DatedBufferFrame(MockObjectFactory.PAGE, false);

		bufferFrame0.pin();
		Thread.sleep(TestUtil.PAUSE_INTERVAL); //Sleep to guarantee that the second frame is created some time after the first one
		bufferFrame1.pin();
		
		assertTrue(bufferFrame0.getLastUsed().before(bufferFrame1.getLastUsed()));
	}
}
