package sample;

public class DoctorDetail {
    UserProfile userProfile;
    private String name;
    private String type;
    private String spec;
    private int fee;
    private int doc_id, user_id;

    public DoctorDetail(UserProfile userProfile,String name, String type, String spec , int fee, int doc_id, int user_id){
        this.name = name;
        this.userProfile = userProfile;
        this.type = type;
        this.spec = spec;
        this.fee = fee;
        this.doc_id = doc_id;
        this.user_id = user_id;
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
