package banking;

import java.util.Random;

public class User {
    private long userCard;
    private int userPin;
    private long balance;
    private final Random random = new Random();

    protected User() {
        generateCardNumber();
        System.out.println("\nYour card has been created\nYour card number:\n" + userCard +
                "\nYour card PIN:\n" + userPin + "\n");
    }

    private void generateCardNumber() {
        userCard += 4000000000000000L + random.nextInt(999999999) +
                (random.nextInt(10) * 1000000000L);
        userPin = random.nextInt(((9999 - 1000) + 1)) + 1000;
    }

    protected long getUserCard() {
        return userCard;
    }

    protected int getUserPin() {
        return userPin;
    }

    protected long getBalance() {
        return balance;
    }
}
