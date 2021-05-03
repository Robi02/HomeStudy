package _ch09_bridge;

import java.nio.charset.Charset;

public class StringDisplayImpl extends DisplayImpl {
    
    private String string;
    private int width;
    
    public StringDisplayImpl(String string) {
        this.string = string;
        this.width = string.getBytes(Charset.forName("euc-kr")).length;
    }

    public void rawOpen() {
        printLine();
    }

    public void rawPrint() {
        System.out.println("|" + this.string + "|");
    }

    public void rawClose() {
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
