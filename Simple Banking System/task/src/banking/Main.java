package banking;

public class Main {
    public static void main(String[] args) {
        if ("-fileName".equals(args[0])) {
            Menu menu = new Menu();
            menu.start(args[1]);
        }
    }
}