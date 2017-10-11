package tv.duojiao.model.async;

/**
 * LoginMsg
 *
 * @author Yodes
 * @version
 */
public class LoginMsg extends BaseMsg {

    public LoginMsg(String clientId) {
        super(clientId);
        this.setType(MsgType.LOGIN);
    }
}
