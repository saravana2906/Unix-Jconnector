/*  <Title>  
                      	Unix JConnector
      </Title> 
      <Summary> 
       		User interface should not start from the main dispatcher thread, This module Swingconsole helps us to start from here.
       </Summary>
       <List of entry Function>  
	   run -JFrame,width,height
</List of entry function>
<Database Requirement>  
NA
</database Requirement>
<Other product Dependency> 
NA
</Other product Dependency>
< packaging>
This will be used by JobConsole module which to start the User interface.
</packaging>
<other>
NA
</other>
*/

import javax.swing.*;
import java.awt.*;
class SwingConsole {
public static void run(final JFrame f, final int width, final int height) {
SwingUtilities.invokeLater(new Runnable() {
public void run() {
f.setTitle(f.getClass().getSimpleName());
f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
f.setSize(width, height);
f.setVisible(true);
}
});
}
}