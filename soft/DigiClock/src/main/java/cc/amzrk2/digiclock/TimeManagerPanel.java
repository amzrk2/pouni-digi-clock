import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

// 资料：https://blog.csdn.net/qq_34908844/article/details/80054703

public class TimeManagerPanel extends JPanel
{
	private TimeManager timeManager;
	public LinkedList<ClockPanel> clockList;
	public LinkedList<CountPanel> countList;
	public LinkedList<ClockPanel> lastClockList = null;
	public LinkedList<CountPanel> lastCountList = null;

	// public JCheckBox enableClockCheckBox;
	// public JCheckBox enableCountCheckBox;
	public JButton addClockButton;
	public JButton addCountButton;
	public JPanel clockRootPane;
	public JPanel countRootPane;
	public JScrollPane clockScrollPane;
	public JScrollPane countScrollPane;

	public TimeManagerPanel(TimeManager timeManager)
	{
		super();

		GridBagLayout layout;
		GridBagConstraints constraints;

		this.setSize(600, 500);
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		this.setLayout(layout);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 0.0;

		this.timeManager = timeManager;
		this.clockList = new LinkedList<>();
		this.countList = new LinkedList<>();
		// this.enableClockCheckBox = new JCheckBox("全局闹钟开关已关闭");
		// this.enableCountCheckBox = new JCheckBox("全局倒计时开关已关闭");
		this.addClockButton = new JButton("新建闹钟");
		this.addCountButton = new JButton("新建倒计时");
		this.clockRootPane = new JPanel(new FlowLayout());
		this.countRootPane = new JPanel(new FlowLayout());
		this.clockRootPane.setSize(300, 800);
		this.countRootPane.setSize(300, 800);
		this.clockScrollPane = new JScrollPane(this.clockRootPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS)
		{
			@Override
			public Dimension getPreferredSize()
			{
				// 强制大小。
				return new Dimension(300, 350);
			}
		};
		this.countScrollPane = new JScrollPane(this.countRootPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS)
		{
			@Override
			public Dimension getPreferredSize()
			{
				// 强制大小。
				return new Dimension(300, 350);
			}
		};

		this.addClockButton.addActionListener(
				e -> DialogManager.showClockDialog(this, this.timeManager)
		);
		this.addCountButton.addActionListener(
				e -> DialogManager.showCountDialog(this, this.timeManager)
		);

		//		// enableClockCheckBox
		//		constraints.gridheight = 1;
		//		constraints.gridwidth = 4;
		//		constraints.gridx = 0;
		//		constraints.gridy = 0;
		//		layout.setConstraints(this.enableClockCheckBox, constraints);
		//		this.add(this.enableClockCheckBox);
		//		// enableCountCheckBox
		//		constraints.gridheight = 1;
		//		constraints.gridwidth = 4;
		//		constraints.gridx = 4;
		//		constraints.gridy = 0;
		//		layout.setConstraints(this.enableCountCheckBox, constraints);
		//		this.add(this.enableCountCheckBox);
		// addClockButton
		constraints.gridheight = 1;
		constraints.gridwidth = 4;
		constraints.gridx = 0;
		constraints.gridy = 1;
		layout.setConstraints(this.addClockButton, constraints);
		this.add(this.addClockButton);
		// addCountButton
		constraints.gridheight = 1;
		constraints.gridwidth = 4;
		constraints.gridx = 4;
		constraints.gridy = 1;
		layout.setConstraints(this.addCountButton, constraints);
		this.add(this.addCountButton);
		// clockListPane
		constraints.gridheight = 4;
		constraints.gridwidth = 4;
		constraints.gridx = 0;
		constraints.gridy = 3;
		layout.setConstraints(this.clockScrollPane, constraints);
		this.add(this.clockScrollPane);
		// countListPane
		constraints.gridheight = 4;
		constraints.gridwidth = 4;
		constraints.gridx = 4;
		constraints.gridy = 3;
		layout.setConstraints(this.countScrollPane, constraints);
		this.add(this.countScrollPane);
	}

	public synchronized void repaintClockPane()
	{
		GridBagLayout layout;
		GridBagConstraints constraints;
		if (!this.clockList.equals(this.lastClockList))
		{
			layout = new GridBagLayout();
			constraints = new GridBagConstraints();
			this.clockRootPane.setLayout(layout);
			constraints.fill = GridBagConstraints.BOTH;
			constraints.weightx = 0.0;
			constraints.gridheight = 1;
			constraints.gridwidth = GridBagConstraints.REMAINDER;
			constraints.gridx = 0;

			this.clockRootPane.removeAll();
			var i = 0;
			for (var item : this.clockList)
			{
				constraints.gridy = i;
				layout.setConstraints(item, constraints);
				this.clockRootPane.add(item);
				i++;
			}
			// this.clockRootPane.revalidate();
			this.clockRootPane.repaint();
			this.lastClockList = (LinkedList<ClockPanel>) this.clockList.clone();
		}

	}

	public synchronized void repaintCountPane()
	{
		GridBagLayout layout;
		GridBagConstraints constraints;

		if (!this.countList.equals(this.lastCountList))
		{
			layout = new GridBagLayout();
			constraints = new GridBagConstraints();
			this.countRootPane.setLayout(layout);
			constraints.fill = GridBagConstraints.BOTH;
			constraints.weightx = 0.0;
			constraints.gridheight = 1;
			constraints.gridwidth = GridBagConstraints.REMAINDER;
			constraints.gridx = 0;

			this.countRootPane.removeAll();
			var i = 0;
			for (var item : this.countList)
			{
				constraints.gridy = i;
				layout.setConstraints(item, constraints);
				this.countRootPane.add(item);
				i++;
			}
			// this.countRootPane.revalidate();
			this.clockRootPane.repaint();
			this.lastCountList = (LinkedList<CountPanel>) this.countList.clone();
		}
	}
}