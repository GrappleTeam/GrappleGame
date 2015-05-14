import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Display_Frame{
	
	static boolean levelChanged = false;
	JFrame j = new JFrame();
	Graphics dbGraphics;
		
	Display_Panel d;
			
	public Display_Frame(){
		j.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		j.setUndecorated(true);
				
		d = new Display_Panel();
		d.setPreferredSize(new Dimension(900, 600));
        j.add(d);
		
		
        	j.addMouseListener(d);
        	j.addMouseMotionListener(d);
			j.addKeyListener(d);
			
			j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			j.pack();

			j.setVisible(true);
			//System.out.println(j.getWidth()+" "+j.getHeight());

	}
}
