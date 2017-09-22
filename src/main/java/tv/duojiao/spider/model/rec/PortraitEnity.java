package tv.duojiao.spider.model.rec;

import java.util.Date;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/21
 */
public class PortraitEnity {
    private int pid;
    private int uid;
    private String keyword;
    private Double score;
    private Date last_update;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }
}
