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
        userCard += 4000000000000000L + random.nextInt(999999999) * 10L;
        userCard += algorithmLuhn();
        userPin = random.nextInt(((9999 - 1000) + 1)) + 1000;
    }

    private int algorithmLuhn () {
        int sum = 0;
        long cloneCard = userCard;
        long number;
        for (int i = 0; i < 16; i++) {
            if (i % 2 != 0) {
                number = (cloneCard % 10) * 2;
                number = number >= 10 ? number - 9 : number;
            } else {
                number = cloneCard % 10;
            }
            sum += number;
            cloneCard /= 10;
        }
        if (sum % 10 == 0) {
            return 0;
        } else {
            return 10 - sum % 10;
        }
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
