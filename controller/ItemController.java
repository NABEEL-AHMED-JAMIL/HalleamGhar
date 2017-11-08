package hg.controller;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import hg.model.Item;
import hg.service.ItemService;
import hg.service.ReportingService;
import hg.vo.ItemListVO;
import hg.vo.ItemVO;

@Controller
@RequestMapping(value = "/items")
public class ItemController {

    private static final String DEFAULT_PAGE_DISPLAYED_TO_USER = "1";

    @Autowired
    private ItemService itemService;
    
    @Autowired
    private ReportingService reportingService;

    @Autowired
    private MessageSource messageSource;

    @Value("5")
    private int maxResults;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("itemsList");
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> listAll(Locale locale) {
        return createListAllResponse(1, locale);
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> create(@RequestBody Item item,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        
    	ItemListVO list = itemService.findByNameOrCode(item.getName(),item.getCode());
    	if(list != null && list.getItems() != null && !list.getItems().isEmpty()){
    		return createErrorResponse(locale, "message.item.error.alreadyAvailable");
    	}
    	itemService.save(item);

        return createListAllResponse(page, locale, "message.create.success");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") int contactId,
                                    @RequestBody Item item,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        if (contactId != item.getId()) {
            return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
        }
        ItemListVO list = itemService.findByNameOrCode(item.getName(),item.getCode());
        if(list != null && list.getItems() != null && !list.getItems().isEmpty()){
        	for(ItemVO itemvo : list.getItems()){
        		if(itemvo.getId() != item.getId()){
        			return createErrorResponse(locale, "message.item.error.alreadyAvailable");
        		}
        	}
    	}
        itemService.save(item);

        if (isSearchActivated(searchFor)) {
            return search(searchFor, page, locale, "message.update.success");
        }

        return createListAllResponse(page, locale, "message.update.success");
    }

    @RequestMapping(value = "/{contactId}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable("contactId") int contactId,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {


        /*try {
            itemService.delete(contactId);
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
        ItemListVO contactListVO = itemService.findByNameLike(page, maxResults, name);

        if (!StringUtils.isEmpty(actionMessageKey)) {
            addActionMessageToVO(contactListVO, locale, actionMessageKey, null);
        }

        Object[] args = {name};

        addSearchMessageToVO(contactListVO, locale, "message.search.for.active", args);

        return new ResponseEntity<ItemListVO>(contactListVO, HttpStatus.OK);
    }

    private ItemListVO listAll(int page) {
        return itemService.findAll(page, maxResults);
    }

    private ResponseEntity<ItemListVO> returnListToUser(ItemListVO contactList) {
        return new ResponseEntity<ItemListVO>(contactList, HttpStatus.OK);
    }
    private ResponseEntity<ItemListVO> returnErrorToUser(ItemListVO contactList) {
        return new ResponseEntity<ItemListVO>(contactList, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private ResponseEntity<?> createListAllResponse(int page, Locale locale) {
        return createListAllResponse(page, locale, null);
    }

    private ResponseEntity<?> createListAllResponse(int page, Locale locale, String messageKey) {
        ItemListVO contactListVO = listAll(page);

        addActionMessageToVO(contactListVO, locale, messageKey, null);

        return returnListToUser(contactListVO);
    }
    private ResponseEntity<?> createErrorResponse(Locale locale, String messageKey) {
    	ItemListVO contactListVO = new ItemListVO();
        addActionMessageToVO(contactListVO, locale, messageKey, null);
        return returnErrorToUser(contactListVO);
    }
    @RequestMapping(value="/list",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> listAll(@RequestParam int page, Locale locale) {
    	
    	ItemListVO itemListVO = listAll(page);
    	/*List<ItemVO> items = itemListVO.getItems();
    	List<PosOrder> list = new ArrayList<PosOrder>();
    	for(PosOrder order: items){ 
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
    	}*/
    	 
    	//contactListVO.setPosOrders(list);
    	return new ResponseEntity<ItemListVO>(itemListVO,HttpStatus.OK);
    	/*
        return createListAllResponse(page, locale);*/
    }
    @RequestMapping(value="/ordered",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> listOrdered(@RequestParam int page, Locale locale) {
    	List<ItemVO> items = reportingService.getMostUsedItems();
    	ItemListVO itemListVO = new ItemListVO(1,items.size(),items);
    	return new ResponseEntity<ItemListVO>(itemListVO,HttpStatus.OK);
    }


    private ItemListVO addActionMessageToVO(ItemListVO contactListVO, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return contactListVO;
        }

        contactListVO.setActionMessage(messageSource.getMessage(actionMessageKey, args, null, locale));

        return contactListVO;
    }

    private ItemListVO addSearchMessageToVO(ItemListVO itemListVO, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return itemListVO;
        }

        itemListVO.setSearchMessage(messageSource.getMessage(actionMessageKey, args, null, locale));

        return itemListVO;
    }

    private boolean isSearchActivated(String searchFor) {
        return !StringUtils.isEmpty(searchFor);
    }
}