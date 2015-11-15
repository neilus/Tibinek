package ioexample;

import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * Egyszeru GUI jatek interfesze.
 * 
 * @author kisuf
 *
 */
public interface IGame {
	
	/**
	 * Elkesziti a jatek feluletet.
	 * 
	 * @return visszaadja a jatekteret tartalmazo JPanel objektumot.
	 */
	public JPanel getGamePanel();
	
	/**
	 * A jatek beallitasainak dialogus ablaka kerheto le a metodus
	 * segitsegevel.
	 * 
	 * @return visszaadja a beallitasokat tartalmazo JDialog objektumot.
	 */
	public JDialog getSettingsDialog();
	
	/**
	 * Megadja a jatek nevet.
	 * 
	 * @return visszaadja a jatek nevet tarolo String objektumot.
	 */
	public String getGameName();

}