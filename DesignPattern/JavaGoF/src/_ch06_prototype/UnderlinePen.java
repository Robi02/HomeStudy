package _ch06_prototype;

import java.nio.charset.Charset;

import _ch06_prototype.framework.Product;

public class UnderlinePen implements Product {

    private char ulchar;

    public UnderlinePen(char ulchar) {
        this.ulchar = ulchar;
    }

    @Override
    public void use(String s) {
        int length = s.getBytes(Charset.forName("EUC-KR")).length;
        System.out.println("\"" + s + "\"");
        System.out.print(" ");
        for (int i = 0; i < length; ++i) {
            System.out.print(this.ulchar);
        }
        System.out.println("");
    }

    @Override
    public Product createClone() {
        Product p = null;
        try {
            p = (Product) clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return p;
    }
}
