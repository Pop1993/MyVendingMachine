package Model;

public enum ProductType {
    COLA(1001, "Coca Cola", "500mL", 5),
    FANTA(1002, "Fanta", "500mL", 5),
    SPRITE(1003, "Sprite", "500mL", 5),
    DORNA(1004, "Dorna", "500mL", 4),
    BORSEC(1005, "Borsec", "500mL", 4),
    CORN(1006, "7Days", "150g", 3),
    BAKEROLLS(1007, "Bakerolls", "130g", 3),
    SNICKERS(1008, "Snickers", "70g", 3),
    MARS(1009, "Mars", "70g", 3),
    TWIX(1010, "Twix", "70g", 3);

    private Integer code;
    private String name;
    private String size;
    private Integer price;

    ProductType(Integer code, String name, String size, Integer price) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.size = size;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
