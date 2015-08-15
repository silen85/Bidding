package com.lessomall.bidding.common;

import android.os.Environment;

import java.text.SimpleDateFormat;

/**
 * Created by meisl on 2015/6/25.
 */
public class Constant {

    public final static boolean DEVELOPER_MODE = true;

    public static String BASE_DIR = Environment.getExternalStorageDirectory().toString() + "/lessomall/";

    public final static String BASE_URL = "/app/";

    public final static String APP_KEY_ANDROID = "ba25623f";     //对应数据库字段DEVICETYPE = 1;
    public final static String APP_KEY_IOS = "fc98141d";         //对应数据库字段DEVICETYPE = 2;

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

    public static final int CONNECT_TIMEOUT = 18000;

    public static int HTTP_STATUS_CODE_SUCCESS = 200;

    public static String[] CATEGORY_CACHE_LEVEL1;

    //10:保存 20:保存并提交
    public final static String[] OPTERATION_TYPE = new String[]{"10", "20"};


    /**
     * 供应商：
     * 待报价  {"0","30"}
     * 未提交/被退回  {"10","30"}
     * 已报价  {"20","30"}
     * 待发货  {"40","40"}
     * 已发货  {"60","40"}
     */
    public final static String[][] BIDDING_SUPPLIER_ORDER_STATUS = new String[][]{{"0", "30"}, {"10", "30"}, {"20", "30"}, {"40", "40"}, {"60", "40"}};

    /**
     * 经销商：
     * 1:买方填写完竞价单后暂存或被联塑退回
     * 2买方填写完竞价单已提交等待联塑审核后发布
     * 3联塑已审核发布竞价信息到平台等待卖方报价
     * 4卖方已报价但被退回
     * 5卖方已报价等待联塑审核单价后才显示给买方查看
     * 6联塑已审核卖方报价买方可查看价格选择价格
     * 7买方已选择合适价格等待卖方发货
     * 8卖方已发货等待卖方确认收货
     * 9买方确认收到货物
     * 10竞价交易完成归档
     */
    public final static int[] BIDDING_DEALER_ORDER_STATUS = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};


    public final static String RECODE_SUCCESS = "0000";
    public final static String RECODE_FAILED = "0001";
    public final static String RECODE_FAILED_NODATA = "1001";
    public final static String RECODE_FAILED_PARAM_WRONG = "1002";
    public final static String RECODE_FAILED_APPKEY_WRONG = "1003";
    public final static String RECODE_FAILED_TOKEN_WRONG = "1004";
    public final static String RECODE_FAILED_TIMESTAMP_WRONG = "1005";

    public final static String RECODE_FAILED_USER_NOTEXIST = "2002";
    public final static String RECODE_FAILED_PASSWORD_WRONG = "2003";
    public final static String RECODE_FAILED_SESSION_WRONG = "2004";

    public final static String RECODE_ERROR_SYSTEM = "9999";
    public final static String RECODE_ERROR_TIPS = "10000";

    public static final String FINISH_ACTION = "com.lesso.data.FINISH_ACTION";

}
