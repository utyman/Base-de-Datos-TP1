package ubadb.components.bufferManager.bufferPool.replacementStrategies.fifo;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import ubadb.components.bufferManager.bufferPool.BufferFrame;
import ubadb.exceptions.PageReplacementStrategyException;
import ubadb.mocks.MockObjectFactory;
import ubadb.util.TestUtil;


public class FIFOReplacementStrategyTest
{
	private FIFOReplacementStrategy strategy;
	
	@Before
	public void setUp()
	{
		strategy = new FIFOReplacementStrategy();
	}
	
	@Test(expected=PageReplacementStrategyException.class)
	public void testNoPageToReplace() throws Exception
	{
		BufferFrame frame0 = strategy.createNewFrame(MockObjectFactory.PAGE);
		BufferFrame frame1 = strategy.createNewFrame(MockObjectFactory.PAGE);
		
		frame0.pin();
		frame1.pin();
		
		strategy.findVictim(Arrays.asList(frame0,frame1));
	}
	
	@Test
	public void testOnlyOneToReplace() throws Exception
	{
		BufferFrame frame0 = strategy.createNewFrame(MockObjectFactory.PAGE);
		BufferFrame frame1 = strategy.createNewFrame(MockObjectFactory.PAGE);
		BufferFrame frame2 = strategy.createNewFrame(MockObjectFactory.PAGE);
		
		frame0.pin();
		frame1.pin();
		
		assertEquals(frame2,strategy.findVictim(Arrays.asList(frame0,frame1,frame2)));
	}
	
	@Test
	public void testMultiplePagesToReplace() throws Exception
	{
		BufferFrame frame0 = strategy.createNewFrame(MockObjectFactory.PAGE);
		Thread.sleep(TestUtil.PAUSE_INTERVAL);	//Add a sleep so that frame dates are different
		BufferFrame frame1 = strategy.createNewFrame(MockObjectFactory.PAGE);
		Thread.sleep(TestUtil.PAUSE_INTERVAL);
		BufferFrame frame2 = strategy.createNewFrame(MockObjectFactory.PAGE);
		
		assertEquals(frame0,strategy.findVictim(Arrays.asList(frame0,frame1,frame2)));
	}

	@Test
	public void testMultiplePagesToReplaceButOldestOnePinned() throws Exception
	{
		BufferFrame frame0 = strategy.createNewFrame(MockObjectFactory.PAGE);
		Thread.sleep(TestUtil.PAUSE_INTERVAL);	//Add a sleep so that frame dates are different
		BufferFrame frame1 = strategy.createNewFrame(MockObjectFactory.PAGE);
		Thread.sleep(TestUtil.PAUSE_INTERVAL);
		BufferFrame frame2 = strategy.createNewFrame(MockObjectFactory.PAGE);
		
		frame0.pin();
		
		assertEquals(frame1,strategy.findVictim(Arrays.asList(frame0,frame1,frame2)));
	}
}
