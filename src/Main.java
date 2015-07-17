public class Main
{			
	public static void main(String[] args)
	{
		boolean running = true;
		
		Display_Frame df = new Display_Frame();
		Thread th = new Thread(df.g); 
		th.start();
		while(running){
			df.g.run();
		}
	}
}
