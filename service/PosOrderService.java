package hg.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hg.model.OrderItem;
import hg.model.PosOrder;
import hg.repository.PosOrderRepository;
import hg.vo.PosOrderListVO;

@Service
@Transactional
public class PosOrderService {

    @Autowired
    private PosOrderRepository posOrderRepository;

    @Transactional(readOnly = true)
    public PosOrderListVO findAll(int page, int maxResults) {
        List<PosOrder> list =posOrderRepository.findAll();
        return buildResult(list);
    }

    public PosOrder save(PosOrder contact) {
    	PosOrder order = posOrderRepository.save(contact);
    	order.setCode(String.valueOf(order.getId()));
    	return order;
    }

    public void delete(Long contactId) {
    	posOrderRepository.delete(contactId);
    }

    @Transactional(readOnly = true)
    public PosOrderListVO findByNameLike(int page, int maxResults, String name) {
        Page<PosOrder> result = executeQueryFindByName(page, maxResults, name);

        if(shouldExecuteSameQueryInLastPage(page, result)){
            int lastPage = result.getTotalPages() - 1;
            result = executeQueryFindByName(lastPage, maxResults, name);
        }

        return buildResult(result);
    }
    @Transactional(readOnly = true)
    public PosOrderListVO findByCode(int page, int maxResults, String name) {
        Page<PosOrder> result = executeQueryFindByCode(page, maxResults, name);

        if(shouldExecuteSameQueryInLastPage(page, result)){
            int lastPage = result.getTotalPages() - 1;
            result = executeQueryFindByName(lastPage, maxResults, name);
        }

        return buildResult(result);
    }
    
    @Transactional(readOnly = true)
    public PosOrderListVO findById(int page, int maxResults, Long id) {
    	final PageRequest pageRequest = new PageRequest(page, maxResults, sortByIdASC());
    	PosOrder po = posOrderRepository.findOne(id);
    	List<PosOrder> list = new ArrayList<PosOrder>();
    	Set<OrderItem> set = po.getItems();
    	for(OrderItem i:set){
    		i.getAmount();
    	}
    	list.add(po);
    	Page<PosOrder> result = new PageImpl<PosOrder>(list);
        return buildResult(result);
    }
    
    @Transactional(readOnly = true)
    public PosOrder findById(Long id) {
    	return posOrderRepository.findOne(id);
    }
    @Transactional(readOnly = true)
    public PosOrderListVO findByTableNoStatus(String tableNo,String cashier,String status) {
    	List<PosOrder> orders = posOrderRepository.findByTableNoAndStatus(tableNo,status);
    	PosOrder po = new PosOrder();
    	List<PosOrder> list = new ArrayList<PosOrder>();
    	if(orders != null && !orders.isEmpty()){
    		po = orders.get(0);
    	}
    	Set<OrderItem> set = po.getItems();
    	for(OrderItem i:set){
    		i.getAmount();
    	}
    	list.add(po);
    	Page<PosOrder> result = new PageImpl<PosOrder>(list);
        return buildResult(result);
    }
    @Transactional(readOnly = true)
    public PosOrderListVO findByCodeAndStatusAndCreatedBy(String code,String status,String createdBy) {
    	List<PosOrder> orders = posOrderRepository.findByCodeAndStatusAndCreatedBy(code,status,createdBy);
    	PosOrder po = new PosOrder();
    	List<PosOrder> list = new ArrayList<PosOrder>();
    	if(orders != null && !orders.isEmpty()){
    		po = orders.get(0);
    	}
    	Set<OrderItem> set = po.getItems();
    	for(OrderItem i:set){
    		i.getAmount();
    	}
    	list.add(po);
    	Page<PosOrder> result = new PageImpl<PosOrder>(list);
        return buildResult(result);
    }
    private boolean shouldExecuteSameQueryInLastPage(int page, Page<PosOrder> result) {
        return isUserAfterOrOnLastPage(page, result) && hasDataInDataBase(result);
    }

  
    private Sort sortByIdASC() {
        return new Sort(Sort.Direction.ASC, "id");
    }
    private PosOrderListVO buildResult(List<PosOrder> result) {
        return new PosOrderListVO(1, result.size(), result);
    }
    private PosOrderListVO buildResult(Page<PosOrder> result) {
        return new PosOrderListVO(result.getTotalPages(), result.getTotalElements(), result.getContent());
    }

    private Page<PosOrder> executeQueryFindByName(int page, int maxResults, String name) {
        final PageRequest pageRequest = new PageRequest(page, maxResults, sortByIdASC());

        return posOrderRepository.findByCodeLike(pageRequest, "%" + name + "%");
    }
    private Page<PosOrder> executeQueryFindByCode(int page, int maxResults, String name) {
        final PageRequest pageRequest = new PageRequest(page, maxResults, sortByIdASC());

        return posOrderRepository.findByCode(pageRequest,  name );
    }
    private boolean isUserAfterOrOnLastPage(int page, Page<PosOrder> result) {
        return page >= result.getTotalPages() - 1;
    }

    private boolean hasDataInDataBase(Page<PosOrder> result) {
        return result.getTotalElements() > 0;
    }

	public PosOrderListVO getDailySalesReport(Date date1, Date date2) {
		return buildResult(posOrderRepository.findByDailySale(date1, "DONE"));
		
	}

	@Transactional
	public void updateItemsAsPrinted(Long orderId) {
		PosOrder order =  findById(orderId);
		Set<OrderItem> items = order.getItems();
		for(OrderItem item: items){
			item.setIsPrinted(true);
		}
		
	}
}