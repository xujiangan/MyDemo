package AsyncHttpClienDemo;

import com.alibaba.fastjson.JSONObject;
import common.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

public class TestHttpClient {
    public static void main(String[] args) {

        Map<String, Object> map = new HashMap<>();
        map.put("deviceId", "1212121");
        map.put("qrCode", "1234");
        map.put("capturedId", "2c90fff56831281e01683135a7c90004");
        //组装实体
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cccc", map);
        JSONObject result = null;

        try {
            result = HttpClientUtil.postJson("http://172.16.127.188:8088/nss-cloud2-api/api/v2/device/confirm_qr_code",
                    jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);

        //okhttpclient  需要复用okhttpclient
       /* try {
            *//*String response = HttpClientUtil.doGet("https://www.baidu.com");
            System.out.println(response);*//*


            *//*String postResponse = HttpClientUtil.doPost("http://172.16.127.123:8088/nss-cloud2-api/api/v2/device/confirm_qr_code", "123");
            System.out.println(postResponse);*//*

           // HttpClientUtil.doAsyncGet("https://www.baidu.com");

            FormBody.Builder builder = new FormBody.Builder();
            builder.add("cccc","1234");
            HttpClientUtil.doAsyncPost("http://172.16.127.123:8088/nss-cloud2-api/api/v2/device/confirm_qr_code"
                    ,builder.build());
        } catch (IOException e) {
            e.printStackTrace();
        }*/



    }
}
