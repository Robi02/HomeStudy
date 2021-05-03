package _ch04_factory_method.idcard;

import java.util.ArrayList;
import java.util.List;

import _ch04_factory_method.framework.Factory;
import _ch04_factory_method.framework.Product;

public class IDCardFactory extends Factory {

    private List<String> owners = new ArrayList<>();

    @Override
    protected Product createProduct(String owner) {
        return new IDCard(owner);
    }

    @Override
    protected void registerProduct(Product product) {
        this.owners.add(((IDCard) product).getOwner());
    }

    public List<String> getOwners() {
        return this.owners;
    }
}
