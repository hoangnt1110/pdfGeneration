package Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URI;

import javax.annotation.PostConstruct;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.avalon.framework.configuration.DefaultConfiguration;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.avalon.framework.configuration.MutableConfiguration;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.apps.MimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ExceptionDepthComparator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import Constant.AuditErrorCode;
import Exception.AuditReportExportServiceException;



@Component
public class PdfGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(PdfGenerator.class);
    private static final String FOP_RESOURCES_PATH = "/templates/fop/";
    private static final String FOP_CONFIGURATION_RESOURCE_NAME = "fop_configuration.xml";
    private static final String FOP_CONFIGURATION_RESOURCE = FOP_RESOURCES_PATH + FOP_CONFIGURATION_RESOURCE_NAME;
    private static TransformerFactory TRANSFORMER_FACTORY;
    private FopFactory fopFactory;

    @PostConstruct
    private void initFopFactory() {
        try {
            TRANSFORMER_FACTORY = TransformerFactory.newInstance();
            Resource resource = new ClassPathResource(FOP_CONFIGURATION_RESOURCE);
            URI parentResourceUri = new URI(resource.getURI().toString().replace(FOP_CONFIGURATION_RESOURCE_NAME,""));
            FopFactoryBuilder fopFactoryBuilder = new FopFactoryBuilder(parentResourceUri).setConfiguration(createConfigurationFromResource(resource,parentResourceUri));
            fopFactory = fopFactoryBuilder.build();
        } catch (Exception e) {
            LOG.error("Error while initializing FOP factory",e);
            throw new AuditReportExportServiceException(AuditErrorCode.INTERNAL_ERROR,"Error while initializing FOP factory");
        }
    }

    private DefaultConfiguration createConfigurationFromResource(Resource configResource, URI parentResourceUri) throws Exception {
        try  {
            InputStream configIs = configResource.getInputStream();
            DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
            DefaultConfiguration configuration = (DefaultConfiguration) cfgBuilder.build(configIs);
            MutableConfiguration base = configuration.getMutableChild("base");
            base.setValue(parentResourceUri.toString());
            return configuration;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public byte[] generateFromFo(String foString) {
        try {
            StringReader foReader = new StringReader(foString);
            ByteArrayOutputStream pdfWriter = new ByteArrayOutputStream();
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, fopFactory.newFOUserAgent(),pdfWriter);
            TRANSFORMER_FACTORY.newTransformer().transform(new StreamSource(foReader), new SAXResult(fop.getDefaultHandler()));
            return pdfWriter.toByteArray();
        } catch (Exception e) {
            LOG.error("Error while generating PDF from FO",e);
            throw new AuditReportExportServiceException(AuditErrorCode.INTERNAL_ERROR,"Could not generate PDF from XSL-FO");
        }
    }

}
