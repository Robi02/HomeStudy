package cleancode.chapter14.args4 ;

import java.util.Iterator;

public class BooleanArgumentMarshaler extends ArgumentMarshaler {

    private boolean booleanValue = false;

    @Override
    public void set(Iterator<String> currentArgument) throws ArgsException {
        booleanValue = true;
    }

    @Override
    public Object get() {
        return booleanValue;
    }
}
