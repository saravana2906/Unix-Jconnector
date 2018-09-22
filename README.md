# Unix-Jconnector
Swing based Application to connect unix systems
The entire System consists of two modules
1.	User interface
2.	Bridge
Mechanism:
•	JobConsole program is responsible for User Interface module, which acts as a View and also as a Controller.
•	JobConsole get input from the User Interface and sends it to the respective unix server for execution using Bridge objects.
•	Once Bridge executes the command in the respective server and sends the result back to User Interface for displaying the result in the front end.
