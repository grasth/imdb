import java.sql.*;
import java.util.List;

import org.postgresql.*;

public class DBConnect {

    private static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/imdb", "postgres", "admin");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static boolean hasConnect(){
        return connect() == null ? false:true;
    }

    static boolean tableExistsSQL() throws SQLException {
        Connection connection = connect();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) "
                + "FROM information_schema.tables "
                + "WHERE table_name = ?");

        preparedStatement.setString(1, "imdb");

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        connection.close();
        return resultSet.getInt(1) != 0;
    }

    public static void createTable() {
        String CreateSql = "Create Table imdb(id SERIAL PRIMARY KEY, name TEXT, age INT, rate REAL)";
        try{
            Connection connection = connect();
            Statement stmt = connection.createStatement();
            stmt.execute(CreateSql);
            stmt.close();
            connection.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
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
                System.out.println("");
            }
            connection.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
