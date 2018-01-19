package SmetaReportPdf.tuln;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import RestController.RestTestController;
import Service.PdfGenerator;

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
}
