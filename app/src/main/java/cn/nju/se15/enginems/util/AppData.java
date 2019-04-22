package cn.nju.se15.enginems.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 果宝 on 2018/3/25.
 */

public class AppData {

    private static Context mContext;

    private static final String APP_DATA = "app_data";

    private static final String IS_LOGIN = "is_login";

    private static final String SAVE_TOKEN = "save_token";
    private static final String BEARER = "Bearer ";

    private static final String SAVE_EXPIRES = "save_expires";

    private AppData() {
    }

    public static void setContext(Context context) {
        mContext = context;
    }

    /**
     * 保存登录状态
     */
    public static void saveLoginState(boolean isLogin) {
        SharedPreferences share = mContext.getSharedPreferences(APP_DATA, mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putBoolean(IS_LOGIN, isLogin);
        editor.apply();
    }

    /**
     * 获取token
     */
    public static boolean getLoginState() {
        SharedPreferences share = mContext.getSharedPreferences(APP_DATA, mContext.MODE_PRIVATE);
        return share.getBoolean(IS_LOGIN, false);
    }

    /**
     * 保存token
     */
    public static void saveToken(String token) {
        SharedPreferences share = mContext.getSharedPreferences(APP_DATA, mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString(SAVE_TOKEN, BEARER + token);
        editor.apply();
    }

    /**
     * 获取token
     */
    public static String getToken() {
        SharedPreferences share = mContext.getSharedPreferences(APP_DATA, mContext.MODE_PRIVATE);
        return share.getString(SAVE_TOKEN, "");
    }

    /**
     * 保存expires
     */
    public static void saveExpires(long expires) {
        SharedPreferences share = mContext.getSharedPreferences(APP_DATA, mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putLong(SAVE_EXPIRES, expires);
        editor.apply();
    }

    /**
     * 获取expires
     */
    public static long getExpires() {
        SharedPreferences share = mContext.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE);
        return share.getLong(SAVE_EXPIRES, 0);
    }

    public static void clear() {
        saveLoginState(false);
        saveToken("");
        saveExpires(0);
    }
}
