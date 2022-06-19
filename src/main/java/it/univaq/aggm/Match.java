package it.univaq.aggm;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Match")
public class Match {
	private Team localTeam;
	private Team visitorTeam;
	private String score;
	private int localScore;
	private int visitorScore;
	
	public void setLocalTeam(Team localTeam) {
		this.localTeam = localTeam;
	}
	
	public Team getLocalTeam() {
		return this.localTeam;
	}
	
	public void setVisitorTeam(Team visitorTeam) {
		this.visitorTeam = visitorTeam;
	}
	
	public Team getVisitorTeam() {
		return this.visitorTeam;
	}
	
	public void setLocalScore(int score) {
		this.localScore = score;
	}
	
	public int getLocalScore() {
		return this.localScore;
	}
	
	public void setVisitorScore(int score) {
		this.visitorScore = score;
	}
	
	public int getVisitorScore() {
		return this.visitorScore;
	}
}
