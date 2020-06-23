package com.atag.atagbank.model;

import javax.persistence.*;
import java.sql.Timestamp;
@Entity
@Table(name="transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    private float amount;
    private	boolean type;
    private Timestamp time;

    public Transaction(Long id, Account account, float amount, boolean type, Timestamp time) {
        this.id = id;
        this.account = account;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
}
