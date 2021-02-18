package fr.adami.pocjasper.controller;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.adami.pocjasper.config.ApplicationProperties;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@RestController
@RequestMapping("/api/reports")
public class GenerateRapportController {


	@Autowired
	ReportLoader reportLoader;
	
	@Autowired
	ApplicationProperties appProperties;
	
	@Autowired
	DataSource datasource;
	
	@GetMapping
	public Map<String,Date> getCompiledReports() {
		Map<String, Long> modified = reportLoader.getModified();
		Map<String, Date> ret = new HashMap<>();
		modified.keySet().stream().forEach(k-> ret.put(k, new Date(modified.get(k))));
		
		return ret;
	}
	
	@PostMapping
	public ResponseEntity<InputStreamSource> generate(@Valid @RequestBody RapportDto rapport) throws JRException, SQLException, FileNotFoundException {
		
		JasperReport report = reportLoader.getCompiledReport(rapport.getName());
		
		JasperPrint print = JasperFillManager.fillReport(report, rapport.getParams(), getConnection());
		

		byte[] bytes = JasperExportManager.exportReportToPdf(print);
		
		 var headers = new HttpHeaders();
	        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

	        return ResponseEntity
	                .ok()
	                .headers(headers)
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(new InputStreamResource(new ByteArrayInputStream(bytes)));
	}
	
	
	private Connection getConnection() throws SQLException {
		return datasource.getConnection();
	}
}