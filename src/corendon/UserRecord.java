/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corendon;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Jeroen de Jong
 */
public class UserRecord {

    private final SimpleStringProperty userId;
    private final SimpleStringProperty userName;
    private final SimpleStringProperty passWord;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty tussenVoegsel;
    private final SimpleStringProperty surName;
    private final SimpleStringProperty function;

    public UserRecord(String userId, String userName, String passWord, String firstName,
            String tussenVoegsel, String surName, String function) {
        this.userId = new SimpleStringProperty(userId);
        this.userName = new SimpleStringProperty(userName);
        this.passWord = new SimpleStringProperty(passWord);
        this.firstName = new SimpleStringProperty(firstName);
        this.tussenVoegsel = new SimpleStringProperty(tussenVoegsel);
        this.surName = new SimpleStringProperty(surName);
        this.function = new SimpleStringProperty(function);
    }

    public String getUserId() {
        return userId.get();
    }

    public void setUserId(String userId) {
        this.userId.set(userId);
    }

    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getPassWord() {
        return passWord.get();
    }

    public void setPassWord(String passWord) {
        this.passWord.set(passWord);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getTussenVoegsel() {
        return tussenVoegsel.get();
    }

    public void setTussenVoegsel(String tussenVoegsel) {
        this.tussenVoegsel.set(tussenVoegsel);
    }

    public String getSurName() {
        return surName.get();
    }

    public void setSurName(String surName) {
        this.surName.set(surName);
    }

    public String getFunction() {
        return function.get();
    }

    public void setFunction(String function) {
        this.function.set(function);
    }

    @Override
    public String toString() {
        return "UserRecord{" + "userId=" + userId + ", userName="
                + userName + ", firstName=" + firstName + ", tussenVoegsel="
                + tussenVoegsel + ", surName=" + surName + ", function="
                + function + '}';
    }

}
