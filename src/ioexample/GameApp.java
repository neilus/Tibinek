package ioexample;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Egyszeru memoriajatek felfordithato kartyakkal. A jatek lenyege a szinparok
 * egymas utan felforditasa.
 * 
 * @author kisuf
 *
 */
public class GameApp implements ActionListener {
	//=========================================================================
	//members
	/** A GridLayoutExample foablaka. */
	private final JFrame frame = new JFrame();
	/** A menupontok. */
	private JMenu setGameMenu = new JMenu( "Select game" );
	private JMenuItem newGameMenuItem = new JMenuItem( "New game" );
	private JMenuItem setGameSizeMenuItem = new JMenuItem( "Game settings" );
	/** A jatek implementacioja */
	private IGame[] games = new IGame[2];
	private int actGameIdx = 0;
	
	//=========================================================================
	//constructors
	public GameApp(){
		games[0] = new MemoryGameImpl();
		games[1] = new SudokuImpl();
		
		frame.setJMenuBar( createMenu() );
		frame.setPreferredSize( new Dimension( 400, 400 ) );
		frame.setTitle( "Games - " + games[actGameIdx].getGameName() );
		frame.setResizable( false );
	}
	
	//=========================================================================
	//implemented interfaces
	public void actionPerformed( ActionEvent e ) {
		String command = e.getActionCommand();
		if( command.equals( "new game" ) ){
			frame.getContentPane().removeAll();
			frame.getContentPane().add( games[actGameIdx].getGamePanel() );
			frame.getContentPane().validate();
		}
		if( command.equals( "game settings" ) ){
			JDialog dialog = games[actGameIdx].getSettingsDialog();
			dialog.setModal( true );	//amig a dialogus aktiv a foablak le van
										//tiltva
			dialog.setVisible( true );
		}
		if( command.startsWith( "game_" ) ){
			try{
				actGameIdx = 
					Integer.valueOf( command.substring( command.length() - 1 ) );
				
				frame.setTitle( "Games - " + games[actGameIdx].getGameName() );
				frame.getContentPane().removeAll();
				frame.getContentPane().validate();
				frame.getContentPane().repaint();
			}catch( Exception ex ){
				//a kivetel lenyelheto, nem a jatekvalaszto menupontot nyomtak
				//meg
			}
		}
			
	}
	
	//=========================================================================
	//public functions
	public JFrame getFrame(){
		return frame;
	}
	
	/**
	 * Az alkalmazas main fuggvenye.
	 * 
	 * @param args a parancssori parameterek (nincs szerepuk)
	 */
	public static void main( String[] args ){
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				//allitsuk be a program kinezetet a rendszer altal megszabott
				//kinezetre
				try {
			        UIManager.setLookAndFeel(
			            UIManager.getSystemLookAndFeelClassName() );
			    } 
			    catch (UnsupportedLookAndFeelException e) {
			       e.printStackTrace();
			    }
			    catch (ClassNotFoundException e) {
				   e.printStackTrace();
			    }
			    catch (InstantiationException e) {
				   e.printStackTrace();
			    }
			    catch (IllegalAccessException e) {
				   e.printStackTrace();
			    }

				createAndShowGUI();
			}
		});
	}

	//=========================================================================
	//private & protected functions
	
	/**
	 * A szokasos createAndShowGUI()...
	 */
	protected static void createAndShowGUI() {
		GameApp game = new GameApp();
		JFrame frame = game.getFrame();
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/**
	 * Elkesziti a menut.
	 * 
	 * @return Az elkeszitett menu.
	 */
	private JMenuBar createMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu edit = new JMenu("File");
		edit.setMnemonic('f');
		
		for( int i = 0; i < games.length; ++i ){
			JMenuItem gameMI = new JMenuItem( games[i].getGameName() );
			gameMI.setActionCommand( "game_" + i );
			gameMI.addActionListener( this );
			setGameMenu.add( gameMI );
		}
		
		newGameMenuItem.setActionCommand( "new game" );
		newGameMenuItem.setMnemonic( KeyEvent.VK_N );
		newGameMenuItem.setAccelerator( KeyStroke.getKeyStroke("ctrl N")  );
		newGameMenuItem.addActionListener( this );
		
		setGameSizeMenuItem.setActionCommand( "game settings" );
		setGameSizeMenuItem.setMnemonic( KeyEvent.VK_S );
		setGameSizeMenuItem.setAccelerator( KeyStroke.getKeyStroke("ctrl S")  );
		setGameSizeMenuItem.addActionListener( this );

		edit.add( setGameMenu );
		edit.add( newGameMenuItem );
		edit.add( setGameSizeMenuItem );

		menuBar.add(edit);
		return menuBar;
	}
}