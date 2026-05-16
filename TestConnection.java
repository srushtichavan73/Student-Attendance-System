import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {
    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection(

    "jdbc:mysql://localhost:3306/Student_attendance?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
    "srushti",
    "1234"
);
            

            System.out.println("Connected Successfully!");
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

