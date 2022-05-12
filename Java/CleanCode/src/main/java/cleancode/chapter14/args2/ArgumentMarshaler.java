package cleancode.chapter14.args2;

public class ArgumentMarshaler {

    private boolean booleanValue;
    private String stringValue;
    private int integerValue;

    public boolean getBoolean() {
        return booleanValue;
    }

    public void setBoolean(boolean value) {
        booleanValue = value;
    }

    public void setString(String s) {
        stringValue = s;
    }

    public String getString() {
        return stringValue == null ? "" : stringValue;
    }

    public int getInteger() {
        return integerValue;
    }

    public void setInteger(int i) {
        integerValue = i;
    }

    private class BooleanArgumentMarshaler {}
    private class StringArgumentMarshaler {}
    private class IntegerArgumentMarshaler {}
}
