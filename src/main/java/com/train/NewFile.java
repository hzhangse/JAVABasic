package com.train;


import java.io.File;

public class NewFile {
	public static void main(String[] args) {
		File f=new File("c:\\hello.txt");
        try{
            f.createNewFile();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("could not create the file.");
        }
        finally
        {
        	f = null;
        }

	}

}
