package cleancode.chapter14.args3;

public abstract class ArgumentMarshaler {

    public abstract Object get();
    public abstract void set(String s) throws ArgsException;
}
