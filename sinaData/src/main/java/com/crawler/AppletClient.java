package com.crawler;

import com.crawler.timerManager.TimerManager;
import org.apache.log4j.Logger;

/**
 * Created by wb-xuzhenbin on 2015/11/23.
 */
public class AppletClient {
    public static Logger log4j = Logger.getLogger("");
    /**主程序入口*/
    public static void main(String[] args) {
        log4j.info("10秒后启动采集");
        new TimerManager().run();
    }
}
