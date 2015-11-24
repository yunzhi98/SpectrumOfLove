package com.crawler.timerManager;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by wb-xuzhenbin on 2015/11/23.
 */
public class SearchBKeyword {
    public static Logger log4j = Logger.getLogger("");
    /** ������վ��ģ���������½��������վʶ��Ϊ�ֻ����� */
    private static final String USERAGENT = "Mozilla/5.0 (Windows NT 6.1; rv:22.0) Gecko/20100101 Firefox/22.0";
    /**���ó�ʱʱ�� */
    private static final int timeout=50000;
    private static  String url="http://www.baidu.com/s?wd=%E4%B8%8A%E6%B5%B7&pn=10&oq=%E4%B8%8A%E6%B5%B7&tn=baidu&ie=utf-8&usm=8";
    public static LinkedBlockingQueue<String> keywordList = new LinkedBlockingQueue<String>();
    public static int pagesize;
    public static boolean process=false;
    String keyword="";
    public void search() {
        process=true;
        int count=keywordList.size();
        for (int i = 0; i < count; i++) {
            keyword=keywordList.poll();
            for (int y = 0; y < pagesize; y++) {
                try {
                    url="http://www.baidu.com/s?wd="+ URLEncoder.encode(keyword, "UTF-8")+"&pn="+(y*10)+"&oq="+URLEncoder.encode(keyword, "UTF-8")+"&tn=baidu&ie=utf-8&usm=8";
                    Document doc= Jsoup.connect(url).userAgent(USERAGENT).timeout(timeout).get();
                    Elements elements=doc.select("#content_left .c-container");
                    if(elements!=null&&elements.size()>0){
                        for (Element e:elements) {
                            log4j.info(new String("��ַ��"+e.select("h3.t a").attr("abs:href")));
                            log4j.info(new String("���⣺"+e.select("h3.t a").text()));
                            String valueString = new String("���⣺"+e.select("h3.t a").text());
                            System.out.println("------valueString= "+valueString);
                            log4j.info(" love ");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        process=false;
    }
    public static void main(String[] args) {
        new SearchBKeyword().search();
    }
}
