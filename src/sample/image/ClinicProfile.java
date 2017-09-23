package sample.image;

import sample.ConnectDatabase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClinicProfile {
    private int user_id;
    private int id;
    private String name;
    private String addr_state;
    private String addr_city;
    private int addr_pin;
    private String addr_line;
    private String ph_no;
    private String email;
    private String website;

    public ClinicProfile(int user_id) throws SQLException {
        Connection con = new ConnectDatabase().connectToDatabase();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from hoc_tbl");
        rs.next();
        this.id = rs.getInt(1);
        this.name = rs.getString(2);
        this.addr_state = rs.getString(3);
        this.addr_city = rs.getString(4);
        this.addr_pin = rs.getInt(5);
        this.addr_line = rs.getString(6);
        this.ph_no = rs.getString(7);
        this.email = rs.getString(8);
        this.website = rs.getString(9);
        this.user_id = rs.getInt(10);
    }

    public String getEmail() {
        return email;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getAddr_pin() {
        return addr_pin;
    }

    public String getAddr_city() {
        return addr_city;
    }

    public String getAddr_line() {
        return addr_line;
    }

    public String getAddr_state() {
        return addr_state;
    }

    public String getPh_no() {
        return ph_no;
    }

    public String getWebsite() {
        return website;
    }

}
