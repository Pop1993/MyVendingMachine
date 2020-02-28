package Model;

import Service.IOService;

public class Product {

    private Integer code;
    private String name;
    private String size;
    private Integer price;

    public Product(Integer code, String name, String size, Integer price) {
        this.code = code;
        this.name = name;
        this.size = size;
        this.price = price;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "" + IOService.addSpaces(code.toString())
                + IOService.addSpaces(name)
                + IOService.addSpaces(size)
                + IOService.addSpaces(price.toString());
    }
}
