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
            String msAccDB = "D:/WORKSPACE/TEST_WORKSPACE/Java-JDBC/Player.accdb";
            String dbURL = "jdbc:ucanaccess://" + file;
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            //Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(dbURL);
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
        String str = "INSERT INTO vesselInspection VALUES (00,'" + vesselID + "','" + location + "', '" + inspection + "', '" + inspectionResult + "'" +
                ", '" + name + "', '" + UUID + "', '" + vesselType + "',CURRENT_DATE);";

        writeData(str);

    }
}

