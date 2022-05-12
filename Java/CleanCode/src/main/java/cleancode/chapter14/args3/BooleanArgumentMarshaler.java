package cleancode.chapter14.args3;

public class BooleanArgumentMarshaler extends ArgumentMarshaler {

    private boolean booleanValue = false;

    @Override
    public Object get() {
        return booleanValue;
    }

    @Override
    public void set(String s) {
        booleanValue = true;
    }
}
