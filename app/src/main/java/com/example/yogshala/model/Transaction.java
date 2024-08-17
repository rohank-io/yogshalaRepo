package com.example.yogshala.model;

public class Transaction {
    private String transactionId;
    private String clientId;

    private String clientName;
    private String fromDate;

    private String toDate;
    private String type;
    private String amount;
    private String remarks;

    public Transaction() {
    }

    // Constructor
    public Transaction(String transactionId, String clientId,String clientName, String fromDate,String toDate, String type, String amount, String remarks) {
        this.transactionId = transactionId;
        this.clientId = clientId;
        this.clientName = clientName;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.type = type;
        this.amount = amount;
        this.remarks = remarks;
    }

    // Getters and setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
