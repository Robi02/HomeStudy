package cleancode.chapter14.args4;

import java.util.Iterator;

public abstract class ArgumentMarshaler {

    public abstract void set(Iterator<String> currentArgument)
                             throws ArgsException;
    public abstract Object get();
}
