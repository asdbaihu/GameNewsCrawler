package tv.duojiao.model.corn;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/15
 */
public class WebpageEntity {
    public String keyword;
    public int count;
    public int time;
    public boolean success;
    public ResultEntity result;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ResultEntity getResult() {
        return result;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }
}
