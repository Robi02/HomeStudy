package _ch03_template_method;

import java.nio.charset.Charset;

public class StringDisplay extends AbstractDisplay {

    private String string;
    private int width;

    public StringDisplay(String string) {
        this.string = string;
        this.width = string.getBytes(Charset.forName("EUC-KR")).length;
    }

    @Override
    public void open() {
        printLine();
    }

    @Override
    public void print() {
        System.out.println("|" + this.string + "|");
    }

    @Override
    public void close() {
        printLine();
    }
    
    private void printLine() {
        System.out.print("+");
        for (int i = 0; i < this.width; ++i) {
            System.out.print("-");
        }
        System.out.println("+");
    }
}
