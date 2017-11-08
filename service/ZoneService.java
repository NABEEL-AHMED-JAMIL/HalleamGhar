package hg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hg.model.Zone;
import hg.repository.ZoneRepository;
import hg.vo.ZoneListVO;

@Service
@Transactional
public class ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    @Transactional(readOnly = true)
    public ZoneListVO findAll(int page, int maxResults) {
        Page<Zone> result = executeQueryFindAll(page, maxResults);

        if(shouldExecuteSameQueryInLastPage(page, result)){
            int lastPage = result.getTotalPages() - 1;
            result = executeQueryFindAll(lastPage, maxResults);
        }

        return buildResult(result);
    }

    public void save(Zone zone) {
    	zoneRepository.save(zone);
    }

    public void delete(int zoneid) {
        zoneRepository.delete(zoneid);
    }

    @Transactional(readOnly = true)
    public ZoneListVO findByNameLike(int page, int maxResults, String name) {
        Page<Zone> result = executeQueryFindByName(page, maxResults, name);

        if(shouldExecuteSameQueryInLastPage(page, result)){
            int lastPage = result.getTotalPages() - 1;
            result = executeQueryFindByName(lastPage, maxResults, name);
        }

        return buildResult(result);
    }

    private boolean shouldExecuteSameQueryInLastPage(int page, Page<Zone> result) {
        return isUserAfterOrOnLastPage(page, result) && hasDataInDataBase(result);
    }

    private Page<Zone> executeQueryFindAll(int page, int maxResults) {
        final PageRequest pageRequest = new PageRequest(page, maxResults, sortByNameASC());

        return zoneRepository.findAll(pageRequest);
    }

    private Sort sortByNameASC() {
        return new Sort(Sort.Direction.ASC, "name");
    }

    private ZoneListVO buildResult(Page<Zone> result) {
        return new ZoneListVO(result.getTotalPages(), result.getTotalElements(), result.getContent());
    }

    private Page<Zone> executeQueryFindByName(int page, int maxResults, String name) {
        final PageRequest pageRequest = new PageRequest(page, maxResults, sortByNameASC());

        return zoneRepository.findByNameLike(pageRequest, "%" + name + "%");
    }

    private boolean isUserAfterOrOnLastPage(int page, Page<Zone> result) {
        return page >= result.getTotalPages() - 1;
    }

    private boolean hasDataInDataBase(Page<Zone> result) {
        return result.getTotalElements() > 0;
    }
}