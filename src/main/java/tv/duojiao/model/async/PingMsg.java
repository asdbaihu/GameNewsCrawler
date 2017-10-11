package tv.duojiao.model.async;

/**
 * PingMsg
 *
 * @author Yodes
 * @version
 */
public class PingMsg extends InfoMsg {
    public PingMsg(String clientId) {
        super(clientId);
        this.setType(MsgType.PING);
    }
}
