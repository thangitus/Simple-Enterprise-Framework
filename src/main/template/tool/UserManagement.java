package ui.tool;

import entity.Users;

public class UserManagement {
    private static UserManagement instance = null;
    private UserManagement(){}

    public static synchronized UserManagement getInstance(){
        if(instance == null){
            instance =  new UserManagement();
        }
        return instance;
    }

    private Users user;

    public boolean isLogging(){
        return (user != null);
    }

    public void logout(){
        user = null;
    }

    public void login(Users user){
        this.user = user;
    }

    public Users getUser() {
        return user;
    }
}
