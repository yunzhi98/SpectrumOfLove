package com.crawler.timerManager;

import com.crawler.config.LoadConfig;

import java.util.TimerTask;

/**
 * Created by wb-xuzhenbin on 2015/11/23.
 */
public class SearchTask extends TimerTask {
    public void run() {
        if(!SearchBKeyword.process){
            /**���عؼ��ʺͲɼ�ҳ��*/
            LoadConfig.loadDemoConfig();
            /**���òɼ�����*/
            new SearchBKeyword().search();
        }
    }
}