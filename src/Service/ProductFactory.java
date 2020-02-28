package Service;

import Model.Product;
import Model.ProductType;

import java.util.ArrayList;
import java.util.List;

public class ProductFactory {

    public List<Product> createProducts (int howMany, ProductType type) {
        List<Product> result = new ArrayList<>();

        for (int index = 0; index < howMany; index++) {
            result.add(new Product(type.getCode(), type.getName(), type.getSize(), type.getPrice()));
        }
        return result;
    }
}
