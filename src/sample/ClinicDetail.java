package sample;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClinicDetail {
    private int id;
    private String name;
    private String addr_state;
    private String addr_city;
    private int addr_pin;
    private String addr_line;
    private String ph_no;
    private String email;
    private String website;
    private int  user_id;

    ClinicDetail(int id, String name, String addr_state,
                 String addr_city, int addr_pin, String addr_line,
                 String ph_no, String email, String website, int user_id)
    {
        this.id = id;
        this.name = name;
        this.addr_state = addr_state;
        this.addr_city = addr_city;
        this.addr_line = addr_line;
        this.addr_pin  = addr_pin;
        this.email = email;
        this.ph_no = ph_no;
        this.website = website;
        this.user_id = user_id;
    }

    ClinicDetail(int id) throws SQLException {
        Connection con = new ConnectDatabase().connectToDatabase();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("Select * from hoc_tbl where hoc_id = " + id);
        while (rs.next()) {
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
    }
    public void getDetailByUserID(int user_id) throws SQLException {

        Connection con = new ConnectDatabase().connectToDatabase();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("Select * from hoc_tbl where user_id = " + id);
        while(rs.next()) {
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
    }
    public String getAddress(){
        return addr_line + ", " + addr_city + ", " + addr_state + ", " + addr_pin;
    }

    public String getWebsite() {
        return website;
    }

    public String getPh_no() {
        return ph_no;
    }

    public String getAddr_state() {
        return addr_state;
    }

    public String getAddr_line() {
        return addr_line;
    }

    public String getAddr_city() {
        return addr_city;
    }

    public int getAddr_pin() {
        return addr_pin;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getEmail() {
        return email;
    }
}
