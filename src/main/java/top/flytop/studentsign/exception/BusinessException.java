package top.flytop.studentsign.exception;

/**
 * @ClassName BusinessException
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/1/2 09:46
 * @Version 1.0
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;

    public BusinessException(String msg) {
        super();
        this.msg = msg;
    }

    public BusinessException(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
