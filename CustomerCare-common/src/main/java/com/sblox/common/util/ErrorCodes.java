package com.sblox.common.util;

public interface ErrorCodes {

    public static final String GENERAL_ERROR = "E-0.0";

    public static final String USER_NOT_EXIST = "E-1.0";
    public static final String USER_PASSWORD_INCORRECT = "E-1.1";
    public static final String USER_INACTIVE = "E-1.2";
    public static final String USER_FIRST_LOGIN = "E-1.3";
    public static final String PASSWORD_RESET_LINK_STILL_VALID = "E-1.4";

    public static final String ORGANIZATION_NOT_EXIST = "E-2.0";
    public static final String ORGANIZATION_INACTIVE = "E-2.1";

    public static final String MOXTRA_INVITATION_FAILED = "E-3.0";
    public static final String MOXTRA_GET_CATEGORIES_FAILED = "E-3.1";
    public static final String MOXTRA_CREATE_CATEGORY_FAILED = "E-3.2";
    public static final String MOXTRA_GET_BINDERS_FAILED = "E-3.3";
    public static final String MOXTRA_BINDER_TO_CATEGORY_FAILED = "E-3.4";
    public static final String MOXTRA_GET_MENTIONS_FAILED = "E-3.5";
    public static final String MOXTRA_GET_FAVORITS_FAILED = "E-3.6";
    public static final String MOXTRA_UPLOAD_USER_PICTURE_FAILED = "E-3.7";
    public static final String MOXTRA_RENAME_CATEGORY_FAILED = "E-3.8";
    public static final String MOXTRA_DELETE_CATEGORY_FAILED = "E-3.9";
    public static final String MOXTRA_GET_BINDERS_DETAILS_FAILED = "E-3.10";
    public static final String MOXTRA_UPLOAD_ORGANIZATION_LOGO_FAILED = "E-3.11";
    public static final String MOXTRA_GET_TODOS_FAILED = "E-3.12";
    public static final String MOXTRA_GET_MEETS_FAILED = "E-3.13";
    public static final String MOXTRA_SCHEDULE_MEET_FAILED = "E-3.14";
    public static final String MOXTRA_UPDATE_MEET_FAILED = "E-3.15";
    public static final String MOXTRA_REMOVE_USER_MEET_FAILED = "E-3.16";
    public static final String MOXTRA_INVITE_USER_MEET_FAILED = "E-3.17";
    public static final String MOXTRA_CREAT_ORGANIZATION_FAILED = "E-3.18";
    public static final String MOXTRA_UPDATE_ORGANIZATION_FAILED = "E-3.18";
    public static final String MOXTRA_UPDATE_TODO_FAILED = "E-3.19";
    public static final String MOXTRA_CREATE_BINDER_FAILED = "E-3.20";
    public static final String MOXTRA_SEARCH_FAILED = "E-3.21";
    public static final String MOXTRA_GET_ORGNAIZATION_USERS_FAILED = "E-3.22";
    public static final String MOXTRA_GET_MEET_INFO_FAILED = "E-3.23";
    public static final String MOXTRA_ACCEPT_MEET_FAILED = "E-3.24";
    
    public static final String PAYMENT_DELETE_FAILED = "E-4.1";
    public static final String PAYMENT_CUSTOMER_HAS_NO_CARD = "E-4.2";
    public static final String PAYMENT_INTEGRATION_ERROR = "E-4.3";

}
