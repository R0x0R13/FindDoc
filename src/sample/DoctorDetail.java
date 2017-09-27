package sample;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DoctorDetail {
    UserProfile userProfile;
    private String name;
    private String type;
    private String spec;
    private int fee;
    private int doc_id, user_id;
    int caller_id;

    public DoctorDetail(UserProfile userProfile,String name, String type, String spec , int fee, int doc_id, int user_id, int caller_id){
        this.name = name;
        this.userProfile = userProfile;
        this.type = type;
        this.spec = spec;
        this.fee = fee;
        this.doc_id = doc_id;
        this.user_id = user_id;
        this.caller_id = caller_id;
    }

    public DoctorDetail(int doc_id) throws SQLException {
        this.doc_id = doc_id;
        Connection con = new ConnectDatabase().connectToDatabase();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from doctor_tbl where doc_id = " + doc_id);
        while(rs.next()){
            this.name = rs.getString(8);
            this.type = rs.getString(5);
            this.spec = rs.getString(3);
            this.fee = rs.getInt(7);
            this.user_id = rs.getInt(2);
        }
    }

    public int getCaller_id() {
        return caller_id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSpec() {
        return spec;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getDoc_id() {
        return doc_id;
    }

    public int getFee() {
        return fee;
    }
}
