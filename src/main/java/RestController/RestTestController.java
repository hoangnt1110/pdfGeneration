package RestController;

import Constant.AuditErrorCode;
import Constant.AuditReportExportFormat;
import Exception.AuditReportExportServiceException;
import Service.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class RestTestController {
	
	@Autowired
	private PdfGenerator pdfGenerator;

    @RequestMapping(method = GET, value = "/tulnTest", produces = "application/json")
    public String getData(@RequestParam(value = "version", required = false) String version){
        System.out.println(version);
        return export();
    }


    String readFile(String filename) {

        try {
            Resource resource = new ClassPathResource(filename);
            File f = new File(resource.getURI());
            byte[] bytes = Files.readAllBytes(f.toPath());
            return new String(bytes,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

	private String export() {

        String reportAsFo = readFile("/templates/SMETA-Report.fo_test.vm");

        byte[] reportAsPdf = pdfGenerator.generateFromFo(reportAsFo);
        String s = savePdfToReportOutput(reportAsPdf, "test");
        return s;
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
    
    
    
    private ResponseEntity<byte[]> createResponseEntity(String reportLocation,String reportCode, AuditReportExportFormat exportFormat) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(exportFormat.getMimeType()));
            byte[] auditReportContent = Files.readAllBytes(Paths.get(new URI(reportLocation)));
            headers.setContentDispositionFormData(reportCode, reportCode + "." + exportFormat.name().toLowerCase());
            return new ResponseEntity<byte[]>(auditReportContent, headers, HttpStatus.OK);
        } catch (Exception e) {
            //LOG.error("Error while creating response entity for {} ",reportCode,e);
            throw new RuntimeException("Internal error while downloading audit report");
        }
    }
    
	
}
