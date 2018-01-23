package com.lanhuawei.videocompressor;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by Ivan.L LanHuaWei
 * on 2018/1/17.
 */

public class VideoPlayActivity extends Activity {
    private VideoView videoView;
    private TextView percentTv;
    private TextView netSpeedTv;
    private int mVideoLayout = 0;
    private String url1 = "http://img5.dfhon.cn/imagefiles/201801/17/2018011716044702403379.mp4";
//    http://img5.dfhon.cn/imagefiles/201801/11/2018011115555023345061.mp4
//    http://img5.dfhon.cn/imagefiles/201801/16/2018011617594884933387.mp4
//    http://img5.dfhon.cn/imagefiles/201801/17/2018011716044702403379.mp4
    private RelativeLayout playVideo;

    private ImageButton ib_video_play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (!LibsChecker.checkVitamioLibs(this)) {
//            return;
//        }
        setContentView(R.layout.activity_video_play);
        //显示缓冲百分比的TextView
        percentTv = (TextView) findViewById(R.id.buffer_percent);
        //显示下载网速的TextView
        netSpeedTv = (TextView) findViewById(R.id.net_speed);
        ib_video_play = (ImageButton) findViewById(R.id.ib_video_play);
        //初始化加载库文件

        //初始化加载库文件
        if (Vitamio.isInitialized(this)) {
            videoView = (VideoView) findViewById(R.id.vitamio);
            videoView.setVideoURI(Uri.parse(url1));
            videoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
            playVideo = (RelativeLayout) findViewById(R.id.playVideo);

            MediaController controller = new MediaController(this, true, playVideo);
//            MediaController controller = new MediaController(this);
            videoView.setMediaController(controller);
            videoView.setBufferSize(5120); //设置视频缓冲大小。默认1024KB，单位byte
            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    // optional need Vitamio 4.0
                    mediaPlayer.setPlaybackSpeed(1.0f);
                    //mediaPlayer.setLooping(true);
                }
            });

            videoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    percentTv.setText("已缓冲：" + percent + "%");
                }
            });
            videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    switch (what) {
                        //开始缓冲
                        case MediaPlayer.MEDIA_INFO_BUFFERING_START:
//                            percentTv.setVisibility(View.VISIBLE);
                            percentTv.setVisibility(View.GONE);
                            netSpeedTv.setVisibility(View.VISIBLE);
//                            ib_video_play.setVisibility(View.VISIBLE);
                            mp.pause();
                            break;
                        //缓冲结束
                        case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                            percentTv.setVisibility(View.GONE);
                            netSpeedTv.setVisibility(View.GONE);
//                            mp.start(); //缓冲结束再播放
//                            ib_video_play.setVisibility(View.VISIBLE);
                            mp.pause();
//                            setImageButtonClick(mp);
                            break;
                        //正在缓冲
                        case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                            netSpeedTv.setText("当前网速:" + extra + "kb/s");
//                            ib_video_play.setVisibility(View.VISIBLE);
                            mp.pause();
                            break;
                    }
                    return true;
                }
            });
        }



    }

    private void setImageButtonClick(final MediaPlayer mediaPlayer) {
        if (mediaPlayer.isPlaying()) {
            ib_video_play.setVisibility(View.GONE);
        } else {
            ib_video_play.setVisibility(View.VISIBLE);
        }
        ib_video_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                }
            }
        });
    }


    public void changeLayout(View view) {
        mVideoLayout++;
        if (mVideoLayout == 4) {
            mVideoLayout = 0;
        }
        switch (mVideoLayout) {
            case 0:
                mVideoLayout = VideoView.VIDEO_LAYOUT_ORIGIN;
//                view.setBackgroundResource(R.mipmap.mediacontroller_sreen_size_100);
                break;
            case 1:
                mVideoLayout = VideoView.VIDEO_LAYOUT_SCALE;
//                view.setBackgroundResource(R.mipmap.mediacontroller_screen_fit);
                break;
            case 2:
                mVideoLayout = VideoView.VIDEO_LAYOUT_STRETCH;
//                view.setBackgroundResource(R.mipmap.mediacontroller_screen_size);
                break;
            case 3:
                mVideoLayout = VideoView.VIDEO_LAYOUT_ZOOM;
//                view.setBackgroundResource(R.mipmap.mediacontroller_sreen_size_crop);

                break;
        }
//        videoView.setVideoLayout(mVideoLayout, 0);
    }


}
