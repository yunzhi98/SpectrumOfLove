package com.crawler.timerManager;

import java.util.Timer;

/**
 * Created by wb-xuzhenbin on 2015/11/23.
 */
public class TimerManager {
    public void run() {
        /**10���ִ�У�2����ִ��һ��*/
        Timer timer = new Timer();
        timer.schedule(new SearchTask(),10*1000l, 120 * 1000l);
    }

}