package com.train;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VisitError {

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);

		for (Iterator<Integer> iter = list.iterator(); iter.hasNext();) {
			int i = iter.next();
			if (i == 3) {
				list.remove(i);
				//iter.remove();
			}
		}
	}
    
}
