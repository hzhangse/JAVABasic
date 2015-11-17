package com.train.jaxp;


import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

/**
 * The reader will filter any invalid characters defined in XML 1.0 spec.
 * 
 * 
 */
public class XMLFilterReader extends FilterReader {

	private static final byte[] CHARS = new byte[0x10FFFF+1];
	
	static {
		CHARS[0x9] = 1;
		CHARS[0xA] = 1;
		CHARS[0xD] = 1;
		Arrays.fill(CHARS, 0x20, 0xD7FF, (byte) 1);
		Arrays.fill(CHARS, 0xE000, 0xFFFD, (byte) 1);
	//	Arrays.fill(CHARS, 0x7F, 0x84, (byte) 0);
	//	Arrays.fill(CHARS, 0x86, 0x9F, (byte) 0);
		Arrays.fill(CHARS, 0x10000, 0x10FFFF, (byte) 1);
	}
	
	/**
	 * Constructor
	 * 
	 * @param in
	 */
	public XMLFilterReader(Reader in) {
		super(in);
	}

	/**
	 * 
	 */
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		int read = super.read(cbuf, off, len);

		if (read == -1) {
			return -1;
		}
		int currPos = off - 1;

		for (int readPos = off; readPos < off + read; readPos++) {
//			if (XMLChar.isValid(cbuf[readPos])) {
			//System.out.print(cbuf[readPos]);
			if (isValidXMLChar(cbuf[readPos])) {
				currPos++;
			} else {
				// Bypass invalid char
				continue;
			}
			// Move character to fill in possible gap
			if (currPos < readPos) {
				cbuf[currPos] = cbuf[readPos];
			}
		}

		return currPos - off + 1;
	}
	
	public static boolean isValidXMLChar(int ch) {
		return (ch <= 0xFFFF && CHARS[ch] != 0) || (ch >= 0x10000 && ch <= 0x10FFFF);
	}
	
}
