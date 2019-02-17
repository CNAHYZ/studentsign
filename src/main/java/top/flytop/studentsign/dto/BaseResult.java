package top.flytop.studentsign.dto;

/**
 * @ClassName BaseResult
 * @Description 封装json对象，所有返回结果都使用它
 * @Auther Wonder-yz
 * @Date 2018/12/29 12:20
 * @Version 1.0
 */
public class BaseResult<T> {
    // 是否成功的标识
    private boolean success;

    // 成功时返回的数据
    private T data;

    // 错误码
    private int errCode;

    // 错误信息
    private String errMsg;

    public BaseResult() {
        super();
    }

    /**
     * @param success
     * @param data
     * @Title:BaseResult
     * @Description:数据获取成功时使用的构造器
     */
    public BaseResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    /***
     * @Title:BaseResult
     * @Description:数据获取失败时使用的构造器
     * @param success
     * @param errCode
     * @param errMsg
     */
    public BaseResult(boolean success, int errCode, String errMsg) {
        this.success = success;
        this.errCode = errCode;
        this.errMsg = errMsg;

    }

    /**
     * @param errCode
     * @param errMsg
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 失败
     * @Date 2019/2/12 21:35
     */
    public static BaseResult fail(int errCode, String errMsg) {
        return new BaseResult(false, errCode, errMsg);
    }

    /**
     * @param data
     * @return top.flytop.studentsign.dto.BaseResult
     * @Description TODO 成功
     * @Date 2019/2/12 21:22
     */
    public static <T> BaseResult success(T data) {
        return new BaseResult<>(true, data);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return "BaseResult{" + "success=" + success + ", data=" + data + ", errCode=" + errCode + ", errMsg='"
                + errMsg + '\'' + '}';
    }
}
