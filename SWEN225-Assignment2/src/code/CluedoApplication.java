package code;

public class CluedoApplication {
	
public static void main(String[] args) throws InterruptedException {
	
	
//		javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                new GUI();
//            }
//        });
		GUI gui = new GUI();
		
		Game game = new Game(gui);
		
	}
	
}
