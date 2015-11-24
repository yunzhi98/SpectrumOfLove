package com.logIn;

/**
 * Created by wb-xuzhenbin on 2015/11/23.
 */

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.logIn.unit.BigIntegerRSA;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
public class WeiboDog {
    private HttpClient client;
    private String username;    //登录帐号(明文)
    private String password;    //登录密码(明文)
    private String su;          //登录帐号(Base64加密)
    private String sp;          //登录密码(各种参数RSA加密后的密文)
    private long servertime;    //初始登录时，服务器返回的时间戳,用以密码加密以及登录用
    private String nonce;       //初始登录时，服务器返回的一串字符，用以密码加密以及登录用
    private String rsakv;       //初始登录时，服务器返回的一串字符，用以密码加密以及登录用
    private String pubkey;      //初始登录时，服务器返回的RSA公钥
    private String errInfo;     //登录失败时的错误信息
    private String location;    //登录成功后的跳转连接
    private String resultUser;//登入成功后的html返回

    private String uid;//微博主页下拉跳转参数
    private String wvr;//微博主页下拉跳转参数
    private String lf;//微博主页下拉跳转参数

    private String end_id;//微博主页下拉第一次跳转参数
    private String min_id;//微博主页下拉第一次跳转参数
    private String end_id2;//微博主页下第二次拉跳转参数
    private String min_id2;//微博主页下拉第二次跳转参数

