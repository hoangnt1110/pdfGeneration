package Constant;

import java.util.Optional;

public enum AuditErrorCode {
    AUDIT_REPORT_NOT_FOUND("Audit report was not found"),
    INVALID_EXPORT_FORMAT("Invalid export format"),
    INTERNAL_ERROR, INVALID_AUDIT_MODEL,INVALID_OUTPUT_FILE,
    INVALID_AUDIT_REPORT_TYPE("Invalid audit report type");

    private String message;

    AuditErrorCode(String message) {
        this.message = message;
    }
    AuditErrorCode() {
        this.message = this.name();
    }

    public String getMessage() {
        return message;
    }

    /*public static AuditErrorCode resolveErrorCode(AuditEntityType auditEntityType, GatewayException exception) {
        AuditErrorCode auditErrorCode = AuditErrorCode.valueOf(auditEntityType.name() + "_" + exception.getHttpStatus().name());
        return Optional.ofNullable(auditErrorCode).orElse(AuditErrorCode.INTERNAL_ERROR);
    }*/
}
