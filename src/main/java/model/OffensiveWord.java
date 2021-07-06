package model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;


/**
 * The persistent class for the offensive_words database table.
 * 
 */
@Entity
@Table(name = "offensive_words")
@NamedQueries({
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
		return Objects.hash(occurrence, word);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof OffensiveWord))
			return false;
		OffensiveWord other = (OffensiveWord) obj;
		return occurrence == other.occurrence && Objects.equals(word, other.word);
	}
}