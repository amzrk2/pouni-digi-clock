/*
2020  B18030616 & B18030620 & B18030626 with The Unlicense
For more information, please refer to <https://unlicense.org>
 */
package cc.amzrk2.digiclock;

import java.awt.*;
import javax.swing.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.regex.Pattern;

public class DigiClock extends JFrame implements Runnable {

    public ClockDrawPanel clockPanel; // 数字钟主显示 Panel
    public WorldClockDrawPanel worldPanel; // 世界时钟显示 Panel
    public DefaultComboBoxModel<String> wtModel; // 世界时钟所有可选时区
    public JPanel tmPanel; // 闹钟模块 Panel
    public Thread clockThread; // 时钟运作线程
    public static final Font fontLg = new java.awt.Font("SansSerif", 0, 16); // 字体（大）
    public static final Font fontSm = new java.awt.Font("SansSerif", 0, 14); // 字体（小）

    public DigiClock() {
        // 初始化世界时钟所有可选时区
        Set<String> allZones = ZoneId.getAvailableZoneIds();
        ArrayList<String> zoneString = new ArrayList<>();
        // 移除过期时区
        int i = 0;
        allZones.stream()
                .filter((zone) -> (Pattern.matches("^(Asia|Africa|America|Antarctica|Atlantic|Europe|Australia|Indian|Pacific)/.*", zone)))
                .forEachOrdered((zone) -> {
                    zoneString.add(zone);
                });
        Collections.sort(zoneString);
        wtModel = new DefaultComboBoxModel<>(zoneString.toArray(new String[zoneString.size()]));
        // 初始化 NetBeans 生成组件
        initComponents();
        // 初始化自定义数字钟主显示 Panel
        initClockDrawPanel();
        clockPanel.initClockPanelData();
        // 初始化世界时钟 Panel
        initWorldClockDrawPanel();
        worldPanel.initWorldClockPanelData();
        initTimeManager(); // 初始化闹钟模块 Panel
        // 启动时钟运作线程
        clockThread = new Thread(this, "clockThread");
        clockThread.start();
    }

    // 初始化自定义数字钟主显示 Panel
    private void initClockDrawPanel() {
        clockPanel = new ClockDrawPanel();
        clockPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // 边框
        clockPanel.setPreferredSize(new Dimension(740, 150)); // 设置主显 Panel 大小
        // 将主显 Panel 加入 mainFrame 的 layout
        // mainFrame 的 layout 已于 initComponents() 中修改过，这里获取并修改
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        // 左右 固定30px
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30).addComponent(clockPanel).addGap(30, 30, 30))
        );
        // 上 固定30px 下 至少30px
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30).addComponent(clockPanel).addContainerGap(30, Short.MAX_VALUE))
        );
        pack();
    }

    // 初始化闹钟模块 Panel
    private void initTimeManager() {
        tmPanel = TimeManager.createAndStartNewPanel();
        // 将闹钟模块 Panel 加入 mainFrame 的 layout
        // mainFrame 的 layout 已于 initComponents() 中修改过，这里获取并修改
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        // 左 固定30px 右 至少 800-30(左空)-400(闹钟模块)
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30).addComponent(tmPanel).addContainerGap(800 - 30 - 400, Short.MAX_VALUE))
        );
        // 上 至少150+60px (主显) 下 固定30px
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(150 + 30 + 30, Short.MAX_VALUE).addComponent(tmPanel).addGap(30, 30, 30))
        );
        pack();
    }

    // 初始化世界时钟 Panel
    private void initWorldClockDrawPanel() {
        worldPanel = new WorldClockDrawPanel();
        worldPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // 边框
        worldPanel.setPreferredSize(new Dimension(310, 150)); // 设置世界时钟 Panel 大小
        // 将世界时钟 Panel 加入 mainFrame 的 layout
        // mainFrame 的 layout 已于 initComponents() 中修改过，这里获取并修改
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        // 右 固定30px 左 至少460px
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap(460, Short.MAX_VALUE).addComponent(worldPanel).addGap(30, 30, 30))
        );
        // 上 至少275px
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap(275, Short.MAX_VALUE).addComponent(worldPanel).addContainerGap(30, Short.MAX_VALUE))
        );
        pack();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        wtComboBox = new javax.swing.JComboBox<>();
        exitButton = new javax.swing.JButton();
        aboutButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("数字时钟");
        setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        setResizable(false);

        wtComboBox.setFont(getFont());
        wtComboBox.setModel(wtModel);
        wtComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wtComboBoxActionPerformed(evt);
            }
        });

        exitButton.setFont(getFont());
        exitButton.setText("退出");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        aboutButton.setFont(getFont());
        aboutButton.setText("关于");
        aboutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(460, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(wtComboBox, 0, 310, Short.MAX_VALUE)
                    .addComponent(aboutButton, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(exitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(215, 215, 215)
                .addComponent(wtComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 269, Short.MAX_VALUE)
                .addComponent(aboutButton)
                .addGap(15, 15, 15)
                .addComponent(exitButton)
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void aboutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutButtonActionPerformed
        JOptionPane.showMessageDialog(this, "2020  B18030616 & B18030620 & B18030626 under The Unlicense");
    }//GEN-LAST:event_aboutButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    // 设置世界时钟的时区
    private void wtComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wtComboBoxActionPerformed
        String zoneString = wtComboBox.getSelectedItem().toString();
        worldPanel.setZone(zoneString);
    }//GEN-LAST:event_wtComboBoxActionPerformed

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        // 主线程
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DigiClock().setVisible(true);
            }
        });
    }

    // 时钟运作线程
    @Override
    public void run() {
        while (true) {
            clockPanel.repaint();
            worldPanel.repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aboutButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JComboBox<String> wtComboBox;
    // End of variables declaration//GEN-END:variables
}
