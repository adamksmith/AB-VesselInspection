import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectSQLite {
    public ConnectSQLite(String state) {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:vesselInspection.sqlite");
            System.out.println("Connected");
            statement = connection.createStatement();
            resultSet = statement
                    .executeQuery(state);
            while (resultSet.next()) {
                System.out.println("EMPLOYEE NAME:"
                        + resultSet.getString("EMPNAME"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

