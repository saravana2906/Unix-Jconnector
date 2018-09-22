/*  <Title>  
                      	Unix JConnector
      </Title> 
      <Summary> 
       		Bridge Module helps in establishing a connection and also in executing given command by the user in respective server
       </Summary>
       <List of entry Function>  
	   executeCommand function 
	   MyUserInfo- static inner class Needs to be implement which will be used by Jsch library function to report the errors to the users.
</List of entry function>
<Database Requirement>  
NA
</database Requirement>
<Other product Dependency> 
 Jsch jar 
</Other product Dependency>
< packaging>
          Bridge class can be instantiated by using (all are string parameters)parameters 
		  user-username,
		  pass-password ,
		  ip-ip of the server or name of the server,
		  port - port number to which it needs to connect
</packaging>
<other>
It also contains constructor which can be used for login to servers using their public key 
</other>
*/
import com.jcraft.jsch.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.Scanner;
import java.io.File;
class Bridge
{
public JSch jsch;
public Session session;
//constructor to create Bridge object with username and password
public Bridge(String user,String pass,String ip,String port)
{
	try
	{
		jsch=new JSch();
session=jsch.getSession(user,ip,Integer.parseInt(port));
session.setPassword(pass);
java.util.Properties config = new java.util.Properties();
config.put("StrictHostKeyChecking", "no");
session.setConfig(config);
UserInfo ui=new MyUserInfo();
 session.setUserInfo(ui);
session.connect();
	}
	catch(Exception e)
{
	e.printStackTrace();
	
}
	
}
public Bridge(String key,String pass,String user,String ip,String port)
{
try
{
jsch=new JSch();
jsch.addIdentity(key,pass);
session=jsch.getSession(user,ip,Integer.parseInt(port));
  UserInfo ui=new MyUserInfo();
 session.setUserInfo(ui);
      session.connect();
}
catch(Exception e)
{
	e.printStackTrace();
	
}
}
//Execute Method executes each command by opening channel each time
public String executeCommand(String cm)
{
	String out="";
	System.out.println("In executing "+ cm);
	Channel channel=null;
	try{
		 channel=session.openChannel("exec");
		((ChannelExec)channel).setCommand(cm);
		channel.setInputStream(null);
		((ChannelExec)channel).setErrStream(System.err);
		InputStream in=channel.getInputStream();
		channel.connect();
		byte[] tmp=new byte[1024];
		while(true){
			while(in.available()>0){
			int i=in.read(tmp, 0, 1024);
			if(i<0)break;
			System.out.print(new String(tmp, 0, i));
			out=out+new String(tmp, 0, i);
        }
        if(channel.isClosed()){
          if(in.available()>0) continue; 
          System.out.println("exit-status: "+channel.getExitStatus());
          break;
        }
		}
	}
	
		
        catch(Exception ee){System.out.println("Error in Executing while  "+ cm );}
	
        channel.disconnect();	
		return out;
}
  public static class MyUserInfo implements UserInfo, UIKeyboardInteractive{
    public String getPassword(){ return null; }
    public boolean promptYesNo(String str){
      
	   return true;
    }
  
    String passphrase;
    JTextField passphraseField=(JTextField)new JPasswordField(20);

    public String getPassphrase(){ return passphrase; }
    public boolean promptPassphrase(String message){
      
	  return true;
    }
    public boolean promptPassword(String message){ return true; }
    public void showMessage(String message){
      JOptionPane.showMessageDialog(null, message);
    }
    final GridBagConstraints gbc = 
      new GridBagConstraints(0,0,1,1,1,1,
                             GridBagConstraints.NORTHWEST,
                             GridBagConstraints.NONE,
                             new Insets(0,0,0,0),0,0);
    private Container panel;
    public String[] promptKeyboardInteractive(String destination,
                                              String name,
                                              String instruction,
                                              String[] prompt,
                                              boolean[] echo){
      panel = new JPanel();
      panel.setLayout(new GridBagLayout());

      gbc.weightx = 1.0;
      gbc.gridwidth = GridBagConstraints.REMAINDER;
      gbc.gridx = 0;
      panel.add(new JLabel(instruction), gbc);
      gbc.gridy++;

      gbc.gridwidth = GridBagConstraints.RELATIVE;

      JTextField[] texts=new JTextField[prompt.length];
      for(int i=0; i<prompt.length; i++){
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.weightx = 1;
        panel.add(new JLabel(prompt[i]),gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1;
        if(echo[i]){
          texts[i]=new JTextField(20);
        }
        else{
          texts[i]=new JPasswordField(20);
        }
        panel.add(texts[i], gbc);
        gbc.gridy++;
      }

      if(JOptionPane.showConfirmDialog(null, panel, 
                                       destination+": "+name,
                                       JOptionPane.OK_CANCEL_OPTION,
                                       JOptionPane.QUESTION_MESSAGE)
         ==JOptionPane.OK_OPTION){
        String[] response=new String[prompt.length];
        for(int i=0; i<prompt.length; i++){
          response[i]=texts[i].getText();
        }
	return response;
      }
      else{
        return null;  // cancel
      }
    }
  }


}