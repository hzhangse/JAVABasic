package com.train;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class HelloWorldSwing {
	public static void main(String[] args) {
		JFrame frame = new JFrame("���Դ���");// ������������
		JPanel pane = new JPanel();// �����м�����
		frame.setContentPane(pane);// ���м����������ڶ���������
		JButton button = new JButton("����һ�����԰�ť");// ����һ���������
		pane.add(button);// ����������������м�������
		frame.setVisible(true);
	}

}
