import java.io.File;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * 闹钟类，用于生成闹钟提醒，需要线程定时调用<code>check()</code>方法。<br/>
 * 直接生成对话框，无需关心是否到时。<br/>
 * 已添加开关、贪睡模式（固定 10 分钟）。<br/>
 * 音乐功能已添加（版本 0.1.1）。<br/>
 * <strong><big>闹钟弹出的对话框会阻塞负责调用的线程！</big></strong><br/>
 * 修改几个小问题，并调整方法以便于更高级的类调用。（版本 0.2.0）
 *
 * @author 8f235831
 * @version 0.2.0
 * @see ClockCheck#check()
 * @see AlarmMusic
 */

public class ClockCheck implements Comparable<ClockCheck>
{
	private long schedule;
	private boolean checkStatus;
	private static final long dayInterval = 86400000;// 1 天 = 24 * 3600 * 1000 。
	private static long repeatInterval = 600000;// 10 分钟。
	private int repeatCount = 0;

	public ClockPanel clockPanel;

	/**
	 * 构造函数。
	 *
	 * @param hour        设定闹钟的小时，24 小时制。
	 * @param min         设定闹钟的分钟。
	 * @param checkStatus 闹钟开关。<code>true</code>为开，<code>false</code>为关。
	 * @throws IllegalArgumentException 时间格式错误时可能会抛出此异常。
	 */
	public ClockCheck(int hour, int min, boolean checkStatus, ClockPanel panel)
	{
		this.schedule = quickSchedule(hour, min);
		this.checkStatus = checkStatus;
		this.clockPanel = panel;
	}

	/**
	 * 检测方法，请确保能有个线程定期调用这个方法来确认闹钟是否到时，间隔 1
	 * 秒左右即可。</br>
	 * <strong><big>闹钟弹出的对话框会阻塞负责调用的线程！</big></strong></br>
	 * 如果不希望线程被阻塞请另外申请专门的线程来调用这个方法。
	 */
	public boolean check()
	{
		int userResult;
		AlarmMusic musicThread;

		// 更新窗口。
		var calendar = Calendar.getInstance();
		var i = (this.getNextRing() - System.currentTimeMillis());
		calendar.setTimeInMillis(this.schedule);
		this.setCheckStatus(this.clockPanel.enableCheckBox.isSelected());

		this.clockPanel.content_1.setText(calendar.get(Calendar.HOUR_OF_DAY) + " : " + calendar.get(Calendar.MINUTE));
		this.clockPanel.content_2.setText(
				(this.checkStatus) ? ("下次响铃时间为" + (i / 3600000) + " : " + ((i % 3600000) / 60000) + " : " +
				                      (((i / 1000 % 3600) % 60)) + "后。") : ("当前闹钟已关闭。"));

		// 检查状态。
		if ((System.currentTimeMillis() >
		     (this.schedule + this.repeatCount * repeatInterval)))
		{
			if (this.checkStatus)
			{
				// 响铃。
				musicThread = new AlarmMusic(
						new File("神前暁 - ハレ晴レユカイ(こなたの着メロ).wav"));
				musicThread.start();
				userResult = JOptionPane.CLOSED_OPTION;
				while (userResult == JOptionPane.CLOSED_OPTION)
				{
					userResult = JOptionPane.showOptionDialog(null,
							("现在是" + new Date()
							 + ("\n已经推迟了" + this.repeatCount + "次闹钟。")),
							"闹钟", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null,
							new Object[]{"推迟闹钟", "关闭闹钟"}, null);
				}
				if (userResult == 0)
				{
					// 推迟。
					this.repeatCount++;
				}
				else if (userResult == 1)
				{
					// 关闭。
					this.schedule = this.schedule + dayInterval;
					this.repeatCount = 0;
				}
				else
				{
					// 程序应该不会执行到这里的。
					throw new RuntimeException();
				}
				musicThread.close();
				return true;
			}
			else
			{
				// 更新内部时间。
				this.schedule = this.schedule + dayInterval;
				this.repeatCount = 0;
			}
		}
		return false;

	}

