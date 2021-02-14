package fr.adami.pocjasper.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import fr.adami.pocjasper.config.ApplicationProperties;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service
public class ReportLoader {

    private static Logger logger = LoggerFactory.getLogger(ReportLoader.class);

	@Autowired
	ApplicationProperties appProperties;
	

	
	private HashMap<String, JasperReport> compiledReports = new HashMap<>();
	private HashMap<String, Long> modified = new HashMap<>();
	
	
	public JasperReport getCompiledReport(String name) throws JRException, FileNotFoundException {
		
		JasperReport report = compiledReports.get(name+".jrxml");
	
		if ( report == null ) {
			logger.debug("Report not in cache "+name+".jrxml");
			// Load jrxml from classpath with the name
			InputStream inputStream = new FileInputStream(appProperties.getReportFolder() + name +".jrxml");
			
			JasperDesign design = JRXmlLoader.load(inputStream);
			report = JasperCompileManager.compileReport(design);
			
			compiledReports.put(name, report);
		}
		return report;
	}
	
	
	
	public HashMap<String, Long> getModified() {
		return modified;
	}



	@Scheduled(fixedRateString = "${app.refresh-rate}")
	public void browseFolder() {
		logger.debug("browse report directory " + appProperties.getReportFolder());
		Stream.of(new File(appProperties.getReportFolder()).listFiles())
		   .filter(f -> compiledReports.get(f.getName()) == null || f.lastModified() != modified.get(f.getName()))
		   .forEach(f -> { 
			   try {
				   logger.debug("Compiling " + f.getName());
			   InputStream inputStream = new FileInputStream(f);
			   JasperDesign design = JRXmlLoader.load(inputStream);
			   JasperReport report = JasperCompileManager.compileReport(design);
			   compiledReports.put(f.getName(), report);
			   modified.put(f.getName(), f.lastModified());
			   } catch (Exception e ) {
				   e.printStackTrace();
			   }
		   });
	}
}
