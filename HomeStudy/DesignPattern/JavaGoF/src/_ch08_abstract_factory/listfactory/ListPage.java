package _ch08_abstract_factory.listfactory;

import java.util.Iterator;

import _ch08_abstract_factory.factory.Item;
import _ch08_abstract_factory.factory.Page;

public class ListPage extends Page {

    public ListPage(String title, String author) {
        super(title, author);
    }

    @Override
    public String makeHTML() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<html><head><title>" + this.title + "</title></head>\n");
        buffer.append("<body>\n");
        buffer.append("<h1>" + this.title + "</h1>\n");
        buffer.append("<ul>\n");

        Iterator<Item> it = this.content.iterator();
        while (it.hasNext()) {
            Item item = (Item) it.next();
            buffer.append(item.makeHTML());
        }

        buffer.append("</ul>\n");
        buffer.append("<hr><address>" + this.author + "</address>\n");
        buffer.append("</body></html>\n");
        return buffer.toString();
    }
}
