package hg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hg.model.Employee;
import hg.repository.EmployeeRepository;
import hg.vo.EmployeeListVO;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public EmployeeListVO findAll(int page, int maxResults) {
        Page<Employee> result = executeQueryFindAll(page, maxResults);

        if(shouldExecuteSameQueryInLastPage(page, result)){
            int lastPage = result.getTotalPages() - 1;
            result = executeQueryFindAll(lastPage, maxResults);
        }

        return buildResult(result);
    }
    @Transactional(readOnly = true)
    public EmployeeListVO findByDesignation(int page, int maxResults,String designation, String name) {
        return executeQueryFindByDesignation(page, maxResults,name,designation);
    }
    public void save(Employee zone) {
    	employeeRepository.save(zone);
    }

    public void delete(int zoneid) {
        employeeRepository.delete(zoneid);
    }


    private boolean shouldExecuteSameQueryInLastPage(int page, Page<Employee> result) {
        return isUserAfterOrOnLastPage(page, result) && hasDataInDataBase(result);
    }

    private Page<Employee> executeQueryFindAll(int page, int maxResults) {
        final PageRequest pageRequest = new PageRequest(page, maxResults, sortByNameASC());

        return employeeRepository.findAll(pageRequest);
    }
    private EmployeeListVO executeQueryFindByDesignation(int page, int maxResults,String name,String designation) {
        List<Employee> list = employeeRepository.findByNameLikeAndDesignation(name+"%",designation);
        Page<Employee> result = new PageImpl<Employee>(list);
        return buildResult(result);
    }
    private Sort sortByNameASC() {
        return new Sort(Sort.Direction.ASC, "name");
    }

    private EmployeeListVO buildResult(Page<Employee> result) {
        return new EmployeeListVO(result.getTotalPages(), result.getTotalElements(), result.getContent());
    }


    private boolean isUserAfterOrOnLastPage(int page, Page<Employee> result) {
        return page >= result.getTotalPages() - 1;
    }

    private boolean hasDataInDataBase(Page<Employee> result) {
        return result.getTotalElements() > 0;
    }
}