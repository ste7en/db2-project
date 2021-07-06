package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the offensive_words database table.
 * 
 */
@Entity
@Table(name = "offensive_words")
@NamedQueries({
	@NamedQuery(name = "OffensiveWord.findAll", query = "SELECT o FROM OffensiveWord o"),
	@NamedQuery(name = "OffensiveWord.findAllWords", query = "SELECT o.word FROM OffensiveWord o")
})
public class OffensiveWord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String word;

	@Column(name = "occurrence")
	private int occurrence;

	public OffensiveWord() {
	}

	public String getWord() {
		return this.word;
	}

	public int getOccurrence() {
		return this.occurrence;
	}
	
	public void incrementOccurrence() {
		this.occurrence += 1;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + occurrence;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OffensiveWord other = (OffensiveWord) obj;
		if (occurrence != other.occurrence)
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}
	
	

}