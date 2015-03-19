package com.train;


import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipeUsage1 {
	public static void main(String[] args) throws IOException {
		PipedOutputStream pout = new PipedOutputStream();
		PipedInputStream pin = new PipedInputStream(pout);
		for (int i = 0; i < 100; i++) {
			pout.write((byte) i);
		}
		pout.close();

		int j = 0;
		while ((j = pin.read()) != -1) {
			System.err.println(j);
		}
		pin.close();
	}
}
