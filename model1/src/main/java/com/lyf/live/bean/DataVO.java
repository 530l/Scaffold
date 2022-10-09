package com.lyf.live.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataVO {
    @SerializedName("curPage")
    public Integer curPage;
    @SerializedName("datas")
    public List<DatasVO> datas;
    @SerializedName("offset")
    public Integer offset;
    @SerializedName("over")
    public Boolean over;
    @SerializedName("pageCount")
    public Integer pageCount;
    @SerializedName("size")
    public Integer size;
    @SerializedName("total")
    public Integer total;

    public static class DatasVO {
        @SerializedName("adminAdd")
        public Boolean adminAdd;
        @SerializedName("apkLink")
        public String apkLink;
        @SerializedName("audit")
        public Integer audit;
        @SerializedName("author")
        public String author;
        @SerializedName("canEdit")
        public Boolean canEdit;
        @SerializedName("chapterId")
        public Integer chapterId;
        @SerializedName("chapterName")
        public String chapterName;
        @SerializedName("collect")
        public Boolean collect;
        @SerializedName("courseId")
        public Integer courseId;
        @SerializedName("desc")
        public String desc;
        @SerializedName("descMd")
        public String descMd;
        @SerializedName("envelopePic")
        public String envelopePic;
        @SerializedName("fresh")
        public Boolean fresh;
        @SerializedName("host")
        public String host;
        @SerializedName("id")
        public Integer id;
        @SerializedName("isAdminAdd")
        public Boolean isAdminAdd;
        @SerializedName("link")
        public String link;
        @SerializedName("niceDate")
        public String niceDate;
        @SerializedName("niceShareDate")
        public String niceShareDate;
        @SerializedName("origin")
        public String origin;
        @SerializedName("prefix")
        public String prefix;
        @SerializedName("projectLink")
        public String projectLink;
        @SerializedName("publishTime")
        public Long publishTime;
        @SerializedName("realSuperChapterId")
        public Integer realSuperChapterId;
        @SerializedName("selfVisible")
        public Integer selfVisible;
        @SerializedName("shareDate")
        public Long shareDate;
        @SerializedName("shareUser")
        public String shareUser;
        @SerializedName("superChapterId")
        public Integer superChapterId;
        @SerializedName("superChapterName")
        public String superChapterName;
        @SerializedName("tags")
        public List<?> tags;
        @SerializedName("title")
        public String title;
        @SerializedName("type")
        public Integer type;
        @SerializedName("userId")
        public Integer userId;
        @SerializedName("visible")
        public Integer visible;
        @SerializedName("zan")
        public Integer zan;
    }
}
