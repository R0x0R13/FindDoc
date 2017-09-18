package sample;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDatabase {
    public Connection connectToDatabase(){
        Connection con = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost/findDoc?user=root&password=mysql");
        }
        catch(Exception e)
        {
            System.out.println("Error in Connection"+e);
        }
        return con;

    }
}
