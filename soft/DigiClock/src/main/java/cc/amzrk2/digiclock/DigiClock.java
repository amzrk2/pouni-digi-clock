/*
2020  B18030616 & B18030620 & B18030626 with The Unlicense
For more information, please refer to <https://unlicense.org>
 */
package cc.amzrk2.digiclock;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class DigiClock extends JFrame implements Runnable {

    public ClockDrawPanel clockPanel; // 数字钟主显示 Panel
    public Thread clockThread; // 时钟运作线程
    public Thread alarmThread; // 监视闹钟线程

    public ArrayList<ClockCheck> alarmList; // 存放闹钟的数组
    public DefaultListModel alarmListModel; // 闹钟显示 ListModel

    public DigiClock() {
        initComponents(); // 初始化 NetBeans 生成组件
        // 初始化自定义数字钟主显示 Panel
        initClockDrawPanel();
        clockPanel.initClockPanelData();
        // 默认开启闹钟
        toggleAlarm.setSelected(true);
        toggleAlarm.setText("闹钟 / 开");
        // 初始化闹钟数组
        alarmList = new ArrayList<>();
        // 初始化 alarmListModel（闹钟显示框）对应的Model，以便添加多个闹钟进行显示
        alarmListModel = new DefaultListModel();
        alarmListPanel.setModel(alarmListModel);
        // 启动时钟运作线程
        clockThread = new Thread(this, "clockThread");
        clockThread.start();
        // 启动监视闹钟线程
        alarmThread = new Thread(new AlarmChecker(alarmList), "alarmThread");
        alarmThread.start();
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
        // 左右 30px
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30).addComponent(clockPanel).addGap(30, 30, 30))
        );
        // 上 30px 下 max 420px
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30).addComponent(clockPanel).addContainerGap(270, Short.MAX_VALUE))
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

        alarmScrollPane1 = new javax.swing.JScrollPane();
        alarmListPanel = new javax.swing.JList<>();
        addAlarm = new javax.swing.JButton();
        delAlarm = new javax.swing.JButton();
        toggleAlarm = new javax.swing.JToggleButton();
        wtComboBox = new javax.swing.JComboBox<>();
        jSeparator = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("数字时钟");
        setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        setResizable(false);

        alarmListPanel.setFont(getFont());
        alarmScrollPane1.setViewportView(alarmListPanel);

        addAlarm.setFont(getFont());
        addAlarm.setText("添加闹钟");
        addAlarm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAlarmActionPerformed(evt);
            }
        });

        delAlarm.setFont(getFont());
        delAlarm.setText("删除闹钟");
        delAlarm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delAlarmActionPerformed(evt);
            }
        });

        toggleAlarm.setFont(getFont());
        toggleAlarm.setText("闹钟 / 关");
        toggleAlarm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleAlarmActionPerformed(evt);
            }
        });

        wtComboBox.setFont(getFont());
        wtComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Europe/Paris", "Asia/Shanghai", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(alarmScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jSeparator)
                                .addGap(459, 459, 459))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addAlarm, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(31, 31, 31))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(delAlarm, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(toggleAlarm, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(wtComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(210, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(alarmScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(toggleAlarm, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(wtComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15)
                        .addComponent(jSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(addAlarm, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(delAlarm, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void toggleAlarmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleAlarmActionPerformed
        if (toggleAlarm.isSelected()) {
            toggleAlarm.setText("闹钟 / 开");
        } else {
            toggleAlarm.setText("闹钟 / 关");
        }
    }//GEN-LAST:event_toggleAlarmActionPerformed

    // 创建闹钟
    private void addAlarmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAlarmActionPerformed
        // 当点击新建闹钟按钮时，自动创建一个闹钟类的对象。在完成闹钟添加前还需要实现的方法：
        // 生成一个对话框，能够输入设定闹钟的hour、min的信息，并自动获取闹钟按钮的开启与否的信息
        // 之后将生成的闹钟信息显示在UI界面左下的框图中（时间）
        boolean checkStatus = true;
        // 时间有效值检查
        while (checkStatus) {
            // 对话框面板
            JTextField alarmHourField = new JTextField(4);
            JTextField alarmMinuteField = new JTextField(4);
            JPanel alarmPanel = new JPanel();
            alarmPanel.add(new JLabel("时："));
            alarmPanel.add(alarmHourField);
            alarmPanel.add(Box.createHorizontalStrut(15)); // 间隔
            alarmPanel.add(new JLabel("分："));
            alarmPanel.add(alarmMinuteField);
            int result = JOptionPane.showConfirmDialog(this, alarmPanel, "请设定闹钟时间 (24 小时制)", JOptionPane.OK_CANCEL_OPTION);
            try {
                // 确认
                if (result == JOptionPane.OK_OPTION) {
                    String alarmHour = alarmHourField.getText();
                    String alarmMinute = alarmMinuteField.getText();
                    int alarmHourI = Integer.valueOf(alarmHour);
                    int alarmMinuteI = Integer.valueOf(alarmMinute);
                    // 时间有效
                    if (alarmHourI >= 0 && alarmHourI <= 24 && alarmMinuteI >= 0 && alarmMinuteI <= 59) {
                        String newAlarm = alarmHour + ":" + alarmMinute;
                        // 检查闹钟是否已经存在
                        boolean alarmExist = false;
                        for (int i = 0; i < alarmListModel.getSize(); i++) {
                            if (newAlarm.equals(alarmListModel.get(i))) {
                                alarmExist = true;
                                JOptionPane.showMessageDialog(this, "闹钟已存在。", "提示", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        if (!alarmExist) {
                            ClockCheck newClock = new ClockCheck(Integer.valueOf(alarmHour), Integer.valueOf(alarmMinute), true);
                            alarmList.add(newClock);
                            alarmListModel.add(alarmList.size() - 1, newAlarm);
                            checkStatus = false;
                        }
                    } // 时间无效
                    else {
                        JOptionPane.showMessageDialog(this, "时间输入有误，请检查输入值！", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                } // 取消
                else {
                    checkStatus = false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "时间输入有误，请检查输入值！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_addAlarmActionPerformed

    // 删除闹钟
    private void delAlarmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delAlarmActionPerformed
        // 此处实现闹钟的删除操作，就是从闹钟数组中删除
        // 获取当前选中的闹钟
        try {
            int index = 0;
            String selectedAlarm = alarmListPanel.getSelectedValue();
            for (index = 0; index < alarmListModel.getSize(); index++) {
                if (selectedAlarm.equals(alarmListModel.get(index))) {
                    break;
                }
            }
            // 删除当前选中的闹钟
            alarmList.remove(index);
            alarmListModel.remove(index);
        } // 啥也没选
        catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "请先选中欲删除的闹钟。", "提示", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_delAlarmActionPerformed

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
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addAlarm;
    private javax.swing.JList<String> alarmListPanel;
    private javax.swing.JScrollPane alarmScrollPane1;
    private javax.swing.JButton delAlarm;
    private javax.swing.JSeparator jSeparator;
    private javax.swing.JToggleButton toggleAlarm;
    private javax.swing.JComboBox<String> wtComboBox;
    // End of variables declaration//GEN-END:variables
}
