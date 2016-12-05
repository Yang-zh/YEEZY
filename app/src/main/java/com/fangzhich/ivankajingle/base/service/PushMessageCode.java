package com.fangzhich.ivankajingle.base.service;

/**
 * PushMessageCode
 * Created by Khorium on 2016/12/2.
 */

public enum PushMessageCode {
    SUCCESS_ORDER_CODE("0001"),
    SHIPPING_ORDER_CODE("1002"),
    COMPLETE_ORDER_CODE("1003"),
    CANCEL_ORDER_CODE("1004"),
    OPERATION_BLACK_FRIDAY_CODE("1101"),
    OPERATION_NETWORK_MONDAY_CODE("1201"),
    PROMOTIONS_SUMMARY_CODE("2101"),
    PROMOTIONS_ONLY_ONE_CODE("2201"),
    UNDEFINED("-1");


    PushMessageCode(String value) {
        this.value = value;
    }

    private String value;
    public String getValue() {
        return value;
    }

    public static PushMessageCode innerValueOf(String value) {
        switch (value) {
            case "0001":
                return SUCCESS_ORDER_CODE;
            case "1002":
                return SHIPPING_ORDER_CODE;
            case "1003":
                return COMPLETE_ORDER_CODE;
            case "1004":
                return CANCEL_ORDER_CODE;
            case "1101":
                return OPERATION_BLACK_FRIDAY_CODE;
            case "1201":
                return OPERATION_NETWORK_MONDAY_CODE;
            case "2101":
                return PROMOTIONS_SUMMARY_CODE;
            case "2201":
                return PROMOTIONS_ONLY_ONE_CODE;
            default:
                return UNDEFINED;
        }
    }
}
