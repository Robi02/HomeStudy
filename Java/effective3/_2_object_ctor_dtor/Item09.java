package _2_object_ctor_dtor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Item09 {
    /**
     * [Item09] try-finally보다는 try-with-resources를 사용하라
     * 
     * [핵심]
     * 꼭 회수해야 하는 자원을 다룰 때는 try-finally말고, try-with-resources(Java 1.7)을 사용하자.
     * 예외는 없다. 코드는 더 짧고 분명해지고 ,만들어지는 예외 정보도 훨씬 유용하다.
     * try-finally로 작성하면 실용적이지 못할 만큼 코드가 지저분해지는 경우라도,
     * try-with-resources로는 정확하고 쉽게 자원을 회수할 수 있다.
     * 
     */

    // [1] try-finally - 더 이상 자원을 회수하는 최선의 방책이 아니다!
    public static String firstLineOfFile1(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        try {
            return br.readLine();
        }
        finally {
            br.close();
        }
    }

    public static final int BUFFER_SIZE = 1024;

    // [2] 자원이 둘 이상이면 try-finally 방식은 너무 지저분하다!
    public static void copy1(String src, String dst) throws IOException {
        InputStream is = new FileInputStream(src);
        try {
            OutputStream os = new FileOutputStream(dst);
            try {
                byte[] buf = new byte[BUFFER_SIZE];
                int n;
                while ((n = is.read(buf)) >= 0)
                    os.write(buf, 0, n);
            }
            finally {
                os.close();
            }
        }
        finally {
            is.close();
        }
    }

    // [3] try-with-resources - 자원을 회수하는 최선책!
    public static String firstLineOfFile2(String path) throws IOException {
        // AutoCloseable 인터페이스를 구현한 객체를
        // try-with-resoruces의 대상 객체로 사용할 수 있다.
        // try 구문 종료 시점에 자동으로 close()를 호출해준다.
        // close()중 예외 발생 시 catch() 로 분기도 가능하다.
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine();
        }
    }

    // [4] 복수의 자원을 처리하는 try-with-resources - 짧고 매력적이다!
    public static void copy2(String src, String dst) throws IOException {
        try (InputStream is = new FileInputStream(src);
             OutputStream os = new FileOutputStream(dst)) {
            byte[] buf = new byte[BUFFER_SIZE];
            int n;
            while ((n = is.read(buf)) >= 0) {
                os.write(buf, 0, n);
            }
        }
    }

    // [5] try-with-resources를 catch절과 함께 쓰는 모습
    public static String firstLineOfFile3(String path, String defaultVal) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine();
        }
        catch (IOException e) {
            return defaultVal;
        }
    }

    public static void main(String[] args) {

    }
}