package ioexample;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Egy egyszeru sudoku jatek implementacio.
 * 
 * @author kisuf
 *
 */
public class SudokuImpl implements IGame{
	//=========================================================================
	//members
	/** A sudoku feladvanyt tartalmazo fajl objektum. */
	protected File source = null;
	/** A jatek tablajan megjelenitett mezok. */
	protected JTextField[] fields = new JTextField[81];
	/** A jatektabla szinezese. */
	protected int[] colors = 		{ 0, 0, 0, 1, 1, 1, 0, 0, 0,
						  0, 0, 0, 1, 1, 1, 0, 0, 0,
						  0, 0, 0, 1, 1, 1, 0, 0, 0,
						  1, 1, 1, 0, 0, 0, 1, 1, 1, 
						  1, 1, 1, 0, 0, 0, 1, 1, 1, 
						  1, 1, 1, 0, 0, 0, 1, 1, 1, 
						  0, 0, 0, 1, 1, 1, 0, 0, 0,
						  0, 0, 0, 1, 1, 1, 0, 0, 0,
						  0, 0, 0, 1, 1, 1, 0, 0, 0, };
	/** Egy egyszeru sudoku feladvany, mint alapertelmezett jatek. */
	protected int[] defaultGame =	{ 0, 6, 0, 2, 5, 3, 9, 8, 4,
						  8, 2, 5, 9, 0, 6, 1, 0, 7,
						  3, 9, 4, 8, 1, 7, 0, 5, 6,
						  4, 0, 1, 3, 9, 5, 6, 2, 0, 
						  6, 5, 0, 7, 0, 1, 3, 4, 9,
						  9, 3, 8, 4, 6, 2, 7, 0, 5,
						  0, 1, 9, 5, 0, 4, 8, 6, 3,
						  7, 4, 0, 1, 3, 8, 5, 9, 0,
						  5, 8, 3, 6, 2, 9, 4, 7, 1 };
	
	//=========================================================================
	//constructors
	
	//=========================================================================
	//inner classes
	public class SudokuSettingsDialog extends JDialog implements ActionListener {
		//=====================================================================
		//members
		/** A dialogusablak szovegcimkeje. */
		private final JLabel pathLabel = new JLabel( "File path: " );
		/** A feladavanyt tartalmazo fajl eleresi utja. */
		private final JTextField pathField = new JTextField();
		/** Tallozast indito gomb. */
		private final JButton selectButton = new JButton( "Select file..." );
		/** A beallitast mento gomb. */
		private final JButton okButton = new JButton( "OK" );
		
		//=====================================================================
		//contructors
		public SudokuSettingsDialog( String path ){
			super();
			initComponents();
			
			setTitle( "Specify game file" );

			if( path != null ) pathField.setText( path );
			
			setDefaultCloseOperation( DISPOSE_ON_CLOSE );
		}
		
		//=====================================================================
		//implemented interfaces
		public void actionPerformed( ActionEvent e ) {
			if( e.getActionCommand().equals( "ok" ) ){
				setFile( pathField.getText() );
				this.dispose();
			}
			if( e.getActionCommand().equals( "select" ) ){
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showOpenDialog( this ) == JFileChooser.APPROVE_OPTION) {
					File f = fileChooser.getSelectedFile();
					pathField.setText( f.getAbsolutePath() );
				}
			}
		}
		
		//=====================================================================
		//protected & private functions
		/**
		 * Inicializalja a dialogus ablak elemeit.
		 */
		protected void initComponents(){
			
			JPanel settingsPanel = new JPanel();
			settingsPanel.setLayout( new FlowLayout() );
			
			okButton.setActionCommand( "ok" );
			okButton.addActionListener( this );
			
			selectButton.setActionCommand( "select" );
			selectButton.addActionListener( this );
			
			pathField.setPreferredSize( new Dimension( 200, 20 ) );
			
			settingsPanel.add( pathLabel );
			settingsPanel.add( pathField );
			settingsPanel.add( selectButton );
			settingsPanel.add( okButton );
			
			getContentPane().add( settingsPanel );
			
			setPreferredSize( new Dimension( 430, 80 ) );
			
			setResizable( false );
			
			pack();
		}
	}
	
	//=========================================================================
	//public functions
	/**
	 * Beallitja a sudoku forrasfajljat a megadott eleresi ut alapjan.
	 */
	public void setFile( String filePath ){
		source = new File( filePath );
	}
	
	//=========================================================================
	//protected & private functions
	/**
	 * Letrehozza (ha tudja fajlbol beolvassa) a sudoku feladvanyt. Ha nincs 
	 * feladvanyt tartalmazo fajl, akkor az alapertelmezett jatekot adja 
	 * vissza.
	 * 
	 * @return visszater a sudoku feladvanyt sorfolytonosan tarolo vektorral
	 */
	private Vector<Integer> generateSudoku(){
		Vector<Integer> values = new Vector<Integer>();
		
		if( source == null ){
			for( int i = 0; i < defaultGame.length; ++i ){
				values.add( defaultGame[i] );
			}
		}else{
			return readFile();
		}
		
		return values;
	}
	
	/**
	 * Egy int-eket tartalmazo fajl elemeit beolvassa.
	 * 
	 * @return visszaadja a beolvasott szamok vektorat.
	 */
	private Vector<Integer> readFile(){
		Vector<Integer> numbers = new Vector<Integer>();
		
		return numbers;
	}
	//=========================================================================
	//imlpemented interfaces
	public String getGameName() { return "Sudoku"; }

	public JPanel getGamePanel() {
		JPanel gamePanel = new JPanel();
		GridLayout gameLayout = new GridLayout( 9, 9, 2, 2 );
		gamePanel.setLayout( gameLayout );
		
		Vector<Integer> actSudoku = generateSudoku();
		
		if( actSudoku == null ){
			JOptionPane.showMessageDialog( null,
                    "Couldn't read the specified file: \n" + 
                    source.getAbsolutePath() + "!", 
                    "Error!", JOptionPane.ERROR_MESSAGE );
			return gamePanel;
		}
		
		for( int i = 0; i < fields.length; ++i ){
			int number = actSudoku.get( i );
			fields[i] = new JTextField();
			fields[i].setBackground( colors[i] == 0 ? Color.WHITE 
													: Color.LIGHT_GRAY );
			fields[i].setOpaque( true );
			fields[i].setHorizontalAlignment( JTextField.CENTER );
			if( number > 0 ){
				fields[i].setText( "" + number );
				fields[i].setEditable( false );
				fields[i].setFont( new Font( fields[i].getFont().getFamily(), 
											 Font.BOLD,
											 fields[i].getFont().getSize() ) );
			}
			gamePanel.add( fields[i] );
		}
		
		return gamePanel;
	}

	public JDialog getSettingsDialog() {
		return new SudokuSettingsDialog( source == null ? null 
										 : source.getAbsolutePath() );
	}

}