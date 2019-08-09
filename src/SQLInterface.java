import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLInterface {
    private Connection connection = null;
    private ResultSet resultSet = null;
    private Statement statement = null;

    public SQLInterface(String file) {


        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + file);
            System.out.println("Connected");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeData(String state) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate(state);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public ResultSet readData(String state) {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(state);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void formatedInput(String vesselType, String vesselID, String location, String name, String inspection
            , String inspectionResult, String UUID) {
        String str = "INSERT INTO vesselInspection VALUES ('" + vesselType + "','" + vesselID + "', '" + location + "', '" + inspection + "'" +
                ", '" + inspectionResult + "', '" + name + "', CURRENT_DATE, '" + UUID + "')";
        writeData(str);

    }
}

