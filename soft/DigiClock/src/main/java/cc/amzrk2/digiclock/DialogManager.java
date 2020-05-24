package cc.amzrk2.digiclock;

import java.awt.*;
import javax.swing.*;

/**
 * 对话框生成器，用于设置闹钟、设置倒计时。
 *
 * @author li-xuan, 8f235831
 * @version 0.1.0
 */
public class DialogManager
{
	/**
	 * 禁止构造此类实例。
	 */
	private DialogManager(){}

	/**
	 * 显示用于设置闹钟的对话框。<br/>
	 * 该方法会根据用户的指令尝试设置一个新的闹钟。
	 *
	 * @param parentComponent 父组件，或者也可以填<code> null </code>。
	 * @param timeManager     管理线程类。
	 */
	public static void showClockDialog(Component parentComponent, TimeManager timeManager)
	{
		boolean checkStatus = true;
		// 时间有效值检查
		while (checkStatus)
		{
			// 对话框面板
			JTextField alarmHourField = new JTextField(4);
			JTextField alarmMinuteField = new JTextField(4);
			JPanel alarmPanel = new JPanel();
			alarmPanel.add(new JLabel("时："));
			alarmPanel.add(alarmHourField);
			alarmPanel.add(Box.createHorizontalStrut(15)); // 间隔
			alarmPanel.add(new JLabel("分："));
			alarmPanel.add(alarmMinuteField);
			int result =
					JOptionPane.showConfirmDialog(parentComponent, alarmPanel, "请设定闹钟时间 (24 小时制)",
							JOptionPane.OK_CANCEL_OPTION);
			try
			{
				// 确认
				if (result == JOptionPane.OK_OPTION)
				{
					String alarmHour = alarmHourField.getText();
					String alarmMinute = alarmMinuteField.getText();
					int alarmHourI = Integer.parseInt(alarmHour);
					int alarmMinuteI = Integer.parseInt(alarmMinute);
					// 时间有效
					if (alarmHourI >= 0 && alarmHourI <= 24 && alarmMinuteI >= 0 && alarmMinuteI <= 59)
					{
						// 检查闹钟是否已经存在
						if (timeManager.containsClock(ClockCheck.calculateHashCode(alarmHourI, alarmMinuteI)))
						{
							JOptionPane.showMessageDialog(parentComponent, "闹钟已存在。", "提示", JOptionPane.ERROR_MESSAGE);
						}
						else
						{
							timeManager.addClock(alarmHourI, alarmMinuteI);
							checkStatus = false;
						}
					} // 时间无效
					else
					{
						JOptionPane
								.showMessageDialog(parentComponent, "时间输入有误，请检查输入值！", "错误", JOptionPane.ERROR_MESSAGE);
					}
				} // 取消
				else
				{
					checkStatus = false;
				}
			}
			catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog(parentComponent, "时间输入有误，请检查输入值！", "错误", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * 显示用于设置倒计时的对话框。<br/>
	 * 该方法会根据用户的指令尝试设置一个新的倒计时。
	 *
	 * @param parentComponent 父组件，或者也可以填<code> null </code>。
	 * @param timeManager     管理线程类。
	 */
	public static void showCountDialog(Component parentComponent, TimeManager timeManager)
	{
		boolean checkStatus = true;
		// 时间有效值检查
		while (checkStatus)
		{
			// 对话框面板
			JTextField inputField = new JTextField(4);
			JPanel alarmPanel = new JPanel();
			alarmPanel.add(new JLabel("单位为秒："));
			alarmPanel.add(inputField);
			int result =
					JOptionPane.showConfirmDialog(parentComponent, alarmPanel, "请输入倒计时时间",
							JOptionPane.OK_CANCEL_OPTION);
			try
			{
				// 确认
				if (result == JOptionPane.OK_OPTION)
				{
					String inputText = inputField.getText();
					int inputInt = Integer.parseInt(inputText);
					// 时间有效
					if (inputInt > 2)
					{
						timeManager.addCount(inputInt * 1000 + System.currentTimeMillis());
						checkStatus = false;
					} // 时间无效
					else
					{
						JOptionPane
								.showMessageDialog(parentComponent, "时间输入有误，请检查输入值！", "错误", JOptionPane.ERROR_MESSAGE);
					}
				} // 取消
				else
				{
					checkStatus = false;
				}
			}
			catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog(parentComponent, "时间输入有误，请检查输入值！", "错误", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
