package com.lanhuawei.videocompressor;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 视频压缩
 */

public class MainActivity extends Activity {
    private String currentInputVideoPath = "/storage/emulated/0/DCIM/Camera/VID_20180116_081915.mp4";
    private String currentOutputVideoPath = "/storage/emulated/0/DCIM/Camera/out.mp4";
    String cmd = "-y -i " + currentInputVideoPath + " -strict -2 -vcodec libx264 -preset ultrafast " +
            "-crf 23 -acodec aac -ar 44100 -ac 2 -b:a 64k -s 640x480 -aspect 16:9 -r 15 " + currentOutputVideoPath;

    private TextView tvVideoFilePath;
    private TextView etCommand;
    private ScrollView scrollView;
    private TextView tvLog;
    private Button btnRun;
    private Button play;
    private Compressor mCompressor;
    //相机权限,录制音频权限,读写sd卡的权限,都为必须,缺一不可
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    private Context context = MainActivity.this;


    private static final int REQUEST_CODE_FOR_PERMISSIONS = 0;//


//    private MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyStoragePermissions(context);

        tvVideoFilePath = (TextView) findViewById(R.id.tvVideoFilePath);//
        etCommand = (TextView) findViewById(R.id.etCommand);//运行命令
        scrollView = (ScrollView) findViewById(R.id.scrollView);//
        tvLog = (TextView) findViewById(R.id.tvLog);
        btnRun = (Button) findViewById(R.id.btnRun);

