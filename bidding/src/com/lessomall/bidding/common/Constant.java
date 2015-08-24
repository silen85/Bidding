package com.lessomall.bidding.common;

import android.os.Environment;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by meisl on 2015/6/25.
 */
public class Constant {

    public final static boolean DEVELOPER_MODE = false;

    public static String LESSO_BIDDING_USERNAME = "LESSO_BIDDING_USERNAME";
    public static String LESSO_BIDDING_USERPASSWORD = "LESSO_BIDDING_USERPASSWORD";

    public static String BASE_DIR = Environment.getExternalStorageDirectory().toString() + "/lessomall/";

    public final static String BASE_URL = "http://10.10.7.155:8080/lots-web/app/";
    public final static String URL_LOGIN = BASE_URL + "login/";
    public final static String URL_UPDATE = BASE_URL + "androidversion/";
    public final static String URL_MAINCOUNT = BASE_URL + "mainCount/";
    public final static String BIDDING_LIST = BASE_URL + "biddingList/";

    public final static String APP_KEY_ANDROID = "ba25623f";     //对应数据库字段DEVICETYPE = 1;

    public final static String SECRET_KEY = "db1e358753b4fe1735dcf50dc1bf465b";

    public final static int INIT_PAGENO = 1;
    public final static int INIT_PAGESIZE = 10;

    public final static int CUSTOMER_TYPE_SUPPLIER = 1;

    public final static int CUSTOMER_TYPE_PROXIER_LEVEL1 = 2;

    public final static String[] IMG_SUFFIX = {".jpg", ".jpeg", ".png", ".bmp"};
    public final static long IMG_MAX_SIZE = 512 * 1024;  //   500k

    public static final SimpleDateFormat DATE_FORMAT_1 = new SimpleDateFormat("yyyy-MM-dd");

    public static final SimpleDateFormat DATE_FORMAT_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat DATE_FORMAT_3 = new SimpleDateFormat("yyyy-MM");

    public static final SimpleDateFormat DATE_FORMAT_4 = new SimpleDateFormat("HHmmss");

    public static final SimpleDateFormat DATE_FORMAT_5 = new SimpleDateFormat("mm/dd/yyyy");

    public static final int CONNECT_TIMEOUT = 12000;

    public static int HTTP_STATUS_CODE_SUCCESS = 200;

    public static String[] CATEGORY_CACHE_LEVEL1;

    //10:保存 20:保存并提交
    public final static String[] OPTERATION_TYPE = new String[]{"10", "20"};

    public final static Map<String,String> BIDDING_STATUS_MAP = new HashMap<String, String>(7);

    public final static Map<String,String> QUOTE_STATUS_MAP = new HashMap<String, String>(6);

    public final static int PAGE_SIZE = 10;

    public final static String RECODE_SUCCESS = "0000";
    public final static String RECODE_FAILED = "0001";
    public final static String RECODE_FAILED_NODATA = "1001";
    public final static String RECODE_FAILED_PARAM_WRONG = "1002";
    public final static String RECODE_FAILED_APPKEY_WRONG = "1003";
    public final static String RECODE_FAILED_TOKEN_WRONG = "1004";
    public final static String RECODE_FAILED_TIMESTAMP_WRONG = "1005";

    public final static String RECODE_FAILED_USER_LOGIN = "2001";
    public final static String RECODE_FAILED_USER_NOTEXIST = "2002";
    public final static String RECODE_FAILED_PASSWORD_WRONG = "2003";
    public final static String RECODE_FAILED_SESSION_WRONG = "2004";

    public final static String RECODE_ERROR_SYSTEM = "9999";
    public final static String RECODE_ERROR_TIPS = "10000";

    public static final String FINISH_ACTION = "com.lessomall.bidding.FINISH_ACTION";

    static {

        BIDDING_STATUS_MAP.put("1","未提交");
        BIDDING_STATUS_MAP.put("2","待审核");
        BIDDING_STATUS_MAP.put("3","竞价中");
        BIDDING_STATUS_MAP.put("4","已审核");
        BIDDING_STATUS_MAP.put("5","已确认报价");
        BIDDING_STATUS_MAP.put("6","已发货");
        BIDDING_STATUS_MAP.put("7","已收货");

        QUOTE_STATUS_MAP.put("1","待报价");
        QUOTE_STATUS_MAP.put("2","已报价");
        QUOTE_STATUS_MAP.put("3","被退回");
        QUOTE_STATUS_MAP.put("4","竞价达成待发货");
        QUOTE_STATUS_MAP.put("5","已收货");
        QUOTE_STATUS_MAP.put("6","已确认收货");

    }

}
