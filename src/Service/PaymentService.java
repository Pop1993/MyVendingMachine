package Service;

import Model.CoinType;
import Model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PaymentService {

    //this holds the inserted money and from this, they are added to the stock if payment is successfull
    //if not, coins are returned and this is cleared
    private List<CoinType> temporaryCoinHolder = new ArrayList<>();

    //this holds the change until we validate that we have all the required change, afte which we give it to user
    //if not, it is added back to the stock
    private List<CoinType> temporaryChangeHolder = new ArrayList<>();

    private final Map<CoinType, Integer> coinStock;
    private boolean isPaymentSuccessfull = false;


    public PaymentService(Map<CoinType, Integer> coinStock) {
        this.coinStock = coinStock;
    }

    public void processPayment(Product selectedProduct) {
        int amountToBePayed = selectedProduct.getPrice();
        int insertedSum = 0;
        boolean isCancelled = false;
        this.isPaymentSuccessfull = false; //this is to make sure with every payment processed, this will be false if payment fails

        IOService.displayMessage("Product: " + selectedProduct.getName() + ". Price: " + amountToBePayed);
        IOService.displayMessage("Type to insert: 1 for 1 RON, 5 for 5 RON, 10 for 10 RON or 50 for 50RON. Type 123 to cancel.");

        while (insertedSum < amountToBePayed) {
            IOService.displayMessage("Inserted: " + insertedSum + " RON.");
            int userInput = IOService.readUserInput();

            if (userInput == 123) {
                returnMoney(temporaryCoinHolder); //returns many if cancelled
                isCancelled = true;
                return;
            }

            CoinType insertedCoin = validateCoin(userInput);
            if (!(insertedCoin == null)) {
                temporaryCoinHolder.add(insertedCoin);
                insertedSum += userInput;
            }
        }

        if (isCancelled)
            return;

        IOService.displayMessage("Inserted: " + insertedSum + " RON.");

        boolean isChangeAvailable = validateChangeAvailability(insertedSum, amountToBePayed);

        if (isChangeAvailable) {
            IOService.displayMessage("Change to be returned: " + (insertedSum - amountToBePayed));
            giveChange(temporaryChangeHolder);
            temporaryChangeHolder.clear();
            balanceCoinStock(temporaryCoinHolder);
            this.isPaymentSuccessfull = true;
        } else {
            IOService.displayMessage("Change not available.");
            returnMoney(temporaryCoinHolder);
        }
    }

    private void balanceCoinStock(List<CoinType> temporaryCoinHolder) {
        for (CoinType each : temporaryCoinHolder) {
            if (coinStock.containsKey(each)) {
                coinStock.put(each, coinStock.get(each) + 1);
            } else {
                coinStock.put(each, 1);
            }
        }
        this.temporaryCoinHolder.clear();
    }

    private void returnMoney(List<CoinType> temporaryCoinHolder) {
        IOService.displayMessage("Payment has been cancelled. Returning mooney:");
        for (CoinType coin : temporaryCoinHolder) {
            IOService.displayMessage(" Returned " + coin.getValue() + " " + coin.getCurrency());
        }
        IOService.displayMessage("Inserted sum has been returned.");
        IOService.addLineBreak(2);
        this.temporaryCoinHolder.clear();
    }

    private void giveChange(List<CoinType> temporaryChangeHolder) {
        for (CoinType coin : temporaryChangeHolder) {
            IOService.displayMessage(" Change: " + coin.getValue() + " " + coin.getCurrency());
            coinStock.put(coin, coinStock.get(coin) - 1);
        }
        IOService.addLineBreak(1);
        this.temporaryChangeHolder.clear();
    }

    private CoinType validateCoin(int userInput) {
        if (userInput == 1) {
            return CoinType.UNU;
        } else if (userInput == 5) {
            return CoinType.CINCI;
        } else if (userInput == 10) {
            return CoinType.ZECE;
        } else if (userInput == 50) {
            return CoinType.CINCIZECI;
        } else {
            IOService.displayMessage("Invalid coin. Try again");
            return null;
        }
    }

    private boolean validateChangeAvailability(int insertedSum, int productValue) {

        if (insertedSum == productValue) {
            return true;
        } else {
            int changeToBeReturned = insertedSum - productValue;

            for (Map.Entry<CoinType, Integer> each : coinStock.entrySet()) {
                CoinType coinToBeChecked = each.getKey();

                while (coinToBeChecked.getValue() <= changeToBeReturned && coinStock.get(coinToBeChecked) > 0) {
                    temporaryChangeHolder.add(coinToBeChecked);
                    changeToBeReturned -= coinToBeChecked.getValue();
                }
            }

            if (changeToBeReturned > 0) {
                return false;
            } else {
                return true;
            }
        }
    }

    public boolean isPaymentSuccessfull() {
        return isPaymentSuccessfull;
    }
}
