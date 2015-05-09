import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class DisplayFrame{
	
	static boolean levelChanged = false;
	JFrame j = new JFrame();
	Graphics dbGraphics;
		
	DisplayPanel d;
			
	public DisplayFrame(){
		j.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		j.setUndecorated(true);
				
		d = new DisplayPanel();
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
