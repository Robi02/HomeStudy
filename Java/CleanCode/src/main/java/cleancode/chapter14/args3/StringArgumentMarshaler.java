package cleancode.chapter14.args3;

public class StringArgumentMarshaler extends ArgumentMarshaler {

    private String stringValue;

    @Override
    public Object get() {
        return stringValue;
    }

    @Override
    public void set(String s) {
        stringValue = s;
    }
}
