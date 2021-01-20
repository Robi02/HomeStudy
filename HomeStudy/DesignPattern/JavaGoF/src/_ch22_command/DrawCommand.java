package _ch22_command;

import java.awt.Point;

public class DrawCommand implements Command {
    
    protected Drawable drawable;
    private Point position;

    public DrawCommand(Drawable drawble, Point position) {
        this.drawable = drawble;
        this.position = position;
    }

    @Override
    public void execute() {
        drawable.draw(position.x, position.y);
    }
}
