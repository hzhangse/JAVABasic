
package com.train.jdk16.console;

import java.io.Console;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class TestConsole {
	
	public static void main(String[] args) throws Exception{
		Console cnsl = null;
		String name = null;
		int one_million = 1_000_000; 
		
		try (
		                InputStream in = new FileInputStream(
		                                "src");
		                OutputStream out = new FileOutputStream(
		                                "dest"))
		{
			// code
		}
		
		try {
			// creates a console object
			cnsl = System.console();
			
			// if console is not null
			if (cnsl != null) {
				
				// read line from the user input
				name = cnsl.readLine("Name: ");
				
				// prints
				System.out.println("Name entered : "
				                + name);
			}
		} catch (Exception ex) {
			
			// if any error occurs
			ex.printStackTrace();
		}
	}
	
}