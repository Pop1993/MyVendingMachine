package Model;

import Service.CoinComparator;
import Service.IOService;
import Service.PaymentService;
import Service.ProductFactory;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class VendingMachine {
    private int pin = 1993; //pin used to shut down (or maybe something else)

    Map<Integer, List<Product>> productStock;
    Map<CoinType, Integer> coinStock;
    ProductFactory productFactory;
    PaymentService paymentService;

    public VendingMachine () {
        this.productStock = new TreeMap<>();
        this.productFactory = new ProductFactory();
        this.coinStock = new TreeMap<>(new CoinComparator());
        this.paymentService = new PaymentService(coinStock);
    }

    //starts the vending machine, which runs until pin is typed
    public void startVendingMachine () {
        while(true) {
            displayStock();
            int userInput = IOService.readUserInput();

            if(isPIN(userInput))
                return; //stops the vending machine

            //this validates user input. if not valid, it will skip the rest and start from the top of the while
            boolean codeValidity = validateUserInput(userInput);
            if (!codeValidity) //jumps this loop if code is valid
                continue;

            //if we get to this point, it means code has been validated (is correct and product in stock)
            //codeValidity == true
            //we can continue with the payment
            Product selectedProduct = productStock.get(userInput).get(0);
            IOService.addLineBreak(1);
            paymentService.processPayment(selectedProduct);

            if (paymentService.isPaymentSuccessfull()) {
                releaseProduct(selectedProduct);
            }
        }
    }

    public void loadProducts() {
        for (ProductType type : ProductType.values()) {
            List<Product> products = productFactory.createProducts(10, type);
            productStock.put(type.getCode(), products);
        }
        IOService.addLineBreak(1);
        IOService.displayMessage("Vending machine fully loaded.");
        IOService.addLineBreak(1);
    }

    public void loadCoins() {
        coinStock.put(CoinType.UNU, 500);
        coinStock.put(CoinType.CINCI, 1);
        coinStock.put(CoinType.ZECE, 5);
        IOService.displayMessage("Coin stock fully loaded: "
                + coinStock.get(CoinType.UNU) + " x " + CoinType.UNU.getValue() + CoinType.UNU.getCurrency() + "; "
                + coinStock.get(CoinType.CINCI) + " x " + CoinType.CINCI.getValue() + CoinType.CINCI.getCurrency() + "; "
                + coinStock.get(CoinType.ZECE) + " x " + CoinType.ZECE.getValue() + CoinType.ZECE.getCurrency() );
        IOService.addLineBreak(1);
    }

    private void releaseProduct(Product selectedProduct) {
        IOService.displayMessage("Releasing " + selectedProduct.getName());
        List<Product> products = productStock.get(selectedProduct.getCode());
        products.remove(0);
        productStock.put(selectedProduct.getCode(), products);

        IOService.displayMessage("Enjoy!!!");
        IOService.addLineBreak(1);
    }

    //checks if input is PIN. If it is, machine shuts down (Process finishes)
    private boolean isPIN(int userInput) {
        if (userInput == pin) {
            IOService.displayMessage("Authentication successfull. Machine shutting down...");
            return true;
        }
        return false;
    }

    //validates user input and returns true (valid) or false (invalid)
    private boolean validateUserInput(int userInput) {
        boolean codeValidity = false;

        if(!(productStock.containsKey(userInput))) {
            //code not valid so the loop will continue until valid code is typed
            IOService.displayMessage("Code " + userInput + " not valid! Try again...");
        } else if (productStock.get(userInput).size() < 1) {
            //product out of stock, so the loop will continue until available product selected
            IOService.displayMessage("Product out of Stock!");
        } else {
            //code is valid -> break the loop -> continue with the purchase
            codeValidity = true;
        }

        return codeValidity;
    }

    //displays the available stock and messages
    private void displayStock() {
        IOService.displayMessage("Type code for desired product.");
        IOService.addLineBreak(1);
        IOService.displayMessage(IOService.addSpaces("Code")
            + IOService.addSpaces("Product") + IOService.addSpaces("Size")
            + IOService.addSpaces("Price(RON)") + IOService.addSpaces("Available Stock"));

        IOService.addLineBreak(1);

        for (Integer eachCode : productStock.keySet()) {
            List<Product> product = productStock.get(eachCode);
            if(product.size() > 0) {
                IOService.displayMessage(product.get(0).toString() + product.size());
            }
        }
        IOService.addLineBreak(2);
    }

}
