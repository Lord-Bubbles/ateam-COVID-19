package application;

public class Event {
	
	String date;
	int cases;
	int deaths;
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public void setCases(int cases) {
		this.cases = cases;
	}
	
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	
	public String getDate() {
		return date;
	}
	
	public int getCases() {
		return cases;
	}
	
	public int getDeaths() {
		return deaths;
	}

}
