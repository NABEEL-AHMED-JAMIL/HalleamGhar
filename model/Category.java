package hg.model;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import com.google.common.base.Objects;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
@JsonPropertyOrder(
        {"id"}
    )
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Category implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5694741375597910416L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("id")
    private Long id;

    //@NotNull
   // @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

   // @Size(max = 200)
    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "status")
    private Boolean status;

    //@Size(max = 20)
    @Column(name = "code", length = 20)
    private String code;

    @ManyToOne
    @JsonIgnore
    private Category parent;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category category) {
        this.parent = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Category category = (Category) o;

        if (id!= null && ! id.equals(category.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", description='" + description + "'" +
                ", status='" + status + "'" +
                ", code='" + code + "'" +
                '}';
    }
}
