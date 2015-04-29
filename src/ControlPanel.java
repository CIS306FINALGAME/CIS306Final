import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Class Name: Control Panel
 * Purpose: 
 * 		The Control Panel is used to provide a means of overall application and game control. This panel houses the buttons to start,
 * 		pause, and exit the game. 
 * 
 * @author Travis
 *
 */

public class ControlPanel extends JPanel {
	
	protected JButton startButton;
	protected JButton pauseButton;
	protected JButton exitButton;
	
	protected JLabel godModeLabel;
	
	private GamePanel gamePanelRef;
	
	//Constructor
	public ControlPanel(GamePanel pGamePanelRef)
	{	
		//Get reference to gamePanel
		gamePanelRef = pGamePanelRef;
		
		//Setup Buttons
		startButton = new JButton("Start");
		pauseButton = new JButton("Pause");
		exitButton = new JButton("Exit");
		
		//Add buttons to panel
		this.add(startButton);
		this.add(pauseButton);
		this.add(exitButton);
		
		//Create a label to show when god mode is enabled...godMode disabled by default, so we hide it
		godModeLabel = new JLabel("God Mode Enabled");
		this.add(godModeLabel, BorderLayout.WEST);
		godModeLabel.setVisible(false);
		
		//Add button listeners
		startButton.addActionListener(new buttonListener());
		pauseButton.addActionListener(new buttonListener());
		exitButton.addActionListener(new buttonListener());
		
		pauseButton.setEnabled(false);
		
		this.setBackground(Color.GRAY);
		this.setVisible(true);
		
	}
	
	//Set the label visible or not based on arg
	public void setGodModeLabel(boolean pEnabled)
	{
		if(pEnabled)
		{
			godModeLabel.setVisible(true);
		}
		else 
		{
			godModeLabel.setVisible(false);
		}
	}
	
	/**
	 * Class Name: ButtonListener
	 * Purpose: 
	 * 		Determine which button user pressed and call appropriate functions.
	 * 
	 * 
	 * @author Travis
	 */
	private class buttonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
		
			//If user presses start, create a new thread and start it. Enable/Disable buttons appropriately.
			if(e.getSource()==startButton)
			{
				gamePanelRef.startGame();
				
				Thread gameThread = new Thread(gamePanelRef);
				gameThread.start();
				
				startButton.setEnabled(false);
				pauseButton.setEnabled(true);
			}
			
			//If user presses pause, stop the thread that is running. Enable/Disable buttons appropriately.
			else if (e.getSource()==pauseButton) 
			{
				gamePanelRef.pauseGame();
				
				startButton.setEnabled(true);
				pauseButton.setEnabled(false);
			}
			
			//If user presses exit, exit the entire application. 
			else if (e.getSource()==exitButton) 
			{
				gamePanelRef.exitGame();
			}
			
		}
		
	}
	
	

}
