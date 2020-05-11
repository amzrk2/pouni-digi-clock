import java.util.*;
import javax.swing.JOptionPane;

/**
 * 闹钟类，用于生成闹钟提醒，需要线程定时调用<code>check()</code>方法。<br/>
 * 直接生成对话框，无需关心是否到时。<br/>
 * 已添加开关、贪睡模式（固定 10 分钟）。<br/>
 * 暂时还没有音乐功能。<br/>
 * <strong><big>闹钟弹出的对话框会阻塞负责调用的线程！</big></strong><br/>
 * 
 * @author 8f235831
 * @version 0.1.0
 * @see ClockCheck#check()
 */
public class ClockCheck
{
	private Date schedule;
	private boolean checkStatus;
	private static final long dayInterval = 86400000;// 1 天 = 24 * 3600 * 1000 。
	private long repeatInterval = 600000;// 10 分钟。
	private int repeatCount = 0;

	/**
	 * 构造函数。
	 * 
	 * @param hour 设定闹钟的小时，24 小时制。
	 * @param min 设定闹钟的分钟。
	 * @param checkStatus
	 *        闹钟开关。<code>true</code>为开，<code>false</code>为关。
	 * @throws IllegalArgumentException
	 *         时间格式错误时可能会抛出此异常。
	 */
	public ClockCheck(int hour, int min, boolean checkStatus)
	{
		this.schedule = quickSchedule(hour, min);
		this.checkStatus = checkStatus;
	}

	/**
	 * 检测方法，请确保能有个线程定期调用这个方法来确认闹钟是否到时，间隔 1
	 * 秒左右即可。</br>
	 * <strong><big>闹钟弹出的对话框会阻塞负责调用的线程！</big></strong></br>
	 * 如果不希望线程被阻塞请另外申请专门的线程来调用这个方法。
	 */
	public void check()
	{
		int userResult;

		if (this.checkStatus && (new Date().after(
			new Date(this.schedule.getTime() + repeatCount * repeatInterval))))
		{
			// 响铃。
			userResult = JOptionPane.CLOSED_OPTION;
			while (userResult == JOptionPane.CLOSED_OPTION)
			{
				userResult = JOptionPane.showOptionDialog(null,
					("现在是" + new Date()
						+ ("\n已经推迟了" + this.repeatCount + "次闹钟。")),
					"闹钟", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null,
					new Object[] { "推迟闹钟", "关闭闹钟" }, null);
			}
			if (userResult == 0)
			{
				// 推迟。
				this.repeatCount++;
			}
			else if (userResult == 1)
			{
				// 关闭。
				this.schedule = new Date(this.schedule.getTime() + dayInterval);
				this.repeatCount = 0;
			}
			else
			{
				// 程序应该不会执行到这里的。
				throw new RuntimeException();
			}
		}
	}

	/**
	 * @return 获取下一次闹钟的时间。如果处于贪睡状态，则返回原本的闹钟时间。
	 */
	public Date getSchedule()
	{
		return (Date) this.schedule.clone();
	}

	/**
	 * @param hour 设定闹钟的小时，24 小时制。
	 * @param min 设定闹钟的分钟。
	 * @throws IllegalArgumentException
	 *         时间格式错误时可能会抛出此异常。
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
	 * @param newCheckStatus
	 *        设置闹钟的开关状态。<code>true</code>为开，<code>false</code>为关。
	 */
	public synchronized void setCheckStatus(boolean newCheckStatus)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.schedule);

		// 重置下一次闹钟时间。
		this.schedule = quickSchedule(calendar.get(Calendar.HOUR_OF_DAY),
			calendar.get(Calendar.MINUTE));
		this.checkStatus = newCheckStatus;
		this.repeatCount = 0;
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
	 * @param min 设定闹钟的分钟。
	 * @return 返回下一次闹钟的合理时间。
	 */
	private static Date quickSchedule(int hour, int min)
	{
		Calendar calendar;
		int currentHour;
		int currentMinute;
		Calendar result;

		try
		{
			calendar = Calendar.getInstance();
			currentHour = calendar.get(Calendar.HOUR_OF_DAY);
			currentMinute = calendar.get(Calendar.MINUTE);
			// 确保不会出现错误，预留 1 分钟缓冲量。
			if ((currentHour << 7 + currentMinute + 1/* 缓冲 */) > (hour << 7
				+ min))
			{
				// 设为明日时间。
				result = (Calendar) calendar.clone();
				result.setTimeInMillis(result.getTimeInMillis() + dayInterval);
				result.set(Calendar.HOUR_OF_DAY, hour);
				result.set(Calendar.MINUTE, min);
				return result.getTime();
			}
			else
			{
				// 设为今日时间。
				result = (Calendar) calendar.clone();
				result.set(Calendar.HOUR_OF_DAY, hour);
				result.set(Calendar.MINUTE, min);
				return result.getTime();
			}
		}
		catch (RuntimeException e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException(
				"疑似错误的输入时间（" + hour + ":" + min + "）。");
		}
	}
}
