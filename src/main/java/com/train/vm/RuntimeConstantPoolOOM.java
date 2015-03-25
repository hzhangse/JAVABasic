package com.train.vm;

import java.util.ArrayList;
import java.util.List;

/**
 * VM Args：-XX:PermSize=10M -XX:MaxPermSize=10M
 * 
 * @author zzm
 */
public class RuntimeConstantPoolOOM {

	public static void main(String[] args) {
		// 使用List保持着常量池引用，压制Full GC回收常量池行为
		List<String> list = new ArrayList<String>();
		try{
		// 10M的PermSize在integer范围内足够产生OOM了
		int i = 0;
		while (true)
		{
			list.add(String.valueOf(i++).intern());
		}}
		catch (OutOfMemoryError error){
			error.printStackTrace();
		}
	//	list = new ArrayList<String>();
	}
}