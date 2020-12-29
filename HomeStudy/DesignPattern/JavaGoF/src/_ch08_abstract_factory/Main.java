package _ch08_abstract_factory;

import _ch08_abstract_factory.factory.Factory;
import _ch08_abstract_factory.factory.Link;
import _ch08_abstract_factory.factory.Page;
import _ch08_abstract_factory.factory.Tray;

public class Main {
    
    public static void main(String[] args) {
        final String[] classNames = {"_ch08_abstract_factory.listfactory.ListFactory", "_ch08_abstract_factory.tablefactory.TableFactory"};
        
        for (int i = 0; i < classNames.length; ++i) {
            Factory factory = Factory.getFactory(classNames[i]);
            Link joins = factory.createLink("중앙일보", "http://www.joins.com");
            Link chosun = factory.createLink("조선일보", "http://www.chosun.com");
            Link google = factory.createLink("Google", "http://www.google.com");
            Link naver = factory.createLink("NAVER", "http://www.naver.com");

            Tray trayNews = factory.createTray("신문");
            trayNews.add(joins);
            trayNews.add(chosun);

            Tray traySearch = factory.createTray("검색엔진");
            traySearch.add(google);
            traySearch.add(naver);

            Page page = factory.createPage("LinkPage", "영진닷컴");
            page.add(trayNews);
            page.add(traySearch);
            page.output();
        }
    }
}
