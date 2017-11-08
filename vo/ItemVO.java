package hg.vo;
import java.io.Serializable;
import java.math.BigDecimal;

import com.google.common.base.Objects;

/**
 * A Item.
 */

public class ItemVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1513458895463720507L;

	private Long id;
    private String name;
    private BigDecimal price;
    private Integer defaultQuantity;
    private String code;
    private String type;
    private Integer orderNo;
	private String unit;
    private String categoryName;
    private Long categoryId;


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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getDefaultQuantity() {
		return defaultQuantity;
	}

	public void setDefaultQuantity(Integer defaultQuantity) {
		this.defaultQuantity = defaultQuantity;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItemVO item = (ItemVO) o;

        if (id!=null && ! id.equals(item.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", price='" + price + "'" +
                ", defaultQuantity='" + defaultQuantity + "'" +
                ", code='" + code + "'" +
                ", type='" + type + "'" +
                ", unit='" + unit + "'" +
                '}';
    }

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
}
