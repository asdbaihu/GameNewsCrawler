package tv.duojiao.spider.model.async;

/**
 * InfoMsg
 *
 * @author Yodes
 * @version
 */
public class InfoMsg extends BaseMsg {
    private String info;

    public InfoMsg(String clientId) {
        super(clientId);
        this.setType(MsgType.INFO);
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
