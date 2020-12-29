package _ch08_abstract_factory.tablefactory;

import java.util.Iterator;

import _ch08_abstract_factory.factory.Item;
import _ch08_abstract_factory.factory.Tray;

public class TableTray extends Tray {

     public TableTray(String caption) {
         super(caption);
     }

    @Override
    public String makeHTML() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<td>");
        buffer.append("<table width=\"100%\" border=\"1\"><tr>");
        buffer.append("<td bgcolor=\"#cccccc\" align=\"center\" colspan=\"" + 
            this.tray.size() + "\"><b>" + this.caption + "</br></td>");
        buffer.append("</tr>\n");
        buffer.append("<tr>\n");

        Iterator<Item> it = this.tray.iterator();
        while (it.hasNext()) {
            Item item = it.next();
            buffer.append(item.makeHTML());
        }

        buffer.append("</tr></table>");
        buffer.append("</td>");
        return buffer.toString();
    }
}
