package _ch12_decorator;

public class SideBorder extends Border {

    private char borderChar;
    
    public SideBorder(Display display, char ch) {
        super(display);
        this.borderChar = ch;
    }

    @Override
    public int getColumns() {
        return 1 + this.display.getColumns() + 1;
    }

    @Override
    public int getRows() {
        return this.display.getRows();
    }

    @Override
    public String getRowText(int row) {
        return this.borderChar + this.display.getRowText(row) + this.borderChar;
    }
}
