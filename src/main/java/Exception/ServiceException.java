package Exception;

import Constant.AuditErrorCode;

public class ServiceException extends RuntimeException {

    private AuditErrorCode errorCode;

    public ServiceException(AuditErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public AuditErrorCode getErrorCode() {
        return errorCode;
    }

}
