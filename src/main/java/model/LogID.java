package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Model class describing the composite key used
 * to identify a Log entity. 
 *
 * It encapsulates the IDs corresponding to the foreign keys of
 * table user (int) and the primary key timestamp (Timestamp) and is used
 * in combination with the JEE annotation `@IdClass`.
 * 
 * For further usages @see Log class.
 */
public class LogID implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Timestamp timestamp;
	private int user;
	
	public LogID() {}

	@Override
	public int hashCode() {
		return Objects.hash(timestamp, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogID other = (LogID) obj;
		return Objects.equals(timestamp, other.timestamp) && Objects.equals(user, other.user);
	}
	
	
}
