package _10_exception;

import java.util.concurrent.Future;

public class Item77 {
    /**
     * [Item77] 예외를 무시하지 말라
     * 
     * [핵심]
     * 너무 뻔하지만 반복해 각인시켜야 할 정도로 많은 사람들이 자주 어기고 있다.
     * catch 블록을 비워두면 예외가 존재할 이유가 없어진다.
     * 물론, FileInputStream을 닫을 때 발생하는 예외는 로깅정도만 하고 무시해도 된다.
     * (어차피 파일에 대한 작업을 다 한 뒤 닫기중 발생한 문제에 불과하므로)
     * 만약, 예외를 무시하기로 했다면 catch 블록 안에 그렇게 결정한 이유를 주석으로 남기고
     * 예외 변수의 이름도 ignored로 바꿔놓도록 하자.
     * 기억하라. 예외를 무시하지 말고 바깥으로 전파되게만 나둬도 최소한 디버깅 정보를 남긴 채
     * 프로그램이 신속히 중단되게는 할 수 있다.
     * 
     */
}