        etCommand.setText(cmd);
        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String command = etCommand.getText().toString();
                if (TextUtils.isEmpty(command)) {
                    Toast.makeText(MainActivity.this, getString(R.string.compree_please_input_command)
                            , Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(currentInputVideoPath)) {
                    Toast.makeText(MainActivity.this, R.string.no_video_tips, Toast.LENGTH_SHORT).show();
                } else {
                    File file = new File(currentOutputVideoPath);
                    if (file.exists()) {
                        file.delete();
                    }
                    execCommand(command);
                }
            }
        });

        mCompressor = new Compressor(this);
        mCompressor.loadBinary(new InitListener() {
            @Override
            public void onLoadSuccess() {
//                Log.v(TAG, "load library succeed");
                textAppend(getString(R.string.compress_load_library_succeed));
            }

            @Override
            public void onLoadFail(String reason) {
//                Log.i(TAG, "load library fail:" + reason);
                textAppend(getString(R.string.compress_load_library_failed, reason));
            }
        });

        play = (Button) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoPlayActivity.class);
                startActivity(intent);

            }
        });

    }

    private void execCommand(String cmd) {
        File mFile = new File(currentOutputVideoPath);
        if (mFile.exists()) {
            mFile.delete();
        }
        mCompressor.execCommand(cmd
                ,new CompressListener() {
            @Override
            public void onExecSuccess(String message) {
//                Log.i(TAG, "success " + message);
//                textAppend(getString(R.string.compress_succeed));
//                Toast.makeText(getApplicationContext(), R.string.compress_succeed, Toast.LENGTH_SHORT).show();
                String result = getString(R.string.compress_result_input_output, currentInputVideoPath
                        , getFileSize(currentInputVideoPath), currentOutputVideoPath, getFileSize(currentOutputVideoPath));
                textAppend(result);

//                mMaterialDialog = new MaterialDialog(MainActivity.this);
//                Toast.makeText(MainActivity.this, "dfsdfd", Toast.LENGTH_SHORT).show();


//                        .setTitle(getString(R.string.compress_succeed))
//                        .setMessage(result)
//                        .setPositiveButton(getString(R.string.open_video), new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                openFile(new File(currentOutputVideoPath));
//                                mMaterialDialog.dismiss();
//                            }
//                        })
//                        .setNegativeButton(getString(R.string.cancel), new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                mMaterialDialog.dismiss();
//                            }
//                        })
//                        ;
//                mMaterialDialog.show();

            }

            @Override
            public void onExecFail(String reason) {
//                Log.i(TAG, "fail " + reason);
//                textAppend(getString(R.string.compress_failed, reason));
//                mMaterialDialog = new MaterialDialog(MainActivity.this)
//                        .setTitle(getString(R.string.compress_failed))
//                        .setMessage(getString(R.string.compress_failed))
//                        .setPositiveButton(getString(R.string.confirm), new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                mMaterialDialog.dismiss();
//                            }
//                        });
//                mMaterialDialog.show();
            }

            @Override
            public void onExecProgress(String message) {
//                Log.i(TAG, "progress " + message);
                textAppend(getString(R.string.compress_progress, message));
//                Log.v(TAG,getString(R.string.compress_progress,getProgress(message)));


            }
        }
        );

//        PermissionsChecker mChecker = new PermissionsChecker(getApplicationContext());
//        if (mChecker.lacksPermissions(PERMISSIONS)) {
//            PermissionsActivity.startActivityForResult(this, REQUEST_CODE_FOR_PERMISSIONS, PERMISSIONS);
//
//        }


    }

    private boolean requiresCheck;
    private PermissionsChecker mChecker;

    @Override
    protected void onResume() {
        super.onResume();
//        if (requiresCheck) {
//            String[] permissions = PERMISSIONS;
//            if (mChecker.lacksPermissions(permissions)) {
//                requestPermissions(permissions);
//            }
////            else {
//////                allPermissionsGranted();
////            }
//        } else {
//            requiresCheck = true;
//        }



    }


    private static final String PACKAGE_URL_SCHEME = "package:";

    private void startAppSettings() {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }


    private static final int PERMISSION_REQUEST_CODE = 0;
    private void requestPermissions(String... permissions) {
//        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }


    private static final Handler handler = new Handler();
    private void textAppend(String text) {
        if (!TextUtils.isEmpty(text)) {
            tvLog.append(text + "\n");
            handler.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
    }


    private void openFile(File file) {
        try {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //设置intent的Action属性
            intent.setAction(Intent.ACTION_VIEW);
            //获取文件file的MIME类型
            String type = getMIMEType(file);
            //设置intent的data和Type属性。
            intent.setDataAndType(/*uri*/Uri.fromFile(file), type);
            //跳转
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, R.string.dont_have_app_to_open_file, Toast.LENGTH_SHORT).show();
        }

    }

    private String getFileSize(String path) {
        File f = new File(path);
        if (!f.exists()) {
            return "0 MB";
        } else {
            long size = f.length();
            return (size / 1024f) / 1024f + "MB";
        }
    }

    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param file
     */
    private String getMIMEType(File file) {

        String type = "*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /* 获取文件的后缀名*/
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "") return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    private final String[][] MIME_MapTable = {
            //{后缀名，MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"},
            {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"},
            {"", "*/*"}
    };

    private Double videoLength = 0.00;//视频时长 s
    private String getProgress(String source) {
        //progress frame=   28 fps=0.0 q=24.0 size= 107kB time=00:00:00.91 bitrate= 956.4kbits/s
        Pattern p = Pattern.compile("00:\\d{2}:\\d{2}");
        Matcher m = p.matcher(source);
        if (m.find()) {
            //00:00:00
            String result = m.group(0);
            String temp[] = result.split(":");
            Double seconds = Double.parseDouble(temp[1]) * 60 + Double.parseDouble(temp[2]);
            if (0 != videoLength) {
                return seconds / videoLength+"";
            }
            return "0";
        }
        return "";
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    /**
     * android6.0权限检查
     *
     * @param context
     */
    private void verifyStoragePermissions(Context context) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////            int checkPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO);
////            int checkPermission2 = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            for (int i = 0; i < PERMISSIONS.length; i++) {
//                int checkPermission = ContextCompat.checkSelfPermission(context, PERMISSIONS[i]);
//                if (checkPermission != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions((Activity) context, PERMISSIONS, REQUEST_EXTERNAL_STORAGE);
//                    return;
//                }
//
//            }
//
//        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_EXTERNAL_STORAGE:
//                for (int i = 0; i < grantResults.length; i++) {
//                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                        return;
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
