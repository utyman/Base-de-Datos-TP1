package ubadb.components.bufferManager.bufferPool.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import ubadb.mocks.MockObjectFactory;
import ubadb.util.TestUtil;

public class DatedBufferFrameTest {
	@Test
	public void testCreationDate() throws Exception {
		DatedBufferFrame bufferFrame0 = new DatedBufferFrame(
				MockObjectFactory.PAGE, false);
		DatedBufferFrame bufferFrame1 = new DatedBufferFrame(
				MockObjectFactory.PAGE_1, false);

		bufferFrame0.pin();
		Thread.sleep(TestUtil.PAUSE_INTERVAL); // Sleep to guarantee that the
												// second frame is created some
												// time after the first one
		bufferFrame1.pin();

		assertTrue(bufferFrame0.getLastUsed()
				.before(bufferFrame1.getLastUsed()));
	}

	@Test
	public void testUnpinNoChange() throws Exception {
		DatedBufferFrame bufferFrame0 = new DatedBufferFrame(
				MockObjectFactory.PAGE, false);

		bufferFrame0.pin();
		Date date = bufferFrame0.getLastUsed();
		Thread.sleep(TestUtil.PAUSE_INTERVAL); // Sleep to guarantee that the
												// second frame is created some
												// time after the first one
		bufferFrame0.unpin();

		assertEquals(date, bufferFrame0.getLastUsed());
	}

	@Test
	public void testUnpinChange() throws Exception {
		DatedBufferFrame bufferFrame0 = new DatedBufferFrame(
				MockObjectFactory.PAGE, true);

		bufferFrame0.pin();
		Date date = bufferFrame0.getLastUsed();
		Thread.sleep(TestUtil.PAUSE_INTERVAL); // Sleep to guarantee that the
												// second frame is created some
												// time after the first one
		bufferFrame0.unpin();

		assertTrue(date.before(bufferFrame0.getLastUsed()));
	}

}
