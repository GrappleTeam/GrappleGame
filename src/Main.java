public class Main
{			
	public static void main(String[] args)
	{
		boolean running = true;
		
		DisplayFrame df = new DisplayFrame();
		Thread th = new Thread(df.d); 
		th.start();
		while(running){
			df.d.run();
		}
	}
}
