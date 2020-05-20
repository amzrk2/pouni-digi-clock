import javax.swing.*;
import java.io.File;
import java.util.Calendar;

public class CountCheck implements Comparable<CountCheck>
{
	private long schedule;
	private boolean outdated;
	public CountPanel countPanel;

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

	public boolean check()
	{
		AlarmMusic musicThread;

		// 更新窗口。
		var calendar = Calendar.getInstance();
		calendar.setTimeInMillis(this.schedule);
		var i = (this.schedule - System.currentTimeMillis());
		this.countPanel.content_1.setText("倒计时将于" + ((i / 3600000) + " : " + ((i % 3600000) / 60000) + " : " +
		                                             (((i / 1000 % 3600) % 60))) + "后结束。");
		this.countPanel.content_2.setText("倒计时的结束时间为" + calendar.getTime().toString() + "。");

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

	public boolean isOutdated()
	{
		return this.outdated;
	}


	@Override
	public int compareTo(CountCheck anotherObject)
	{
		return Long.compare(this.schedule, anotherObject.schedule);
	}

	@Override
	public int hashCode()
	{
		return (int) (this.schedule / 1000);
	}

	@Override
	public boolean equals(Object obj)
	{
		return (obj instanceof CountCheck) && (this.hashCode() == obj.hashCode());
	}

}
