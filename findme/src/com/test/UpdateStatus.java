package com.test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import weibo4j.Status;
import weibo4j.Weibo; 
import weibo4j.WeiboException;
import weibo4j.http.AccessToken;
import weibo4j.http.RequestToken;
import weibo4j.util.BareBonesBrowserLaunch;

public class UpdateStatus { 

	/**
	 * 发布一条微博信息 
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
       try {
       	Weibo weibo = getWeibo(true,args);
       	Status status = weibo.updateStatus("测试发表微博");
       	System.out.println(status.getId() + " : "+ status.getText()+"  "+status.getCreatedAt());
       	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Weibo getWeibo(boolean isOauth,String ... args) throws WeiboException, IOException {
		Weibo weibo = new Weibo();
        // set callback url, desktop app please set to null
        // http://callback_url?oauth_token=xxx&oauth_verifier=xxx
        //1。根据app key第三方应用向新浪获取requestToken
        RequestToken requestToken = weibo.getOAuthRequestToken();
       
        System.out.println("1.......Got request token成功");
        System.out.println("Request token: "+ requestToken.getToken());
        System.out.println("Request token secret: "+ requestToken.getTokenSecret());
        AccessToken accessToken = null;
      //2。用户从新浪获取verifier_code 如果是Android或Iphone应用可以callback =json&userId=xxs&password=XXX
        System.out.println("Open the following URL and grant access to your account:");
        System.out.println(requestToken.getAuthorizationURL());
        BareBonesBrowserLaunch.openURL(requestToken.getAuthorizationURL());
       //3。用户输入验证码授权信任第三方应用
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (null == accessToken) {
            System.out.print("Hit enter when it's done.[Enter]:");
            String pin = br.readLine();
            System.out.println("pin: " + br.toString());
            try{
             //4。通过传递requestToken和用户验证码获取AccessToken
                accessToken = requestToken.getAccessToken(pin);
            } catch (WeiboException te) {
                if(401 == te.getStatusCode()){
                    System.out.println("Unable to get the access token.");
                }else{
                    te.printStackTrace();
                }
            }
        }       
        System.out.println("Got access token.");
        System.out.println("Access token: "+ accessToken.getToken());
        System.out.println("Access token secret: "+ accessToken.getTokenSecret());
       //使用AccessToken来操作用户的所有接口
       /* Weibo weibo=new Weibo();
        以后就可以用下面accessToken访问用户的资料了*/
        weibo.setToken(accessToken.getToken(), accessToken.getTokenSecret());
		return weibo;
	}
}