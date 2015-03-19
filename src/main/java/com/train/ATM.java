package com.train;


import java.util.HashMap;
import java.util.Map;

class Account {
	String name;
	// float amount;

	// ʹ��һ��Mapģ��־ô洢
	static Map storage = new HashMap();
	static {
		storage.put("John", new Float(1000.0f));
		storage.put("Mike", new Float(800.0f));
	}

	public Account(String name) {
		// System.out.println("new account:" + name);
		this.name = name;
		// this.amount = ((Float)storage.get(name)).floatValue();
	}

	public synchronized void deposit(float amt) {
		float amount = ((Float) storage.get(name)).floatValue();
		storage.put(name, new Float(amount + amt));
	}

	public synchronized void withdraw(float amt) {
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
}

public class ATM {
	Account acc;

	// ��Ϊ��ʾ��ʡ����������֤
	public boolean login(String name) {
		if (acc != null)
			throw new IllegalArgumentException("Already logged in!");
		acc = new Account(name);
		return true;
	}

	public void deposit(float amt) {
		acc.deposit(amt);
	}

	public void withdraw(float amt) {
		acc.withdraw(amt);
	}

	public float getBalance() {
		return acc.getBalance();
	}

	public void logout() {
		acc = null;
	}
}
