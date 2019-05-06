package AsyncHttpClienDemo;

import common.AsyncHttpClient1;

import java.util.HashMap;
import java.util.Map;

public class TestAsyncClient {

    public static void main(String[] args) {
        AsyncHttpClient1.startHttpClient();


        try {
            //GET
           /* AsyncHttpClient1.get("https://www.so.com", content -> {
                System.out.println("获取内容：");
                System.out.println(content);
            });

            AsyncHttpClient1.get("https://www.baidu.com", content -> {
                System.out.println("获取内容：");
                System.out.println(content);
            });*/


            // POST
            //组装数据
            Map<String, Object> map = new HashMap<>();
            map.put("deviceId", "1212121");
            map.put("qrCode", "1234");
            map.put("capturedId", "2c90fff56831281e01683135a7c90004");

            AsyncHttpClient1.post("http://172.16.127.123:8088/nss-cloud2-api/api/v2/device/confirm_qr_code",
                    map, content -> {
                        System.out.println(content);
                    });
        } catch (Exception ex) {

        } finally {
            //释放或者睡眠
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AsyncHttpClient1.closeHttpClient();
        }


    }
}
