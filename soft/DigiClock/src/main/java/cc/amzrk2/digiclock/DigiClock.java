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
    public ArrayList<ClockCheck> alarmList; // 存放闹钟的数组
    public DefaultListModel defaultListModel1;

    public DigiClock() {
        initComponents(); // 初始化 NetBeans 生成组件
        // 初始化自定义数字钟主显示 Panel
        initClockDrawPanel();
        clockPanel.initClockPanelData();
        // 初始化闹钟数组
        alarmList = new ArrayList<>();
        //初始化jList1（闹钟显示框）对应的Model，以便添加多个闹钟进行显示
        defaultListModel1 = new DefaultListModel();
        // 启动时钟运作线程
        clockThread = new Thread(this, "clockThread");
        clockThread.start();
        //修改对话框样式为Windows样式
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        toggleAlarm = new javax.swing.JToggleButton();
        btnSetting = new javax.swing.JButton();
        btnAlarm = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jComboBox1 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("数字时钟");
        setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        setResizable(false);

        jList1.setFont(getFont());
        jScrollPane1.setViewportView(jList1);

        jButton1.setFont(getFont());
        jButton1.setText("添加闹钟");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(getFont());
        jButton2.setText("删除闹钟");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        toggleAlarm.setFont(getFont());
        toggleAlarm.setText("闹钟 / 关");
        toggleAlarm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleAlarmActionPerformed(evt);
            }
        });

        btnSetting.setFont(getFont());
        btnSetting.setText("设置");
        btnSetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSettingActionPerformed(evt);
            }
        });

        btnAlarm.setFont(getFont());
        btnAlarm.setText("闹铃");

        jButton5.setText("不知道干啥用");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(toggleAlarm, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 206, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSetting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAlarm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(210, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(toggleAlarm, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAlarm)
                                .addGap(18, 18, 18)
                                .addComponent(btnSetting)))))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // 当点击新建闹钟按钮时，自动创建一个闹钟类的对象。在完成闹钟添加前还需要实现的方法：
        // 生成一个对话框，能够输入设定闹钟的hour、min的信息，并自动获取闹钟按钮的开启与否的信息
        // 之后将生成的闹钟信息显示在UI界面左下的框图中（时间）
        JTextField alarmHourField = new JTextField(4);
        JTextField alarmMinuteField = new JTextField(4);
        JPanel alarmPanel = new JPanel();
        alarmPanel.add(new JLabel("时："));
        alarmPanel.add(alarmHourField);
        alarmPanel.add(Box.createHorizontalStrut(15)); // 间隔
        alarmPanel.add(new JLabel("分："));
        alarmPanel.add(alarmMinuteField);
        int result = JOptionPane.showConfirmDialog(this, alarmPanel,
                "请设定闹钟时间 (24 小时制)", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String alarmHour = alarmHourField.getText();
            String alarmMinute = alarmMinuteField.getText();
            ClockCheck newClock = new ClockCheck(Integer.valueOf(alarmHour), Integer.valueOf(alarmMinute), true);
            int Cnum = alarmList.size();
            alarmList.add(newClock);
            defaultListModel1.add(Cnum, alarmHour + " : " + alarmMinute);
            jList1.setModel(defaultListModel1);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // 此处实现闹钟的删除操作，就是从闹钟数组中删除
        //生成1个对话框，提示要删除的闹钟在闹钟列表中的位置，输入一个数字，然后删除对应的闹钟
        String dele = JOptionPane.showInputDialog(this,"请输入要删除的闹钟在闹钟列表中的位置：","删除闹钟",JOptionPane.PLAIN_MESSAGE);
        alarmList.remove(Integer.valueOf(dele)-1);
        defaultListModel1.remove(Integer.valueOf(dele)-1);
        jList1.setModel(defaultListModel1);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnSettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSettingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSettingActionPerformed

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DigiClock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
        Thread thisThread = Thread.currentThread();
        while (clockThread == thisThread) {
            clockPanel.repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlarm;
    private javax.swing.JButton btnSetting;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToggleButton toggleAlarm;
    // End of variables declaration//GEN-END:variables
}
