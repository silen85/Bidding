package com.lessomall.bidding;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.lessomall.bidding.common.DefaultExceptionHandler;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
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
                //.showImageOnLoading(R.drawable.default_head)
                //.showImageForEmptyUri(R.mipmap.pic_default)
                //.showImageOnFail(R.mipmap.pic_default)
                .cacheInMemory(true).cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();

        ImageLoaderConfiguration config;
        config = new ImageLoaderConfiguration.Builder(
                context).defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new LruMemoryCache(3 * 1024 * 1024))
                .memoryCacheSize(3 * 1024 * 1024)
                .memoryCacheExtraOptions(480, 800)
                .threadPriority(Thread.NORM_PRIORITY)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .diskCacheFileCount(100)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs()
                .threadPoolSize(3)
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

        private String userid = "";
        private String username = "";
        private String sessionid = "";
        private String type = "";
        private String email = "";
        private String status = "";
        private String customName = "";
        private String address = "";
        private String linkman = "";
        private String phone = "";
        private String lastLoginTime = "";
        private String createTime = "";

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getSessionid() {
            return sessionid;
        }

        public void setSessionid(String sessionid) {
            this.sessionid = sessionid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCustomName() {
            return customName;
        }

        public void setCustomName(String customName) {
            this.customName = customName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }

}