	/**
	 * @return 获取下一次闹钟的时间。不考虑当前的贪睡状态。
	 * @see #getNextSchedule
	 * @see #getNextRing()
	 * @deprecated 0.2.0 请使用getNextSchedule()方法
	 */
	public Date getSchedule()
	{
		return new Date(this.schedule);
	}

	/**
	 * @return 获取下一次闹钟的时间。<big><strong>不考虑</strong></big>当前的贪睡状态。
	 * @see #getNextRing()
	 */
	public long getNextSchedule()
	{
		return this.schedule;
	}

	/**
	 * @return 获取下一次响铃的时间。<big><strong>考虑</strong></big>当前的贪睡状态。
	 * @see #getNextSchedule()
	 */
	public long getNextRing()
	{
		return this.schedule + this.repeatCount * repeatInterval;
	}

	/**
	 * @param hour 设定闹钟的小时，24 小时制。
	 * @param min  设定闹钟的分钟。
	 * @throws IllegalArgumentException 时间格式错误时可能会抛出此异常。
	 */
	public synchronized void setSchedule(int hour, int min)
	{
		this.schedule = quickSchedule(hour, min);
		this.repeatCount = 0;
	}

	/**
	 * @return 获取闹钟的开关状态。<code>true</code>为开，<code>false</code>为关。
	 */
	public boolean getCheckStatus()
	{
		return this.checkStatus;
	}

	/**
	 * 设置闹钟的开关状态，当前的贪睡状态会被清除。
	 *
	 * @param newCheckStatus 设置闹钟的开关状态。<code>true</code>为开，<code>false</code>为关。
	 */
	public synchronized void setCheckStatus(boolean newCheckStatus)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(this.schedule);

		if (this.checkStatus != newCheckStatus)
		{
			// 重置闹钟状态。
			// this.schedule = quickSchedule(this.schedule);
			this.checkStatus = newCheckStatus;
			this.repeatCount = 0;
		}
	}

	/**
	 * @return 返回当前贪睡的次数。
	 */
	public int getRepeatCount()
	{
		return this.repeatCount;
	}

	/**
	 * 根据当前时间、设定的小时的分钟，获取下一次闹钟的时间。
	 *
	 * @param hour 设定闹钟的小时，24 小时制。
	 * @param min  设定闹钟的分钟。
	 * @return 返回下一次闹钟的合理时间。
	 */
	private static long quickSchedule(int hour, int min)
	{
		Calendar result;

		try
		{
			result = Calendar.getInstance();
			result.set(Calendar.HOUR_OF_DAY, hour);
			result.set(Calendar.MINUTE, min);
			result.set(Calendar.SECOND, 0);
			result.set(Calendar.MILLISECOND, 0);
			var millis = result.getTimeInMillis();
			return (millis > System.currentTimeMillis() + 1000) ? millis : millis + dayInterval;
		}
		catch (RuntimeException e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException(
					"疑似错误的输入时间（" + hour + ":" + min + "）。");
		}
	}

	private static long quickSchedule(long outdatedSchedule)
	{
		var calendar = Calendar.getInstance();
		calendar.setTimeInMillis(outdatedSchedule);
		return quickSchedule(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
	}

	@Override
	public int compareTo(ClockCheck anotherObject)
	{
		return Long.compare(this.schedule, anotherObject.schedule);
	}

	@Override
	public int hashCode()
	{
		var calendar = Calendar.getInstance();
		calendar.setTimeInMillis(this.schedule);
		return ((calendar.get(Calendar.HOUR_OF_DAY) << 8) + calendar.get(Calendar.MINUTE));
	}

	@Override
	public boolean equals(Object obj)
	{
		return (obj instanceof ClockCheck) && (this.hashCode() == obj.hashCode());
	}
}
