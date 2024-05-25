package ATMServer_Group10;

public class User {
    private String userid;
    private String passwd;

    public User(String userid, String passwd) {
        this.userid = userid;
        this.passwd = passwd;
    }

    public String getUserid() {
        return userid;
    }

    public String getPasswd() {
        return passwd;
    }

    @Override
    public String toString() {
        return "User{" +
                ", userid='" + userid + '\'' +
                ", passwd='" + passwd + '\'' +
                '}';
    }
}
