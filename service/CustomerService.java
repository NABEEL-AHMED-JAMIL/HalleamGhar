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

import hg.model.Customer;
import hg.repository.CustomerRepository;
import hg.vo.CustomerListVO;
import hg.vo.CustomerVO;

@Service
@Transactional
public class CustomerService {

    private static final String GENERAL = "General";
	@Autowired
    private CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public CustomerListVO findAll(int page, int maxResults) {
        List<Customer> customersList = executeQueryFindAll(page, maxResults);
        return buildResult(customersList);
    }

    public Customer save(Customer contact) {
        return customerRepository.save(contact);
    }

    public void delete(int contactId) {
        customerRepository.delete(contactId);
    }

    @Transactional(readOnly = true)
    public CustomerListVO findByNameLike(int page, int maxResults, String name) {
        List<Customer> result = executeQueryFindByName(name, name);

       /* if(shouldExecuteSameQueryInLastPage(page, result)){
            int lastPage = result.getTotalPages() - 1;
            result = executeQueryFindByName(lastPage, maxResults, name);
        }*/

        return buildResult(result);
    }

    private boolean shouldExecuteSameQueryInLastPage(int page, Page<Customer> result) {
        return isUserAfterOrOnLastPage(page, result) && hasDataInDataBase(result);
    }

    private List<Customer> executeQueryFindAll(int page, int maxResults) {
        //final PageRequest pageRequest = new PageRequest(page, maxResults, sortByNameASC());

        return customerRepository.findAll();
    }

    private Sort sortByNameASC() {
        return new Sort(Sort.Direction.ASC, "name");
    }

    private CustomerListVO buildResult(List<Customer> result) {
       // return new CustomerListVO(result.getTotalPages(), result.getTotalElements(), result.getContent());
    	 
    	 List<CustomerVO> list = new ArrayList<CustomerVO>(result.size());
         for (int i = 0; i < result.size(); i++) {
        	 CustomerVO vo = new CustomerVO();
        	 Customer customer = result.get(i);
			try {
				BeanUtils.copyProperties(customer, vo);
			} catch (BeansException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
             list.add(vo);
        }
    	
    	return new CustomerListVO(1, list.size(),list);
    }

    private List<Customer> executeQueryFindByName(String name,String cellNo) {
      //  final PageRequest pageRequest = new PageRequest(page, maxResults, sortByNameASC());

        return customerRepository.findByNameIgnoreCaseLikeOrCellNoLike("%" + name + "%","%" + cellNo + "%");
    }

    private boolean isUserAfterOrOnLastPage(int page, Page<Customer> result) {
        return page >= result.getTotalPages() - 1;
    }

    private boolean hasDataInDataBase(Page<Customer> result) {
        return result.getTotalElements() > 0;
    }

	public CustomerVO findById(Integer customerId) {
		// TODO Auto-generated method stub 
		Customer customer = customerRepository.findById(customerId);
		CustomerVO customerVO = new CustomerVO();
		BeanUtils.copyProperties(customer, customerVO);
		return customerVO;
	}
}