package tv.duojiao.model.rec;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/26
 */
public class RecommendEnity {
    /**
     * 资讯ID
     */
    private String id;
    /**
     * 资讯标题
     */
    private String title;
    /**
     * 资讯详情
     */
    private String[] summary;
    /**
     * 资讯分类
     */
    private String category;
    /**
     * 资讯发布日期
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;
    /**
     * 资讯展示页图片列表
     */
    private List<String> imageList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getSummary() {
        return summary;
    }

    public void setSummary(String[] summary) {
        this.summary = summary;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
