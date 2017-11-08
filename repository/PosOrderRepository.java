package hg.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import hg.model.PosOrder;

public interface PosOrderRepository extends PagingAndSortingRepository<PosOrder, Long> {
	
    public final static String FIND_BY_TABLE_CASHIER_STATUS_QUERY = "SELECT o " + 
            "FROM PosOrder o WHERE o.tableNo = :tableNo and o.createdBy = :cashier and o.status = :status";
	public static final String FIND_ORDERS_DONE_BETWEEN_DATES_QUERY = "SELECT o.creationDate,SUM(o.amount) " + 
            "FROM PosOrder o WHERE o.creationDate between :creationDate and :creationDate and o.status = :status group by o.creationDate";;
    Page<PosOrder> findByCodeLike(Pageable pageable, String code);
    Page<PosOrder> findByCode(Pageable pageable, String code);

    Page<PosOrder> findById(Pageable pageable, Long id);
    
    /**
     * Find orders by Table No and status
     */
    @Query(FIND_BY_TABLE_CASHIER_STATUS_QUERY)
    public List<PosOrder> findByTableNoStatus(@Param("tableNo") String tableNo,@Param("cashier") String cashier,@Param("status") String status);
    public List<PosOrder> findByTableNoAndStatus(String tableNo,String status);
	public List<PosOrder> findByCodeAndStatusAndCreatedBy(String code, String status,String createdBy);
	@Query(FIND_ORDERS_DONE_BETWEEN_DATES_QUERY)
    public List<PosOrder> findByDailySale(@Param("creationDate") Date creationDate,@Param("status") String status);
	public List<PosOrder> findAll();
}