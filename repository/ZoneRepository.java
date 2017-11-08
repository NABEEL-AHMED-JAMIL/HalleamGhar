package hg.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import hg.model.Zone;

public interface ZoneRepository extends PagingAndSortingRepository<Zone, Integer> {
    Page<Zone> findByNameLike(Pageable pageable, String name);
}