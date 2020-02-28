import Model.VendingMachine;

public class VendingMachineMain {

    public static void main(String[] args) {

        //Initializing vending machine
        VendingMachine vendingMachine = new VendingMachine();

        vendingMachine.loadProducts(); //loading vending machine with 10 products
        vendingMachine.loadCoins(); //loading vending machine with coins for change

        vendingMachine.startVendingMachine(); //start
    }
}
