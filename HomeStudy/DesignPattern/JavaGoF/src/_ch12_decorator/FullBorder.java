package _ch12_decorator;

public class FullBorder extends Border {

    public FullBorder(Display display) {
        super(display);
    }

    @Override
    public int getColumns() {
        return 1 + this.display.getColumns() + 1;
    }

    @Override
    public int getRows() {
        return 1 + this.display.getRows() + 1;
    }

    @Override
    public String getRowText(int row) {
        if (row == 0) {
            return "+" + makeLine('-', this.display.getColumns()) + "+";
        }
        else if (row == this.display.getRows() + 1) {
            return "+" + makeLine('-', this.display.getColumns()) + "+";
        }
        else {
            return "|" + this.display.getRowText(row - 1) + "|";
        }
    }
    
    private String makeLine(char ch, int count) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < count; ++i) {
            buf.append(ch);
        }
        return buf.toString();
    }
}
