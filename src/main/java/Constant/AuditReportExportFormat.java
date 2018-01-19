package Constant;

public enum AuditReportExportFormat {
    PDF("application/pdf");

    private final String mimeType;

    AuditReportExportFormat(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }
}