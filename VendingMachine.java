import java.util.Scanner;
public class VendingMachine {

    public VendingMachine(int maxSlots, int slotMaxItems, int passcode) {
        this.itemSlots = new ItemSlot[maxSlots];    //initializing the number of slots in the vending machine
        for (int i = 0; i < maxSlots; i++) {    //loop to assign SLOTNUMBER and max items in each slot
            itemSlots[i] = new ItemSlot(i + 1, slotMaxItems);
        }
        startingInventory = null;   //there is no startingInventory when the Vending Machine is first instantiated
        transactionLog = new ItemTransaction[maxSlots]; //initializing the number of possible items for the item transactions
        money = new Money();
        maintenanceCode = passcode;
    }

    public void mainMenu(Money wallet) {
        boolean bExit;
        boolean bTransaction = false;
        Scanner sc = new Scanner(System.in);
        int slotSelection;
        Item itemSelection;

        displayItemMenu();
        do {
            System.out.println("(0) Exit");
            System.out.println("Pick an Item: ");
            slotSelection = sc.nextInt();
            if (slotSelection != maintenanceCode) {
                bExit = chooseItem(slotSelection);
                if (bExit && slotSelection != 0) {
                    itemSelection = itemSlots[slotSelection - 1].getItem();
                    bTransaction = receivePayment(itemSelection.getCost(), wallet);

                    if (bTransaction)
                        //make transactionlog

                        mainMenu(wallet);
                }
            }
            else
                maintenance();
        } while (!bExit);   //exit detection also exists within the chooseItem() method

        if (bTransaction)
            System.out.println("Thank You for Your Purchase!");
        else
            System.out.println("Thank You Come Again!");
    }

    private void displayItemMenu(){

    }

    private boolean chooseItem(int slot){
        boolean b = false;
        ItemSlot selectedSlot = null;
        Item selectedItem = null;
        int temp;
        Scanner sc = new Scanner(System.in);

        if (slot == 0)  //slot selection at 0 for exit per user input
            return true;

        for (ItemSlot itemSlot: itemSlots){ //looks for the corresponding slot number in the itemSlots array
            if (slot == itemSlot.getSlotNumber()) {
                b = true;
                selectedSlot = itemSlot;
                selectedItem = selectedSlot.getItem();
            }
        }

        if (!b) //identifying whether the item selection is valid
            System.out.println("Error: Invalid Item Selection");
        else {  //details of the selected item
            System.out.println("=========================");
            System.out.println("(" + slot + ")Selected Item: " + selectedItem.getName());
            System.out.println("Price:           " + selectedItem.getCost() + "PHP");
            System.out.println("Calorie/s:       " + selectedItem.getCalories());
            System.out.println("=========================");
        }

        return b;
    }

    private boolean receivePayment(int cost, Money wallet) {
        int change;
        int temp = -1;
        Money payment = new Money();
        Money tempWallet = new Money(wallet);
        Scanner sc = new Scanner(System.in);
        boolean b = false;

        System.out.println("Amount to Pay: " + cost + " PHP");
        System.out.println("=========================");
        System.out.println("(0) Cancel Payment");
        System.out.println("Insert Bills/Coins: ");
        System.out.println("(1) 1 Peso");
        System.out.println("(2) 5 Pesos");
        System.out.println("(3) 10 Pesos");
        System.out.println("(4) 20 Pesos");
        System.out.println("(5) 50 Pesos");
        System.out.println("(6) 100 Pesos");
        System.out.println("(7) 200 Pesos");
        System.out.println("(8) 500 Pesos");
        System.out.println("(9) 1000 Pesos");
        System.out.println("=========================");

        while (payment.getMoney() < cost && temp != 0) {
            System.out.println("Insert: ");
            temp = sc.nextInt();
            switch (temp) {
                case 1:
                    payment.addOnePeso(1);
                    break;
                case 2:
                    payment.addFivePeso(1);
                    break;
                case 3:
                    payment.addTenPeso(1);
                    break;
                case 4:
                    payment.addTwentyPeso(1);
                    break;
                case 5:
                    payment.addFiftyPeso(1);
                    break;
                case 6:
                    payment.addOneHundredPeso(1);
                    break;
                case 7:
                    payment.addTwoHundredPeso(1);
                    break;
                case 8:
                    payment.addFiveHundredPeso(1);
                    break;
                case 9:
                    payment.addOneThousandPeso(1);
                case 0:
                    break;
                default:
                    System.out.println("Error: Invalid Option");
            }

            if (temp <= 9 && temp >= 1) {
                if (tempWallet.removeMoney(payment)) {
                    System.out.println("Paid: " + payment.getMoney());
                    if (payment.getMoney() < cost)
                        tempWallet = wallet;
                }
            }
        }

        do {
            System.out.println("Confirm Transaction: (1) Yes   (0) No");
            temp = sc.nextInt();
        } while (temp != 1 && temp != 0);

        if (temp == 0) {
            System.out.println("Cancelling Transaction...");
        }
        else if (payment.getMoney() >= cost) {
            if (payment.getMoney() != cost)
                System.out.println("Calculating Change");

            change = payment.getMoney() - cost;
            //Display Details of the Transaction
            System.out.println("=========================");
            System.out.println("Amount Paid: " + payment.getMoney());
            System.out.println("Total Cost:  " + cost);
            System.out.println("Amount Paid - Total Cost");
            System.out.println("(Change):    " + change);
            System.out.println("=========================");

            money.addMoney(payment);

            if (money.removeMoney(change)) {
                wallet = tempWallet;
                System.out.println("Transaction Successful");
                b = true;
            }
            else {
                money.removeMoney(payment);
                System.out.println("The Machine Does Not Have Enough Change\nCancelling Transaction...");
            }
        }

        payment = null;
        tempWallet = null;

        if (!b)
            System.out.println("Transaction Failed");
        return b;   //true transaction is successful, false otherwise (cancelling of payment or no change)
    }

    private void maintenance() {

    }

    private ItemSlot[] itemSlots;
    private Money money;
    private ItemTransaction[] transactionLog;
    private VendingMachine startingInventory;
    private int maintenanceCode;

}
