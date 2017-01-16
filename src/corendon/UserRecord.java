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
    private final SimpleStringProperty email;
    private final SimpleStringProperty function;

    
        public UserRecord(String user_id, String username, String password,
            String firstname, String tussenvoegsel, String surname, String email, String function) {
        this.user_id = new SimpleStringProperty(user_id);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.firstname = new SimpleStringProperty(firstname);
        this.tussenvoegsel = new SimpleStringProperty(tussenvoegsel);
        this.surname = new SimpleStringProperty(surname);
        this.email = new SimpleStringProperty(email);
        this.function = new SimpleStringProperty(function);   
    }
    

    public String getUser_id() {
        return user_id.get();
    }

    public void setUser_id(String user_id) {
        this.user_id.set(user_id);
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getFirstname() {
        return firstname.get();
    }

    public void setFirstname(String firstname) {
        this.firstname.set(firstname);
    }

    public String getTussenvoegsel() {
        return tussenvoegsel.get();
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel.set(tussenvoegsel);
    }

    public String getSurname() {
        return surname.get();
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }
    
    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
    

    public String getFunction() {
        return function.get();
    }

    public void setFunction(String function) {
        this.function.set(function);
    }

    @Override
    public String toString() {
        return "UserRecord{" + "user_id=" + user_id + ", username="
                + username + ", firstname=" + firstname + ", tussenvoegsel="
                + tussenvoegsel + ", surname=" + surname + ", email="+ email +", function="
                + function + '}';
    }

}
