package sample;

public class LoginProfile {
    private String user_name;
    private String password;
    private String acc_type;

    LoginProfile(String user_name, String password, String acc_type){
        this.user_name = user_name;
        this.password = password;
        this.acc_type = acc_type;
    }

    public String getAcc_type() {
        return acc_type;
    }

    public String getPassword() {
        return password;
    }

    public String getUser_name() {
        return user_name;
    }
}
