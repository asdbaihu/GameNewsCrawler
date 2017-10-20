package tv.duojiao.core.Exceptions;

/**
 * Description:
 * User: Yodes
 * Date: 2017/10/21
 */
public class ImgException extends RuntimeException {
    private String errCode;
    private String message;

    public ImgException() {
        super();
    }

    public ImgException(String message) {
        super();
        this.message = message;
    }

    public ImgException(String errCode, String message) {
        super();
        this.errCode = errCode;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.getMessage();
    }

    @Override
    public void printStackTrace() {
        System.err.println("ImgException发生：{errcode: " + errCode + ", message: " + message + "}");
    }
}
