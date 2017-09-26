package sample;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserProfile {
    private String first_name;
    private String last_name;
    private String dob;
    private String email;
    private String mob_no;
    private int user_id;
    private String blood_group;
    private String gender;
    public UserProfile(int user_id,String first_name, String last_name, String dob, String email, String mob_no, String blood_group, String gender){
        this.user_id = user_id;
        this.dob = dob;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.mob_no = mob_no;
        this.blood_group = blood_group;
        this.gender = gender;
    }

    public UserProfile(int user_id)  {
        this.user_id = user_id;
        Connection con = new ConnectDatabase().connectToDatabase();
        Statement stmt = null;
        try {
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select * from user_profile where user_id = " + user_id);
            while(rs.next()){
                this.first_name = rs.getString(2);
                this.last_name = rs.getString(3);
                this.dob = rs.getString(4);
                this.email = rs.getString(5);
                this.mob_no = rs.getString(6);
                this.blood_group = rs.getString(7);
                this.gender = rs.getString(8);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getUser_id() {
        return user_id;
    }

    public String getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getMob_no() {
        return mob_no;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public String getGender() {
        return gender;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
