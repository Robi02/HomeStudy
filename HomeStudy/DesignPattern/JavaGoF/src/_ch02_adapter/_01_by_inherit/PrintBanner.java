package _ch02_adapter._01_by_inherit;

// Adapter | Wrapper
public class PrintBanner extends Banner implements Print {
    
    public PrintBanner(String string) {
        super(string);
    }

    public void printWeak() {
        showWithParen(); // 인터페이스(Print)와 클래스(Banner)의 매핑
    }

    public void printStrong() {
        showWithAster();
    }
}
