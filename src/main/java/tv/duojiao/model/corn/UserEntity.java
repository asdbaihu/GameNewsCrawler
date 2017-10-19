package tv.duojiao.model.corn;

/**
 * Description:
 * User: Yodes
 * Date: 2017/10/20
 */
public class UserEntity {
    int uid;
    String uname;
    String oauth_token;
    String oauth_token_secret;
    String game_name;

    public UserEntity(int uid, String uname, String oauth_token, String oauth_token_secret, String game_name) {
        this.uid = uid;
        this.uname = uname;
        this.oauth_token = oauth_token;
        this.oauth_token_secret = oauth_token_secret;
        this.game_name = game_name;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "uid=" + uid +
                ", uname='" + uname + '\'' +
                ", oauth_token='" + oauth_token + '\'' +
                ", oauth_token_secret='" + oauth_token_secret + '\'' +
                ", game_name='" + game_name + '\'' +
                '}';
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getOauth_token() {
        return oauth_token;
    }

    public void setOauth_token(String oauth_token) {
        this.oauth_token = oauth_token;
    }

    public String getOauth_token_secret() {
        return oauth_token_secret;
    }

    public void setOauth_token_secret(String oauth_token_secret) {
        this.oauth_token_secret = oauth_token_secret;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }
}