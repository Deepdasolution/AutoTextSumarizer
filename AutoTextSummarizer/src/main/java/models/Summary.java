package models;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="summary")
public class Summary {
	@Id
	private String sourceUrl;
	private String sourceText;
	private String summary;
	private int sentenceNumber;
	private boolean IsID;
	private String jpt;
		
	public String getJpt() {
		return jpt;
	}
	public void setJpt(String jpt) {
		this.jpt = jpt;
	}
	public boolean isIsID() {
		return IsID;
	}
	public void setIsID(boolean isID) {
		this.IsID = isID;
	}
	private ArrayList<String> finalSummary= new ArrayList<>();
	
	
	public ArrayList<String> getFinalSummary() {
		return finalSummary;
	}
	public void setFinalSummary(ArrayList<String> finalSummary) {
		this.finalSummary = finalSummary;
	}
	public int getSentenceNumber() {
		return sentenceNumber;
	}
	public void setSentenceNumber(int sentenceNumber) {
		this.sentenceNumber = sentenceNumber;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public String getSourceText() {
		return sourceText;
	}
	public void setSourceText(String sourceText) {
		this.sourceText = sourceText;
	}

	
}
