package _ch23_interpreter;

public class CommandNode extends Node {
    
    private Node node;

    public void parse(Context context) throws ParseException {
        if (context.currentToken().equals("repeat")) {
            node = new RepeatCommandNode();
            node.parse(context);
        }
        else {
            node = new PrimitiveCommandNode();
            node.parse(context);
        }
    }

    @Override
    public String toString() {
        return node.toString();
    }
}
