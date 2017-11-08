package hg.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {
				
	private static final long serialVersionUID = 1136521684720429522L;
	
	@Id
	@GeneratedValue
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method get a {@link Set} of {@link BaseEntity} objects
	 * and an identifier. It searches the {@link Set} for the entity with
	 * matching identifier and returns the entity with matching id. If the
	 * entity does not exist it returns null.
	 * 
	 * @param set
	 *            {@link Set} containing objects of
	 *            {@link BaseEntity}
	 * @param entityIdentifier
	 *            {@link Long} identifier of the entity.
	 * @return {@link BaseEntity}
	 */
	public BaseEntity getObjectByIdentifier(
			Set<? extends BaseEntity> set,
			Long entityIdentifier) {
		for (BaseEntity entity : set) {
			if (entity.getId().longValue() == entityIdentifier.longValue())
				return entity;
		}
		return null;
	}// end of method getObjectByIdentifier
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	/*@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.appendSuper(super.hashCode()).append(this.id).toHashCode();
	}*/ 

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	/*@Override
	public boolean equals(Object object) {
		if (!(object instanceof BaseEntity)) {
			return false;
		}
		BaseEntity rhs = (BaseEntity) object;
		return new EqualsBuilder().appendSuper(super.equals(object))
				.append(this.id, rhs.id).isEquals();
	}*/

	/**
	 * @see java.lang.Object#toString()
	 */
	/*@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).toString();
	} */
	
}
