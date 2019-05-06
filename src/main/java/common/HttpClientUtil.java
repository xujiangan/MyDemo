package common;


import com.alibaba.fastjson.JSONObject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.ws.http.HTTPException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

//import java.net.HttpURLConnection;

public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    public static JSONObject postJson(String strUrl, JSONObject object) throws Exception {
        JSONObject result = null;
        if (strUrl.startsWith("https")) {
            try {
                URL url = new URL(strUrl);
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                // HTTP
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                // 指定编码方式
                con.setRequestProperty("Content-Type", "application/JSON; charset=UTF-8");
                // post数据长度设定
                con.setRequestProperty("Content-Length", String.valueOf(object.toJSONString().length()));
                // 设定超时时间
                //con.setConnectTimeout(2000);
                con.setReadTimeout(10000);
                // 请求内容
                OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
                out.write(object.toJSONString());
                out.flush();
                con.connect();

                // 返回结果
                final InputStream in = con.getInputStream();
                String encoding = con.getContentEncoding();
                if (null == encoding) {
                    encoding = "UTF-8";
                }
                final InputStreamReader inReader = new InputStreamReader(in, encoding);
                final BufferedReader bufReader = new BufferedReader(inReader);
                StringBuffer resultStr = new StringBuffer();
                String line = null;
                while ((line = bufReader.readLine()) != null) {
                    resultStr.append(line);
                }
                result = JSONObject.parseObject(resultStr.toString());
                result.put("Status Code", con.getResponseCode());
                bufReader.close();
                inReader.close();
                in.close();
            } catch (ConnectTimeoutException ex) {
                throw new Exception("connection超时了");
            } catch (SocketTimeoutException ex) {
                throw new Exception("超时了");
            } catch (Exception e) {

            }
        } else if (strUrl.startsWith("http")) {
            try {
                URL url = new URL(strUrl);
                // 服务器版
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                // HTTP
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                // 指定编码方式
                con.setRequestProperty("Content-Type", "application/JSON;charset=UTF-8");
                // post数据长度设定
                con.setRequestProperty("Content-Length", String.valueOf(object.toJSONString().length()));
                // 设定超时时间
                con.setConnectTimeout(10);
                con.setReadTimeout(10000);
                // 请求内容
                OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
                out.write(object.toJSONString());
                out.flush();
                con.connect();

                // 返回结果
                final InputStream in = con.getInputStream();
                String encoding = con.getContentEncoding();
                if (null == encoding) {
                    encoding = "UTF-8";
                }
                final InputStreamReader inReader = new InputStreamReader(in, encoding);
                final BufferedReader bufReader = new BufferedReader(inReader);
                StringBuffer resultStr = new StringBuffer();
                String line = null;
                while ((line = bufReader.readLine()) != null) {
                    resultStr.append(line);
                }

                // httpclient 默认超时时间为20S  需要手动设置connectiontimeout
                if(con.getResponseCode() == 200){
                    result = JSONObject.parseObject(resultStr.toString());
                }
                else{

                }
                result.put("Status Code", con.getResponseCode());
                bufReader.close();
                inReader.close();
                in.close();

            } catch (HTTPException ex){
                ex.getStatusCode();
            }catch (ConnectTimeoutException ex) {
                throw new Exception("connection超时了");
            }catch (SocketTimeoutException ex) {
                throw new Exception("socket超时了");
            } catch (Exception e) {
                throw new Exception("xxx");
            }
        }

        return result;
    }

    public static JSONObject getJson(String strUrl) {
        JSONObject result = null;
        BufferedReader in = null;
        try {
            URL realUrl = new URL(strUrl);
            // 打开和URL之间的连接
            HttpsURLConnection connection = (HttpsURLConnection) realUrl.openConnection();
            ;
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            result = JSONObject.parseObject(sb.toString());
        } catch (Exception e) {

        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


    public static JSONObject getJson(String strUrl, JSONObject object, String token) {
        JSONObject result = null;
        if (strUrl.startsWith("https")) {
            try {
                URL url = new URL(strUrl);
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                // HTTP
                con.setDoOutput(true);
                con.setRequestMethod("GET");
                // 指定编码方式
                con.setRequestProperty("Content-Type", "application/JSON; charset=UTF-8");
                // post数据长度设定
                con.setRequestProperty("Content-Length", String.valueOf(object.toJSONString().length()));
                con.setRequestProperty("Authorization", "Bearer " + " " + token);
                // 设定超时时间
                con.setReadTimeout(10000);

                // 返回结果
                final InputStream in = con.getInputStream();
                String encoding = con.getContentEncoding();
                if (null == encoding) {
                    encoding = "UTF-8";
                }
                final InputStreamReader inReader = new InputStreamReader(in, encoding);
                final BufferedReader bufReader = new BufferedReader(inReader);
                StringBuffer resultStr = new StringBuffer();
                String line = null;
                while ((line = bufReader.readLine()) != null) {
                    resultStr.append(line);
                }
                result = JSONObject.parseObject(resultStr.toString());
                bufReader.close();
                inReader.close();
                in.close();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        } else if (strUrl.startsWith("http")) {
            try {
                URL url = new URL(strUrl);
                // 服务器版
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                // HTTP
                con.setDoOutput(true);
                con.setRequestMethod("GET");
                // 指定编码方式
                con.setRequestProperty("Content-Type", "application/JSON;charset=UTF-8");
                // post数据长度设定
                con.setRequestProperty("Content-Length", String.valueOf(object.toJSONString().length()));
                con.setRequestProperty("Authorization", "Bearer " + " " + token);
                // 设定超时时间
                con.setReadTimeout(10000);

                // 返回结果
                final InputStream in = con.getInputStream();
                String encoding = con.getContentEncoding();
                if (null == encoding) {
                    encoding = "UTF-8";
                }
                final InputStreamReader inReader = new InputStreamReader(in, encoding);
                final BufferedReader bufReader = new BufferedReader(inReader);
                StringBuffer resultStr = new StringBuffer();
                String line = null;
                while ((line = bufReader.readLine()) != null) {
                    resultStr.append(line);
                }
                result = JSONObject.parseObject(resultStr.toString());
                bufReader.close();
                inReader.close();
                in.close();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        return result;
    }

    public static JSONObject postJson(String strUrl, JSONObject object, String token) {
        JSONObject result = null;
        if (strUrl.startsWith("https")) {
            try {
                URL url = new URL(strUrl);
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                // HTTP
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                // 指定编码方式
                con.setRequestProperty("Content-Type", "application/JSON; charset=UTF-8");
                // post数据长度设定
                con.setRequestProperty("Content-Length", String.valueOf(object.toJSONString().length()));
                con.setRequestProperty("Authorization", "Bearer " + " " + token);
                // 设定超时时间
                con.setReadTimeout(10000);
                // 请求内容
                OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
                out.write(object.toJSONString());
                out.flush();
                con.connect();

                // 返回结果
                final InputStream in = con.getInputStream();
                String encoding = con.getContentEncoding();
                if (null == encoding) {
                    encoding = "UTF-8";
                }
                final InputStreamReader inReader = new InputStreamReader(in, encoding);
                final BufferedReader bufReader = new BufferedReader(inReader);
                StringBuffer resultStr = new StringBuffer();
                String line = null;
                while ((line = bufReader.readLine()) != null) {
                    resultStr.append(line);
                }
                result = JSONObject.parseObject(resultStr.toString());
                if (result != null) {
                    result.put("Status Code", con.getResponseCode());
                } else {
                    result = new JSONObject();
                    result.put("Status Code", con.getResponseCode());
                }
                bufReader.close();
                inReader.close();
                in.close();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        } else if (strUrl.startsWith("http")) {
            try {
                URL url = new URL(strUrl);
                // 服务器版
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                // HTTP
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                // 指定编码方式
                con.setRequestProperty("Content-Type", "application/JSON;charset=UTF-8");
                // post数据长度设定
                con.setRequestProperty("Content-Length", String.valueOf(object.toJSONString().length()));
                con.setRequestProperty("Authorization", "Bearer " + " " + token);
                // 设定超时时间
                con.setReadTimeout(10000);
                // 请求内容
                OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
                out.write(object.toJSONString());
                out.flush();
                con.connect();

                // 返回结果
                final InputStream in = con.getInputStream();
                String encoding = con.getContentEncoding();
                if (null == encoding) {
                    encoding = "UTF-8";
                }
                final InputStreamReader inReader = new InputStreamReader(in, encoding);
                final BufferedReader bufReader = new BufferedReader(inReader);
                StringBuffer resultStr = new StringBuffer();
                String line = null;
                while ((line = bufReader.readLine()) != null) {
                    resultStr.append(line);
                }
                result = JSONObject.parseObject(resultStr.toString());
                if (result != null) {
                    result.put("Status Code", con.getResponseCode());
                } else {
                    result = new JSONObject();
                    result.put("Status Code", con.getResponseCode());
                }
                bufReader.close();
                inReader.close();
                in.close();

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        return result;
    }

    //防止线程过多 不够用
    public final OkHttpClient clients = new OkHttpClient().newBuilder().build();

    public static String doPost(String url, String content) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), content);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String doGet(String url) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = httpClient.newCall(request).execute();
        return response.body().string();
    }

    public static void doAsyncGet(String url) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Call call = httpClient.newCall(request);
        String response = "";
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().toString());
            }
        });
    }

    public static void doAsyncPost(String url, FormBody body) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).post(body).build();
        Call call = httpClient.newCall(request);
        String response = "";
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //解析数据
                String str = response.body().toString();
                System.out.println(str);
            }
        });
    }

}
