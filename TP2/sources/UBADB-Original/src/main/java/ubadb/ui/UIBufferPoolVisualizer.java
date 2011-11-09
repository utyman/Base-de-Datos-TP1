package ubadb.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.Timer;

import ubadb.Database;

public class UIBufferPoolVisualizer
{
	private static final int	TIMER_DELAY_IN_MILLISECS	= 1000;
	private JFrame	frame;
	private Database database;

	public static void visualize(Database database)
	{
		UIBufferPoolVisualizer ui = new UIBufferPoolVisualizer(database);
		ui.frame.setVisible(true);
	}
	
	public UIBufferPoolVisualizer(Database database)
	{
		initialize();

		this.database = database;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		ActionListener taskToPerform = new ActionListener() {
		      public void actionPerformed(ActionEvent evt)
		      {
		    	  try
		    	  {
		    		  drawBufferFrames(database);
		    	  }
		    	  catch(Exception e)
		    	  {
		    		  System.out.println("Cannot draw buffer pool");
		    		  e.printStackTrace();
		    	  }
		      }
		  };
		  Timer timer = new Timer(TIMER_DELAY_IN_MILLISECS, taskToPerform);
		  timer.start();
	}

	private void drawBufferFrames(Database database) throws IllegalAccessException, NoSuchFieldException
	{
//		BufferPool bufferPool = (BufferPool) BufferManagerImpl.class.getField("bufferPool").get(database.getBufferManager());
//
//		for(BufferFrame bufferFrame : bufferPool.getBufferFrames())
//		{
//			UIBufferFrame uiBufferFrame = new UIBufferFrame(bufferFrame);
//			this.frame.getContentPane().add(uiBufferFrame);
//		}
	}
}
