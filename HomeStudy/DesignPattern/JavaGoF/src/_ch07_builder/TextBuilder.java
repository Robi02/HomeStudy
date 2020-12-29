package _ch07_builder;

public class TextBuilder extends Builder {

    private StringBuffer buffer = new StringBuffer();

    @Override
    public void makeTitle(String title) {
        this.buffer.append("================================\n");
        this.buffer.append("『" + title + "』\n\n");
    }

    @Override
    public void makeString(String str) {
        this.buffer.append('■' + str + "\n\n");
    }

    @Override
    public void makeItems(String[] items) {
        for (int i = 0; i < items.length; ++i) {
            this.buffer.append(" ·" + items[i] + "\n");
        }
        this.buffer.append("\n");
    }

    @Override
    public void close() {
        this.buffer.append("================================\n");
    }

    public String getResult() {
        return this.buffer.toString();
    }
}
