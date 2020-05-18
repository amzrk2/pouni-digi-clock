/*
2020  B18030616 & B18030620 & B18030626 with The Unlicense
For more information, please refer to <https://unlicense.org>
 */
package cc.amzrk2.digiclock;

import java.util.ArrayList;

/**
 *
 * @author amzrk2
 */
public class AlarmChecker implements Runnable {

    private ArrayList<ClockCheck> alarmList; // 存放闹钟的数组

    public AlarmChecker(ArrayList<ClockCheck> alarmList) {
        this.alarmList = alarmList;
    }

    // 每秒检查所有闹钟实例
    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < alarmList.size(); i++) {
                alarmList.get(i).check();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
