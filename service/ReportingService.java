package hg.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hg.model.PosOrder;
import hg.model.SimpleVO;
import hg.vo.ItemVO;


@Service
@Transactional
public class ReportingService extends BaseService<PosOrder> {

	public List<SimpleVO> prepareDailyReport(){
		logger.info("calling daily reports..");
		List<SimpleVO> list = new ArrayList<SimpleVO>();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		EntityManager em = dao.getEntityManager();
		Query query = em.createNativeQuery("select creation_date,SUM(amount) from posorder group by creation_date");
		List<SimpleVO> result = query.getResultList();
		for(Object o : result){
			Object[] r = (Object[])o;
			 
			 String date = "";
			 if(r[0] != null){
				 Timestamp ts =  (Timestamp)r[0];
				 cal.setTimeInMillis(ts.getTime());
				 date = sdf.format(cal.getTime());
			 }
			 BigDecimal bd = (BigDecimal)r[1];
			 Double amount = bd.doubleValue();
			 SimpleVO vo = new SimpleVO(amount, date);
			 list.add(vo);
		}
		return list;
	}
	public List<ItemVO> getMostUsedItems(){
		logger.info("calling ordered Items..");
		List<ItemVO> list = new ArrayList<ItemVO>();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		EntityManager em = dao.getEntityManager();
		Query query = em.createNativeQuery("SELECT "+
		" item.id,item.name,item.price,item.default_quantity,item.code,item.type,item.unit,"
		+ " category.name AS categoryName,category.id AS categoryId "+
				" FROM orderitem  "+
				" INNER JOIN posorder ON posorder.id = orderitem.posorder_id "+
				" LEFT JOIN item ON item.name = orderitem.name "+
				" INNER JOIN category ON category.id = item.category_id "+
				//" WHERE MONTH(posorder.creation_date) = :month AND YEAR(posorder.creation_date) = :year "+
				" GROUP BY orderitem.name ORDER BY COUNT(orderitem.name) DESC "+
				" LIMIT 20");
		/*query.setParameter("month", cal.get(Calendar.MONTH));
		query.setParameter("year", cal.get(Calendar.YEAR));*/
		List<ItemVO> result = query.getResultList();
		for(Object o : result){
			Object[] r = (Object[])o;
			 ItemVO vo = new ItemVO();
			 if(r[0] != null){
				 r[0].getClass();
			 }
			 BigInteger id = (BigInteger)r[0];
			 if(id != null){
				 vo.setId(id.longValue());
			 }
			 vo.setName((String)r[1]);
			 vo.setPrice((BigDecimal)r[2]);
			 vo.setDefaultQuantity((Integer)r[3]);
			 vo.setCode((String)r[4]);
			 vo.setType((String)r[5]);
			 vo.setUnit((String)r[6]);
			 vo.setCategoryName((String)r[7]);
			 if(r[8] != null){
				 r[8].getClass();
			 }
			 BigInteger categoryId = (BigInteger)r[8];
			 if(categoryId != null){
				 vo.setCategoryId(categoryId.longValue());
			 }
			 list.add(vo);
		}
		return list;
	}
	
	
}
