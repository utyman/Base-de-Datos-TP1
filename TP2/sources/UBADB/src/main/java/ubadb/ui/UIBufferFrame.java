package ubadb.ui;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import ubadb.components.bufferManager.bufferPool.BufferFrame;

@SuppressWarnings("serial")
public class UIBufferFrame extends JPanel
{
	public UIBufferFrame(BufferFrame bufferFrame)
	{
		initialize();
		
		if(bufferFrame.isDirty())
			this.setBackground(Color.RED);
		else
			this.setBackground(Color.GREEN);
		
		JLabel lblPinCount = new JLabel("Pin count: " + bufferFrame.getPinCount());
		
		JLabel lblPageNumber = new JLabel("Page number: " + bufferFrame.getPage().getPageId().getNumber());
		
		JLabel lblTableName = new JLabel("Table name: " + bufferFrame.getPage().getPageId().getTableId().getRelativeFilePath());
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(lblPinCount);
		add(lblPageNumber);
		add(lblTableName);
	}

	private void initialize()
	{
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	}
}
