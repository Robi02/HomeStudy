package cleancode.chapter14.args4;

import java.util.Iterator;
import java.util.NoSuchElementException;

import cleancode.chapter14.args4.ArgsException.ErrorCode;

public class IntegerArgumentMarshaler extends ArgumentMarshaler {

    private int integerValue;

    @Override
    public void set(Iterator<String> currentArgument) throws ArgsException {
        String parameter = null;
        try {
            parameter = currentArgument.next();
            integerValue = Integer.parseInt(parameter);
        } catch (NoSuchElementException e) {
            throw new ArgsException(ErrorCode.MISSING_INTEGER);
        } catch (NumberFormatException e) {
            throw new ArgsException(ErrorCode.INVALID_INTEGER, parameter);
        }
    }

    @Override
    public Object get() {
        return integerValue;
    }
}
