package _ch15_facade;

public class Main {
    
    public static void main(String[] args) {
        // maildata.txt 파일을 src디렉토리와 동일 경로에 위치 후 테스트 가능
        PageMaker.makeWelcomPage("youngjin@youngjin.com", "welcom.html");
    }
}
