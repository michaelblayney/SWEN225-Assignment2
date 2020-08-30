package code;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SuggestionConfirmingPanel extends JPanel {
	Player currentPlayer;
	Player storedCurrentPlayer;
	JLabel text;
	
	
	
	public SuggestionConfirmingPanel(){
		//text= new JLabel("Please click on the card you want to show to ");
		text = new JLabel();
		add(text);
		
	}
	
	public void updatePlayerName(Player p){
		currentPlayer = p;
	}
	
	public void updateText(String s){
		//append text
		text.setText(text.getText()+"\n"+s);
	}
	public void resetText(){
		text.setText("Please click on the card you want to show to "+((GUI) this.getParent().getParent().getParent().getParent().getParent().getParent().getParent()).getGame().getStoredCurrentPlayer().getIRLname());
	}
}
