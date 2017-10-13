package tv.duojiao.model.rec;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;

public class Portrait {
    @Id
    private Integer pid;

    private Integer uid;

    private Integer gid;

    private String keyword;

    private double score;

    @Column(name = "last_update")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdate;

    public Portrait() {
    }

    public Portrait(Integer pid, Integer uid, Integer gid, String keyword, double score, Date lastUpdate) {
        this.pid = pid;
        this.uid = uid;
        this.gid = gid;
        this.keyword = keyword;
        this.score = score;
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return pid
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * @return uid
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * @param uid
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    /**
     * @return keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    /**
     * @return last_update
     */
    public Date getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate
     */
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "Portrait{" +
                "pid=" + pid +
                ", uid=" + uid +
                ", gid=" + gid +
                ", keyword='" + keyword + '\'' +
                ", score=" + score +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}