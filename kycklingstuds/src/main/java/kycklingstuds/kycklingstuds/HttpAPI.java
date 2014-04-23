package kycklingstuds.kycklingstuds;


import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class HttpAPI {

    public static void getOrCreateUser(final String facebook_id, final String name  ) {
        // Create a new HttpClient and Post Header
       AsyncTask atask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                HttpClient httpclient = new DefaultHttpClient();

                System.out.println("DEBUG: FACEBOOK ID  : " + facebook_id);
                System.out.println("DEBUG: FACEBOOK NAME: " + name);
                HttpGet httpGet = new HttpGet("http://sheep-leap.raimat.webfactional.com/api/getOrCreateUserByFacebookID?name="+URLEncoder.encode(name)+"&facebookID="+facebook_id);

                try {

                    // Execute HTTP Post Request

                    HttpResponse response = httpclient.execute(httpGet);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                    StringBuilder builder = new StringBuilder();
                    for (String line; (line = reader.readLine()) != null;) {
                        builder.append(line).append("\n");
                    }
                    JSONTokener tokener = new JSONTokener(builder.toString());
                    try {
                        JSONObject finalResult = new JSONObject(tokener);
                        System.out.println("DEBUG: SERVER RESPONSE: " + finalResult);
                        System.out.println("DEBUG: SERVER RESPONSE STATUS: " + response.getStatusLine());
                        System.out.println("DEBUG: SERVER RESPONSE ID: " + finalResult.getInt("userID"));
                        Resources.CURRENT_USER_ID = finalResult.getInt("userID");
                    }
                    catch (Exception e){
                        System.out.println("DEBUG: JSON ERROR: " + e);
                    }



                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    System.out.println("DEBUG: ClientProtocolException: " + e);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    System.out.println("DEBUG: IOException: " + e);
                }
                return null;
            }
        };
        atask.execute();

    }

    public static void postNewHighscore(final int user_id, final int highscore  ) {
        // Create a new HttpClient and Post Header
        AsyncTask atask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                HttpClient httpclient = new DefaultHttpClient();

                System.out.println("DEBUG: USER ID  : " + user_id);
                System.out.println("DEBUG: HIGHSCORE: " + highscore);
                HttpGet httpGet = new HttpGet("http://sheep-leap.raimat.webfactional.com/api/addNewHighscore?score=" + highscore + "&user_id=" + user_id);

                try {
                    // Add your data
                    //BasicHttpParams params = new BasicHttpParams();
                    //params.setParameter("name", URLEncoder.encode(name,  "utf-8"));
                    //params.setParameter("facebook_id", URLEncoder.encode(facebook_id,  "utf-8"));
                    //httpGet.setParams(params);
                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httpGet);
                    System.out.println("DEBUG: SERVER RESPONSE: " + response.getStatusLine());
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    System.out.println("DEBUG: ClientProtocolException: " + e);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    System.out.println("DEBUG: IOException: " + e);
                }
                return null;
            }
        };
        atask.execute();

    }
}
