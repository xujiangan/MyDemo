package AsyncHttpClienDemo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AsyncTest {

    //@Resource(name="asyncService")
    // private AsyncService asyncService;

    public static void main(String[] args) {
        new AsyncTest().test();
    }

    public void test() {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("spring/springs.xml");
            AsyncService asyncService = (AsyncService) context.getBean("asyncService");



            String s = asyncService.doWork2();
            asyncService.doWork1();
            System.out.println(s);
            //asyncService.doWork2();
            //asyncService.doWork3();

            //有返回结果的异步
           /* Future<String> future4 = asyncService.doWork4();
            while (true) {
                if (future4.isDone()) {
                    System.out.println("任务4也完成了，耗时：" + System.currentTimeMillis());
                    break;
                }
            }*/
            //Thread.sleep(100);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


}
