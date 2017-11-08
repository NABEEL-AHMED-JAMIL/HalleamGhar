package hg.model;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Objects;

/**
 * A Item.
 */
@Entity
@Table(name = "zone")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Zone implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7259686301489752825L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /*@NotNull
    @Size(max = 100)*/
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "rate", precision=10, scale=2, nullable = false)
    private BigDecimal rate;
    
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

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	@Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Zone{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", rate='" + rate + "'" +
                '}';
    }
}
