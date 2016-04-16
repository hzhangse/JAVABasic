package com.train.vm;
import java.util.ArrayList;
import java.util.List;

/**
 * VM Args：-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * 
 */
class OOMObject {
}

public class HeapOOM {

	public static void main(String[] args) throws InterruptedException {
		List<OOMObject> list = new ArrayList<OOMObject>();

		while (true) {
			//Thread.sleep(1);
			System.out.println();
			list.add(new OOMObject());
		}
	}
}