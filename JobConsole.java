/*  <Title>  
                      	Unix JConnector
      </Title> 
      <Summary> 
       		Jobconsole Module gets config file from the user and builds the dynamic server options for the user from the config file and also creates Bridge for that server.
			It is the entry point of he application .
       </Summary>
       <List of entry Function>  
	   NA
</List of entry function>
<Database Requirement>  
NA
</database Requirement>
<Other product Dependency> 
 This module requires config file as the input.
 _________Config file model_____________________
 server_name1 or ip1<space>username<space>password<space>port 
 server_name2 or ip2<space>username<space>password<space>port
 server_name3 or ip3<space>username<space>password<space>port  
 Note: Please avoid empty line in the config file
 
 This module uses Bridge objects to create connections to the server
 
</Other product Dependency>
< packaging>
This Module connects the User interface and Bridge.  
</packaging>
<other>
NA
</other>
*/
import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.awt.*;
import javax.swing.*;
class JobConsole extends JFrame
{
public JComboBox servers;
public JTextField command;
public JButton submit;
public JTextArea output;
public JScrollPane sp ;
public String path;
public ArrayList<Server> logins;
public ArrayList<Bridge> shuriken;

public JobConsole()
{
	command=new JTextField();
	submit=new JButton("Submit");
	output=new JTextArea();
	sp=new JScrollPane(output);
	Scanner n=null;
	logins=new ArrayList<Server>();
	shuriken=new ArrayList<Bridge>();
JFileChooser chooser = new JFileChooser();
      chooser.setDialogTitle("Choose your login file");
	  int returnVal = chooser.showOpenDialog(null);
      if(returnVal == JFileChooser.APPROVE_OPTION) {
	  try{
n=new Scanner(new File(chooser.getSelectedFile().getAbsolutePath()));
}
catch(Exception e)
{
	System.out.println(e.getMessage());
}
}
while(n.hasNextLine())
{
	Server login=new Server();
	String server[]=n.nextLine().split(" ");
	System.out.println(server[0]+"   "+server[1]+"  "+server[2]+"  "+server[3]);
	login.server=server[0];login.user=server[1];login.password=server[2];login.port=server[3];
	logins.add(login);
	Bridge rasegan=new Bridge(server[1],server[2],server[0],server[3]);
	shuriken.add(rasegan);
}
String tmp[] =new String[logins.size()];
for(int i=0;i<logins.size();i++)
{
	tmp[i]=(logins.get(i)).server;
	
}
servers=new JComboBox(tmp);
submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
             int option=servers.getSelectedIndex();
			 System.out.println(option);
			 Bridge act=shuriken.get(option);
			 output.setText(act.executeCommand(command.getText()));
        }
		});
setLayout(new GridLayout(4,1));
add(servers);
add(command);
add(submit);
add(sp);
}
public static void main(String args[])
{
	JobConsole sa=new JobConsole();
	SwingConsole.run(sa,500,500);
}

}
