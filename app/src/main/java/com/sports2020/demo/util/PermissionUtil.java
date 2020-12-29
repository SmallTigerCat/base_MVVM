package com.sports2020.demo.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.sports2020.demo.R;
import com.sports2020.demo.view.PopupDialog;
import com.tbruyelle.rxpermissions2.BuildConfig;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * 动态权限申请
 * Created by KongXin on 2020/7/8
 */
public class PermissionUtil {

    /**
     * 动态请求权限
     * 当有多个权限请求时，拒绝了其中一个权限，那返回的结果也是 拒绝权限
     * @param permissionsListener 返回结果监听
     * @param permissions         请求的权限
     */
    public static void RxPermissions(FragmentActivity activity, final PermissionsListener permissionsListener, String... permissions) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        Disposable subscribe = rxPermissions.requestEachCombined(permissions)
                .subscribe(permission -> {
                    if (permission.granted) { // 用户已经同意该权限
                        permissionsListener.granted();
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时。还会提示请求权限的对话框
                        List<String> refuseName = new ArrayList<>();
                        for (String s : permissions) {
                            //把被拒绝的权限添加进集合
                            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, s)) {
                                refuseName.add(s);
                            }
                        }
                        permissionsListener.rationale(refuseName);
                    } else {
                        // 用户拒绝了该权限，而且选中『不再询问』那么下次启动时，就不会提示出来了，
                        //处理权限名字字符串
                        PopupDialog.create(activity, activity.getString(R.string.permission_title),
                                activity.getString(R.string.permission_need) + permission.name
                                + activity.getString(R.string.permission_detail),
                                activity.getString(R.string.confirm), view -> {
                            //打开系统权限页面
                            PermissionUtil.gotoPermission(activity);
                        }, activity.getString(R.string.cancel),
                                view -> activity.finish(), false, false, true).show();
                    }
                });
    }

    /**
     * 权限结果监听
     */
    public interface PermissionsListener {
        /**
         * 用户同意
         */
        void granted();

        /**
         * 用户拒绝
         */
        void rationale(List<String> refuseName);
    }

    private static void gotoPermission(Context context) {
        String brand = Build.BRAND;//手机厂商
        if (TextUtils.equals(brand.toLowerCase(), "redmi") || TextUtils.equals(brand.toLowerCase(), "xiaomi")) {
            PermissionUtil.gotoMiuiPermission(context);//小米
        } else if (TextUtils.equals(brand.toLowerCase(), "meizu")) {
            PermissionUtil.gotoMeizuPermission(context);
        } else if (TextUtils.equals(brand.toLowerCase(), "huawei") || TextUtils.equals(brand.toLowerCase(), "honor")) {
            PermissionUtil.gotoHuaweiPermission(context);
        } else {
            context.startActivity(PermissionUtil.getAppDetailSettingIntent(context));
        }
    }


    /**
     * 跳转到miui的权限管理页面
     */
    private static void gotoMiuiPermission(Context context) {
        try { // MIUI 8
            Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            localIntent.putExtra("extra_pkgname", context.getPackageName());
            context.startActivity(localIntent);
        } catch (Exception e) {
            try { // MIUI 5/6/7
                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                localIntent.putExtra("extra_pkgname", context.getPackageName());
                context.startActivity(localIntent);
            } catch (Exception e1) { // 否则跳转到应用详情
                context.startActivity(getAppDetailSettingIntent(context));
            }
        }
    }

    /**
     * 跳转到魅族的权限管理系统
     */
    private static void gotoMeizuPermission(Context context) {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            context.startActivity(getAppDetailSettingIntent(context));
        }
    }

    /**
     * 华为的权限管理页面
     */
    private static void gotoHuaweiPermission(Context context) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            context.startActivity(getAppDetailSettingIntent(context));
        }

    }

    /**
     * 获取应用详情页面intent（如果找不到要跳转的界面，也可以先把用户引导到系统设置页面）
     */
    private static Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        return localIntent;
    }
}
