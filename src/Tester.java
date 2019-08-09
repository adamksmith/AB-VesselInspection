import java.sql.SQLException;
import java.util.UUID;

public class Tester {
    public static void main(String args[]) {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        String str = "INSERT INTO vesselInspection VALUES ('BK','2009', 'ColdSide', 'Is it running'" +
                ", 'NO', 'Philip', CURRENT_DATE, '" + randomUUIDString + "')";
        String str1 = "SELECT * FROM vesselInspection WHERE VesselType LIKE 'BK'";
        SQLInterface sql = new SQLInterface("H:\\Code\\DB\\vesselInspection.db");
        sql.writeData(str);
        try {
            System.out.println(sql.readData(str1).getString("VesselID"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
