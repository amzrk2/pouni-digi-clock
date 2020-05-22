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
public class WorldClockDrawPanel extends JPanel {

    private Font clockPanelFont;
    private Font clockDateFont;
    private int xCAxis;
    private int yCAxis;
    // 绘图用数据初始化状态检测
    private boolean initStatus = false;
    private String zoneString; //时区

    // 初始化绘图用数据
    public void initWorldClockPanelData() {
        // 世界时钟 Panel 字体
        this.clockPanelFont = new Font("Arial", Font.BOLD, 48);
        // 日期字体
        this.clockDateFont = new Font("微软雅黑", Font.PLAIN, 24);
        // 世界时钟 Panel 中心点 x 坐标
        this.xCAxis = this.getWidth() / 2;
        // 世界时钟 Panel 中心点 y 坐标
        this.yCAxis = this.getHeight() / 2;
        this.zoneString = "Asia/Shanghai";
        this.initStatus = true;
    }

    // 设置时区
    public synchronized void setZone(String zoneString) {
        this.zoneString = zoneString;
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
            ZonedDateTime zoneTime = ZonedDateTime.now(ZoneId.of(zoneString));
            // 绘制时间
            String time = zoneTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            g2d.setFont(clockPanelFont); // 时间字体
            FontMetrics dateFm = g2d.getFontMetrics(clockPanelFont);
            int stringHeight = dateFm.getAscent() - dateFm.getDescent() - dateFm.getLeading();
            int stringWidth = dateFm.stringWidth(time); // 时间字体宽度
            g2d.drawString(time, xCAxis - stringWidth / 2, yCAxis + stringHeight / 2 - 20);
            // 绘制日期
            String date = zoneTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            g2d.setFont(clockDateFont); // 日期字体
            dateFm = g2d.getFontMetrics(clockDateFont);
            stringHeight = dateFm.getAscent() - dateFm.getDescent() - dateFm.getLeading();
            stringWidth = dateFm.stringWidth(date); // 日期字体宽度
            g2d.drawString(date, xCAxis - stringWidth / 2, yCAxis + stringHeight / 2 + 25);
        } else {
            throw new RuntimeException("PaintingDataNotInitialized");
        }
    }
}
