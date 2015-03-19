package com.train;


public class StringConnects {
	public static void main(String[] args) {
		System.out.println("before running the free mem is " + Runtime.getRuntime().freeMemory());
		String s = "";
		String newStr = new String("abc");
		StringBuilder builder = new StringBuilder(1000);
        for(int i = 0;i<100;i++)
        {
             s = s + newStr;
        	 //builder.append(newStr);
             if(i%10 == 0)
             {
            	 System.out.println("when running the free mem is " + Runtime.getRuntime().freeMemory());
             }
        }
        //System.out.print(builder.toString());
		
	}

}
