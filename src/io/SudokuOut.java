package io;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SudokuOut {
	static int[] defaultGame =	{ 1, 6, 0, 2, 5, 3, 9, 8, 4,
			  8, 2, 5, 9, 0, 6, 1, 0, 7,
			  3, 9, 4, 8, 1, 7, 0, 5, 6,
			  4, 0, 1, 3, 9, 5, 6, 2, 0, 
			  6, 5, 0, 7, 0, 1, 3, 4, 9,
			  9, 3, 8, 4, 6, 2, 7, 0, 5,
			  0, 1, 9, 5, 0, 4, 8, 6, 3,
			  7, 4, 0, 1, 3, 8, 5, 9, 0,
			  5, 8, 3, 6, 2, 9, 4, 7, 1 };
	
	public static void main( String[] args ){
		try{
			FileOutputStream fos = new FileOutputStream("numbers.txt");
			DataOutputStream dos = new DataOutputStream(fos);
			
			for( int i = 0; i < defaultGame.length; ++i ){
				dos.writeInt( defaultGame[i] );
			}
		}catch( FileNotFoundException e ){
			e.printStackTrace();
		}catch( IOException e ){
			e.printStackTrace();
		}
	}
}