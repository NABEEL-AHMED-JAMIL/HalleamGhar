package hg.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import hg.model.OrderItem;
import hg.model.PosOrder;
import hg.model.User;
import hg.service.PosOrderService;
import hg.vo.PosOrderListVO;

@Controller
@RequestMapping(value = "/posorders")
public class PosOrderController {
	Logger logger = Logger.getLogger(PosOrderController.class.getName());
    private static final String DEFAULT_PAGE_DISPLAYED_TO_USER = "0";
  
    @Autowired
    private PosOrderService posOrderService;

    @Autowired
    private MessageSource messageSource;


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("pos");
    }
    @RequestMapping(value="/pos",method = RequestMethod.GET)
    public ModelAndView pos() {
        return new ModelAndView("posScreen");
    }
    @RequestMapping(value="/order/{id}",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getPosOrder(@PathVariable Long id,HttpServletRequest request) {
    	PosOrderListVO vo = posOrderService.findById(1,1,id);
    	return prepareOrderResponse(vo);
    }
    private ResponseEntity<?> prepareOrderResponse(PosOrderListVO vo) {
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
        	o.setDeliveryCharges(order.getDeliveryCharges());
        	o.setDeliveryBoy(order.getDeliveryBoy());
        	o.setZone(order.getZone());
        	o.setId(order.getId());
        	o.setOrderType(order.getOrderType());
        	Set<OrderItem> set = order.getItems();
        	List<OrderItem> oilist = new ArrayList<OrderItem>();
        	for(OrderItem item : set){
        		OrderItem oi = new OrderItem();
        		item.setPosOrder(null);
        		oilist.add(item);
        	}
        	o.setCustomer(order.getCustomer());
        	o.setOrderItems(oilist);
        	list.add(o);
    	}
    	vo.setPosOrders(list);
    	return new ResponseEntity<PosOrderListVO>(vo,HttpStatus.OK);
	}
	@RequestMapping(value="/order/table/{tableNo}",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getPosOrderByTable(@PathVariable String tableNo,HttpServletRequest request) {
    	PosOrderListVO vo = posOrderService.findByTableNoStatus(tableNo,null,"SAVED");
    	List<PosOrder> orders = vo.getPosOrders();
    	List<PosOrder> list = new ArrayList<PosOrder>();
    	for(PosOrder order: orders){ 
    		PosOrder o = new PosOrder();
        	o.setAmount(order.getAmount());
        	o.setCode(order.getCode());
        	o.setCreatedBy(order.getCreatedBy());
        	o.setCreationDate(order.getCreationDate());
        	o.setWaiter(order.getWaiter());
        	o.setTableNo(order.getTableNo());
        	o.setId(order.getId());
        	o.setPayment(order.getPayment());
        	o.setDeliveryBoy(order.getDeliveryBoy());
        	o.setZone(order.getZone());
        	o.setDeliveryCharges(order.getDeliveryCharges());
        	Set<OrderItem> set = order.getItems();
        	List<OrderItem> oilist = new ArrayList<OrderItem>();
        	for(OrderItem item : set){
        		item.setPosOrder(null);
        		oilist.add(item);
        	}
        	o.setOrderItems(oilist);
        	list.add(o);
    	}
    	vo.setPosOrders(list);
    	return new ResponseEntity<PosOrderListVO>(vo,HttpStatus.OK);
    }
    @RequestMapping(value="/search",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> search(@RequestParam String q,HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	User user = (User)session.getAttribute("user");
    	PosOrderListVO vo = posOrderService.findByCodeAndStatusAndCreatedBy(q,"SAVED",user.getName());
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
        	o.setOrderType(order.getOrderType());
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
    	return new ResponseEntity<PosOrderListVO>(vo,HttpStatus.OK);
    }
    @RequestMapping(value="/pos/{id}",method = RequestMethod.GET)
    public ModelAndView getPosOrders(@PathVariable Long id,HttpServletRequest request) {
    	ModelAndView mv = new ModelAndView("posScreen");
    	mv.addObject("orderId", id);
    	request.setAttribute("id", id);
        return new ModelAndView("posScreen");
    }
    @RequestMapping(value="/list",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> listAll(@RequestParam int page, Locale locale) {
    	
    	PosOrderListVO contactListVO = listAll(page);
    	List<PosOrder> orders = contactListVO.getPosOrders();
    	List<PosOrder> list = new ArrayList<PosOrder>();
    	for(PosOrder order: orders){ 
    		PosOrder o = new PosOrder();
        	o.setAmount(order.getAmount());
        	o.setCode(order.getCode());
        	o.setCreatedBy(order.getCreatedBy());
        	o.setCreationDate(order.getCreationDate());
        	o.setWaiter(order.getWaiter());
        	o.setPayment(order.getPayment());
        	o.setStatus(order.getStatus());
        	o.setTableNo(order.getTableNo());
        	o.setId(order.getId());
        	list.add(o);
    	}
    	
    	contactListVO.setPosOrders(list);
    	return new ResponseEntity<PosOrderListVO>(contactListVO,HttpStatus.OK);
    	/*
        return createListAllResponse(page, locale);*/
    }

    
    
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> create(@RequestBody PosOrder posOrder,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale, HttpServletRequest request) {
        
    	List<OrderItem> list = posOrder.getOrderItems();
    
    	Set<OrderItem> items = new HashSet<OrderItem>();
    	for(OrderItem item : list){
    		item.setPosOrder(posOrder);
    		items.add(item);
    	}
    	posOrder.setCreationDate(new Date());
    	posOrder.addAll(items);
    	
    	if(posOrder.getPayment()!= null && posOrder.getPayment().doubleValue() > 0){
    		posOrder.setStatus("DONE");
    	}else {
    		posOrder.setStatus("SAVED");
    	}
    	User user = (User)request.getSession().getAttribute("user");
    	if(user != null){
    		posOrder.setCreatedBy(user.getName());
    	}
    	System.out.println("Saving POSOrder .. ["+posOrder.getCreationDate()+"]");
    	posOrder = posOrderService.save(posOrder);
        PosOrderListVO vo = posOrderService.findById(1,1,posOrder.getId());
    	return prepareOrderResponse(vo);
       // return createListAllResponse(page, locale, "message.create.success");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") int contactId,
                                    @RequestBody PosOrder posOrder,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale,HttpServletRequest request) {
        if (contactId != posOrder.getId()) {
            return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
        }
        PosOrder savedPosOrder = posOrderService.findById(posOrder.getId());
        if(savedPosOrder != null && "DONE".equalsIgnoreCase(savedPosOrder.getStatus())) {
        	return createErrorResponse(locale,"error.order.update.done");
        }
        List<OrderItem> list = posOrder.getOrderItems();
        
    	Set<OrderItem> items = new HashSet<OrderItem>();
    	for(OrderItem item : list){
    		item.setPosOrder(posOrder);
    		items.add(item);
    	}
    	posOrder.addAll(items);
    	if(posOrder.getPayment()!= null && posOrder.getPayment().doubleValue() > 0){
    		posOrder.setStatus("DONE");
    	}else {
    		posOrder.setStatus("SAVED");
    	}
    	User user = (User)request.getSession().getAttribute("user");
    	if(user != null){
    		posOrder.setCreatedBy(user.getName());
    	}
    	posOrder = posOrderService.save(posOrder);
        PosOrderListVO vo = posOrderService.findById(1,1,posOrder.getId());
    	return prepareOrderResponse(vo);
    }

    @RequestMapping(value = "/{contactId}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable("contactId") long contactId,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {


       /* try {
            posOrderService.delete(contactId);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
        }*/

        if (isSearchActivated(searchFor)) {
            return search(searchFor, page, locale, "message.delete.success");
        }

        return createListAllResponse(page, locale, "message.delete.success");
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> search(@PathVariable("name") String name,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        return search(name, page, locale, null);
    }

    private ResponseEntity<?> search(String name, int page, Locale locale, String actionMessageKey) {
        PosOrderListVO contactListVO = posOrderService.findByCode(page, 100, name);
        if (!StringUtils.isEmpty(actionMessageKey)) {
            addActionMessageToVO(contactListVO, locale, actionMessageKey, null);
        }

        Object[] args = {name};

        addSearchMessageToVO(contactListVO, locale, "message.search.for.active", args);

        return new ResponseEntity<PosOrderListVO>(contactListVO, HttpStatus.OK);
    }

    private PosOrderListVO listAll(int page) {
        return posOrderService.findAll(page, 0);
    }

    private ResponseEntity<PosOrderListVO> returnListToUser(PosOrderListVO contactList) {
        return new ResponseEntity<PosOrderListVO>(contactList, HttpStatus.OK);
    }

    private ResponseEntity<?> createListAllResponse(int page, Locale locale) {
        return createListAllResponse(page, locale, null);
    }

    private ResponseEntity<?> createListAllResponse(int page, Locale locale, String messageKey) {
        PosOrderListVO contactListVO = listAll(page);

        addActionMessageToVO(contactListVO, locale, messageKey, null);

        return returnListToUser(contactListVO);
    }

    private PosOrderListVO addActionMessageToVO(PosOrderListVO contactListVO, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return contactListVO;
        }

        contactListVO.setActionMessage(messageSource.getMessage(actionMessageKey, args, null, locale));

        return contactListVO;
    }

    private PosOrderListVO addSearchMessageToVO(PosOrderListVO posOrderListVO, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return posOrderListVO;
        }

        posOrderListVO.setSearchMessage(messageSource.getMessage(actionMessageKey, args, null, locale));

        return posOrderListVO;
    }

    private boolean isSearchActivated(String searchFor) {
        return !StringUtils.isEmpty(searchFor);
    }
    private ResponseEntity<String> createErrorResponse(Locale locale, String messageKey) {
    	return new ResponseEntity<String>(messageSource.getMessage(messageKey, null, null, locale),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}