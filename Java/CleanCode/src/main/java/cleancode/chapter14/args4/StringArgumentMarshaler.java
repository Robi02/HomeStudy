package cleancode.chapter14.args4;

import java.util.Iterator;
import java.util.NoSuchElementException;

import cleancode.chapter14.args4.ArgsException.ErrorCode;

public class StringArgumentMarshaler extends ArgumentMarshaler {

    private String stringValue;

    @Override
    public void set(Iterator<String> currentArgument) throws ArgsException {
        try {
            stringValue = currentArgument.next();
        } catch (NoSuchElementException e) {
            throw new ArgsException(ErrorCode.MISSING_STRING);
        }
    }

    @Override
    public Object get() {
        return stringValue;
    }
}
