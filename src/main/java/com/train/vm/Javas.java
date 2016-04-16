package com.train.vm;

import java.util.ArrayList;
import java.util.List;

public class Javas {
	public static void main(String[] args)
	{
		List<List<Integer>> dataList = new ArrayList<List<Integer>>();
		for(int i=0;i<5000;i++)
		{
			List<Integer> intList = new ArrayList<Integer>();
			dataList.add(intList);
		}
		
		while(true)
		{
			for(List<Integer> list : dataList)
			{
				for(Integer data : list)
				{
					System.out.println("=======");
				}
				//list.clear();
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
