import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBFunctions {

    private static Connection conn;

    public static void main(String[] args) throws SQLException, IOException {
        executeQueryResult("SELECT region_id, region_description FROM public.region");
    }


    public static Connection getConnection() throws SQLException, IOException {
        if (conn == null || conn.isClosed()) {
            FileInputStream fis = new FileInputStream("src/properties/db.properties");
            Properties p = new Properties();
            p.load(fis);
            String url = p.getProperty("database-url");
            String username = p.getProperty("database-username");
            String password = p.getProperty("database-password");

            conn = DriverManager.getConnection(url, username, password);

            if (conn != null) {
                System.out.println("Connected");
            }
        }
        return conn;
    }


    public static void executeQueryResult(String query) throws SQLException, IOException {
        try {

            ResultSet rs = executeQuery(query);

            while (rs.next()) {
                String result = rs.getString(1);
                System.out.println("Result: " + result);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static ResultSet executeQuery(String query) throws SQLException, IOException {
        Connection conn = getConnection();
        Statement smt = conn.createStatement();
        ResultSet rs = smt.executeQuery(query);
        return rs;
    }


}
