package cc.amzrk2.digiclock;
import javax.swing.*;
import java.awt.*;

public class ClockPanel extends JPanel implements Comparable<ClockPanel>
{
	private int hashCode;
	public JLabel content_1;
	public JLabel content_2;
	public JButton deleteButton;
	public JCheckBox enableCheckBox;

	private ClockPanel(int hashCode){this.hashCode = hashCode;}

	public ClockPanel(TimeManager timeManager)
	{
		super();

		GridBagLayout layout;
		GridBagConstraints constraints;

		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		this.setLayout(layout);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 0.0;

		this.content_1 = new JLabel("content1");
		this.content_2 = new JLabel("content2");
		this.deleteButton = new JButton("删除");
		this.enableCheckBox = new JCheckBox("启动", true);

		this.deleteButton.addActionListener(
				event -> timeManager.removeClock(this.hashCode)
		);

		// content_1
		constraints.gridheight = 1;
		constraints.gridwidth = 2;
		constraints.gridx = 0;
		constraints.gridy = 0;
		layout.setConstraints(this.content_1, constraints);
		this.add(this.content_1);
		// deleteButton
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.gridx = 2;
		constraints.gridy = 0;
		layout.setConstraints(this.deleteButton, constraints);
		this.add(this.deleteButton);
		// content_2
		constraints.gridheight = 1;
		constraints.gridwidth = 2;
		constraints.gridx = 0;
		constraints.gridy = 1;
		layout.setConstraints(this.content_2, constraints);
		this.add(this.content_2);
		// enableCheckBox
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.gridx = 2;
		constraints.gridy = 1;
		layout.setConstraints(this.enableCheckBox, constraints);
		this.add(this.enableCheckBox);

		// this.setVisible(true);
	}

	public void setHashCode(int hashCode)
	{
		this.hashCode = hashCode;
	}

	@Override
	public Dimension getPreferredSize()
	{
		// 强制大小。
		return new Dimension(260, 70);
	}

	@Override
	public int compareTo(ClockPanel o)
	{
		return Integer.compare(this.hashCode, o.hashCode);
	}

	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof ClockPanel && this.hashCode == obj.hashCode();
	}

	@Override
	public int hashCode()
	{
		return this.hashCode;
	}

	public static ClockPanel getInstanceCompare(int hashCode)
	{
		return new ClockPanel(hashCode);
	}
}