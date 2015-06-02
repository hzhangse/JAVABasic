package com.train.classloader;

public class CallPrinter extends AbstractCallPrinter{
    IPrinter printer;
	
	public void setPrinter(IPrinter printer) {
		this.printer = printer;
		this.printer.print();
	}
	
	
     
}
