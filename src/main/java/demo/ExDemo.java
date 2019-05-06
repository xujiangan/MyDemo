package demo;

public class ExDemo {
    public static void main(String[] args) {
        try {

            int s = 0;
            for (int i = 0; i < 10; i++) {
                s = s += i;
            }
            System.out.println("");
        } catch (Exception e) {
            System.out.println(e.toString() + "----------------" + e.getMessage());
        }

    }
}
