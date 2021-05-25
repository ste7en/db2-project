package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the offensive_words database table.
 * 
 */
@Entity
@Table(name="offensive_words")
@NamedQuery(name="OffensiveWord.findAll", query="SELECT o FROM OffensiveWord o")
public class OffensiveWord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String word;

	private int occurrence;

	public OffensiveWord() {
	}

	public String getWord() {
		return this.word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getOccurrence() {
		return this.occurrence;
	}

	public void setOccurrence(int occurrence) {
		this.occurrence = occurrence;
	}

}