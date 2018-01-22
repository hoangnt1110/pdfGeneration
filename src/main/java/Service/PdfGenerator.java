package Service;

import java.io.*;
import java.net.URI;

import javax.annotation.PostConstruct;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.avalon.framework.configuration.DefaultConfiguration;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.avalon.framework.configuration.MutableConfiguration;
import org.apache.fop.apps.*;
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
    private static final String XSLT_RESOURCES_PATH = "/templates/";
    private static final String XSLT_CONFIGURATION_RESOURCE_GENERIC_NAME = "vatInvoice-bacs";
    private static final String XSLT_CONFIGURATION_RESOURCE_NAME = "vatInvoice-bacs.xslt";
    private static final String XSLT_CONFIGURATION_RESOURCE = XSLT_RESOURCES_PATH + XSLT_CONFIGURATION_RESOURCE_NAME;
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

    public byte[] generateFromXslt(String objectXml) {
        final ByteArrayOutputStream pdf = new ByteArrayOutputStream();
        try {
            Resource resource = new ClassPathResource(XSLT_CONFIGURATION_RESOURCE);
            final Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, pdf);
            final Transformer transformer = TRANSFORMER_FACTORY.newTransformer(new StreamSource(resource.getInputStream()));

            final Result result = new SAXResult(fop.getDefaultHandler());

            transformer.transform(new StreamSource(new StringReader(objectXml)), result);
        } catch (FOPException | TransformerException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        LOG.debug("pdf: {}", pdf);
        return pdf.toByteArray();
    }

    public byte[] generateFromXslt(String objectXml, String language, String type) {
        final ByteArrayOutputStream pdf = new ByteArrayOutputStream();
        try {
            String resourceName = XSLT_RESOURCES_PATH + XSLT_CONFIGURATION_RESOURCE_GENERIC_NAME + "-" + language + "-" + type + ".xslt";
            Resource resource = new ClassPathResource(resourceName);
            final Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, pdf);
            final Transformer transformer = TRANSFORMER_FACTORY.newTransformer(new StreamSource(resource.getInputStream()));

            final Result result = new SAXResult(fop.getDefaultHandler());

            transformer.transform(new StreamSource(new StringReader(objectXml)), result);
        } catch (FOPException | TransformerException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        LOG.debug("pdf: {}", pdf);
        return pdf.toByteArray();
    }

}
