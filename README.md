# Unix-Jconnector
Swing based Application to connect unix systems
This system takes input file which contains username and password for server as input.
This application helps user to connect to multiple unix systems simultaneously.Initially designed to help the support people to check the logs of the servers.<br>
Note: Jsch jar is needed as dependency for working. http://www.jcraft.com/jsch/ <br>
 
The entire System consists of two modules<br>
1.	User interface<br>
2.	Bridge<br>
Mechanism:<br>
•	JobConsole program is responsible for User Interface module, which acts as a View and also as a Controller.<br>
•	JobConsole get input from the User Interface and sends it to the respective unix server for execution using Bridge objects.<br>
•	Once Bridge executes the command in the respective server and sends the result back to User Interface for displaying the result in the front end.

This can be further improved to work with ssh keys instead of passwords.
