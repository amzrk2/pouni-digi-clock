/*
2020  B18030616 & B18030620 & B18030626 with The Unlicense
For more information, please refer to <https://unlicense.org>
 */
package cc.amzrk2.digiclock;

import java.awt.*;
import javax.swing.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author amzrk2
 */
public class ClockDrawPanel extends JPanel {

    private Font clockPanelFont;
    private Font clockDateFont;
    private int xCAxis;
    private int yCAxis;
    // 绘图用数据初始化状态检测
    private boolean initStatus = false;
    String[] weekDays = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    // 初始化绘图用数据
    public void initClockPanelData() {
        // 数字钟主显示 Panel 字体
        this.clockPanelFont = new Font("Arial", Font.BOLD, 80);
        // 日期、星期字体
        this.clockDateFont = new Font("Microsoft YaHei", Font.PLAIN, 24);
        // 数字钟主显示 Panel 中心点 x 坐标
        this.xCAxis = this.getWidth() / 2;
        // 数字钟主显示 Panel 中心点 y 坐标
        this.yCAxis = this.getHeight() / 2;
        this.initStatus = true;
    }

    // 重写 paintComponent 避免闪烁
    @Override
    public void paintComponent(Graphics mg) throws RuntimeException {
        if (initStatus) {
            // 转换为 G2D 方便使用高级设置
            Graphics2D g2d = (Graphics2D) mg;
            // 开启字体抗锯齿
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            super.paintComponent(g2d);
            // 2倍粗边框
            g2d.setStroke(new BasicStroke(2f));
            // 获取当前日期和时间
            LocalDateTime localTime = LocalDateTime.now();
            // 绘制时间
            String time = localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            g2d.setFont(clockPanelFont); // 时间字体
            FontMetrics dateFm = g2d.getFontMetrics(clockPanelFont);
            int stringHeight = dateFm.getAscent() - dateFm.getDescent() - dateFm.getLeading();
            int stringWidth = dateFm.stringWidth(time); // 时间字体宽度
            g2d.drawString(time, xCAxis - 150 - stringWidth / 2, yCAxis + stringHeight / 2);
            // 绘制日期
            String date = localTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            g2d.setFont(clockDateFont); // 日期字体
            dateFm = g2d.getFontMetrics(clockDateFont);
            stringHeight = dateFm.getAscent() - dateFm.getDescent() - dateFm.getLeading();
            stringWidth = dateFm.stringWidth(date); // 日期字体宽度
            g2d.drawString(date, xCAxis + 180 - stringWidth / 2, yCAxis - 20 + stringHeight / 2);
            // 绘制星期
            String day = weekDays[localTime.getDayOfWeek().getValue() - 1];
            stringWidth = dateFm.stringWidth(day); // 星期字体宽度
            g2d.drawString(day, xCAxis + 180 - stringWidth / 2, yCAxis + 20 + stringHeight / 2);
        } else {
            throw new RuntimeException("PaintingDataNotInitialized");
        }
    }
}
