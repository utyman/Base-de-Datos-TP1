package ubadb.components.bufferManager.bufferPool.replacementStrategies.beststrategy;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import ubadb.apps.bufferManagement.PageReference;
import ubadb.apps.bufferManagement.PageReferenceTrace;
import ubadb.apps.bufferManagement.PageReferenceType;
import ubadb.components.bufferManager.bufferPool.BufferFrame;
import ubadb.mocks.MockObjectFactory;

public class BestReplacementStrategyTest {
	private BestReplacementStrategy strategy;

	@Before
	public void setUp()
	{
		PageReferenceTrace trace = new PageReferenceTrace();
			
		trace.addPageReference(new PageReference(MockObjectFactory.PAGE.getPageId(), PageReferenceType.REQUEST));
		trace.addPageReference(new PageReference(MockObjectFactory.PAGE.getPageId(), PageReferenceType.RELEASE));
		trace.addPageReference(new PageReference(MockObjectFactory.PAGE_1.getPageId(), PageReferenceType.REQUEST));
		trace.addPageReference(new PageReference(MockObjectFactory.PAGE_1.getPageId(), PageReferenceType.RELEASE));
			

		strategy = new BestReplacementStrategy(trace);
	}

	@Test
	public void testReplace() throws Exception {
		BufferFrame frame0 = strategy.createNewFrame(MockObjectFactory.PAGE);
		BufferFrame frame1 = strategy.createNewFrame(MockObjectFactory.PAGE_1);
		BufferFrame frame2 = strategy.createNewFrame(MockObjectFactory.PAGE_2);


		assertEquals(frame2,strategy.findVictim(Arrays.asList(frame0,frame1,frame2)));

	}

	@Test
	public void testReplaceBeginning() throws Exception {
		BufferFrame frame0 = strategy.createNewFrame(MockObjectFactory.PAGE);
		BufferFrame frame1 = strategy.createNewFrame(MockObjectFactory.PAGE_1);


		assertEquals(frame1,strategy.findVictim(Arrays.asList(frame0, frame1)));

	}

	@Test
	public void testReplaceAfterOneStep() throws Exception {
		BufferFrame frame0 = strategy.createNewFrame(MockObjectFactory.PAGE);
		BufferFrame frame1 = strategy.createNewFrame(MockObjectFactory.PAGE_1);
		strategy.moveNextPositionInTrace();
		assertEquals(frame1,strategy.findVictim(Arrays.asList(frame0, frame1)));

	}

	@Test
	public void testReplaceAfterTwoStep() throws Exception {
		BufferFrame frame0 = strategy.createNewFrame(MockObjectFactory.PAGE);
		BufferFrame frame1 = strategy.createNewFrame(MockObjectFactory.PAGE_1);
		strategy.moveNextPositionInTrace();
		strategy.moveNextPositionInTrace();
		assertEquals(frame0,strategy.findVictim(Arrays.asList(frame0, frame1)));
	}

}
