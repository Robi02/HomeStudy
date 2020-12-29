package _ch07_builder;

public class Director {
    
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public void construct() {
        this.builder.makeTitle("Greeting");
        this.builder.makeString("아침과 낮에");
        this.builder.makeItems(new String[] {
            "좋은 아침입니다.",
            "안녕하세요."
        });
        this.builder.makeString("밤에");
        this.builder.makeItems(new String[] {
            "안녕하세요.",
            "안녕히 주무세요.",
            "안녕히 계세요."
        });
        this.builder.close();
    }
}
