package banking;

import java.util.Scanner;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private static final User[] users = new User[50];
    private static int usersNum = 0;

    protected void start() {
        boolean onOff = true;
        while (onOff) {
            System.out.println("1. Create an account\n" +
                    "2. Log into account\n" +
                    "0. Exit");
            onOff = changeMenu();
        }
        System.out.print("\nBye!");
        scanner.close();
    }

    private boolean changeMenu() {
        switch (scanner.next()) {
            case "1":
                users[usersNum] = new User();
                usersNum++;
                return true;
            case "2":
                System.out.println("\nEnter your card number:");
                long numberCard = scanner.nextLong();
                System.out.println("Enter your PIN:");
                return findUser(numberCard, scanner.nextInt());
            default:
                return false;
        }
    }

    private boolean secondMenu(int userId) {
        boolean check = true;
        while (check) {
            System.out.println("\n1. Balance\n" +
                    "2. Log out\n" +
                    "0. Exit");
            switch (scanner.next()) {
                case "1":
                    System.out.println("\nBalance: " + users[userId].getBalance());
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

    private boolean findUser(long card, int pin) {
        for (int i = 0; i < usersNum; i++) {
            if (users[i].getUserCard() == card && users[i].getUserPin() == pin) {
                System.out.println("\nYou have successfully logged in!");
                return secondMenu(i);
            } else {
                System.out.println("Wrong card number or PIN!\n");
            }
        }
        return true;
    }
}
