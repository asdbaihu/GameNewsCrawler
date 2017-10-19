package tv.duojiao.model.corn;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/15
 */public class ResultList {
    public String id;
    public Integer processTime;

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Integer processTime) {
        this.processTime = processTime;
    }
}
