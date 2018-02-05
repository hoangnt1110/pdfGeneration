package RestController;

import Constant.AuditErrorCode;
import Exception.AuditReportExportServiceException;
import Service.PdfGenerator;
import entity.*;
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
import java.math.BigDecimal;
import java.nio.file.Files;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class PdfJaxbTestController {

    @Autowired
    private PdfGenerator pdfGenerator;
    @Autowired
    private Marshaller jaxb;

    @RequestMapping(method = GET, value = "/jaxbYesChinesePdf", produces = "application/json")
    public String export(@RequestParam(value = "version", required = false) String version){
        System.out.println(version);
        byte[] reportAsPdf = pdfGenerator.generateFromXslt(marshalToString(new InvoiceInfo()));

        return savePdfToReportOutput(reportAsPdf, "test");
    }

    @RequestMapping(method = GET, value = "/jaxbPdf", produces = "application/json")
    public String export(@RequestParam(value = "version", required = false) String version,
                         @RequestParam(value = "language", required = false) String language,
                         @RequestParam(value = "type", required = false) String type){
        System.out.println(version);
        byte[] reportAsPdf;

        ChinaPaymentDetails paymentDetails = new ChinaPaymentDetails();
        paymentDetails.setAmount(BigDecimal.valueOf(1800));
        paymentDetails.setCurrencyCode("RMB");
        paymentDetails.setPaymentCode("ZP00001");
        paymentDetails.setActualPayer("Hoang Payer");

        OrganisationDetails organisationDetails = new OrganisationDetails();
        organisationDetails.setOrganisationCode("ZC00001");
        organisationDetails.setOrganisationName("Hoang Company");

        ChinaInvoiceDetails invoiceDetails = new ChinaInvoiceDetails();
        invoiceDetails.setBankAccountNumber("AX00001");
        invoiceDetails.setBankName("Hoang Gia");
        invoiceDetails.setCompanyAddress("TDH");
        invoiceDetails.setCompanyPhoneNumber("01236652288");
        invoiceDetails.setContactName("Hoang Gia Lam");
        invoiceDetails.setContactPhoneNumber("0949101188");
        invoiceDetails.setDeliveryAddress("Hoang Delivery Now");
        invoiceDetails.setCompanyName("Hoang Company");
        invoiceDetails.setTaxPayerIdentification("Tax Payer Identification");

        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOrganisationDetails(organisationDetails);
        paymentInfo.setChinaPaymentDetails(paymentDetails);
        paymentInfo.setChinaInvoiceDetails(invoiceDetails);

        reportAsPdf = pdfGenerator.generateFromXslt(marshalToString(paymentInfo), language, type);

        return savePdfToReportOutput(reportAsPdf, "test");
    }

    private String marshalToString(final Object invoiceInfo) {
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
