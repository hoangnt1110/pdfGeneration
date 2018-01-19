package SmetaReportPdf.tuln;

import RestController.ObjectFactory;
import RestController.RestTestController;
import Service.PdfGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * Hello world!
 *
 */
@ComponentScan(basePackageClasses={RestTestController.class, PdfGenerator.class})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class, FreeMarkerAutoConfiguration.class})
public class App 
{
    public static void main( String[] args )
    {
    	System.out.println( "Hello World!" );
    	SpringApplication.run(App.class, args);
    }

    @Bean
    public Marshaller jaxbMarshaller() {
        final Jaxb2Marshaller jaxb = new Jaxb2Marshaller();
        jaxb.setPackagesToScan(ObjectFactory.class.getPackage().getName());
        return jaxb;
    }

}
