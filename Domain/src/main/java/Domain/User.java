package Domain;

/**
 * Created by Utilizator on 20-Mar-17.
 */
public class User implements HasID<String> {

    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getID() {
        return username;
    }

    @Override
    public void setID(String id) {
        username=id;
    }

}
