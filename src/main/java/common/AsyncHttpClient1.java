package common;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 异步Http请求封装工具类
 */
public class AsyncHttpClient1
{
    private static CloseableHttpAsyncClient httpClient;
    private static volatile boolean isClientStart;

    /**
     * 创建CloseableHttpAsyncClient
     * @return
     */
    private static CloseableHttpAsyncClient createCustomAsyncClient()
    {
        //Preconditions.checkState(!isClientStart, "客户端HttpAsyncClient已经建立过了");
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                .setIoThreadCount(Runtime.getRuntime().availableProcessors())
                .setConnectTimeout(60000)
                .setSoTimeout(60000)
                .build();
        // 设置超时时间 毫秒为单位
        RequestConfig requestConfig = RequestConfig
                .copy(RequestConfig.DEFAULT)
                .setConnectTimeout(60000)
                .build();
        return HttpAsyncClients
                .custom()
                .setDefaultIOReactorConfig(ioReactorConfig)
                .setDefaultRequestConfig(requestConfig)
                .build();

    }


    public static void startHttpClient()
    {
        httpClient = createCustomAsyncClient();
        httpClient.start();
        isClientStart = true;
    }

    public static void closeHttpClient()
    {
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isClientStart = false;
    }

    public static void http(String method,
                            String url,
                            Object parameter,
                            StringFutureCallback callback)
    {
        //Preconditions.checkNotNull(method);
        if ("GET".equals(method))
        {
            get(url, callback);
        }
        else if ("POST".equals(method))
        {
            post(url, parameter, callback);
        }
    }


    public static void get(String url, StringFutureCallback callback)
    {
        //Preconditions.checkArgument(isClientStart, "还没有建立Http Client");
        HttpUriRequest request = new HttpGet(url);
        httpClient.execute(request, new DefaultFutureCallback(callback));
    }

    public static void post(String url, Object parameter, StringFutureCallback callback)
    {
        //Preconditions.checkArgument(isClientStart, "还没有建立Http Client");
        HttpPost httpPost = new HttpPost(url);
        if (parameter != null)
        {
            List<BasicNameValuePair> pairs = new ArrayList<>();
            UrlEncodedFormEntity entity = null;
            try {
                if (parameter instanceof HashMap)
                {
                    Map<String, String> parameters = (Map<String, String>) parameter;
                    parameters.forEach((k, v) -> pairs.add(new BasicNameValuePair(k,v)));
                    entity = new UrlEncodedFormEntity(pairs, "UTF-8");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            httpPost.setEntity(entity);
        }
        httpClient.execute(httpPost, new DefaultFutureCallback(callback));
    }


    /**
     * 字符串类型结果回调
     */
    public interface StringFutureCallback
    {
        void success(String content);
    }


    public static class DefaultFutureCallback implements FutureCallback<HttpResponse>
    {
        private StringFutureCallback callback;
        public DefaultFutureCallback(StringFutureCallback callback)
        {
            this.callback = callback;
        }
        @Override
        public void completed(HttpResponse httpResponse)
        {
            HttpEntity entity = httpResponse.getEntity();
            String content = "";
            try
            {
                content = EntityUtils.toString(entity, "UTF-8");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            callback.success(content);
        }

        @Override
        public void failed(Exception e)
        {
            e.printStackTrace();
        }

        @Override
        public void cancelled()
        {
            System.out.println("http request cancelled");
            //log.debug("http request cancelled");
        }
    }
}
