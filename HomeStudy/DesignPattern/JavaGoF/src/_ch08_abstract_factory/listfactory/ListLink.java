package _ch08_abstract_factory.listfactory;

import _ch08_abstract_factory.factory.Link;

public class ListLink extends Link {

    public ListLink(String caption, String url) {
        super(caption, url);
    }

    @Override
    public String makeHTML() {
        return "  <li><a href=\"" + this.url + "\">" + this.caption + "</a></li>\n";
    }
}
