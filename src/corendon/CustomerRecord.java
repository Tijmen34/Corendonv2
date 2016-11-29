/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corendon;

/**
 *
 * @author JerryJerr
 */
public class CustomerRecord {
    
    private String customerId;
    private String gender;
    private String surname;
    private String tussenVoegsel;
    private String firstName;
    private String dateOfBirth;
    private String street;
    private String houseNr;
    private String town;
    private String zipCode;
    private String country;
    private String phoneNr;
    private String eMail;
    private String airPort;
    
    
    public CustomerRecord(String customerId, String gender, String surname, String tussenvoegsel, 
            String firstName, String dateOfBirth, String street, String houseNr,
            String town, String zipCode, String country, String phoneNr,
            String eMail, String airPort) {
        
        this.customerId = customerId;
        this.gender = gender;
        this.surname = surname;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.street = street;
        this.houseNr = houseNr;
        this.town = town;
        this.zipCode = zipCode;
        this.country = country;
        this.phoneNr = phoneNr;
        this.eMail = eMail;
        this.airPort = airPort;
    }

    public String getTussenVoegsel() {
        return tussenVoegsel;
    }

    public void setTussenVoegsel(String tussenVoegsel) {
        this.tussenVoegsel = tussenVoegsel;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNr() {
        return houseNr;
    }

    public void setHouseNr(String houseNr) {
        this.houseNr = houseNr;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getAirPort() {
        return airPort;
    }

    public void setAirPort(String airPort) {
        this.airPort = airPort;
    }

    @Override
    public String toString() {
        return "CustomerRecord{" + "customerId=" + customerId + ", gender=" 
                + gender + ", surname=" + surname + ", firstName=" + firstName 
                + ", dateOfBirth=" + dateOfBirth + ", street=" + street 
                + ", houseNr=" + houseNr + ", town=" + town + ", zipCode=" 
                + zipCode + ", country=" + country + ", phoneNr=" + phoneNr 
                + ", eMail=" + eMail + ", airPort=" + airPort + '}';
    }
    
    
}
