package _ch13_visitor;

public interface Element {
    
    public abstract void accept(Visitor v);
}
