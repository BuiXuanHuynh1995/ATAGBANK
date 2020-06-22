package com.atag.atagbank.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
@Entity
@Table(name="transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany (targetEntity = MyUser.class)
    private List<MyUser> sender;
    @OneToMany (targetEntity = MyUser.class)
    private List<MyUser> receiver;
    private float amount;
    private	boolean type;
    private Timestamp time;

    public Transaction(Long id, List<MyUser> sender, List<MyUser> receiver, float amount, boolean type, Timestamp time) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.type = type;
        this.time = time;
    }

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MyUser> getSender() {
        return sender;
    }

    public void setSender(List<MyUser> sender) {
        this.sender = sender;
    }

    public List<MyUser> getReceiver() {
        return receiver;
    }

    public void setReceiver(List<MyUser> receiver) {
        this.receiver = receiver;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", amount=" + amount +
                ", type=" + type +
                ", time=" + time +
                '}';
    }
}
