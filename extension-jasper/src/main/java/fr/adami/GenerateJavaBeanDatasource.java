/**
 * 
 */
package fr.adami;

import java.awt.Dimension;
import java.io.File;
import java.util.HashMap;
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
import net.sf.jasperreports.samples.datasource.CustomDataSource;
import net.sf.jasperreports.swing.JRViewer;



/**
 * @author David
 *
 */
public class GenerateJavaBeanDatasource {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if ( args.length == -1 ) {
			System.out.println("Usage : run [<report_title> <datasource>]");
			System.exit(0);
		}
		String reportTitle = "Titre par défaut";
		String datasource = "CustomDatasource.java";
		if ( args.length > 0 ) {
			reportTitle = args[0];
		}
		if ( args.length > 1 ) {
			datasource = args[1];
		}
		
		GenerateJavaBeanDatasource me = new GenerateJavaBeanDatasource();
		
		JasperPrint jasperPrint = me.generate(reportTitle, datasource);
		
		JFrame frame = new JFrame("Jasper report");

        JRViewer viewer = new JRViewer(jasperPrint);
        
        frame.add(viewer);
        frame.setSize(new Dimension(500, 400));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

	}
	
	public JasperPrint generate(String reportTitle, String datasource)  {
		try {
			File file = new File(
					getClass().getClassLoader().getResource("DataSourceReport1.jrxml").getFile()
				);
			JasperDesign design = JRXmlLoader.load(file);
			
			JasperReport jasperReport = JasperCompileManager.compileReport(design);
			
			JasperPrint jasperPrint = null;

				Map<String,Object> parametersMap = new HashMap<String,Object>();
				parametersMap.put("ReportTitle",reportTitle);
				parametersMap.put("DataFile",datasource);
				jasperPrint =JasperFillManager.fillReport(jasperReport, parametersMap, new CustomDataSource());
			
			
			JasperExportManager.exportReportToPdfFile(jasperPrint, "DataSourceReport1.pdf");
			
			return jasperPrint;
		} catch (JRException e) {
			e.printStackTrace();
		}
		
		 return null;
	}
	


}
