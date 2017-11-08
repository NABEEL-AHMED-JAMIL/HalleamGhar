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

import hg.model.Item;
import hg.repository.ItemRepository;
import hg.vo.ItemListVO;
import hg.vo.ItemVO;

@Service
@Transactional
public class ItemService {

    private static final String GENERAL = "General";
	@Autowired
    private ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public ItemListVO findAll(int page, int maxResults) {
        List<Item> itemsList = executeQueryFindAll(page, maxResults);
        return buildResult(itemsList);
    }

    public void save(Item contact) {
        itemRepository.save(contact);
    }

    public void delete(int contactId) {
        itemRepository.delete(contactId);
    }

    @Transactional(readOnly = true)
    public ItemListVO findByNameLike(int page, int maxResults, String name) {
        List<Item> result = executeQueryFindByName(name);

       /* if(shouldExecuteSameQueryInLastPage(page, result)){
            int lastPage = result.getTotalPages() - 1;
            result = executeQueryFindByName(lastPage, maxResults, name);
        }*/

        return buildResult(result);
    }
    @Transactional(readOnly = true)
    public ItemListVO findByNameOrCode(String name,String code) {
    	List<Item> result = itemRepository.findByNameOrCode(name, code);
        return buildResult(result);
    }

    private boolean shouldExecuteSameQueryInLastPage(int page, Page<Item> result) {
        return isUserAfterOrOnLastPage(page, result) && hasDataInDataBase(result);
    }

    private List<Item> executeQueryFindAll(int page, int maxResults) {
        //final PageRequest pageRequest = new PageRequest(page, maxResults, sortByNameASC());

        return itemRepository.findAll();
    }

    private Sort sortByNameASC() {
        return new Sort(Sort.Direction.ASC, "name");
    }

    private ItemListVO buildResult(List<Item> result) {
       // return new ItemListVO(result.getTotalPages(), result.getTotalElements(), result.getContent());
    	 
    	 List<ItemVO> list = new ArrayList<ItemVO>(result.size());
         for (int i = 0; i < result.size(); i++) {
        	 ItemVO vo = new ItemVO();
        	 Item item = result.get(i);
			try {
				BeanUtils.copyProperties(item, vo); 
				if(item.getCategory() != null){
					vo.setCategoryName(item.getCategory().getName());
					vo.setCategoryId(item.getCategory().getId());
				} else {
					vo.setCategoryName(ItemService.GENERAL);
					vo.setCategoryId(0L);
				}
			} catch (BeansException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
             list.add(vo);
        }
    	
    	return new ItemListVO(1, list.size(),list);
    }

    private List<Item> executeQueryFindByName(String name) {
      //  final PageRequest pageRequest = new PageRequest(page, maxResults, sortByNameASC());

        return itemRepository.findByNameLike("%" + name + "%");
    }

    private boolean isUserAfterOrOnLastPage(int page, Page<Item> result) {
        return page >= result.getTotalPages() - 1;
    }

    private boolean hasDataInDataBase(Page<Item> result) {
        return result.getTotalElements() > 0;
    }
}