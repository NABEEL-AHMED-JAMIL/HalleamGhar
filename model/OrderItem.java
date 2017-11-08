package hg.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.google.common.base.Objects;

/**
 * A OrderItem.
 */
@Entity
@Table(name = "orderitem")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderItem implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4781963052518129315L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@NotNull
    //@Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Transient
    private String itemType = "order";
    

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	@Column(name = "quantity")
    private Integer quantity;

    //@NotNull
    //@Size(max = 20)
    @Column(name = "code", length = 20, nullable = false)
    private String code;

    @Column(name = "default_quantity")
    private Integer defaultQuantity;

    //@NotNull
    @Column(name = "amount", precision=10, scale=2, nullable = false)
    private BigDecimal amount;
    
    @Column(name = "price", precision=10, scale=2, nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JsonIgnore
    private PosOrder posOrder;
    
    private Boolean isPrinted;
    
    private Boolean isRefund;
    
    private String categoryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getDefaultQuantity() {
        return defaultQuantity;
    }

    public void setDefaultQuantity(Integer defaultQuantity) {
        this.defaultQuantity = defaultQuantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PosOrder getPosOrder() {
        return posOrder;
    }

    public void setPosOrder(PosOrder posOrder) {
        this.posOrder = posOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderItem orderItem = (OrderItem) o;

       // if ( ! com.google.common.base.Objects.Objects.equals(id, orderItem.id)) return false;
        if (id == null || ! id.equals(orderItem.id)) return false;

        return true;
    }
    

    public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", quantity='" + quantity + "'" +
                ", code='" + code + "'" +
                ", defaultQuantity='" + defaultQuantity + "'" +
                ", amount='" + amount + "'" +
                '}';
    }

	public Boolean getIsPrinted() {
		return isPrinted;
	}

	public void setIsPrinted(Boolean isPrinted) {
		this.isPrinted = isPrinted;
	}

	public Boolean getIsRefund() {
		return isRefund;
	}

	public void setIsRefund(Boolean isRefund) {
		this.isRefund = isRefund;
	}


	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
