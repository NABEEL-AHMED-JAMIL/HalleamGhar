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

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import com.google.common.base.Objects;

/**
 * A Item.
 */
@Entity
@Table(name = "item")
@JsonPropertyOrder(
        {"id","code", "name", "unit", "price", "defaultQuantity", "type", "category"}
    )
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Item implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("id")
    private Long id;

    /*@NotNull
    @Size(max = 100)*/
    @Column(name = "name", length = 100, nullable = false)
    @JsonProperty("name")
    private String name;

    @Column(name = "price", precision=10, scale=2, nullable = false)
    @JsonProperty("price")
    private BigDecimal price;

    @Column(name = "default_quantity")
    @JsonProperty("defaultQuantity")
    private Integer defaultQuantity;

   /* @NotNull
    @Size(max = 20)*/
    @Column(name = "code", length = 20, nullable = false)
    @JsonProperty("code")
    private String code;

    @Column(name = "type")
    @JsonProperty("type")
    private String type;

    @Column(name = "unit")
    @JsonProperty("unit")
    private String unit;

   // @Size(max = 100)
    @Column(name = "image", length = 100)
    private String image;

    @Column(name="orderNo")
    private Integer orderNo;
    
    @Column(name="shortName")
    private String shortName;
    
    public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@ManyToOne
	@JsonProperty("category")
    private Category category;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Item item = (Item) o;

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
                ", image='" + image + "'" +
                '}';
    }
}
