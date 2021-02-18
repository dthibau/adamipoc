/**
 * 
 */
package fr.adami;

import java.awt.Dimension;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.JFrame;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;



/**
 * @author David
 *
 */
public class GenerateRapportFromJasper {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Class.forName("org.hsqldb.jdbcDriver"); //Or any other driver
		}
		catch(Exception x){
			System.out.println("Unable to load the driver class!");
		}
//		ResourceBundle res = ResourceBundle.getBundle("labels");
		if ( args.length ==0 ) {
			System.out.println("Usage : run [<country_parameter> <locale>]");
			System.exit(0);
		}
		String country = "France";
		String language = "EN";
		if ( args.length > 0 ) {
			country = args[0];
		}
		if ( args.length > 1 ) {
			language = args[1];
		}
		
		GenerateRapportFromJasper me = new GenerateRapportFromJasper();
		
		JasperPrint jasperPrint = me.generate(country, language);
		
		JFrame frame = new JFrame("Jasper report");

        JRViewer viewer = new JRViewer(jasperPrint);
        
        frame.add(viewer);
        frame.setSize(new Dimension(500, 400));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

	}
	
	public JasperPrint generate(String country, String language)  {
		System.out.println("Generating Parameter.jasper with country="+country + ",language="+language);

		try {
			Class clazz = GenerateRapportScriptlet.class;
			java.io.InputStream inputStream = clazz.getResourceAsStream("/Parameter.jasper");
			
			System.out.println(inputStream);

			
			JasperPrint jasperPrint = null;
			if ( country == null ) {
				jasperPrint =JasperFillManager.fillReport(inputStream, null,getConnection());
			} else {
				Map<String,Object> parametersMap = new HashMap<String,Object>();
				parametersMap.put("SHIPCOUNTRY",country);
				parametersMap.put("REPORT_LOCALE",new Locale(language));
				jasperPrint =JasperFillManager.fillReport(inputStream, parametersMap,getConnection());
			}
			
			JasperExportManager.exportReportToPdfFile(jasperPrint, "ParameterJasper.pdf");
			
			return jasperPrint;
		} catch (JRException e) {
			e.printStackTrace();
		}
		
		 return null;
	}
	
	private static Connection getConnection() {
		Connection c = null;
		try {
			c = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost","sa",null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

}
