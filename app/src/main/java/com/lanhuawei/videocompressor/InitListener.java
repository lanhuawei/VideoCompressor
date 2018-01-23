package com.lanhuawei.videocompressor;

/**
 * Created by Ivan.L LanHuaWei
 * on 2018/1/15.
 */

public interface InitListener {
    void onLoadSuccess();
    void onLoadFail(String reason);
}
