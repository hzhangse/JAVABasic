package com.train;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SwingLister {
	static final int WIDTH = 300;
	static final int HEIGHT = 200;
	static JTextField l = new JTextField(20);

	public static void main(String[] args) {
		JFrame jf = new JFrame("���Գ���");
		jf.setSize(WIDTH, HEIGHT);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		jf.setContentPane(contentPane);
		JButton b = new JButton("����ı����е���Ϣ");
		contentPane.add(l, "North");
		contentPane.add(b, "South");
		ActionListener ac = new ActionHandler();// ����һ���¼�������
		b.addActionListener(ac); // ���¼�Դע��
	}
}

// ����ʵ���¼�������
class ActionHandler implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		new SwingLister().l.setText("");
	}
}
