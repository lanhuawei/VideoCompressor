package com.lanhuawei.videocompressor;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Ivan.L LanHuaWei
 * on 2018/1/15.
 */

public class PermissionsActivity extends Activity {
    public static final int PERMISSIONS_GRANTED = 0;
    public static final int PERMISSIONS_DENIED = 1;

    private static final int PERMISSION_REQUEST_CODE = 0;
    private static final String EXTRA_PERMISSIONS = "com.permissions.EXTRA_PERMISSIONS";
    private static final String PACKAGE_URL_SCHEME = "package:";

    private PermissionsChecker mChecker;
    private boolean requiresCheck;

    public static void startActivityForResult(Activity activity, int requestCode, String... permissions) {
        Intent intent = new Intent(activity, PermissionsActivity.class);
        intent.putExtra(EXTRA_PERMISSIONS, permissions);
        ActivityCompat.startActivityForResult(activity, intent, requestCode, null);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (requiresCheck) {
            String[] permissions = getPermissions();
            if (mChecker.lacksPermissions(permissions)) {
                requestPermissions(permissions);
            } else {
                allPermissionsGranted();
            }
        } else {
            requiresCheck = true;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        if (getIntent() == null || !getIntent().hasExtra(EXTRA_PERMISSIONS)) {
            throw new RuntimeException("This Activity needs to be launched using the static startActivityForResult() method.");
        }
        mChecker = new PermissionsChecker(this);
        requiresCheck = true;
    }


    private String[] getPermissions() {
        return getIntent().getStringArrayExtra(EXTRA_PERMISSIONS);
    }

    private void allPermissionsGranted() {
        setResult(PERMISSIONS_GRANTED);
        finish();
    }

//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions
//            , @NonNull int[] grantResults) {
//        if (requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)) {
//            requiresCheck = true;
//            allPermissionsGranted();
//        } else {
//            requiresCheck = false;
//            showMissingPermissionDialog();
//        }
//    }

    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

//    private void showMissingPermissionDialog() {
//        AlertDialog.Builder alertDialogBuilder;
//        alertDialogBuilder = new AlertDialog.Builder(this);
//
//        alertDialogBuilder.setTitle(getString(R.string.tips_permission_request_title))
//                .setMessage(getString(R.string.tips_permission_request_content))
//                .setPositiveButton(R.string.go_and_grant_permission, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        startAppSettings();
//                    }
//                })
//                .setNegativeButton(R.string.deny_and_quit, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        setResult(PERMISSIONS_DENIED);
//                        finish();
//                    }
//                });
//
//        final AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.setCanceledOnTouchOutside(true);
//        alertDialog.show();
//
//    }
//
//    private void startAppSettings() {
//        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
//        startActivity(intent);
//    }

    private void requestPermissions(String... permissions) {
//        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.CAMERA",
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION",
            "Manifest.permission.ACCESS_FINE_LOCATION"};

    /**
     * android6.0权限检查
     *
     * @param context
     */
//    private void verifyStoragePermissions(Context context) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            int checkPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
//            int checkPermission2 = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
//            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions((Activity) context, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
//                return;
//            }
//            if (checkPermission2 != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions((Activity) context, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
//                return;
//            }
////            else {
////                judgeVersion();
////            }
//        }
////        else {
////            judgeVersion();
////        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_EXTERNAL_STORAGE:
//                for (int i = 0; i < grantResults.length; i++) {
//                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
////                        judgeVersion();
//                        return;
//                    } else {
////                        showDialogUtil.showDialog(
////                                getResources().getString(R.string.permission_hint), context);
////                        Toast.makeText(context,  getResources().getString(R.string.permission_hint), Toast.LENGTH_SHORT).show();
//                    }
//                }
//                break;
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        }
//
//    }


}
