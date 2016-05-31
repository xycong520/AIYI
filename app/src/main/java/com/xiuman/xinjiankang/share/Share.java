package com.xiuman.xinjiankang.share;/**
 * Created by PCPC on 2015/7/20.
 */

/**
 * 描述: 分享
 * 名称: Share
 * User: csx
 * Date: 07-20
 */
public class Share {

    String title;
    String content;
    String contentUrl;
    String imgUrl;
    String imgPath;

    /**
     *
     * @param title 标题
     * @param content 内容
     * @param contentUrl 跳转链接
     * @param imgUrl 图片地址(网络)
     * @param imgPath 图片路径(本地)
     */
    public Share(String title, String content, String contentUrl, String imgUrl, String imgPath) {
        this.title = title;
        this.content = content;
        this.contentUrl = contentUrl;
        this.imgUrl = imgUrl;
        this.imgPath = imgPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
