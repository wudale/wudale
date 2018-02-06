package rxrpc;

/**
 * @author dale.wdl@alibaba-inc.com
 * @date 2018/1/17
 */
public interface FlowableResult<T> {

    T getResult() ;

    boolean isSuccess();

    String getErrorMsg();

    String getErrorCode();

    String getHint();

}
