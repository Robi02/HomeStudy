package _ch02_adapter._02_by_delegate;

public class PrintBanner extends Print {
    
    private Banner banner; // 객체(Banner)와 추상클래스(Print)의 매핑

    public PrintBanner(String string) {
        this.banner = new Banner(string);
    }

    public void printWeak() {
        banner.showWithParen();
    }

    public void printStrong() {
        banner.showWithAster();
    }
}
