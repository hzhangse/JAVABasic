package com.train;


class Base
{
	void f()
	{}
}

class Child extends Base
{
	/*void f(int i) //ok
	{}*/
	
	/*
	int f()  // error
	{
		return 1;
	}*/ 
	
	int f(int i) //ok
	{
		return 1;
	}
	
	/*
	private void f() //error
	{}
	*/
}

public class BaseAndChild {

	public static void main(String[] args) {
		
	}

}
