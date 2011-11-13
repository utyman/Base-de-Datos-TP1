package ubadb.components.bufferManager.bufferPool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ubadb.exceptions.BufferFrameException;
import ubadb.mocks.MockObjectFactory;

public class BufferFrameTest {
	@Test
	public void testFrameIsNotDirtyAndWithoutPinsByDefault() {
		BufferFrame bufferFrame = new BufferFrame(MockObjectFactory.PAGE);

		assertFalse(bufferFrame.isDirty());
		assertEquals(0, bufferFrame.getPinCount());
	}

	@Test
	public void testCanBeReplacedTrue() {
		BufferFrame bufferFrame = new BufferFrame(MockObjectFactory.PAGE);

		assertTrue(bufferFrame.canBeReplaced());
	}

	@Test
	public void testCanBeReplacedFalse() {
		BufferFrame bufferFrame = new BufferFrame(MockObjectFactory.PAGE);
		bufferFrame.pin();

		assertFalse(bufferFrame.canBeReplaced());
	}

	@Test
	public void testUnpinPinnedPage() throws Exception {
		BufferFrame bufferFrame = new BufferFrame(MockObjectFactory.PAGE);
		bufferFrame.pin();
		bufferFrame.pin();

		bufferFrame.unpin();
		assertEquals(1, bufferFrame.getPinCount());
	}
	
	public void testReplacePinUnpinnedPage() throws BufferFrameException {
		BufferFrame bufferFrame = new BufferFrame(MockObjectFactory.PAGE);
		bufferFrame.pin();
		bufferFrame.pin();

		bufferFrame.unpin();
		bufferFrame.unpin();
		assertEquals(0, bufferFrame.getPinCount());
		assertTrue(bufferFrame.canBeReplaced());
	}

	@Test(expected = BufferFrameException.class)
	public void testUnpinUnpinnedPage() throws Exception {
		BufferFrame bufferFrame = new BufferFrame(MockObjectFactory.PAGE);

		bufferFrame.unpin();
	}
}
