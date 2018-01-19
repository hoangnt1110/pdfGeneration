package Exception;


import java.io.FileNotFoundException;

import Constant.AuditErrorCode;

public class AuditReportExportServiceException extends ServiceException {

    public AuditReportExportServiceException(AuditErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public AuditReportExportServiceException(AuditErrorCode errorCode) {
        this(errorCode,errorCode.getMessage());
    }
}
