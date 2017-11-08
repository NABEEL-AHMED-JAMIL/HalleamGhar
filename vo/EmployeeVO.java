package hg.vo;
import java.io.Serializable;
import java.math.BigDecimal;

import com.google.common.base.Objects;

/**
 * A Item.
 */

public class EmployeeVO implements Serializable {

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1513458895463720507L;

	private Integer id;

    private String name;

    private BigDecimal price;

    private Integer defaultQuantity;

    private String code;

    private String type;

    private String unit;
    
    private String categoryName;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeVO item = (EmployeeVO) o;

        if (id!=null && ! id.equals(item.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", price='" + price + "'" +
                ", defaultQuantity='" + defaultQuantity + "'" +
                ", code='" + code + "'" +
                ", type='" + type + "'" +
                ", unit='" + unit + "'" +
                '}';
    }

}
