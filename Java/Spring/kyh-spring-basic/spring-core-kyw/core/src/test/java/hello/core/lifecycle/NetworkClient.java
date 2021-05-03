package hello.core.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient /*implements InitializingBean, DisposableBean // @1 */  {
    
    private String url;

    public NetworkClient() {
        System.out.println("constructor: " + url);
        //connect();
        //call("초기화 연결 메시지."); // @0
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작 시
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + ", message: " + message);
    }

    // 서비스 종료 시
    public void disconnect() {
        System.out.println("disconnect: " + url);
    }

    /* // @1
    // 의존관계 주입(DI)종료 후 호출
    @Override
    public void afterPropertiesSet() throws Exception {
        connect();
        setUrl("https://de4bi.com");
        call("초기화 연결 메시지.");
    }

    // 빈이 종료될 때 호출
    @Override
    public void destroy() throws Exception {
        disconnect();
    }
    */

    /* // @2
    public void init() {
        connect();
        setUrl("https://de4bi.com");
        call("초기화 연결 메시지.");
    }

    public void close(){
        disconnect();
    }
    */

    @PostConstruct // @3
    public void init() {
        connect();
        setUrl("https://de4bi.com");
        call("초기화 연결 메시지.");
    }

    @PreDestroy
    public void close(){
        disconnect();
    }
}
