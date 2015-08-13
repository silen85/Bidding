package com.lessomall.bidding;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.lessomall.bidding.common.DefaultExceptionHandler;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;


/**
 * Created by meisl on 2015/6/8.
 */
public class LessoApplication extends Application {

    private String terminalid = "";
    private LoginUser user = null;
    private String station = "佛山";

    // 相机拍照图片路径
    // 这里的imgPath是全局变量，有些手机比如三星SCH-I699，
    // 它在拍完照快速跳转回来的时候，imgPath是空的，故此处使用缓存application读取
    private String imgPath = "";

    @Override
    public void onCreate() {
        super.onCreate();


        initImageLoader(getApplicationContext());

        Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler(this));


        //捕获程序的崩溃信息
//		CrashHandler crashHandler = CrashHandler.getInstance();
//		crashHandler.init(this);
    }

    /**
     * 配置使用universal-image-loader-1.9.3.jar显示图片的基本信息
     * 这个jar包可用来异步显示图片,同步下载图片,图片显示的时候自动缓存
     */
    public static void initImageLoader(Context context) {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                // .showImageOnLoading(R.drawable.default_head)
                // .showImageForEmptyUri(R.drawable.default_head)
                // .showImageOnFail(R.drawable.default_head)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();

        ImageLoaderConfiguration config;
        config = new ImageLoaderConfiguration.Builder(
                context).defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .memoryCacheExtraOptions(480, 800)
                .threadPriority(Thread.NORM_PRIORITY)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                        // .writeDebugLogs() // Remove for release app
                .threadPoolSize(4)
                .build();


        ImageLoader.getInstance().init(config);
    }


    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid;
    }

    public LoginUser getUser() {
        return user;
    }

    public void setUser(LoginUser user) {
        this.user = user;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }


    public class LoginUser {

        private String userId = "";
        private String userName = "";
        private String headShip = "";
        private String company = "";
        private String introduce = "";
        private String address = "";
        private String mobile = "";
        private String msgnum = "0";
        private String imgurl = "";
        private String mainsort = "";
        private String token = "";
        private String ismember = "0";
        private String isvalidation = "0";
        private String mobileauth = "0";
        private String iscertshow = "0";
        private String accountno = "";
        private String imPasswd = "";
        private String companyId = "";

        public String getAccountno() {
            return accountno;
        }

        public void setAccountno(String accountno) {
            this.accountno = accountno;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getHeadShip() {
            return headShip;
        }

        public void setHeadShip(String headShip) {
            this.headShip = headShip;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getMsgnum() {
            return msgnum;
        }

        public void setMsgnum(String msgnum) {
            this.msgnum = msgnum;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getMainsort() {
            return mainsort;
        }

        public void setMainsort(String mainsort) {
            this.mainsort = mainsort;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getIsmember() {
            return ismember;
        }

        public void setIsmember(String ismember) {
            this.ismember = ismember;
        }

        public String getIsvalidation() {
            return isvalidation;
        }

        public void setIsvalidation(String isvalidation) {
            this.isvalidation = isvalidation;
        }

        public String getMobileauth() {
            return mobileauth;
        }

        public void setMobileauth(String mobileauth) {
            this.mobileauth = mobileauth;
        }

        public String getIscertshow() {
            return iscertshow;
        }

        public void setIscertshow(String iscertshow) {
            this.iscertshow = iscertshow;
        }

        public String getImPasswd() {
            return imPasswd;
        }

        public void setImPasswd(String imPasswd) {
            this.imPasswd = imPasswd;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

    }

}
