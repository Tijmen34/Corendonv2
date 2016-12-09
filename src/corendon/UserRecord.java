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

    private final SimpleStringProperty user_id;
    private final SimpleStringProperty username;
    private final SimpleStringProperty password;
    private final SimpleStringProperty firstname;
    private final SimpleStringProperty tussenvoegsel;
    private final SimpleStringProperty surname;
    private final SimpleStringProperty function;

    
        public UserRecord(String user_id, String username, String password,
            String firstname, String tussenvoegsel, String surname, String function) {
        this.user_id = new SimpleStringProperty(user_id);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.firstname = new SimpleStringProperty(firstname);
        this.tussenvoegsel = new SimpleStringProperty(tussenvoegsel);
        this.surname = new SimpleStringProperty(surname);
        this.function = new SimpleStringProperty(function);   
    }
    

    public String getuser_id() {
        return user_id.get();
    }

    public void setuser_id(String user_id) {
        this.user_id.set(user_id);
    }

    public String getusername() {
        return username.get();
    }

    public void setusername(String username) {
        this.username.set(username);
    }

    public String getpassword() {
        return password.get();
    }

    public void setpassword(String password) {
        this.password.set(password);
    }

    public String getfirstname() {
        return firstname.get();
    }

    public void setfirstname(String firstname) {
        this.firstname.set(firstname);
    }

    public String gettussenvoegsel() {
        return tussenvoegsel.get();
    }

    public void settussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel.set(tussenvoegsel);
    }

    public String getsurname() {
        return surname.get();
    }

    public void setsurname(String surname) {
        this.surname.set(surname);
    }

    public String getfunction() {
        return function.get();
    }

    public void setfunction(String function) {
        this.function.set(function);
    }

    @Override
    public String toString() {
        return "UserRecord{" + "user_id=" + user_id + ", userName="
                + username + ", firstname=" + firstname + ", tussenvoegsel="
                + tussenvoegsel + ", surname=" + surname + ", function="
                + function + '}';
    }

}
