package com.logIn;

/**
 * Created by wb-xuzhenbin on 2015/11/23.
 */
import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Document;

public class HttpTools {
    /**
     * 正常GET方式HTTP请求
     * @param client
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String getRequest(HttpClient client, String url) throws  IOException {

        HttpGet get = new HttpGet(url);
        get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11");
        HttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity, "GBK");
        return content;
    }

    /**
     * 正常POST方式HTTP请求
     * @param client
     * @param url
     * @param parms
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String postRequest(HttpClient client, String url, List<NameValuePair> parms) throws IOException {
        HttpPost post = new HttpPost(url);
        post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11");
        post.addHeader("Content-Type", "application/x-www-form-urlencoded");
        UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(parms, "UTF-8");
        post.setEntity(postEntity);
        HttpResponse response = client.execute(post);
        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity, "GBK");
        return content;
    }
}