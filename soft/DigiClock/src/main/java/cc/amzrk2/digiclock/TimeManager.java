package cc.amzrk2.digiclock;

import javax.swing.JPanel;
import java.util.LinkedList;

/**
 * 时间管理类。继承自<code>java.lang.Thread</code>类。<br/>
 * 使用静态的<code>createAndStartNewPanel()</code>方法可以构造一个需要的Panel类。
 *
 * @author 8f235831
 * @see #createAndStartNewPanel()
 */
public class TimeManager extends Thread
{
	private LinkedList<ClockCheck> clockList = new LinkedList<>();// 闹钟类。
	private LinkedList<CountCheck> countList = new LinkedList<>();// 倒计时类。
	private TimeManagerPanel panel;// 窗口组件。
	private LinkedList<ClockCheck> removeClockList = new LinkedList<>();// 待移除的闹钟。
	private LinkedList<CountCheck> removeCountList = new LinkedList<>();// 待移除的倒计时。

	/**
	 * 禁止直接构造实例。
	 */
	private TimeManager(){}

	/**
	 * 刷新内部状态，刷新窗口显示。
	 */
	private synchronized void refresh()
	{
		for (ClockCheck item : this.clockList)
		{
			item.check();
		}
		for (CountCheck item : this.countList)
		{
			if (item.check())
			{
				// 如果倒计时已过期，添加入待清理列表。
				this.removeCountList.add(item);
			}
		}
		this.removeClockAndCount();
	}

	/**
	 * 清理需要删除的闹钟和倒计时。
	 */
	private void removeClockAndCount()
	{
		for (ClockCheck item : this.removeClockList)
		{
			this.clockList.remove(item);
			this.panel.clockList.remove(ClockPanel.getInstanceCompare(item.hashCode()));
		}
		this.panel.repaintClockPane();
		for (CountCheck item : this.removeCountList)
		{
			this.countList.remove(item);
			this.panel.countList.remove(CountPanel.getInstanceCompare(item.hashCode()));
		}
		this.panel.repaintCountPane();
		this.removeClockList = new LinkedList<>();
		this.removeCountList = new LinkedList<>();
	}

	/**
	 * 移除闹钟。<br/>
	 * 调用该方法后，直到调用<code>refresh()</code>方法为止，窗口上指定的闹钟才会被清除。
	 *
	 * @param hashCode 闹钟的哈希值。
	 * @see #refresh()
	 */
	public synchronized void removeClock(int hashCode)
	{
		for (ClockCheck item : this.clockList)
		{
			if (item.hashCode() == hashCode)
			{
				this.removeClockList.add(item);
			}
		}
	}

	/**
	 * 移除倒计时。<br/>
	 * 调用该方法后，直到调用<code>refresh()</code>方法为止，窗口上指定的倒计时才会被清除。
	 *
	 * @param hashCode 倒计时的哈希值。
	 * @see #refresh()
	 */
	public synchronized void removeCount(int hashCode)
	{
		for (CountCheck item : this.countList)
		{
			if (item.hashCode() == hashCode)
			{
				this.removeCountList.add(item);
			}
		}
	}

	/**
	 * 添加闹钟。<br/>
	 * 调用该方法后，直到调用<code>refresh()</code>方法为止，窗口上才会出现对应的闹钟。
	 *
	 * @param hour 小时，24小时值。
	 * @param min  分钟。
	 * @see #refresh()
	 */
	public synchronized void addClock(int hour, int min)
	{
		ClockPanel clockPanel = new ClockPanel(this);
		ClockCheck clockCheck = new ClockCheck(hour, min, true, clockPanel);
		clockPanel.setHashCode(clockCheck.hashCode());
		this.clockList.add(clockCheck);
		this.panel.clockList.add(clockPanel);
	}

	/**
	 * 添加倒计时。<br/>
	 * 调用该方法后，直到调用<code>refresh()</code>方法为止，窗口上才会出现对应的倒计时。
	 *
	 * @param schedule 期望的时间。
	 * @see #refresh()
	 */
	public synchronized void addCount(long schedule)
	{
		CountPanel countPanel = new CountPanel(this);
		CountCheck countCheck = new CountCheck(schedule, countPanel);
		countPanel.setHashCode(countCheck.hashCode());
		this.countList.add(countCheck);
		this.panel.countList.add(countPanel);
	}

	/**
	 * 测试是否包含闹钟。
	 *
	 * @param hashCode 闹钟的哈希值。
	 */
	public synchronized boolean containsClock(int hashCode)
	{
		for (ClockCheck i : this.clockList)
		{
			if (i.hashCode() == hashCode)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 测试是否包含倒计时。
	 *
	 * @param hashCode 倒计时的哈希值。
	 */
	public synchronized boolean containsCount(int hashCode)
	{
		for (CountCheck i : this.countList)
		{
			if (i.hashCode() == hashCode)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 线程的运作方法。<br/>
	 * 定时调用<code>refresh()</code>方法。
	 *
	 * @see #refresh()
	 */
	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				Thread.sleep(500);
				this.refresh();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 时间管理系统组件创建方法。<br/>
	 * 调用这个类后，会从构造一个<code>TimeManager</code>类实例开始，
	 * 最终返回一个<code>javax.swing.JPanel</code>类实例。<br/>
	 * 该实例能够自行运作，无需关心内部细节。
	 *
	 * @return 获得的组件实例。
	 * @see JPanel
	 * @see TimeManagerPanel
	 */
	public static JPanel createAndStartNewPanel()
	{
		TimeManager timeManager;

		timeManager = new TimeManager();

		timeManager.panel = new TimeManagerPanel(timeManager);
		timeManager.start();
		return timeManager.panel;
	}
}
