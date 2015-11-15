package ioexample;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MemoryGameImpl implements IGame, ActionListener {
	//=========================================================================
	//members

	/** A tabla merete. */
	private int gameSize = 2;
	/** A felhasznalhato szinek. */
	private String[] items = 
						{ "Cat", "Dog", "Bug", "Shark", "Bear", "Wolf",
						  "Chicken", "Cow" };
	/** A kitalalando elemek sorfolytonos abrazolasban */
	private int[] itemsOfTheTable = null;
	/** A "kartyak". */
	private JButton[] cards;
	/** A megengedett jatekmeretek. */
	private String[] sizes = { "2x2", "4x4" };
	/** Az utoljara felforditott ket elem. */
	private int[] lastPair = new int[]{ -1, -1 };
	
	//=========================================================================
	//constructors
	
	//=========================================================================
	//inner classes
	public class AskSizeDialog extends JDialog implements ActionListener {
		//=====================================================================
		//members
		/** A dialogusablak szovegcimkeje. */
		private final JLabel sizeLabel = new JLabel( "Board size: " );
		/** Biztonsagos adatbevitelt segito valasztodoboz. */
		private final JComboBox sizeBox = new JComboBox( sizes );
		/** A jatekter meretet beallito gomb. */
		private final JButton okButton = new JButton( "OK" );
		
		//=====================================================================
		//contructors
		public AskSizeDialog( int size){
			super();
			initComponents();
			
			setTitle( "Adjust game board size" );
			
			//allitsuk az aktualis tablameretre a combo boxot
			if( size == 4 ) sizeBox.setSelectedIndex( 1 );
		}
		
		//=====================================================================
		//implemented interfaces
		public void actionPerformed( ActionEvent e ) {
			if( e.getActionCommand().equals( "ok" ) ){
				int gameSize = sizeBox.getSelectedIndex() == 0 ? 2 : 4;
				setGameSize( gameSize );
				this.dispose();			//bezarja a dialogust
			}
		}
		
		//=====================================================================
		//protected & private functions
		protected void initComponents(){
			
			JPanel settingsPanel = new JPanel();
			settingsPanel.setLayout( new FlowLayout() );
			
			okButton.setActionCommand( "ok" );
			okButton.addActionListener( this );
			
			settingsPanel.add( sizeLabel );
			settingsPanel.add( sizeBox );
			settingsPanel.add( okButton );
			
			getContentPane().add( settingsPanel );
			
			setPreferredSize( new Dimension( 200, 80 ) );
			
			setResizable( false );
			
			pack();
		}
	}
	
	//=========================================================================
	//public functions
	public int getGameSize(){
		return gameSize;
	}
	
	public void setGameSize( int size ){
		gameSize = size;
	}
	
	//=========================================================================
	//private & protected functions
	/**
	 * Ellatja a gomb lenyomasaval kapcsolatos teendoket.
	 * 
	 * @param buttonIdx a megnyomott gomb indexe
	 */
	protected void buttonClicked( int buttonIdx ){
		if( buttonIdx < 0 || cards.length <= buttonIdx ) return;

		//mutassuk meg a kartyat
		cards[buttonIdx].setText( items[itemsOfTheTable[buttonIdx]] );
		
		if( lastPair[0] == -1 ){
			lastPair[0] = buttonIdx;
		}else if( lastPair[0] > -1 && lastPair[0] != buttonIdx ){
			if( lastPair[1] == -1 ){
				lastPair[1] = buttonIdx;
			}else{
				if( itemsOfTheTable[lastPair[0]] != 
							itemsOfTheTable[lastPair[1]] ){
					cards[lastPair[0]].setText( "" );
					cards[lastPair[1]].setText( "" );
				}else{
					cards[lastPair[0]].setEnabled( false );
					cards[lastPair[1]].setEnabled( false );
				}
				lastPair[0] = buttonIdx;
				lastPair[1] = -1;
			}
		}
	}

	
	/**
	 * Ez a fuggveny keverne meg az elemeket.
	 * 
	 * @return
	 */
	private int[] getShuffledItems(){
		if( gameSize == 2 ) return new int[]{ 0, 1, 1, 0 };
		
		if( gameSize == 4 ) 
			return new int[]{ 6, 2, 7, 5, 4, 1, 3, 0, 4, 0, 2, 3, 7, 1, 5, 6 };
		
		return null;
	}
	//=========================================================================
	//implemented interfaces
	public JPanel getGamePanel() {
		lastPair = new int[]{ -1, -1 };
		//gombok peldanyositasa
		cards = new JButton[gameSize*gameSize];
		for( int i = 0; i < cards.length; ++i ){
			cards[i] = new JButton();
			cards[i].setSize( new Dimension( 16, 16 ) );
			cards[i].setActionCommand( String.valueOf( i ) );
			cards[i].addActionListener( this );
		}
		
		JPanel gamePanel = new JPanel();
		//parameterek gridlayout horizontalis merete, vertikalis merete, es a 
		//komponensek kozti vertikalis es horizontalis resek
		GridLayout gameLayout = new GridLayout( gameSize, gameSize, 5, 5 );
		gamePanel.setLayout( gameLayout );
		//gamePanel.setPreferredSize( frame.getPreferredSize() );

		for( int i = 0; i < cards.length; ++i ){
			gamePanel.add( cards[i] );
		}
		
		itemsOfTheTable = getShuffledItems();
		
		return gamePanel;
	}

	public JDialog getSettingsDialog() {
		AskSizeDialog dialog = new AskSizeDialog( gameSize );
		return dialog;
	}
	
	public String getGameName(){
		return "Memory game";
	}

	public void actionPerformed( ActionEvent e ) {
		try{
			Integer buttonIdx = Integer.valueOf( e.getActionCommand() );
			buttonClicked( buttonIdx );
		}catch( NumberFormatException ex ){
			//unexpected action command, do nothing
		}
	}
}