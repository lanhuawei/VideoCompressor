package com.lanhuawei.videocompressor;

import android.content.Context;

/**
 * Created by Ivan.L LanHuaWei
 * on 2018/1/15.
 */

public class PermissionsChecker {
    private final Context context;

    public PermissionsChecker(Context context) {
        this.context = context;
    }

    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
//            if (lacksPermission(permission)) {
//                return true;
//            }
        }
        return false;
    }

//    private boolean lacksPermission(String permission) {
////        return ContextCompat.checkSelfPermission(context, permission)
////                == PackageManager.PERMISSION_DENIED;
//    }
}
