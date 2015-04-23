import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class ControlPanel extends JPanel {
	
	protected JButton startButton;
	protected JButton pauseButton;
	protected JButton exitButton;
	
	private GamePanel gamePanelRef;
	
	public ControlPanel(GamePanel pGamePanelRef)
	{		
		gamePanelRef = pGamePanelRef;
	
		startButton = new JButton("Start");
		pauseButton = new JButton("Pause");
		exitButton = new JButton("Exit");
		
		this.add(startButton);
		this.add(pauseButton);
		this.add(exitButton);
		
		
		startButton.addActionListener(new buttonListener());
		pauseButton.addActionListener(new buttonListener());
		exitButton.addActionListener(new buttonListener());
		
		
		pauseButton.setEnabled(false);
		
		
		this.setBackground(Color.GRAY);
		this.setVisible(true);
		
	}
	
	
	private class buttonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		
			
			if(e.getSource()==startButton)
			{
				gamePanelRef.startGame();
				//Create a new thread in which is the drawing Panel
				//You must pass the drawingPanel reference int
				Thread gameThread = new Thread(gamePanelRef);
				//In order to tell the thread to run its run method, call the start method
				gameThread.start();
				//Disable the start button
				startButton.setEnabled(false);
				pauseButton.setEnabled(true);
			}
			
			else if (e.getSource()==pauseButton) 
			{
				gamePanelRef.pauseGame();
				
				startButton.setEnabled(true);
				pauseButton.setEnabled(false);
			}
			
			else if (e.getSource()==exitButton) 
			{
				gamePanelRef.exitGame();
			}
			
		}
		
	}
	
	

}
