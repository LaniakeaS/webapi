import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class TestDatabaseAPI {

    private static Connection c;


    static {

        c = getConnection("com.mysql.cj.jdbc.Driver", "jdbc:mysql://10.128.11.95:6666/course",
                "distribsys", "distribsys");

    }


    public static void main(String[] args) {

        IDGenerator id = new IDGenerator(5L, 4L);
        System.out.println(id.nextId());

    }


    public static boolean isPasswordCorrect(String accountName, String password) throws  SQLException {

        String pw = runQuery("select password_md5 from customer where login_name=" +
                "\"" + accountName + "\";").get(0).get(0);
        return pw.equals(password);

    }


    public static boolean isAccountNameExist(String accountName) throws SQLException {

        List<List<String>> result = runQuery("select login_name from customer where login_name = \"" + accountName +
                "\";");
        return result.size() != 0;

    }


    public static List<List<String>> runQuery(String query) throws SQLException {

        assert c != null;
        Statement statement;
        statement = c.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return getResult(resultSet);

    }


    public static void runModify(String query) throws SQLException {

        assert c != null;
        Statement statement;
        statement = c.createStatement();
        statement.executeUpdate(query);

    }


    /**
     * Connect to database and return the connection.
     * @param dbDriver Database driver class name.
     * @param dbURL Database URL address.
     * @param username Database host username.
     * @param password Database access password.
     * @return Database connection; If the database cannot be connected, return null.
     */
    public static Connection getConnection(String dbDriver, String dbURL, String username, String password) {

        Connection connection;

        try {

            Class.forName(dbDriver).newInstance();
            connection = DriverManager.getConnection(dbURL, username, password);
            return connection;

        } catch (Exception e) {

            System.out.println("Database cannot be connected!");
            e.printStackTrace();

        }

        return null;

    }



    /**Close database connection.
     *@param c Database Connection object.
     */
    public static void close(Connection c) {
        if (c != null) {
            try {
                c.close();
                System.out.println("Database connection closed.");
            } catch (Exception e) {
                /* ignore close errors */
                System.err.println("Database cannot be closed!");
                e.printStackTrace();
            }
        }
    }

    /**
     * @param rsmd JDBC ResultSetMetaData object.
     * @return List of Strings containing Column Names.
     */
    public static List<String> getColumnNames(ResultSetMetaData rsmd) {

        try {
            int colNum = rsmd.getColumnCount();
            List<String> colNames = new ArrayList<String>();

            for (int i = 1; i <= colNum; i++) {
                colNames.add(rsmd.getColumnName(i));
            }

            return colNames;
        } catch (SQLException e) {
            System.err.println("Error in retrieving column names.");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param rset JDBC ResultSet.
     * @return List of Lists containing the elements of a table
     */
    public static List<List<String>> getResult(ResultSet rset) {

        List<List<String>> result = new ArrayList<List<String>>();
        List<String> row;

        try {
            int colNum = rset.getMetaData().getColumnCount();
            while (rset.next()) {
                row = new ArrayList<String>();

                for (int i = 1; i <= colNum; i++) {
                    row.add(rset.getString(i));
                }

                result.add(row);
            }
            return result;
        } catch (SQLException e) {
            System.err.println("Error in retrieving data.");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param rset JDBC ResultSet.
     * @return List of Lists containing the elements of a table
     */
    public static List<List> getFilteredResult(ResultSet rset, int col, String filterValue) {

        List<List> result = new ArrayList<List>();
        List<String> row;

        try {
            int colNum = rset.getMetaData().getColumnCount();
            while (rset.next()) {

                //If the value of this colums equals to the filter string, ignore this row.
                if (rset.getString(col).equals(filterValue)) {
                    continue;
                }

                row = new ArrayList<String>();
                for (int i = 1; i <= colNum; i++) {
                    row.add(rset.getString(i));
                }

                result.add(row);
            }
            return result;
        } catch (SQLException e) {
            System.err.println("Error in retrieving data.");
            e.printStackTrace();
        }

        return null;
    }

}
