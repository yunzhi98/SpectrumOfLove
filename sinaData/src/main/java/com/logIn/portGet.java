package com.logIn;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wb-xuzhenbin on 2015/11/24.
 */
public class portGet {
    public static void main(String[] args){
        URL ur = null;
        BufferedReader reader = null;
        try {
           // ur = new URL("http://hq.sinajs.cn/list=sh601006");
            ur = new URL("https://api.weibo.com/2/statuses/friends_timeline.json");
            HttpURLConnection uc = (HttpURLConnection) ur.openConnection();
            reader = new BufferedReader(new InputStreamReader(ur.openStream(),"GBK"));
            String line;
            while ((line = reader.readLine()) != null)
            {
                System.out.print(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String line;
       // System.out.print("---->html="+html);
    }
}
