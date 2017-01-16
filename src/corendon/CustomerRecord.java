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
public class CustomerRecord {
    
   private final SimpleStringProperty customerId;
    private final SimpleStringProperty gender;
    private final SimpleStringProperty firstname;
    private final SimpleStringProperty tussenVoegsel;
    private final SimpleStringProperty surname;
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty street;
    private final SimpleStringProperty houseNr;
    private final SimpleStringProperty town;
    private final SimpleStringProperty zipCode;
    private final SimpleStringProperty country;
    private final SimpleStringProperty phoneNr;
    private final SimpleStringProperty eMail;

    

    public CustomerRecord(String customerId, String gender, String firstname, String tussenVoegsel,
            String surname, String dateOfBirth, String street, String houseNr,
            String town, String zipCode, String country, String phoneNr, String eMail) {
        this.customerId = new SimpleStringProperty(customerId);
        this.gender = new SimpleStringProperty(gender);
        this.firstname = new SimpleStringProperty(firstname);
        this.tussenVoegsel = new SimpleStringProperty(tussenVoegsel);
        this.surname = new SimpleStringProperty(surname);
        this.dateOfBirth = new SimpleStringProperty(dateOfBirth);
        this.street = new SimpleStringProperty(street);
        this.houseNr = new SimpleStringProperty(houseNr);
        this.town = new SimpleStringProperty(town);
        this.zipCode = new SimpleStringProperty(zipCode);
        this.country = new SimpleStringProperty(country);
        this.phoneNr = new SimpleStringProperty(phoneNr);
        this.eMail = new SimpleStringProperty(eMail);

        
    }
public String getCustomerId() {
        return customerId.get();
    }

    public void setCustomerId(String customerId) {
        this.customerId.set(customerId);
    }

    public String getGender() {
        return gender.get();
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getFirstname() {
        return firstname.get();
    }

    public void setFirstname(String firstname) {
        this.firstname.set(firstname);
    }
    
    public String getTussenVoegsel() {
        return tussenVoegsel.get();
    }

    public void setTussenVoegsel(String tussenVoegsel) {
        this.tussenVoegsel.set(tussenVoegsel);
    }
    
    public String getSurname() {
        return surname.get();
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }
    
    public String getDateOfBirth() {
        return dateOfBirth.get();
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }
    
    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }
    
    public String getHouseNr() {
        return houseNr.get();
    }

    public void setHouseNr(String houseNr) {
        this.houseNr.set(houseNr);
    }
    
    public String getTown() {
        return town.get();
    }

    public void setTown(String town) {
        this.town.set(town);
    }
    
    public String getZipCode() {
        return zipCode.get();
    }

    public void setZipCode(String zipCode) {
        this.zipCode.set(zipCode);
    }
    
    public String getCountry() {
        return country.get();
    }

    public void setCountry(String country) {
        this.country.set(country);
    }
    
    public String getPhoneNr() {
        return phoneNr.get();
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr.set(phoneNr);
    }
    
    public String getEMail() {
        return eMail.get();
    }

    public void setEMail(String eMail) {
        this.eMail.set(eMail);
    }
    


    @Override
    public String toString() {
        return "LuggageRecord2{" + "customerId=" + customerId + ", gender=" + gender + ", firstname=" + firstname + ", "
                + "tussenVoegsel=" + tussenVoegsel + ", surname=" + surname + ", "
                + "dateOfBirth=" + dateOfBirth + ", street=" + street + ", houseNr=" + houseNr + ", town=" + town + ", zipCode" + zipCode 
                + ", country=" + country + ", phoneNr=" + phoneNr + ", eMail=" + eMail +'}';
    }
    
    public SimpleStringProperty[] toArray() {
        SimpleStringProperty[] properties = new SimpleStringProperty[]
                {this.customerId, this.gender, this.firstname, 
                this.tussenVoegsel, this.surname, this.dateOfBirth, this.street,
                this.houseNr, this.town, this.zipCode,
                this.country, this.phoneNr, this.eMail};
        return properties;
    }
}
