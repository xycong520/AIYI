package com.xiuman.xinjiankang.Bean;

import java.io.Serializable;

/**
 * 描述：科普知识详情
 * Created by hxy on 2015/8/20.
 */
public class ScienceDetail implements Serializable{

    private static final long serialVersionUID = -7497722028652124827L;
    /**
     * id : 402890094eaa548b014eaa6461500001
     * icon : null
     * title : adfadfwe
     * description : afweqwefdsa
     * createDate : 1437377782000
     */
    private String id;
    private String icon;
    private String title;
    private String description;
    private String labelName;
    private long createDate;
    //点赞数量
    private int pariseCount;
    //评论数量
    private int commentCount;
    //标签
    private String categoryName;

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getPariseCount() {
        return pariseCount;
    }

    public void setPariseCount(int pariseCount) {
        this.pariseCount = pariseCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public String getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getCreateDate() {
        return createDate;
    }
}
