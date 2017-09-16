package tv.duojiao.spider.model.robots;


import java.util.ArrayList;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/15
 */
public class NewsEnity {
    public ArrayList<ResultList> resultList;
    public String keyword;
    public Integer count;
    public Integer time;
    public boolean success;

    public ArrayList<ResultList> getResultList() {
        return resultList;
    }

    public void setResultList(ArrayList<ResultList> resultList) {
        this.resultList = resultList;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
