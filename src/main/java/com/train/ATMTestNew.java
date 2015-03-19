package com.train;




public class ATMTestNew {
	private static final int NUM_OF_ATM = 10;

	public static void main(String[] args) {
		ATMTestNew tester = new ATMTestNew();

		final Thread thread[] = new Thread[NUM_OF_ATM];
		final ATMNew atm[] = new ATMNew[NUM_OF_ATM];
		for (int i = 0; i < 5; i++) {
			atm[i] = new ATMNew();
			thread[i] = new Thread(tester.new Runner(atm[i]));
			thread[i].start();
		}

	}

	class Runner implements Runnable {
		ATMNew atm;

		Runner(ATMNew atm) {
			this.atm = atm;
		}

		public void run() {
			atm.login("John");
			// ��ѯ���
			float bal = atm.getBalance();
			try {
				Thread.sleep(1); // ģ���˴Ӳ�ѯ��ȡ��֮��ļ��
			} catch (InterruptedException e) {
			}

			System.out.println("Your balance is:" + bal);
			System.out.println("withdraw:" + bal * 0.8f);
			atm.withdraw(bal * 0.8f);
			System.out.println("deposit:" + bal * 0.8f);
			atm.deposit(bal * 0.8f);
		}
	}
}
