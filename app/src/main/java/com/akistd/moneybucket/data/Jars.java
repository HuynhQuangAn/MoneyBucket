package com.akistd.moneybucket.data;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Jars extends RealmObject {
    @PrimaryKey
    @Required
    private ObjectId _id;

    @Required
    private Integer jar_amount;

    private Double jar_balance;

    @Required
    private String jar_name;


    private String owner_id="";

    // Standard getters & setters
    public ObjectId getId() { return _id; }
    public void setId(ObjectId _id) { this._id = _id; }

    public Integer getJarAmount() { return jar_amount; }
    public void setJarAmount(Integer jar_amount) { this.jar_amount = jar_amount; }

    public Double getJarBalance() { return jar_balance; }
    public void setJarBalance(Double jar_balance) { this.jar_balance = jar_balance; }

    public String getJarName() { return jar_name; }
    public void setJarName(String jar_name) { this.jar_name = jar_name; }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public Jars() {
    }

    public Jars(Jars jarsToCopy){
        this._id = jarsToCopy.getId();
        this.jar_amount = jarsToCopy.getJarAmount();
        this.jar_balance = jarsToCopy.getJarBalance();
        this.jar_name = jarsToCopy.getJarName();
        this.owner_id = jarsToCopy.getOwner_id();
    }

    public Jars(ObjectId _id, Integer jar_amount, Double jar_balance, String jar_name, String owner_id) {
        this._id = _id;
        this.jar_amount = jar_amount;
        this.jar_balance = jar_balance;
        this.jar_name = jar_name;
        this.owner_id = owner_id;
    }
}

