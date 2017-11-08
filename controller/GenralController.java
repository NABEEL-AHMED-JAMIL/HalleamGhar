package hg.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import hg.model.OrderItem;
import hg.model.PosOrder;
import hg.model.PrintDTO;
import hg.service.CustomerService;
import hg.service.PosOrderService;
import hg.service.ZoneService;
import hg.vo.CustomerVO;
import hg.vo.ZoneListVO;

import com.pos.print.PrintPS;

@Controller
public class GenralController {
	@Resource(name="setupProperties")
	private Properties setupProperties;
	private Logger logger = Logger.getLogger(GenralController.class.getName());
    private static final String DEFAULT_PAGE_DISPLAYED_TO_USER = "0";

    @Autowired
    private ZoneService zoneService;
    @Autowired
    private PosOrderService orderService;
    
    @Autowired
    private CustomerService customerService;
    @Autowired
    private MessageSource messageSource;

    @Value("5")
    private int maxResults;

   /* @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("itemsList");
    }*/

    @RequestMapping(value="/zones", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> listAll(Locale locale) {
        return createListAllZoneResponse(1, locale);
    }
    
    @RequestMapping(value="/printBill", method = RequestMethod.POST)
    public ResponseEntity<String> printBill(@RequestBody PrintDTO printDTO,
    					HttpServletResponse response) {
    	
    	CustomerVO customer = null;
    	Long orderId = printDTO.getOrderId();
    	PosOrder posOrder = orderService.findById(orderId);
    	if(posOrder == null){
    		throw new RuntimeException("Order not found.");
    	}
    	//get customer
    	
    	if(posOrder.getCustomer() != null){ 
    		Integer customerId = posOrder.getCustomer().getId();
    		customer = customerService.findById(customerId);
    	}
    	
    	String outputFile = printReport(printDTO.getReciept(), orderId,posOrder.getCreatedBy(),null,customer,posOrder.getDeliveryBoy(),posOrder);
    	try {
			//downloadReport(outputFile,orderId,response,"billPrint");
    		PrintPS.printPdf(outputFile);
			//response.flushBuffer();
		} catch (Exception e1) {
			logger.log(Level.SEVERE,"Error occured while printing report"+outputFile+" for order["+orderId+"]");
			e1.printStackTrace();
		}
    	
		return new ResponseEntity<String>("Bill printed for order#"+posOrder.getId(),HttpStatus.OK);

    }
    private String printQT(String json,Long orderNo, String tableNo, String waiter, String user,PosOrder posOrder){
    	String reportsFolder = setupProperties.getProperty("reports.folder");
    	String outputFileName = reportsFolder+"/"+orderNo+System.currentTimeMillis()+".pdf";
		try {
			String reportPath  = reportsFolder+"/qt.jasper";
			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObject(new File(reportPath));
			
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("tableNo", tableNo);
			parameters.put("waiter", waiter);
			parameters.put("orderId", orderNo);
			parameters.put("user", user);
			parameters.put("orderType", posOrder.getOrderType());
			parameters.put("logoPath", setupProperties.getProperty("logo.path"));
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
    
    @RequestMapping(value="/printQT", method = RequestMethod.POST)
    public ResponseEntity<?> printQT(@RequestBody PrintDTO printDTO,HttpServletResponse response) {
    	PosOrder posOrder = orderService.findById(printDTO.getOrderId());
    	if(posOrder == null){
    		throw new RuntimeException("Order not found.");
    	}
    	String outputFile = printQT(printDTO.getReciept(), printDTO.getOrderId(),posOrder.getTableNo(),posOrder.getWaiter(),posOrder.getCreatedBy(),posOrder);
    	/*try {
    		orderService.updateItemsAsPrinted(orderId);
			downloadReport(outputFile,orderId,response,"QTPrint");
			response.flushBuffer();
		} catch (IOException e1) {
			logger.log(Level.SEVERE,"Error occured while downloading QT report"+outputFile+" for order["+orderId+"]");
			e1.printStackTrace();
		}*/
    	try {
    		orderService.updateItemsAsPrinted(printDTO.getOrderId());
    		PrintPS.printPdf(outputFile);
		} catch (Exception e1) {
			logger.log(Level.SEVERE,"Error occured while printing QT report"+outputFile+" for order["+printDTO.getOrderId()+"]");
			e1.printStackTrace();
		}
		return new ResponseEntity<String>("QT Printed for order#"+posOrder.getId(),HttpStatus.OK);

    }
    private ZoneListVO listAll(int page) {
        return zoneService.findAll(page, maxResults);
    }

    private ResponseEntity<ZoneListVO> returnListToUser(ZoneListVO contactList) {
        return new ResponseEntity<ZoneListVO>(contactList, HttpStatus.OK);
    }

    private ResponseEntity<?> createListAllZoneResponse(int page, Locale locale) {
        return createListAllZoneResponse(page, locale, null);
    }

    private ResponseEntity<?> createListAllZoneResponse(int page, Locale locale, String messageKey) {
    	ZoneListVO contactListVO = listAll(page);

        addActionMessageToVO(contactListVO, locale, messageKey, null);

        return returnListToUser(contactListVO);
    }

    private ZoneListVO addActionMessageToVO(ZoneListVO contactListVO, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return contactListVO;
        }

        contactListVO.setActionMessage(messageSource.getMessage(actionMessageKey, args, null, locale));

        return contactListVO;
    }
    
    public void downloadReport(String outputFile,Long orderId, HttpServletResponse response,String pdfName) throws IOException{
    	File file = new File(outputFile);
    	response.setContentType( "application/pdf" );
        response.setContentLength((int)file.length());
        response.setHeader("Content-Disposition","attachment; filename=\""+pdfName+".pdf\"");

        response.setHeader("Cache-Control", "no-cache");
    	 try {
			FileCopyUtils.copy(new FileInputStream(outputFile),response.getOutputStream());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 try {
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//delete file
    	file.delete();
    }
    public String printReport(String json,Long orderNo,String user,String cashText,CustomerVO customerVO,String deliveryBoy,PosOrder posOrder){
    	logger.info("printReport started:"+json);
    	String reportsFolder = setupProperties.getProperty("reports.folder");
    	String outputFileName = reportsFolder+"/"+orderNo+System.currentTimeMillis()+".pdf";
		try {
			String reportPath  = reportsFolder+"/bill.jasper";
			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObject(new File(reportPath));
			Map<String,Object> parameters = new HashMap<String,Object>();
			
			if(posOrder!= null){
				parameters.put("deliveryCharges",posOrder.getDeliveryCharges());
			}
			
			parameters.put("orderId", orderNo);
			parameters.put("user", user);
			parameters.put("orderType", posOrder.getOrderType());
			parameters.put("tableNo", posOrder.getTableNo());
			parameters.put("waiter", posOrder.getWaiter());
			if(!StringUtils.isEmpty(cashText)){
				Double cash;
				try {
					cash = Double.parseDouble(cashText);
					parameters.put("payment",cash);
					if(cash != null){
						parameters.put("checkout","true");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			parameters.put("deliveryBoy",deliveryBoy);
			if(customerVO != null){
				parameters.put("customerName",customerVO.getName());
				parameters.put("customerContact",customerVO.getCellNo());
				parameters.put("customerAddress",customerVO.getAddress());
			} 
			
			parameters.put("IS_IGNORE_PAGINATION", Boolean.TRUE);
			parameters.put("logoPath", setupProperties.getProperty("logo.path"));
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
}