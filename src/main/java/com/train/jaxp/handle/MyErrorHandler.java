package com.train.jaxp.handle;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class MyErrorHandler implements ErrorHandler {

	public MyErrorHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void warning(SAXParseException exception) throws SAXException {
		System.err.println("warn:" + exception.getLocalizedMessage());

	}

	@Override
	public void error(SAXParseException exception) throws SAXException {
		System.err.println("error:" + exception.getLocalizedMessage());

	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		System.err.println("fatal:" + exception.getLocalizedMessage());

	}

}
