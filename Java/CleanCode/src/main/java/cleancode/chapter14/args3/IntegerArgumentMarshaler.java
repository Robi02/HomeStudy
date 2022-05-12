package cleancode.chapter14.args3;

import java.rmi.AccessException;

public class IntegerArgumentMarshaler extends ArgumentMarshaler {

    private int integerValue;

    @Override
    public Object get() {
        return integerValue;
    }

    @Override
    public void set(String s) throws ArgsException {
        try {
            integerValue = 0;
        } catch (NumberFormatException e) {
            throw new ArgsException();
        }
    }
}
 