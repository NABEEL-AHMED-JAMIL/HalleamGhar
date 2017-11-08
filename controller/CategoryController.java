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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import hg.model.Category;
import hg.service.CategoryService;
import hg.vo.CategoryListVO;
import hg.vo.CategoryVO;
import hg.vo.ItemListVO;

@Controller
@RequestMapping(value = "/categories")
public class CategoryController {

    private static final String DEFAULT_PAGE_DISPLAYED_TO_USER = "0";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MessageSource messageSource;

    @Value("5")
    private int maxResults;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("categories");
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> listAll(Locale locale) {
        return createListAllResponse(1, locale);
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> create(@RequestBody Category category,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
    	CategoryListVO list = categoryService.findByNameOrCode(category.getName(),category.getCode());
    	if(list != null && list.getCategories() != null && !list.getCategories().isEmpty()){
    		return createErrorResponse(locale, "message.item.error.alreadyAvailable");
    	}
    	categoryService.save(category);

        if (isSearchActivated(searchFor)) {
            return search(searchFor, page, locale, "message.create.success");
        }

        return createListAllResponse(page, locale, "message.create.success");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") int contactId,
                                    @RequestBody Category category,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        if (contactId != category.getId()) {
            return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
        }
    	CategoryListVO list = categoryService.findByNameOrCode(category.getName(),category.getCode());
        if(list != null && list.getCategories() != null && !list.getCategories().isEmpty()){
        	for(CategoryVO itemvo : list.getCategories()){
        		if(itemvo.getId() != category.getId()){
        			return createErrorResponse(locale, "message.item.error.alreadyAvailable");
        		}
        	}
    	}
        categoryService.save(category);

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

/*
        try {
            categoryService.delete(contactId);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
        }
*/
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
        CategoryListVO contactListVO = categoryService.findByNameLike(page, maxResults, name);

        if (!StringUtils.isEmpty(actionMessageKey)) {
            addActionMessageToVO(contactListVO, locale, actionMessageKey, null);
        }

        Object[] args = {name};

        addSearchMessageToVO(contactListVO, locale, "message.search.for.active", args);

        return new ResponseEntity<CategoryListVO>(contactListVO, HttpStatus.OK);
    }

    private CategoryListVO listAll(int page) {
        return categoryService.findAll(page, maxResults);
    }

    private ResponseEntity<CategoryListVO> returnListToUser(CategoryListVO contactList) {
        return new ResponseEntity<CategoryListVO>(contactList, HttpStatus.OK);
    }

    private ResponseEntity<?> createListAllResponse(int page, Locale locale) {
        return createListAllResponse(page, locale, null);
    }

    private ResponseEntity<?> createListAllResponse(int page, Locale locale, String messageKey) {
        CategoryListVO contactListVO = listAll(page);

        addActionMessageToVO(contactListVO, locale, messageKey, null);

        return returnListToUser(contactListVO);
    }
    @RequestMapping(value="/list",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> listAll(@RequestParam int page, Locale locale) {
    	CategoryListVO categoriesListVO = listAll(page);
    	return new ResponseEntity<CategoryListVO>(categoriesListVO,HttpStatus.OK);
    }


    private CategoryListVO addActionMessageToVO(CategoryListVO contactListVO, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return contactListVO;
        }

        contactListVO.setActionMessage(messageSource.getMessage(actionMessageKey, args, null, locale));

        return contactListVO;
    }

    private CategoryListVO addSearchMessageToVO(CategoryListVO itemListVO, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return itemListVO;
        }

        itemListVO.setSearchMessage(messageSource.getMessage(actionMessageKey, args, null, locale));

        return itemListVO;
    }

    private boolean isSearchActivated(String searchFor) {
        return !StringUtils.isEmpty(searchFor);
    }
    private ResponseEntity<?> createErrorResponse(Locale locale, String messageKey) {
    	CategoryListVO contactListVO = new CategoryListVO();
        addActionMessageToVO(contactListVO, locale, messageKey, null);
        return returnErrorToUser(contactListVO);
    }
    private ResponseEntity<CategoryListVO> returnErrorToUser(CategoryListVO contactList) {
        return new ResponseEntity<CategoryListVO>(contactList, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private ItemListVO addActionMessageToVO(ItemListVO contactListVO, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return contactListVO;
        }

        contactListVO.setActionMessage(messageSource.getMessage(actionMessageKey, args, null, locale));

        return contactListVO;
    }
}