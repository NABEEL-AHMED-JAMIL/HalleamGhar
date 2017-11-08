package hg.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hg.model.Category;
import hg.model.Item;
import hg.repository.CategoryRepository;
import hg.vo.CategoryListVO;
import hg.vo.CategoryVO;
import hg.vo.ItemListVO;

@Service
@Transactional
public class CategoryService {

    private static final String GENERAL = "General";
	@Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public CategoryListVO findAll(int page, int maxResults) {
        List<Category> itemsList = executeQueryFindAll(page, maxResults);
        return buildResult(itemsList);
    }

    public void save(Category contact) {
        categoryRepository.save(contact);
    }

    public void delete(int contactId) {
        categoryRepository.delete(contactId);
    }

    @Transactional(readOnly = true)
    public CategoryListVO findByNameLike(int page, int maxResults, String name) {
        List<Category> result = executeQueryFindByName(name);

       /* if(shouldExecuteSameQueryInLastPage(page, result)){
            int lastPage = result.getTotalPages() - 1;
            result = executeQueryFindByName(lastPage, maxResults, name);
        }*/

        return buildResult(result);
    }

    private boolean shouldExecuteSameQueryInLastPage(int page, Page<Category> result) {
        return isUserAfterOrOnLastPage(page, result) && hasDataInDataBase(result);
    }

    private List<Category> executeQueryFindAll(int page, int maxResults) {
        return categoryRepository.findAll();
    }

    private Sort sortByNameASC() {
        return new Sort(Sort.Direction.ASC, "name");
    }

    private CategoryListVO buildResult(List<Category> result) {
       // return new ItemListVO(result.getTotalPages(), result.getTotalElements(), result.getContent());
    	 
    	 List<CategoryVO> list = new ArrayList<CategoryVO>(result.size());
         for (int i = 0; i < result.size(); i++) {
        	 CategoryVO vo = new CategoryVO();
        	 Category category = result.get(i);
			try {
				BeanUtils.copyProperties(category, vo);
				if(category.getParent() != null){
					vo.setParentCategory(category.getParent().getName());
					vo.setParentCategoryId(category.getParent().getId());
				} 
			} catch (BeansException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
             list.add(vo);
        }
    	
    	return new CategoryListVO(1, list.size(),list);
    }
    @Transactional(readOnly = true)
    public CategoryListVO findByNameOrCode(String name,String code) {
    	List<Category> result = categoryRepository.findByNameOrCode(name, code);
        return buildResult(result);
    }

    private List<Category> executeQueryFindByName(String name) {
      //  final PageRequest pageRequest = new PageRequest(page, maxResults, sortByNameASC());

        return categoryRepository.findByNameLike("%" + name + "%");
    }

    private boolean isUserAfterOrOnLastPage(int page, Page<Category> result) {
        return page >= result.getTotalPages() - 1;
    }

    private boolean hasDataInDataBase(Page<Category> result) {
        return result.getTotalElements() > 0;
    }
}