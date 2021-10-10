package banking;

import java.util.Scanner;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);

    protected void start(String fileName) {
        try {
            Connect connect = Connect.getInstance(fileName);
            boolean onOff = true;
            while (onOff) {
                System.out.println("1. Create an account\n" +
                        "2. Log into account\n" +
                        "0. Exit");
                onOff = changeMenu(connect);
            }
            System.out.print("\nBye!");
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean changeMenu(Connect connect) {
        switch (scanner.next()) {
            case "1":
                connect.addCard(new User());
                return true;
            case "2":
                System.out.println("\nEnter your card number:");
                String numberCard = scanner.next();
                System.out.println("Enter your PIN:");
                return findUser(connect, numberCard, scanner.next());
            default:
                return false;
        }
    }

    private boolean secondMenu(Connect connect, String card) {
        boolean check = true;
        while (check) {
            System.out.println("\n1. Balance\n" +
                    "2. Add income\n" +
                    "3. Do transfer\n" +
                    "4. Close account\n" +
                    "5. Log out\n" +
                    "0. Exit");
            switch (scanner.next()) {
                case "1":
                    connect.getBalance(card);
                    break;
                case "2":
                    System.out.println("\nEnter income:");
                    connect.addIncome(card, scanner.nextInt());
                    System.out.println("Income was added!");
                    break;
                case "3":
                    doTransfer(connect, card);
                    break;
                case "4":
                    System.out.println("\nThe account has been closed!\n");
                    connect.closeAccount(card);
                    return true;
                case "5":
                    System.out.println("\nYou have successfully logged out!\n");
                    return true;
                default:
                    check = false;
                    break;
            }
        }
        return false;
    }

    private boolean findUser(Connect connect, String card, String pin) {
        if (connect.checkUser(card, pin)) {
            System.out.println("\nYou have successfully logged in!");
            return secondMenu(connect, card);
        } else {
            System.out.println("Wrong card number or PIN!\n");
        }
        return true;
    }

    private void doTransfer(Connect connect, String card) {
        System.out.println("\nTransfer\n" +
                "Enter card number:");
        String cardTransfer = scanner.next();
        if (cardTransfer.equals(card)) {
            System.out.println("You can't transfer money to the same account!");
        } else if (checkAlgorithm(cardTransfer)) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
        } else if (!connect.checkCardNumber(cardTransfer)) {
            System.out.println("Such a card does not exist.");
        } else {
            System.out.println("Enter how much money you want to transfer:");
            int moneyTransfer = scanner.nextInt();
            if (!connect.checkCardMoney(card, moneyTransfer)) {
                System.out.println("Not enough money!");
            } else {
                connect.transferMoney(card, cardTransfer, moneyTransfer);
                System.out.println("Success!");
            }
        }
    }

    private boolean checkAlgorithm(String card) {
        long cardNumber = Long.parseLong(card);
        int a = 0;
        int b = 0;
        for (int i = 0; i < 16; i++) {
            if (i % 2 != 0) {
                long plus = cardNumber % 10 * 2;
                if (plus >= 10) {
                    a += plus % 10 + plus / 10;
                } else {
                    a += plus;
                }
            } else {
                b += cardNumber % 10;
            }
            cardNumber /= 10;
        }
        return (a + b) % 10 != 0;
    }
}
