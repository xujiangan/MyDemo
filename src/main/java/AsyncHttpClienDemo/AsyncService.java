package AsyncHttpClienDemo;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;


/**
 *
 * 加上@async后将函数调用变为异步处理(可根据需要自己定义线程池数目)
 * 需要调用的函数都需要注入，否则无法生效
 * :1.所有方法必须为public
 * :2.所有方法不能为static 报错
 * :3.调用方法和异步方法不能在同一个函数中 ***
 * */
@Service("asyncService")
public  class AsyncService {
    @Async
    public void doWork1() throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println("dowork1 start...");
        Thread.sleep(2000);
        System.out.println("dowork1 end 耗时" + (System.currentTimeMillis() - startTime) + "秒");
    }


    public String doWork2() throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println("dowork2 start...");
        Thread.sleep(2000);
        System.out.println("dowork2 end 耗时" + (System.currentTimeMillis() - startTime) + "秒");

        //doWork1();

        return "work2";
    }


    public void doWork3() throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println("dowork3 start...");
        Thread.sleep(2000);
        System.out.println("dowork3 end 耗时" + (System.currentTimeMillis() - startTime) + "秒");
    }


    public Future<String> doWork4() throws Exception{
        long startTime = System.currentTimeMillis();
        System.out.println("dowork4 start...");
        Thread.sleep(2000);
        System.out.println("dowork4 end 返回耗时" + (System.currentTimeMillis() - startTime) + "秒");
        return new AsyncResult<>("异步返回了");
    }
}
