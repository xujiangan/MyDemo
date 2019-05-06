package demo;



import java.io.Serializable;

public class FormatterSimple {

   /**
    * @Author xujan
    * @Description //TODO nss
    * @Date 15:07 2019/3/27
    * @Param [args]
    * @return void
    **/
    public static void main(String[] args) {
        // 格式化字符串
        System.out.println(String.format("newstarsea","%s","test"));
    }


   /**
    * @Author xujan
    * @Description //TODO nss
    * @Date 15:06 2019/3/27
    * @Param [testNumber]
    * @return void
    **/
    public void Test(String testNumber){

    }

}

class TestEntity implements Serializable {

    private static final long serialVersionUID = -9203655417932533146L;
}