    public WeiboDog(String username, String password) {
        client = new DefaultHttpClient();
        this.username = username;
        this.password = password;
    }
    /**
     * 初始登录信息&lt;br&gt;
     * 返回false说明初始失败
     * @return
     */
    public boolean preLogin() {
        boolean flag = false;
        try {
            su = new String(Base64.encodeBase64(URLEncoder.encode(username, "UTF-8").getBytes()));
            String url = getUrl(1);
            String content;
            content = HttpTools.getRequest(client, url);
            System.out.print("------>content="+content);
            content = StringUtils.substringBetween(content, "(", ")");
            JSONObject json = JSONObject.fromObject(content);
            servertime = json.getLong("servertime");
            nonce = json.getString("nonce");
            rsakv = json.getString("rsakv");
            pubkey = json.getString("pubkey");
            flag = encodePwd();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        return flag;
    }

    /**
     * 登录
     * @return true:登录成功
     */
    public boolean login() {
        if(preLogin()) {
            String url = getUrl(2);
            List<NameValuePair> parms = new ArrayList<NameValuePair>();
            parms.add(new BasicNameValuePair("entry", "weibo"));
            parms.add(new BasicNameValuePair("geteway", "1"));
            parms.add(new BasicNameValuePair("from", ""));
            parms.add(new BasicNameValuePair("savestate", "7"));
            parms.add(new BasicNameValuePair("useticket", "1"));
            parms.add(new BasicNameValuePair("pagerefer", "http://login.sina.com.cn/sso/logout.php?entry=miniblog&r=http%3A%2F%2Fweibo.com%2Flogout.php%3Fbackurl%3D%2F"));
            parms.add(new BasicNameValuePair("vsnf", "1"));
            parms.add(new BasicNameValuePair("su", su));
            parms.add(new BasicNameValuePair("service", "miniblog"));
            parms.add(new BasicNameValuePair("servertime", servertime + ""));
            parms.add(new BasicNameValuePair("nonce", nonce));
            parms.add(new BasicNameValuePair("pwencode", "rsa2"));
            parms.add(new BasicNameValuePair("rsakv", rsakv));
            parms.add(new BasicNameValuePair("sp", sp));
            parms.add(new BasicNameValuePair("sr", "1366*768"));
            parms.add(new BasicNameValuePair("encoding", "UTF-8"));
            parms.add(new BasicNameValuePair("prelt", "837"));
            parms.add(new BasicNameValuePair("url", "http://weibo.com/ajaxlogin.php?framelogin=1&callback=parent.sinaSSOController.feedBackUrlCallBack"));
            parms.add(new BasicNameValuePair("returntype", "META"));
            try {
                String content = HttpTools.postRequest(client, url, parms);
                String jsonBody = StringUtils.substringBetween(content, "replace('", "')")+"";
                if(jsonBody != null) {
                    location = jsonBody;
                    String result = HttpTools.getRequest(client, location);
                    resultUser = result;
                    System.out.println("result--------------" + result);
                    return true;
                }
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }
    /**
     * 密码进行RSA加密&lt;br&gt;
     * 返回false说明加密失败
     * @return
     */
    private boolean encodePwd(){
        String value = servertime+"\t"+nonce+"\n"+password;
        try {
            sp = new BigIntegerRSA().rsaCrypt(pubkey, "10001", value);
            return true;
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 获取时间戳
     * @return
     */
    private long getTimestamp() {
        Date now = new Date();
        System.out.println("now.getTime()="+now.getTime());
        return now.getTime();
    }
    /**
     * 个人微博主页跳转
     * @return
     */
    private String getUserWeibo(){
        uid =StringUtils.substringBetween(resultUser, "uniqueid\":\"", "\",\"userid")+"";
        wvr =StringUtils.substringBetween(resultUser, "wvr=", "&lf")+"";
        lf =StringUtils.substringBetween(resultUser, "lf=", "\"}")+"";
        String url = getUrl(3);
          try {
          String weiboHtml = HttpTools.getRequest(client, url);
            //后续加载
          if(weiboHtml !=null){
              String weiboHtml2 = nextGetHtml(weiboHtml);
              System.out.print("------>weiboHtml2="+weiboHtml2);
          }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     *个人微博 下拉加载
     * @param
     * @throws ClientProtocolException
     * @throws IOException
     */
    private String nextGetHtml(String html){
        String backHtml="";
        if(cutOutString(html,1)){
            String url3 = getUrl(4);
            try {
                String getHtml = HttpTools.getRequest(client, url3);
                if(cutOutString(getHtml,2)){//下拉后一个加载
                    String url = getUrl(5);
                    String getHtml2 = HttpTools.getRequest(client, url3);
                    System.out.println("---------->getHtml2="+getHtml2);
                }
                backHtml = getHtml;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return backHtml;
    }

    /**
     * 对获取的url 拼接
     * @return
     */
    private String getUrl(int size){
        String url = "";
        if(size ==1 ){//1为第一次预登入模拟url
            url = "http://login.sina.com.cn/sso/prelogin.php?entry=weibo&callback=sinaSSOController.preloginCallBack&" +
                "su="+su+"t&rsakt=mod&checkpin=1&client=ssologin.js(v1.4.18)&_=" + getTimestamp();
        }else if(size == 2 ){//2为预登入成功后的获取成熟请求url
            url = "http://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.18)";
        }else if(size == 3){//3登入成功后的获取html请求url
            url = "http://weibo.com/u/"+uid+"/home?wvr="+wvr+"&lf="+lf;
        }else if(size == 4 ){//4为微博个人主页下拉第一次请求
            url = "http://weibo.com/aj/mblog/fsearch?ajwvr=6" +
                    "&wvr="+wvr+"&lf="+lf+"&pre_page=1&page=1&end_id="+end_id+"" +
                    "&min_id="+min_id+"&pagebar=0&__rnd="+getTimestamp();
        }else if(size == 5 ){//4为微博个人主页下拉第二次请求
            url = "http://weibo.com/aj/mblog/fsearch?ajwvr=6" +
                    "&wvr="+wvr+"&lf="+lf+"&pre_page=1&page=1&end_id="+end_id+"" +
                    "&min_id="+min_id+"&pagebar=1&__rnd="+getTimestamp();
        }else if(size ==6 ){//下一页url拼接

        }
        return url;
    }

    /**
     * 对微博的mid节点截取
     * @param html
     * @return
     */
    private boolean cutOutString(String html,int size){
        boolean back = false;
        String[] arraw = StringUtils.substringsBetween(html, "WB_cardwrap WB_feed_type S_bg2", ">");
        String endGet = arraw[1];
        String minGet = arraw[arraw.length-1];
        System.out.print(endGet);
        if(size == 1){//第一次跳转
            end_id =StringUtils.substringBetween(endGet, "mid=\\\"", "\\\"")+"";
            min_id =StringUtils.substringBetween(minGet, "mid=\\\"", "\\\"")+"";
            if(end_id !=null && min_id!=null){
                back = true;
            }
        }else if (size == 2){//第二次跳转
            end_id2 =StringUtils.substringBetween(endGet, "mid=\\\"", "\\\"")+"";
            min_id2 =StringUtils.substringBetween(minGet, "mid=\\\"", "\\\"")+"";
            if(end_id2 !=null && min_id2!=null){
                back = true;
            }
        }
        return back;
    }
    /**
     * 对微博的WB_cardwrap WB_feed_type S_bg2节点截取
     * @param html
     * @return
     */
    private StringBuffer cutOutByCardwrap(String html){
        boolean back = false;
        String[] arraw = StringUtils.substringsBetween(html, "WB_cardwrap WB_feed_type S_bg2", "WB_cardwrap WB_feed_type S_bg2");
        String last =StringUtils.substringBetween(html, "mid=\\\"", "\\\"")+"";
        return null;
    }

    public static void main(String[] args) throws ClientProtocolException, IOException {
        WeiboDog weibo = new WeiboDog("549271427@qq.com", "yunzhi98");
        if(weibo.login()) {
            System.out.println("登陆成功！");
            //获取个人微博主页跳转url跳转
            String html = weibo.getUserWeibo();
            System.out.println("-------userHtml="+html);
        } else {
            System.out.println("登录失败！");
        }
    }
}