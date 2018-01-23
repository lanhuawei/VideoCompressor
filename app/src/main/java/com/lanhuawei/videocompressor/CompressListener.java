package com.lanhuawei.videocompressor;

/**
 * Created by Ivan.L LanHuaWei
 * on 2018/1/15.
 */

public interface CompressListener {
    void onExecSuccess(String message);
    void onExecFail(String reason);
    void onExecProgress(String message);
}
