package Messages;

/**
 * Created by Cyan on 2016/12/18.
 */
public class User{
    private String username;
    private String password;
    private String email;

    public User(String Username,String Password,String Email){
        username=Username;
        password=Password;
        email=Email;
    }

    public User(String Username,String Password){
        username=Username;
        password= Password;
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

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}