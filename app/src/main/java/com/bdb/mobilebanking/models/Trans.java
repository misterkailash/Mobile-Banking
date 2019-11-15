package com.bdb.mobilebanking.models;

public class Trans {

    private String TRAN_REF_NO;
    private String DT;
    private String USERNAME;
    private String FROM_FORACID;
    private String FROM_SOL;
    private String TO_SOL;
    private String TO_FORACID;
    private String TRANSACTION_MODE;
    private String AMOUNT;
    private String OTP;
    private String DEVICE_ID;

    public String getTRAN_REF_NO() {
        return TRAN_REF_NO;
    }

    public void setTRAN_REF_NO(String TRAN_REF_NO) {
        this.TRAN_REF_NO = TRAN_REF_NO;
    }

    public String getDATE_TIME() {
        return DT;
    }

    public void setDATE_TIME(String DATE_TIME) {
        this.DT = DATE_TIME;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getFROM_FORACID() {
        return FROM_FORACID;
    }

    public void setFROM_FORACID(String FROM_FORACID) {
        this.FROM_FORACID = FROM_FORACID;
    }

    public String getFROM_SOL() {
        return FROM_SOL;
    }

    public void setFROM_SOL(String FROM_SOL) {
        this.FROM_SOL = FROM_SOL;
    }

    public String getTO_SOL() {
        return TO_SOL;
    }

    public void setTO_SOL(String TO_SOL) {
        this.TO_SOL = TO_SOL;
    }

    public String getTO_FORACID() {
        return TO_FORACID;
    }

    public void setTO_FORACID(String TO_FORACID) {
        this.TO_FORACID = TO_FORACID;
    }

    public String getTRANSACTION_MODE() {
        return TRANSACTION_MODE;
    }

    public void setTRANSACTION_MODE(String TRANSACTION_MODE) {
        this.TRANSACTION_MODE = TRANSACTION_MODE;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public String getDEVICE_ID() {
        return DEVICE_ID;
    }

    public void setDEVICE_ID(String DEVICE_ID) {
        this.DEVICE_ID = DEVICE_ID;
    }
}
