package ScheduleTask;

import NSRServer.NSRStrategyDefault;
import NSRStructs.HashStoreLoadMap;
import NSRStructs.LinkedStorePositionMap;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ss on 16-6-6.
 */
public class NSRScheduleTask extends ScheduleTask {
    @Override
    public void task() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                balance();
            }
        };
        Timer timer = new Timer();
        long delay = 0;
        long inteval = 10 * 1000;
        timer.scheduleAtFixedRate(task,delay,inteval);
    }

    public void balance() {
        System.out.println("balance begin");

        NSRStrategyDefault.balance(HashStoreLoadMap.getHashStoreLoadMap().getStoreLoadMap(), LinkedStorePositionMap.getLinkedStorePositionMap());
    }
    public static void main(String args[]){
        NSRScheduleTask task = new NSRScheduleTask();
        task.task();
    }
}
