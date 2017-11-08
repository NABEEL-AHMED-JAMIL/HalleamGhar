package hg.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
public class JasperUtil {
	private static Logger  logger = Logger.getLogger(JasperUtil.class.getName());
	private static ResourceBundle rb = ResourceBundle
			.getBundle("com.hg.setup");
	
	static {
		System.setProperties(convertResourceBundleToProperties(rb));
	}

	public static Properties convertResourceBundleToProperties(ResourceBundle resource) {
	    Properties properties = new Properties();

	    Enumeration<String> keys = resource.getKeys();
	    while (keys.hasMoreElements()) {
	      String key = keys.nextElement();
	      properties.put(key, resource.getString(key));
	    }

	    return properties;
	  }
	public String printReport(String json,String orderNo,String user){
		String outputFileName = System.getProperty("reports.folder")+"/"+orderNo+System.currentTimeMillis()+".pdf";
		try {
			String reportPath  = System.getProperty("reports.folder")+"/"+"bill.jasper";
			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObject(new File(reportPath));
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("orderId", orderNo);
			parameters.put("user", user);
			parameters.put("IS_IGNORE_PAGINATION", Boolean.TRUE);
			parameters.put("logoPath", System.getProperty("logo.path"));
			JsonDataSource jds = new JsonDataSource(new ByteArrayInputStream(json.getBytes()));
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, parameters, jds);
			JasperExportManager.exportReportToPdfFile(jasperPrint,outputFileName);
			logger.info("Report Printed for Order["+orderNo+"]");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
		}
		return outputFileName;
	}
	public String printQT(String json,String orderNo, String tableNo, String waiter, String user){
		String outputFileName = System.getProperty("reports.folder")+"/"+orderNo+System.currentTimeMillis()+".pdf";
		try {
			String reportPath  = System.getProperty("reports.folder")+"/"+"qt.jasper";
			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObject(new File(reportPath));
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("tableNo", tableNo);
			parameters.put("waiter", waiter);
			parameters.put("orderId", orderNo);
			parameters.put("user", user);
			parameters.put("logoPath", System.getProperty("logo.path"));
			parameters.put("IS_IGNORE_PAGINATION", Boolean.TRUE);
			JsonDataSource jds = new JsonDataSource(new ByteArrayInputStream(json.getBytes()));
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, parameters, jds);
			JasperExportManager.exportReportToPdfFile(jasperPrint,outputFileName);
			logger.info("QT Report Printed for Order["+orderNo+"]");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
		}
		return outputFileName;
	}

}
