package com.lessomall.bidding.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Tools {

    public static Map<String, String> generateRequestMap() {

        Map<String, String> map = new HashMap<String, String>();

        map.put("appkey", Constant.APP_KEY_ANDROID);
        map.put("timestamp", (System.currentTimeMillis() / 1000) + "");
        map.put("token", new MD5().GetMD5Code(Constant.SECRET_KEY + Constant.DATE_FORMAT_1.format(new Date())));

        return map;
    }


    public static PackageInfo getPackageInfo(Context context) {

        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
        }
        return info;
    }

    /**
     * 从json HASH表达式中获取一个map，该map支持嵌套功能
     *
     * @param jsonString
     * @return
     * @throws JSONException
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map json2Map(String jsonString) {
        JSONObject jsonObject;
        Map valueMap = new HashMap();
        try {
            jsonObject = new JSONObject(jsonString);
            Iterator keyIter = jsonObject.keys();
            String key;
            Object value;

            while (keyIter.hasNext()) {
                key = (String) keyIter.next();
                value = jsonObject.get(key);

                if (value instanceof JSONArray) {
                    List vlist = new ArrayList();
                    vlist = Tools.getList(value.toString());
                    value = vlist;
                }
                valueMap.put(key, value);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return valueMap;
    }

    // 把json 转换为 ArrayList 形式
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> getList(String jsonString) {
        List<Map<String, Object>> list = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            JSONObject jsonObject;
            list = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                list.add(json2Map(jsonObject.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 使用格式<b>_pattern</b>格式化日期输出
     *
     * @param _date    日期对象
     * @param _pattern 日期格式
     * @return 格式化后的日期
     */
    public static String formatDate(Date _date, String _pattern) {

        if (_date == null) {
            return "";
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(_pattern);
        String stringDate = simpleDateFormat.format(_date);

        return stringDate;
    }

    public static Date parseDate(String _date, String _pattern) {

        if (_date == null || "".equals(_date.trim())) {
            return null;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(_pattern);
        try {
            return simpleDateFormat.parse(_date);
        } catch (ParseException e) {
            return null;
        }

    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     */
    public static int daysBetween(Date smdate, Date bdate) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);

            return Integer.parseInt(String.valueOf(between_days));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}