package cn.nju.se15.enginems.util;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.util.Log;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by 果宝 on 2018/1/13.
 * 专门负责动态权限管理的类
 */

public class PermissionUtil {
    private volatile static PermissionUtil mPermissionUtil;

    private Activity mActivity;

    private String[] permissions = {
            Manifest.permission.READ_PHONE_STATE,           //获取手机状态的权限
            Manifest.permission.ACCESS_COARSE_LOCATION,     //获取位置信息的权限
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,      //获取读写SD卡的权限
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private PermissionUtil() {
    }

    public static PermissionUtil getInstance() {
        if(mPermissionUtil == null) {
            synchronized (PermissionUtil.class) {
                mPermissionUtil = new PermissionUtil();
            }
        }
        return mPermissionUtil;
    }

    public void getPermissions(Activity activity) {
        if(Build.VERSION.SDK_INT >= 23) {
            this.mActivity = activity;
            if (EasyPermissions.hasPermissions(mActivity, permissions)) { //检查是否获取该权限
                Log.e("getPermission", "已获取权限");
            } else {
                EasyPermissions.requestPermissions(mActivity, "必要的权限", 0, permissions);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //把申请权限的回调交由EasyPermissions处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, mActivity);
    }

    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.e("PermissionUtil", "获取成功的权限" + perms);
    }

    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.e("PermissionUtil", "获取成功的权限" + perms);
    }

}
