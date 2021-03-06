/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corendon;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author JerryJerr
 */
public class LuggageRecord2 {
    //SimpleStringProperty ipv String omdat deze waarden observable moeten zijn
    //voor de tabel
    private final SimpleStringProperty lostId;
    private final SimpleStringProperty labelNr;
    private final SimpleStringProperty flightNr;
    private final SimpleStringProperty iata;
    private final SimpleStringProperty type;
    private final SimpleStringProperty brandName;
    private final SimpleStringProperty primaryColor;
    private final SimpleStringProperty secondaryColor;
    private final SimpleStringProperty info;
    private final SimpleStringProperty customerId;
    private final SimpleStringProperty status;
    private final SimpleStringProperty date;
    private final SimpleStringProperty time;

    public LuggageRecord2(String lostId, String labelNr, String flightNr, String iata,
            String type, String brandName, String primaryColor, String secondaryColor,
            String info, String customerId, String status, String date, String time) {
        this.lostId = new SimpleStringProperty(lostId);
        this.labelNr = new SimpleStringProperty(labelNr);
        this.flightNr = new SimpleStringProperty(flightNr);
        this.iata = new SimpleStringProperty(iata);
        this.type = new SimpleStringProperty(type);
        this.brandName = new SimpleStringProperty(brandName);
        this.primaryColor = new SimpleStringProperty(primaryColor);
        this.secondaryColor = new SimpleStringProperty(secondaryColor);
        this.info = new SimpleStringProperty(info);
        this.customerId = new SimpleStringProperty(customerId);
        this.status = new SimpleStringProperty(status);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);

    }
    
    //ook de getters en setters gaan volgens een speciale conventie voor de 
    //tabel.
    
    public String getLostId() {
        return lostId.get();
    }

    public void setLostId(String lostId) {
        this.lostId.set(lostId);
    }

    public String getLabelNr() {
        return labelNr.get();
    }

    public void setLabelNr(String labelNr) {
        this.labelNr.set(labelNr);
    }

    public String getFlightNr() {
        return flightNr.get();
    }

    public void setFlightNr(String flightNr) {
        this.flightNr.set(flightNr);
    }

    public String getIata() {
        return iata.get();
    }

    public void setIata(String iata) {
        this.iata.set(iata);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getBrandName() {
        return brandName.get();
    }

    public void setBrandName(String brandName) {
        this.brandName.set(brandName);
    }

    public String getPrimaryColor() {
        return primaryColor.get();
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor.set(primaryColor);
    }

    public String getSecondaryColor() {
        return secondaryColor.get();
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor.set(secondaryColor);
    }

    public String getInfo() {
        return info.get();
    }

    public void setInfo(String info) {
        this.info.set(info);
    }

    public String getCustomerId() {
        return customerId.get();
    }

    public void setCustomerId(String customerId) {
        this.customerId.set(customerId);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getTime() {
        return time.get();
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    @Override
    public String toString() {
        return "LuggageRecord2{" + "lostId=" + lostId + ", labelNr=" + labelNr + ", flightNr=" + flightNr 
                + ", iata=" + iata + ", type=" + type + ", brandName=" + brandName + ", primaryColor=" 
                + primaryColor + ", secondaryColor=" + secondaryColor + ", info=" + info + ", customerId=" 
                + customerId + ", status=" + status + '}';
    }

    //alle velden in een array stoppen zodat de zoek methode er makkelijk in kan zoeken
    public SimpleStringProperty[] toArray() {
        SimpleStringProperty[] properties = new SimpleStringProperty[]{this.brandName, this.customerId, this.date,
            this.flightNr, this.iata, this.info, this.labelNr,
            this.lostId, this.primaryColor, this.secondaryColor,
            this.status, this.time, this.type};
        return properties;
    }

}
