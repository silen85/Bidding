package com.lessomall.bidding.common;

import java.text.SimpleDateFormat;

/**
 * Created by meisl on 2015/6/25.
 */
public class Constant {

    public final static boolean DEVELOPER_MODE = false;

    public static String LESSO_BIDDING_USERNAME = "LESSO_BIDDING_USERNAME";
    public static String LESSO_BIDDING_USERPASSWORD = "LESSO_BIDDING_USERPASSWORD";

    public final static String BASE_URL = "http://10.10.7.155:8080/lots-web/bidding/";
    public final static String URL_LOGIN = "http://10.10.7.155:8080/lots-web/app/login/";
    public final static String URL_FIRST_CATEGORY = "http://10.10.7.155:8080/lots-web/app/findFirstCategory/";
    public final static String URL_PUSH_BIND = BASE_URL + "pushBind/";
    public final static String URL_UPDATE = BASE_URL + "androidversion/";
    public final static String URL_MAINCOUNT = BASE_URL + "mainCount/";
    public final static String BIDDING_LIST = BASE_URL + "biddingList/";
    public final static String DEALER_CONFIRM_PRICE = BASE_URL + "dealerConfirmPrice/";
    public final static String DEALER_CONFIRM_STORE = BASE_URL + "dealerConfirmStore/";
    public final static String SEARCH_PRODUCT = BASE_URL + "searchProduct/";
    public final static String DEALER_ADD_PRICE = BASE_URL + "dealerAddPrice/";
    public final static String SUPPLIER_CONFIRM_STORE = BASE_URL + "confirmSend/";
    public final static String SUPPLIER_SUBMIT_PRICE = BASE_URL + "submitPrice/";

    public final static String APP_KEY_ANDROID = "ba25623f";     //对应数据库字段DEVICETYPE = 1;

    public final static String SECRET_KEY = "db1e358753b4fe1735dcf50dc1bf465b";

    public final static int IMG_MAX_COUNT = 5;
    public final static String[] IMG_SUFFIX = {".jpg", ".jpeg", ".png", ".bmp"};

    public static final SimpleDateFormat DATE_FORMAT_1 = new SimpleDateFormat("yyyy-MM-dd");

    public static final int CONNECT_TIMEOUT = 8000;

    public static int HTTP_STATUS_CODE_SUCCESS = 200;

    public static String[] CATEGORY_CACHE_LEVEL1;

    public final static int APP_BIDDING_STATUS_1 = 1, APP_BIDDING_STATUS_2 = 2, APP_BIDDING_STATUS_3 = 3, APP_BIDDING_STATUS_4 = 4, APP_BIDDING_STATUS_5 = 5, APP_BIDDING_STATUS_6 = 6, APP_BIDDING_STATUS_7 = 7;

    public final static int APP_QUOTE_STATUS_1 = 1, APP_QUOTE_STATUS_2 = 2, APP_QUOTE_STATUS_3 = 3, APP_QUOTE_STATUS_4 = 4, APP_QUOTE_STATUS_5 = 5, APP_QUOTE_STATUS_6 = 6;

    //10:保存 20:保存并提交
    public final static String[] OPTERATION_TYPE = new String[]{"10", "20"};

    public final static int PAGE_SIZE = 10;

    public final static String RECODE_SUCCESS = "0000";
    public final static String RECODE_FAILED = "0001";
    public final static String RECODE_FAILED_NODATA = "1001";
    public final static String RECODE_FAILED_PARAM_WRONG = "1002";

    public final static String RECODE_FAILED_USER_LOGIN = "2001";
    public final static String RECODE_FAILED_USER_NOTEXIST = "2002";
    public final static String RECODE_FAILED_PASSWORD_WRONG = "2003";
    public final static String RECODE_FAILED_SESSION_WRONG = "2004";

    public final static String RECODE_ERROR_SYSTEM = "9999";
    public final static String RECODE_ERROR_TIPS = "10000";

    public static final String FINISH_ACTION = "com.lessomall.bidding.FINISH_ACTION";

    public static final String FINISH_ACTION_BIDDINGLIST = "com.lessomall.bidding.FINISH_ACTION_BIDDINGLIST";

    public static final String FINISH_ACTION_QUOTELIST  = "com.lessomall.bidding.FINISH_ACTION_QUOTELIST";

    public static final String FINISH_ACTION_ORDERLIST = "com.lessomall.bidding.FINISH_ACTION_ORDERLIST";

    static {


    }

}
