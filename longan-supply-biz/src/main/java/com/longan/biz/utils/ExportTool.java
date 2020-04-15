package com.longan.biz.utils;

public class ExportTool {
    private final static String DOWNLOAD_PATH = Utils.getProperty("download.url");

    public static int getTotalPage(int totalCount, int maxCount) {
	int result = totalCount / maxCount;
	if ((totalCount == 0) || ((totalCount % maxCount) != 0)) {
	    result++;
	}
	return result;
    }

    public static String getFilePath(Long userId, String bizId) {
	return new StringBuilder(DOWNLOAD_PATH).append(userId).append("/").append(bizId).append("/").toString();
    }

    public static String getFileName(String fileName) {
	int pos = fileName.indexOf("from");
	return fileName.substring(pos);
    }
}
