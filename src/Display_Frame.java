import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Display_Frame{
	
	static boolean levelChanged = false;
	JFrame j = new JFrame();

	Graphics dbGraphics;
		
	Game_Logic g;
	JPanel d;
			
	public Display_Frame(){
//		j.setExtendedState(JFrame.MAXIMIZED_BOTH);
		j.setUndecorated(true);
			
		g = new Game_Logic();
        j.add(g.getJPanel());

        	j.addMouseListener(g);
        	j.addMouseMotionListener(g);
			j.addKeyListener(g);
			
			j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			j.pack();

			j.setVisible(true);
			//System.out.println(j.getWidth()+" "+j.getHeight());

	}
}
