package application;

/**
 * This is an Event class used to represent a single statistic. 
 * 
 * @author paul
 *
 */
public class Event {
	
	String date;
	int cases;
	int deaths;

	/**
	 * This is a setter method to set the date of an event
	 * 
	 * @param date - String date to be given
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * This is a setter method to set the number of cases of an event
	 * 
	 * @param cases - String cases to be given
	 */
	public void setCases(int cases) {
		this.cases = cases;
	}
	
	/**
	 * This is a setter method to set the number of deaths of an event
	 * 
	 * @param deaths - String deaths to be given
	 */
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	
	/**
	 * This is a getter method to get the current date of an event
	 * 
	 * @return String - the date
	 */
	public String getDate() {
		return date;
	}
	
	/**
	 * This is a getter method to get the current cases of an event
	 * 
	 * @return int - the number of cases
	 */
	public int getCases() {
		return cases;
	}
	
	/**
	 * This is a getter method to get the current deaths of an event
	 * 
	 * @return int - the number of deaths
	 */
	public int getDeaths() {
		return deaths;
	}

}
