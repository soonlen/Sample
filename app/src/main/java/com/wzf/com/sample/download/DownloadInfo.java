package com.wzf.com.sample.download;

/**
 * Created by soonlen on 2017/3/1 16:14.
 * email wangzheng.fang@zte.com.cn
 */

public class DownloadInfo {
    private int threadId;// 下载器id
    private long startPos;// 开始点
    private long endPos;// 结束点
    private long compeleteSize;// 完成度
    private String url;// 下载器网络标识

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public long getStartPos() {
        return startPos;
    }

    public void setStartPos(long startPos) {
        this.startPos = startPos;
    }

    public long getEndPos() {
        return endPos;
    }

    public void setEndPos(long endPos) {
        this.endPos = endPos;
    }

    public long getCompeleteSize() {
        return compeleteSize;
    }

    public void setCompeleteSize(long compeleteSize) {
        this.compeleteSize = compeleteSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
