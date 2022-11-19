import java.sql.*;

public class SQL {

    public static void createDB(){
        String url = "jdbc:sqlite:C:/sqlite/users.db";

        try {
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTable() {
        String url = "jdbc:sqlite:C://sqlite/testdb.db";

        String sql = "CREATE TABLE IF NOT EXISTS employees (\n"
                + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL,\n"
                + " capacity real\n"
                + ");";

        try{
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/testdb.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    public void insert(String name, double capacity) {
        String sql = "INSERT INTO employees(name, capacity) VALUES(?,?)";

        try{
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setDouble(2, capacity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        /* put in main method
        SQL app = new SQL();
        app.insert("Aryan", 30000);
        app.insert("Robert", 40000);
        app.insert("Jerry", 50000);*/
    }

    public void selectAll(){
        String sql = "SELECT * FROM employees";

        try {
            Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("name") + "\t" +
                        rs.getDouble("capacity"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        //SQL app = new SQL();
        //app.selectAll();
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/testdb.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String sql_create_table = "CREATE TABLE IF NOT EXISTS employees (\n"
                + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL,\n"
                + " capacity real\n"
                + ");";

        try{
            Statement stmt = conn.createStatement();
            stmt.execute(sql_create_table);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Fired");

        String sql_insert_into_table = "INSERT INTO employees(name, capacity) VALUES(?,?)";

        try{
            PreparedStatement pstmt = conn.prepareStatement(sql_insert_into_table);
            pstmt.setString(1, "test2");
            pstmt.setDouble(2, 420);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String sql_get_users = "SELECT * FROM employees";

        try {
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql_get_users);
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("name") + "\t" +
                        rs.getDouble("capacity"));
            }
        } catch (SQLException e) {
            System.out.println("An error has occoured:" + e.getMessage());
        }

    }


}