package fr.adami.scriptlet;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

public class ComputeTime extends JRDefaultScriptlet {

	long reportInitTime = 0;

	@Override
	public void beforeReportInit() throws JRScriptletException {
		reportInitTime = System.currentTimeMillis();		

	}


	public Long getComputeTime() {
		long now = System.currentTimeMillis();
		return new Long(now - reportInitTime);
	}
	
	
	public String reverse(String s) {
		StringBuffer sbf = new StringBuffer();
		
		for ( int i=s.length()-1; i >=0; i-- ) {
			sbf.append(s.charAt(i));
		}
		
		return sbf.toString();
	}

}
