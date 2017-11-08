package hg.controller;

import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import hg.model.SimpleVO;
import hg.service.PosOrderService;
import hg.service.ReportingService;
import hg.service.ZoneService;

@Controller
@RequestMapping(value = "/dailySales")
public class ReportingController {
	@Resource(name="setupProperties")
	private Properties setupProperties;
	private Logger logger = Logger.getLogger(ReportingController.class.getName());
    private static final String DEFAULT_PAGE_DISPLAYED_TO_USER = "0";

    @Autowired
    private ZoneService zoneService;
    @Autowired
    private PosOrderService orderService;
    @Autowired
    private ReportingService reportingService;
    
    
    @Autowired
    private MessageSource messageSource;

    @Value("5")
    private int maxResults;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("dailySales");
    }

    @RequestMapping(value="/datewise", method = RequestMethod.GET)
    public ResponseEntity<?>  printJob(@RequestParam("startDate") String date1,@RequestParam("endDate") String date2,
    					HttpServletResponse response) {
    	List<SimpleVO>  vo=reportingService.prepareDailyReport();
    	return new ResponseEntity<List<SimpleVO>>(vo,HttpStatus.OK);
    	/*SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    	Date startDate = null;
    	try {
			startDate = df.parse(date1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	PosOrderListVO vo = orderService.getDailySalesReport(startDate,startDate);
    	List<PosOrder> orders = vo.getPosOrders();
    	List<PosOrder> list = new ArrayList<PosOrder>();
    	for(PosOrder order: orders){ 
    		PosOrder o = new PosOrder();
        	o.setAmount(order.getAmount());
        	o.setCode(order.getCode());
        	o.setCreatedBy(order.getCreatedBy());
        	o.setCreationDate(order.getCreationDate());
        	o.setWaiter(order.getWaiter());
        	o.setPayment(order.getPayment());
        	o.setTableNo(order.getTableNo());
        	o.setId(order.getId());
        	o.setDeliveryBoy(order.getDeliveryBoy());
        	o.setZone(order.getZone());
        	o.setDeliveryCharges(order.getDeliveryCharges());
        	Set<OrderItem> set = order.getItems();
        	List<OrderItem> oilist = new ArrayList<OrderItem>();
        	for(OrderItem item : set){
        		item.setPosOrder(null);
        		oilist.add(item);
        	}
        	o.setCustomer(order.getCustomer());
        	o.setOrderItems(oilist);
        	list.add(o);
    	}
    	vo.setPosOrders(list);
    	return new ResponseEntity<PosOrderListVO>(vo,HttpStatus.OK);*/
    }
    @RequestMapping(value="/fetch", method = RequestMethod.GET)
    public ModelAndView  test(@RequestParam("REF") String ref,@RequestParam("user") String user,
    					HttpServletResponse response) {
    	return new ModelAndView("dailySales");
    }
  
}