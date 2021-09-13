package banking;

import org.sqlite.JDBC;

import java.sql.*;

public class Connect {

    // Используем шаблон одиночка, чтобы не плодить множество
    // экземпляров класса Connect
    private static Connect instance = null;

    protected static synchronized Connect getInstance(String fileName) throws SQLException {

        if (instance == null)
            instance = new Connect(fileName);
        return instance;
    }

    // Объект, в котором будет храниться соединение с БД
    private final Connection connection;

    private Connect(String fileName) throws SQLException {
        // Константа, в которой хранится адрес подключения
        String CON_STR = "jdbc:sqlite:" + fileName;
        // Регистрируем драйвер, с которым будем работать
        // в нашем случае Sqlite
        DriverManager.registerDriver(new JDBC());
        // Выполняем подключение к базе данных
        this.connection = DriverManager.getConnection(CON_STR);
        checkTable();
    }

    // Подготавливающие таблицы
    private void checkTable() {
        try (Statement statement = this.connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                    "id INTEGER PRIMARY KEY," +
                    "number TEXT NOT NULL," +
                    "pin TEXT NOT NULL," +
                    "balance INTEGER DEFAULT 0)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Добавление карты в БД
    protected void addCard(User user) {
        // Создадим подготовленное выражение, чтобы избежать SQL-инъекций
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO card('number', 'pin') " +
                        "VALUES(?, ?)")) {
            statement.setObject(1, user.getCardNumber());
            statement.setObject(2, user.getPinNumber());
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Проверка пользователя
    protected boolean checkUser(String number, String pin) {
        String text = "SELECT * FROM card WHERE number = " + number + " AND pin = " + pin;
        try (Statement statement = this.connection.createStatement()) {
            try (ResultSet greatHouses = statement.executeQuery(text)) {
                while (greatHouses.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // getBalance
    protected void getBalance(String card) {
        String text = "SELECT * FROM card WHERE number = " + card;
        try (Statement statement = this.connection.createStatement()) {
            try (ResultSet greatHouses = statement.executeQuery(text)) {
                while (greatHouses.next()) {
                    int balance = greatHouses.getInt("balance");
                    System.out.println("\nBalance: " + balance);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
