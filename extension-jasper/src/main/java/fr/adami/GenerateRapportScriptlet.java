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

import net.sf.jasperreports.engine.JREmptyDataSource;
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
public class GenerateRapportScriptlet {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if ( args.length ==0 ) {
			System.out.println("Usage : run report-file [<locale>]");
			System.exit(0);
		}
		System.out.println("Usage : run report-file  [<locale>]");


		String file = args[0];
		
		String language = "FR";
		if ( args.length > 1 ) {
			language = args[1];
		}
		
		GenerateRapportScriptlet me = new GenerateRapportScriptlet();
		
		JasperPrint jasperPrint = me.generate(file, language);
		
		JFrame frame = new JFrame("Jasper report");

        JRViewer viewer = new JRViewer(jasperPrint);
        
        frame.add(viewer);
        frame.setSize(new Dimension(500, 400));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

	}
	
	public JasperPrint generate(String fichier, String language)  {
//		System.out.println(getClass().getClassLoader().getResource("Scriptlet.jrxml").getFile());
		try {
//			File file = new File(
//					getClass().getClassLoader().getResource("Scriptlet.jrxml").getFile()
//				);
			File file = new File(fichier);
			JasperDesign design = JRXmlLoader.load(file);
			
			JasperReport jasperReport = JasperCompileManager.compileReport(design);
			
			JasperPrint jasperPrint = null;
			Map<String,Object> parametersMap = new HashMap<String,Object>();
			parametersMap.put("REPORT_LOCALE",new Locale(language));
			jasperPrint =JasperFillManager.fillReport(jasperReport, parametersMap, new JREmptyDataSource());
			
//			JasperExportManager.exportReportToPdfFile(jasperPrint, "/Scriptlet.pdf");
			
			return jasperPrint;
		} catch (JRException e) {
			e.printStackTrace();
		}
		
		 return null;
	}
	

}
