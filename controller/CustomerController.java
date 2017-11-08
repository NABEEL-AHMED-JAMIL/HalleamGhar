package hg.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
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

import hg.model.Customer;
import hg.service.CustomerService;
import hg.vo.CustomerListVO;
import hg.vo.CustomerVO;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

    private static final String DEFAULT_PAGE_DISPLAYED_TO_USER = "0";

    @Autowired
    private CustomerService itemService;

    @Autowired
    private MessageSource messageSource;

    @Value("5")
    private int maxResults;

   /* @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("itemsList");
    }*/

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> listAll(Locale locale) {
        return createListAllResponse(1, locale);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> create(@RequestBody Customer item,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        
    	Customer customer = itemService.save(item);

        if (isSearchActivated(searchFor)) {
            return search(searchFor, page, locale, "message.create.success");
        }

        return createListCustomerResponse(customer,page, locale, "message.create.success");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") int contactId,
                                    @RequestBody Customer item,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        if (contactId != item.getId()) {
            return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
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

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> search(@RequestParam("q") String name,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        return search(name, page, locale, null);
    }

    private ResponseEntity<?> search(String name, int page, Locale locale, String actionMessageKey) {
        CustomerListVO contactListVO = itemService.findByNameLike(page, maxResults, name);

        if (!StringUtils.isEmpty(actionMessageKey)) {
            addActionMessageToVO(contactListVO, locale, actionMessageKey, null);
        }

        Object[] args = {name};

        addSearchMessageToVO(contactListVO, locale, "message.search.for.active", args);

        return new ResponseEntity<CustomerListVO>(contactListVO, HttpStatus.OK);
    }

    private CustomerListVO listAll(int page) {
        return itemService.findAll(page, maxResults);
    }

    private ResponseEntity<CustomerListVO> returnListToUser(CustomerListVO contactList) {
        return new ResponseEntity<CustomerListVO>(contactList, HttpStatus.OK);
    }

    private ResponseEntity<?> createListAllResponse(int page, Locale locale) {
        return createListAllResponse(page, locale, null);
    }

    private ResponseEntity<?> createListAllResponse(int page, Locale locale, String messageKey) {
        CustomerListVO contactListVO = listAll(page);

        addActionMessageToVO(contactListVO, locale, messageKey, null);

        return returnListToUser(contactListVO);
    }

    private ResponseEntity<?> createListCustomerResponse(Customer customer,int page, Locale locale, String messageKey) {
        CustomerListVO contactListVO = new CustomerListVO();
        List<CustomerVO> customers = new ArrayList<CustomerVO>();
        CustomerVO cvo = new CustomerVO();
        BeanUtils.copyProperties(customer, cvo);
        customers.add(cvo);
        contactListVO.setCustomers(customers);
        addActionMessageToVO(contactListVO, locale, messageKey, null);
        return returnListToUser(contactListVO);
    }
    private CustomerListVO addActionMessageToVO(CustomerListVO contactListVO, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return contactListVO;
        }

        contactListVO.setActionMessage(messageSource.getMessage(actionMessageKey, args, null, locale));

        return contactListVO;
    }

    private CustomerListVO addSearchMessageToVO(CustomerListVO itemListVO, Locale locale, String actionMessageKey, Object[] args) {
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