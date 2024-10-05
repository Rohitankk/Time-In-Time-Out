package Client;

public class User {
    private String username;
    private String password;
    private boolean isTimedIn;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        isTimedIn = false;
    }

    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    public boolean getIsTimedIn() {
        return isTimedIn;
    }
    public void setIsTimedIn(boolean timedIn) {
        isTimedIn = timedIn;
    }
}

