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
    private final SimpleStringProperty surname;
    private final SimpleStringProperty tussenVoegsel;
    private final SimpleStringProperty brandName;
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty street;
    private final SimpleStringProperty houseNr;
    private final SimpleStringProperty town;
    private final SimpleStringProperty zipCode;
    private final SimpleStringProperty country;
    private final SimpleStringProperty phoneNr;
    private final SimpleStringProperty eMail;
    private final SimpleStringProperty airPort;
    

    public CustomerRecord(String customerId, String gender, String surname, String tussenVoegsel,
            String brandName, String dateOfBirth, String street, String houseNr,
            String town, String zipCode, String country, String phoneNr, String eMail, String airPort) {
        this.customerId = new SimpleStringProperty(customerId);
        this.gender = new SimpleStringProperty(gender);
        this.surname = new SimpleStringProperty(surname);
        this.tussenVoegsel = new SimpleStringProperty(tussenVoegsel);
        this.brandName = new SimpleStringProperty(brandName);
        this.dateOfBirth = new SimpleStringProperty(dateOfBirth);
        this.street = new SimpleStringProperty(street);
        this.houseNr = new SimpleStringProperty(houseNr);
        this.town = new SimpleStringProperty(town);
        this.zipCode = new SimpleStringProperty(zipCode);
        this.country = new SimpleStringProperty(country);
        this.phoneNr = new SimpleStringProperty(phoneNr);
        this.eMail = new SimpleStringProperty(eMail);
        this.airPort = new SimpleStringProperty(airPort);
        
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

    public String getSurname() {
        return surname.get();
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }
    
    public String getTussenvoegsel() {
        return tussenVoegsel.get();
    }

    public void setTussenVoegsel(String tussenVoegsel) {
        this.tussenVoegsel.set(tussenVoegsel);
    }
    
    public String getBrandname() {
        return brandName.get();
    }

    public void setBrandname(String brandName) {
        this.brandName.set(brandName);
    }
    
    public String getDateofbirth() {
        return dateOfBirth.get();
    }

    public void setDateofbirth(String dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }
    
    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }
    
    public String getHousenr() {
        return houseNr.get();
    }

    public void setHousenr(String houseNr) {
        this.houseNr.set(houseNr);
    }
    
    public String getTown() {
        return town.get();
    }

    public void setTown(String town) {
        this.town.set(town);
    }
    
    public String getZipcode() {
        return zipCode.get();
    }

    public void setzipCode(String zipCode) {
        this.zipCode.set(zipCode);
    }
    
    public String getCountry() {
        return country.get();
    }

    public void setCountry(String country) {
        this.country.set(country);
    }
    
    public String getPhonenr() {
        return phoneNr.get();
    }

    public void setPhonenr(String phoneNr) {
        this.phoneNr.set(phoneNr);
    }
    
    public String getEmail() {
        return eMail.get();
    }

    public void setEmail(String eMail) {
        this.eMail.set(eMail);
    }
    
    public String getAirport() {
        return airPort.get();
    }
    
    public void setAirport(String airPort) {
        this.airPort.set(airPort);
    }

    @Override
    public String toString() {
        return "LuggageRecord2{" + "customerId=" + customerId + ", gender=" + gender + ", surname=" + surname + ", tussenVoegsel=" + tussenVoegsel + ", brandName=" + brandName + ", brandName=" + brandName + ", street=" + street + ", houseNr=" + houseNr + ", town=" + town + ", customerId=" + customerId + ", country=" + country + '}';
    }
    
    public SimpleStringProperty[] toArray() {
        SimpleStringProperty[] properties = new SimpleStringProperty[]
                {this.customerId, this.gender, this.surname, 
                this.tussenVoegsel, this.brandName, this.dateOfBirth, this.street,
                this.houseNr, this.street, this.houseNr,
                this.country, this.eMail, this.brandName};
        return properties;
    }
}
