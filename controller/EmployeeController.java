package hg.controller;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import hg.service.EmployeeService;
import hg.vo.EmployeeListVO;

@Controller
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private MessageSource messageSource;

    @Value("5")
    private int maxResults;

   /* @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("itemsList");
    }*/
    @RequestMapping(value = "/employee/{type}",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> listAll(@PathVariable String type,@RequestParam String q, Locale locale) {
        return createListAllResponse(1, locale, q,type);
    }

    private EmployeeListVO listAll(int page,  String name,String designation) {
        return employeeService.findByDesignation(page, maxResults,name,designation);
    }

    private ResponseEntity<EmployeeListVO> returnListToUser(EmployeeListVO contactList) {
        return new ResponseEntity<EmployeeListVO>(contactList, HttpStatus.OK);
    }

    private ResponseEntity<?> createListAllResponse(int page, Locale locale, String name,String designation) {
        return createListAllResponse(page, locale, null,name,designation);
    }

    private ResponseEntity<?> createListAllResponse(int page, Locale locale, String messageKey,String designation, String name) {
        EmployeeListVO contactListVO = listAll(page,name,designation);
        addActionMessageToVO(contactListVO, locale, messageKey, null);

        return returnListToUser(contactListVO);
    }

    private EmployeeListVO addActionMessageToVO(EmployeeListVO contactListVO, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return contactListVO;
        }

        contactListVO.setActionMessage(messageSource.getMessage(actionMessageKey, args, null, locale));

        return contactListVO;
    }

}