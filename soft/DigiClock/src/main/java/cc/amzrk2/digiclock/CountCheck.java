package cc.amzrk2.digiclock;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;


/**
 * 倒计时类，用于生成倒计时提醒，管理倒计时。<br/>
 * 需要线程定时调用<code>check()</code>方法。<br/>
 *
 * @author 8f235831
 * @see CountCheck#check()
 */
public class CountCheck implements Comparable<CountCheck>
{
	private long schedule;
	private boolean outdated;// 是否已经过期。
	public CountPanel countPanel;// 对应的窗口组件。

	/**
	 * 构造函数。
	 *
	 * @param schedule   预定的响铃时间。采取标准的计时方法。
	 *                   即<code> UTC 1970-01-01 00:00:00 <code/>以来的毫秒数。
	 * @param countPanel 对应的窗口组件。
	 * @throws IllegalArgumentException 当给定的时间已经经过，或距现在不足2000毫秒时抛出此异常。
	 */
	public CountCheck(long schedule, CountPanel countPanel)
	{
		if (schedule < System.currentTimeMillis() + 2000)
		{
			throw new IllegalArgumentException();
		}
		else
		{
			this.schedule = schedule;
			this.countPanel = countPanel;
		}
	}

	/**
	 * 检测方法，需要另外的线程定期调用这个方法来确认闹钟是否到时。<br/>
	 * <strong><big>闹钟弹出的对话框会阻塞负责调用的线程！</big></strong><br/>
	 * 如果不希望线程被阻塞请另外申请专门的线程来调用这个方法。
	 *
	 * @return 返回是否响铃。
	 */
	public boolean check()
	{
		AlarmMusic musicThread;

		// 更新窗口。
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(this.schedule);
		long i = (this.schedule - System.currentTimeMillis());
		this.countPanel.content_1.setText("倒计时 " + ((i / 3600000) + " : " + ((i % 3600000) / 60000) + " : " +
		                                            (((i / 1000 % 3600) % 60))));
		this.countPanel.content_2.setText("目标时间 " + (new SimpleDateFormat("HH:mm:ss")).format(calendar.getTime()));

		// 检查状态。
		if (!this.outdated)
		{
			if (System.currentTimeMillis() > this.schedule)
			{
				this.outdated = true;
				// 响铃。
				musicThread = new AlarmMusic(
						new File("神前暁 - ハレ晴レユカイ(こなたの着メロ).wav"));
				musicThread.start();
				JOptionPane.showMessageDialog(null, "设定的倒计时已结束。", "倒计时", JOptionPane.INFORMATION_MESSAGE, null);
				musicThread.close();
			}
		}
		return this.outdated;
	}

	/**
	 * 获取倒计时是否已经过期。
	 *
	 * @return 已经过期返回<code>true<code/>，否则返回<code>false</code>。
	 */
	public boolean isOutdated()
	{
		return this.outdated;
	}

	/**
	 * 比较方法，根据预计提醒的时间比较。
	 *
	 * @return 这个倒计时预计提醒时间较晚，返回<code> 1 </code>；这个闹钟预计提醒时间较早，返回<code> -1 </code>；
	 * * 预计同时提醒，返回<code> 0 <code/>。
	 */
	@Override
	public int compareTo(CountCheck anotherObject)
	{
		return Long.compare(this.schedule, anotherObject.schedule);
	}

	/**
	 * 哈希函数，根据预计提醒的时间生成。<br/>
	 * 同一秒提醒的倒计时会生成相同的哈希值。
	 *
	 * @return 返回值为<code> ( 设定的提醒时间 / 1000 ) <code/>。
	 */
	@Override
	public int hashCode()
	{
		return (int) (this.schedule / 1000);
	}

	/**
	 * 检查两个倒计时是否相等。
	 *
	 * @see #hashCode()
	 */
	@Override
	public boolean equals(Object obj)
	{
		return (obj instanceof CountCheck) && (this.hashCode() == obj.hashCode());
	}
}
