package tv.duojiao.spider.model.robots;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/15
 */
public class ResultEnity{
    public String content;
    public String title;
    public String url;
    public String domain;
    public String spiderUUID;
    public String spiderInfoId;
    public String category;
    public String rawHTML;
    public ArrayList<String> keywords;
    public ArrayList<String> summary;
    public String gatherTime;
    public String id;
    public String publishTime;

    /**
     * 命名实体
     */
    private Map<String, Set<String>> namedEntity;
    /**
     * 动态字段
     */
    private Map<String, Object> dynamicFields;
    /**
     * 静态字段
     */
    private Map<String, Object> staticFields;
    /**
     * 本网页处理时长
     */
    private long processTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSpiderUUID() {
        return spiderUUID;
    }

    public void setSpiderUUID(String spiderUUID) {
        this.spiderUUID = spiderUUID;
    }

    public String getSpiderInfoId() {
        return spiderInfoId;
    }

    public void setSpiderInfoId(String spiderInfoId) {
        this.spiderInfoId = spiderInfoId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRawHTML() {
        return rawHTML;
    }

    public void setRawHTML(String rawHTML) {
        this.rawHTML = rawHTML;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public ArrayList<String> getSummary() {
        return summary;
    }

    public void setSummary(ArrayList<String> summary) {
        this.summary = summary;
    }

    public String getGatherTime() {
        return gatherTime;
    }

    public void setGatherTime(String gatherTime) {
        this.gatherTime = gatherTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public Map<String, Set<String>> getNamedEntity() {
        return namedEntity;
    }

    public void setNamedEntity(Map<String, Set<String>> namedEntity) {
        this.namedEntity = namedEntity;
    }

    public Map<String, Object> getDynamicFields() {
        return dynamicFields;
    }

    public void setDynamicFields(Map<String, Object> dynamicFields) {
        this.dynamicFields = dynamicFields;
    }

    public Map<String, Object> getStaticFields() {
        return staticFields;
    }

    public void setStaticFields(Map<String, Object> staticFields) {
        this.staticFields = staticFields;
    }

    public long getProcessTime() {
        return processTime;
    }

    public void setProcessTime(long processTime) {
        this.processTime = processTime;
    }
}