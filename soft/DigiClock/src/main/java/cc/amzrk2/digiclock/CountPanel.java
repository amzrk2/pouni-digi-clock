package cc.amzrk2.digiclock;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

/**
 * 倒计时对应的窗口组件类。继承自<code>javax.swing.JPanel</code>类。
 *
 * @author 8f235831
 * @see CountCheck
 */
public class CountPanel extends JPanel implements Comparable<CountPanel>
{
	private int hashCode;// 哈希值，与对应的倒计时类保持一致。
	public JLabel content_1;// 文本，用于显示具体时间。
	public JLabel content_2;// 文本，用于显示预计时间。
	public JButton deleteButton; // 删除按钮。

	/**
	 * 构造方法，使用哈希函数生成。不会实际构造，仅供比较使用。
	 *
	 * @param hashCode 需要的哈希值。
	 */
	private CountPanel(int hashCode)
	{
		this.hashCode = hashCode;
	}

	/**
	 * 构造方法，构造一个窗口组件。
	 *
	 * @param timeManager 管理线程类。
	 */
	public CountPanel(TimeManager timeManager)
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

		this.content_1.setFont(DigiClock.fontSm);
		this.content_2.setFont(DigiClock.fontSm);
		this.deleteButton.setFont(DigiClock.fontSm);

		this.deleteButton.addActionListener(
				event -> timeManager.removeCount(this.hashCode)
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

		// this.setVisible(true);
	}

	/**
	 * 设置哈希值。
	 *
	 * @param hashCode 哈希值，应当由对应的闹钟时间生成。
	 */
	public void setHashCode(int hashCode)
	{
		this.hashCode = hashCode;
	}

	/**
	 * 这个方法规定了窗口组件的大小。<br/>
	 * <big><strong>不用碰这个方法。</strong></big>
	 */
	@Override
	public Dimension getPreferredSize()
	{
		// 强制大小。
		return new Dimension(180, 70);
	}

	/**
	 * 根据给定的哈希值，生成一个用于比较的实例。该实例不能用于嵌入窗口中显示。
	 *
	 * @param hashCode 给定的哈希值。
	 * @return 返回一个实例。
	 */
	public static CountPanel getInstanceCompare(int hashCode)
	{
		return new CountPanel(hashCode);
	}

	/**
	 * 返回设定的哈希值。
	 *
	 * @return 设定的哈希值。
	 */
	@Override
	public int hashCode()
	{
		return this.hashCode;
	}

	/**
	 * 比较两个窗口是否相同。
	 */
	@Override
	public boolean equals(Object obj)
	{
		return (obj instanceof CountPanel) && this.hashCode == obj.hashCode();
	}

	/**
	 * 比较方法，比较哪一个组件更早。
	 *
	 * @param o 另一个组件。
	 * @return 这个倒计时结束的时间较早，返回<code> 1 </code>；
	 * 这个闹倒计时结束的时间较晚，返回<code> -1 </code>；
	 * 结束时间相同，返回<code> 0 </code>。
	 */
	@Override
	public int compareTo(CountPanel o)
	{
		return -(Integer.compare(this.hashCode, o.hashCode));
	}
}
