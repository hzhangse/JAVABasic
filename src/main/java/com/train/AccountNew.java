package com.train;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AccountNew {
	String name;
	// float amount;
	BusyFlag flag = new BusyFlag();
	// ʹ��һ��Mapģ��־ô洢
	static Map storage = new HashMap();
	static {
		storage.put("John", new Float(1000.0f));
		storage.put("Mike", new Float(800.0f));
	}
	static Map accounts = Collections.synchronizedMap(new HashMap());

	private AccountNew(String name) {
		this.name = name;
		// this.amount = ((Float)storage.get(name)).floatValue();
	}

	public synchronized static AccountNew getAccount(String name) {
		if (accounts.get(name) == null)

			accounts.put(name, new AccountNew(name));
		return (AccountNew) accounts.get(name);
	}

	public synchronized void deposit(float amt) {
		float amount = ((Float) storage.get(name)).floatValue();
		storage.put(name, new Float(amount + amt));
	}

	public synchronized void withdraw(float amt)
			 {
		float amount = ((Float) storage.get(name)).floatValue();
		if (amount >= amt)

			amount -= amt;
		else
			System.out.println("����");
		storage.put(name, new Float(amount));
	}

	public float getBalance() {
		float amount = ((Float) storage.get(name)).floatValue();
		return amount;
	}

	public void lock() {
		flag.getBusyFlag();
	}

	public void unlock() {
		flag.freeBusyFlag();
	}
}
