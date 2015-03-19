package com.train;


public class ATMNew {
	AccountNew acc;

	// ��Ϊ��ʾ��ʡ����������֤
	public synchronized boolean login(String name) {
		if (acc != null)

			throw new IllegalArgumentException("Already logged in!");
		acc = AccountNew.getAccount(name);
		acc.lock();
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

	public synchronized void logout() {
		acc.unlock();
		acc = null;
	}
}
