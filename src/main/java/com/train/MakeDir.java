package com.train;


import java.io.File;

public class MakeDir {
	public static void main(String[] args) {
		String fileName="c:"+File.separator+"hello";
        File f=new File(fileName);
        f.mkdir();

        String dirName="c:"+File.separator;
        File o=new File(dirName);
        String[] str=o.list();
        for (int i = 0; i < str.length; i++) {
            System.out.println(str[i]);
        }
	}

}
