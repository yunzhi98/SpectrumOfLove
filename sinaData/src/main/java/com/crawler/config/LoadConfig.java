package com.crawler.config;

import com.crawler.timerManager.SearchBKeyword;
import com.crawler.util.FileUtils;
import com.crawler.util.XmlUtil;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.File;

/**
 * Created by wb-xuzhenbin on 2015/11/23.
 */
public class LoadConfig {

    private static Logger log4j = Logger.getLogger(LoadConfig.class.getName());
    private static String userDir;
    private static final String demoConfigPath = "sinaData"+ File.separator +"config" + File.separator + "demoConfig.xml";

    public static void main(String[] args) {
        loadDemoConfig();
        System.out.println(SearchBKeyword.pagesize);
    }
    /**
     * Created by wb-xuzhenbin on 2015/11/23.
     * 初始化方法，加载相关配置文件
     */
    public static void init() {
        userDir = System.getProperty("user.dir");
        loadDemoConfig();
    }


    /**
     * Created by wb-xuzhenbin on 2015/11/23.
     */
    public static String getDir() {
        return userDir == null ? System.getProperty("user.dir") : userDir;
    }

    /**
     * Created by wb-xuzhenbin on 2015/11/23.
     */
    public static void reload() {

    }

    /**
     * Created by wb-xuzhenbin on 2015/11/23.
     */
    public static void loadDemoConfig() {
        try {
            Document document = XmlUtil.loadXML(FileUtils.makeFilePath(getDir(), demoConfigPath));
            Element rootElement = XmlUtil.getRootElement(document);
            Element collecterRrootElement = XmlUtil.getElementChildElement(rootElement, "demo-config");
            /**加载采集关键词*/
            String []keywords= ((Element) XmlUtil.getElementChildElement(
                    collecterRrootElement, "demo-keywords")).getStringValue().split(",");
            if(keywords!=null&&keywords.length>0){
                for (int i = 0; i < keywords.length; i++) {
                    SearchBKeyword.keywordList.add(keywords[i]);
                }
            }
            /**加载采集页数*/
            SearchBKeyword.pagesize=Integer.parseInt(((Element) XmlUtil.getElementChildElement(
                    collecterRrootElement, "demo-pagesize")).getStringValue());
        } catch (Exception e) {
            log4j.info("Load demo config fail!" + e.getMessage(),e);
        }
    }

}
