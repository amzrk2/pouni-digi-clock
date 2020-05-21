package cc.amzrk2.digiclock;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

// 资料：https://blog.csdn.net/qq_34908844/article/details/80054703

/**
 * 时间管理总组件。继承自<code>javax.swing.JPanel</code>类。<br/>
 * 包含了闹钟组件和倒计时组件。<br/>
 * 构造需要使用<code>TimeManager.createAndStartNewPanel()</code>方法。
 *
 * @author 8f235831
 * @see TimeManager
 * @see TimeManager#createAndStartNewPanel()
 */
public class TimeManagerPanel extends JPanel
{
	private TimeManager timeManager;// 管理线程类。
	public LinkedList<ClockPanel> clockList;// 闹钟组件。
	public LinkedList<CountPanel> countList;// 倒计时组件。
	public LinkedList<ClockPanel> lastClockList = null;// 闹钟组件备份。
	public LinkedList<CountPanel> lastCountList = null;// 倒计时组件备份。

	// public JCheckBox enableClockCheckBox;
	// public JCheckBox enableCountCheckBox;
	public JButton addClockButton;// 闹钟添加按钮。
	public JButton addCountButton;// 倒计时添加按钮。
	public JPanel clockRootPane;// 闹钟根组件。
	public JPanel countRootPane;// 倒计时根组件。
	public JScrollPane clockScrollPane;// 闹钟滚轴组件。
	public JScrollPane countScrollPane;// 倒计时滚轴组件。

	/**
	 * 构造方法。<br/>
	 * <strong><big>仅供<code>TimeManager</code>类创建！</big></strong><br/>
	 * 需使用<code>TimeManager.createAndStartNewPanel()</code>方法构造。
	 *
	 * @param timeManager 管理线程类。
	 * @see TimeManager#createAndStartNewPanel()
	 */
	public TimeManagerPanel(TimeManager timeManager)
	{
		super();

		GridBagLayout layout;
		GridBagConstraints constraints;

		this.setSize(400, 500);
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
		this.addClockButton.setFont(DigiClock.fontLg);
		this.addCountButton = new JButton("新建倒计时");
		this.addCountButton.setFont(DigiClock.fontLg);
		this.clockRootPane = new JPanel(new FlowLayout());
		this.clockRootPane.setFont(DigiClock.fontSm);
		this.countRootPane = new JPanel(new FlowLayout());
		this.countRootPane.setFont(DigiClock.fontSm);
		this.clockRootPane.setSize(200, 800);
		this.countRootPane.setSize(200, 800);
		this.clockScrollPane = new JScrollPane(this.clockRootPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER)
		{
			@Override
			public Dimension getPreferredSize()
			{
				// 强制大小。
				return new Dimension(200, 350);
			}
		};
		this.countScrollPane = new JScrollPane(this.countRootPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER)
		{
			@Override
			public Dimension getPreferredSize()
			{
				// 强制大小。
				return new Dimension(200, 350);
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

	/**
	 * 重绘闹钟组件排版。
	 */
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
			int i = 0;
			for (ClockPanel item : this.clockList)
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

	/**
	 * 重绘倒计时组件排版。
	 */
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
			int i = 0;
			for (CountPanel item : this.countList)
			{
				constraints.gridy = i;
				layout.setConstraints(item, constraints);
				this.countRootPane.add(item);
				i++;
			}
			// this.countRootPane.revalidate();
			this.countRootPane.repaint();
			this.lastCountList = (LinkedList<CountPanel>) this.countList.clone();
		}
	}
}
