package it.univaq.aggm;

import java.util.ArrayList;

public class MatchRepositoryResponseWrapper {

	private ArrayList<Match> matches;

	public ArrayList<Match> getMessage() {
		if (matches == null) {
			return new ArrayList<Match>();
		}
		return this.matches;
	}

	public void setMessage(ArrayList<Match> matches) {
		this.matches = matches;
	}

}
