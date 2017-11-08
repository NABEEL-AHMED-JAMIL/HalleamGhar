package hg.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.google.common.base.Objects;

/**
 * A PosOrder.
 */
@Entity
@Table(name = "posorder")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PosOrder extends BaseEntity implements Serializable {

    
	private static final long serialVersionUID = 4906179167726001288L;


    //@NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "amount", precision=10, scale=2, nullable = false)
    private BigDecimal amount;

    @Column(name = "creation_date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	//@NotNull
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "waiter")
    private String waiter;

    @Column(name = "table_no")
    private String tableNo;
    
    @Column(name = "delivery_boy")
    private String deliveryBoy;
    
    @Column(name = "zone")
    private String zone;
    
    /*@Column(columnDefinition = "enum('TAKEAWAY','DINEIN','DELIVERY','CATERING')")
    @Enumerated(EnumType.STRING)*/
    private String orderType;
    
    @Column(name = "delivery_charges")
    private BigDecimal deliveryCharges;
    
    @Column(name = "payment")
    private BigDecimal payment;

    /*@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "order_date", nullable = false)
    private DateTime orderDate;*/

   public BigDecimal getPayment() {
		return payment;
	}
	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}


	/* @ManyToOne
    private OrderStatus status;

    @OneToMany(mappedBy = "posOrder")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrderItem> items = new HashSet<>();

    @ManyToOne
    private Branch branch;
*/
    @Column(name = "status")
    private String status;
    
    @Transient
    @JsonIgnore
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();
    public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	
	@ManyToOne
	@JsonIgnore 
    private Customer customer;

	@OneToMany(mappedBy = "posOrder",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.EAGER)
    //@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrderItem> items = new HashSet<OrderItem>();
    public Set<OrderItem> getItems() { 
		return items;
	}

	public void setItems(Set<OrderItem> items) {
		this.items = items;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

   /* public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }*/

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getWaiter() {
        return waiter;
    }

    public void setWaiter(String waiter) {
        this.waiter = waiter;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

   /* public DateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(DateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public void setItems(Set<OrderItem> orderItems) {
        this.items = orderItems;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

       // PosOrder posOrder = (PosOrder) o;

       // if ( ! Objects.equals(id, posOrder.id)) return false;

        return true;
    }
    
    
    
    public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PosOrder{" +
                "id=" + id +
                ", code='" + code + "'" +
                ", amount='" + amount + "'" +
                ", createdBy='" + createdBy + "'" +
                ", waiter='" + waiter + "'" +
                ", tableNo='" + tableNo + "'" +
                '}';
    }
	public void addAll(Set<OrderItem> items2) {
		// TODO Auto-generated method stub
		if(items == null){
			items = new HashSet<OrderItem>();
		}
		//items.clear();
		items.addAll(items2);
	}
	public String getDeliveryBoy() {
		return deliveryBoy;
	}
	public void setDeliveryBoy(String deliveryBoy) {
		this.deliveryBoy = deliveryBoy;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public BigDecimal getDeliveryCharges() {
		return deliveryCharges;
	}
	public void setDeliveryCharges(BigDecimal delivery_charges) {
		this.deliveryCharges = delivery_charges;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	
}
