package transpool.logic.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserManager {
    private Map<String, User> allUsers;
    private Map<String, Boolean> loggedInUsers;

    public UserManager() {
        this.allUsers = new HashMap<>();
        this.loggedInUsers = new HashMap<>();
    }

    public synchronized void addUser(String userName, String userType){
        User user = new User(userName);
        user.setType(User.Type.valueOf(userType));
        this.allUsers.put(userName, user);
        this.loggedInUsers.put(userName, true);
    }

    public synchronized void removeUser(User username) {
        this.allUsers.remove(username.getName());
        this.loggedInUsers.put(username.getName(), false);
    }

    public synchronized User getUser(String userName){
        return this.allUsers.get(userName);
    }

    public boolean userExist(String userName){
        return this.loggedInUsers.containsKey(userName) && this.loggedInUsers.get(userName);
//        return this.allUsers.containsKey(userName);
    }

    public Map<String, User> getUsers() {
        return allUsers;
    }
}
