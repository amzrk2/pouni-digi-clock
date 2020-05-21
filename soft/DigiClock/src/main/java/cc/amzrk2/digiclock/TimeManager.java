package cc.amzrk2.digiclock;
import java.util.Date;
import java.util.LinkedList;

public class TimeManager extends Thread
{
	private LinkedList<ClockCheck> clockList = new LinkedList<>();
	private LinkedList<CountCheck> countList = new LinkedList<>();
	private TimeManagerPanel panel;
	private LinkedList<ClockCheck> removeClockList = new LinkedList<>();
	private LinkedList<CountCheck> removeCountList = new LinkedList<>();

	private TimeManager(){}

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
				// 如果已过期，添加入待清理列表。
				this.removeCountList.add(item);
			}
		}
		this.removeClockAndCount();
	}

	private synchronized void removeClockAndCount()
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
			this.panel.countList.remove(ClockPanel.getInstanceCompare(item.hashCode()));
		}
		this.panel.repaintCountPane();
		this.removeClockList = new LinkedList<>();
		this.removeCountList = new LinkedList<>();
	}

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

	public synchronized void addClock(int hour, int min)
	{
		ClockPanel clockPanel = new ClockPanel(this);
		ClockCheck clockCheck = new ClockCheck(hour, min, true, clockPanel);
		clockPanel.setHashCode(clockCheck.hashCode());
		this.clockList.add(clockCheck);
		this.panel.clockList.add(clockPanel);
	}

	public synchronized void addCount(long schedule)
	{
		CountPanel countPanel = new CountPanel(this);
		CountCheck countCheck = new CountCheck(schedule, countPanel);
		countPanel.setHashCode(countCheck.hashCode());
		this.countList.add(countCheck);
		this.panel.countList.add(countPanel);
	}

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

	public static TimeManagerPanel createAndStartNewPanel()
	{
		TimeManager timeManager;

		timeManager = new TimeManager();

		timeManager.panel = new TimeManagerPanel(timeManager);
		timeManager.start();
		return timeManager.panel;
	}
}
