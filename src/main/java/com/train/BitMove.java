package com.train;


public class BitMove {

	public static void main(String[] args) {
		System.out.println(7>>1); //3
		System.out.println(7<<1); //14
		System.out.println(-7>>1); //-4
		System.out.println(-7>>2); //-2
		System.out.println(-6>>1); //-3
		System.out.println(-7<<1); //-14
		
		System.out.println(7>>>1); //3
		System.out.println(-7>>>1); //2147483644
		//System.out.println(7<<<1); //-14

	}

}
