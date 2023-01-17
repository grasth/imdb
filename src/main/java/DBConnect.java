import java.sql.*;
import java.util.List;

public class DBConnect {

    private static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://sql8.freesqldatabase.com:3306/sql8591294", "sql8591294", "CAcU7XGhNj");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static boolean hasConnect(){
        return connect() != null;
    }

    private static boolean tableExists(Connection connection){
        try {
            boolean tExists = false;
            try (ResultSet rs = connection.getMetaData().getTables(null, null, "imdb", null)) {
                while (rs.next()) {
                    String tName = rs.getString("TABLE_NAME");
                    if (tName != null && tName.equals("imdb")) {
                        tExists = true;
                        break;
                    }
                }
            }
            return tExists;
        } catch (Exception e){
            return false;
        }
    }
    static void createTableIfNotExists() throws SQLException {
        Connection connection = connect();
        if (!tableExists(connection)) {
            String sqlCreateTableScheme = "CREATE TABLE imdb " +
                    "(id INTEGER not NULL AUTO_INCREMENT, " +
                    " name TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin, " +
                    " age INTEGER, " +
                    " rate FLOAT, " +
                    " PRIMARY KEY ( id ))";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlCreateTableScheme);
            connection.close();
            System.out.println("Table created");
        } else {
            System.out.println("Table exists");
        }
    }

    public static void insertData(List<Film> films){
        try {
            Connection connection = connect();
            String sql = "INSERT INTO imdb" +
                    "  (name, age, rate) VALUES " +
                    " (?, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for(Film film: films) {
                preparedStatement.setString(1, film.getName());
                preparedStatement.setInt(2, film.getAge());
                preparedStatement.setDouble(3, film.getRate());
                preparedStatement.addBatch();
                preparedStatement.executeUpdate();
            }

            connection.close();

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void printSql(String sql){
        try {
            Connection connection = connect();
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);

            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()){
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println();
            }
            System.out.println("================");
            connection.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
