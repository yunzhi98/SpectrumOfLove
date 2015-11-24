package com.crawler.timerManager;

import com.crawler.config.LoadConfig;

import java.util.TimerTask;

/**
 * Created by wb-xuzhenbin on 2015/11/23.
 */
public class SearchTask extends TimerTask {
    public void run() {
        if(!SearchBKeyword.process){
            /**加载关键词和采集页数*/
            LoadConfig.loadDemoConfig();
            /**调用采集方法*/
            new SearchBKeyword().search();
        }
    }
}