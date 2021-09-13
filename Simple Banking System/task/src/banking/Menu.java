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
                    "2. Log out\n" +
                    "0. Exit");
            switch (scanner.next()) {
                case "1":
                    connect.getBalance(card);
                    break;
                case "2":
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
}
