package com.bdb.mobilebanking.utils;

public class RestClient {
    private static String ROOT = "http://202.144.141.142/";
    //private static String ROOT = "http://192.168.137.114/mobilebanking/";

    public static String LOGIN_URL = ROOT + "login_validate.php";
    public static String OTP_VERIFY = ROOT + "otp_verify.php";
    public static String OTP_RESEND = ROOT + "otp_resend.php";
    public static String BALANCE_ENQUIRY = ROOT + "balance_enquiry.php";
    public static String SOL_ENQUIRY = ROOT + "sol_enquiry.php";
    public static String DEPOSIT = ROOT + "deposit.php";
    public static String WITHDRAW = ROOT + "withdraw.php";
    public static String WITHDRAW_OTP = ROOT + "otp_withdraw.php";
    public static String TRANSFER = ROOT + "transfer.php";
    public static String TRANSFER_OTP = ROOT + "otp_transfer.php";
    public static String LOAN_REPAYMENT = ROOT + "loan_repayment.php";
    public static String GET_BALANCE = ROOT + "get_balance.php";
    public static String RECENT_TRANSACTIONS = ROOT + "recent_transactions.php";
    public static String RECENT_TRANSACTIONS_THIS_MONTH = ROOT + "recent_trans_this_month.php";
    public static String RECENT_TRANSACTIONS_LAST_MONTH = ROOT + "recent_trans_last_month.php";
    public static String PASSWORD_CHANGE = ROOT + "change_password.php";
    public static String PASSWORD_RESET = ROOT + "reset_password.php";
    public static String FORGOT_PASSWORD = ROOT + "forgot_password.php";
    public static String RESET_OTP_VERIFY = ROOT + "reset_otp_verify.php";
    public static String FOB_SERVICE = ROOT + "fob_service_test.php";
    public static String SMS_SERVICE = ROOT + "sms_service_test.php";
}