/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corendon;

/**
 *
 * @author Jeroen de Jong
 */
public class UserRecord {
    private String userId;
    private String userName;
    private String firstName;
    private String tussenVoegsel;
    private String surName;
    private String function;

    public UserRecord(String userId, String userName, String firstName, 
            String tussenVoegsel, String surName, String function) {
        this.userId = userId;
        this.userName = userName;
        this.firstName = firstName;
        this.tussenVoegsel = tussenVoegsel;
        this.surName = surName;
        this.function = function;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getTussenVoegsel() {
        return tussenVoegsel;
    }

    public void setTussenVoegsel(String tussenVoegsel) {
        this.tussenVoegsel = tussenVoegsel;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    @Override
    public String toString() {
        return "UserRecord{" + "userId=" + userId + ", userName=" 
                + userName + ", firstName=" + firstName + ", tussenVoegsel=" 
                + tussenVoegsel + ", surName=" + surName + ", function=" 
                + function + '}';
    }
    
    
    
    
}


