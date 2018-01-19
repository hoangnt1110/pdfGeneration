package RestController;

import Constant.AuditErrorCode;
import Exception.AuditReportExportServiceException;
import Service.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Marshaller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class PdfJaxbTestController {

    @Autowired
    private PdfGenerator pdfGenerator;
    @Autowired
    private Marshaller jaxb;

    @RequestMapping(method = GET, value = "/jaxbPdf", produces = "application/json")
    public String export(@RequestParam(value = "version", required = false) String version){
        System.out.println(version);
        byte[] reportAsPdf = pdfGenerator.generateFromXslt(marshalToString(new InvoiceInfo()));

        return savePdfToReportOutput(reportAsPdf, "test");
    }

    private String marshalToString(final InvoiceInfo invoiceInfo) {
        final StringWriter xml = new StringWriter();
        try {
            jaxb.marshal(invoiceInfo, new StreamResult(xml));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xml.toString();
    }

    private String savePdfToReportOutput(byte[] auditReportAsPdf, String auditReportCode) {
        File outputFile;
        try {
            outputFile = Files.createTempFile(auditReportCode, ".pdf").toFile();
        } catch (IOException e) {
            //LOG.error(e.getMessage(), e);
            throw new AuditReportExportServiceException(AuditErrorCode.INVALID_OUTPUT_FILE, String.format("Could not find/create file '%s'", auditReportCode));
        }
        try (FileOutputStream pdfOutput = new FileOutputStream(outputFile)) {
            pdfOutput.write(auditReportAsPdf);
            return outputFile.toURI().toString();
        } catch (Exception e) {
            //LOG.error(e.getMessage(), e);
            throw new AuditReportExportServiceException(AuditErrorCode.INVALID_OUTPUT_FILE, String.format("Could not find/create file '%s'", auditReportCode));
        }
    }

}
