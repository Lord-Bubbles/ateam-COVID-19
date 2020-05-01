README

Course: Comp Sci 400
Semester: Spring 2020
Project name: Crown Inc.
Ateam#: 10
Team Members:
	1. Kevin Yu, Lec001, kyu72@wisc.edu
	2. Andrew Li, Lec001, ali252@wisc.edu
	3. Paul Pak, Lec001, pepak@wisc.edu
	4. Justin Li, Lec001, jli2226@wisc.edu

Which team members were on same xteam together?
 
 	None

Data Instruction:

	 The official data used to measure the confirmed cases and death rates for the countries we recorded is stored in two csv files called "confirmedglobal.csv" and "deathsglobal.csv". Although the program will catch incorrectly formatted files in case one wanted to input their own data, they should format their csv files in accordance with the "confirmedglobal.csv" and "deathsglobal.csv" files to ensure accurate data for all the given countries.

Other notes or comments to the grader:

 	Instructions: Type or select a country from the drop down menu in order to display the data & graph for that country. You must press enter after selecting a country from the drop down menu to see its data. Select the checkboxes on the top right to compare different graphs (Monthly graph, Daily graph, Real-time Graph). Return to the main menu by pressing the back button on the top left, and exit the application from the home page by pressing the exit button on the bottom right. You may also download a report of the country's data you are seeing by pressing the download button on the top right. Pressing this button downloads a text file with the name "%country_data_report.txt", where %country is the current country you are observing.

 Bug Report:

 	There are two minor bugs. The first is invoked after returning from a country's data screen. If you press ENTER again without selecting a different country, the application will lead you to the previous data screen the user was on. The second is also invoked after returning from a country's data screen. The user is unable to type the same country in a row; they have to type in a different country unless they exploit the first bug to access the prior country's data.