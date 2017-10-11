package tv.duojiao.model.async;

/**
 * CallbackMsg
 *
 * @author Yodes
 * @version
 */
public class CallbackMsg extends BaseMsg {

    public CallbackMsg(String clientId) {
        super(clientId);
        this.setType(MsgType.CALLBACK);
    }
}
