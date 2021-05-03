package _ch04_factory_method.idcard;

import _ch04_factory_method.framework.Product;

public class IDCard extends Product {

    private String owner;

    IDCard(String owner) {
        this.owner = owner;
        System.out.println(this.owner + "의 카드를 만듭니다.");
    }

    @Override
    public void use() {
        System.out.println(this.owner + "의 카드를 사용합니다.");
    }

    public String getOwner() {
        return this.owner;
    }
    
}
