package com.train.jaxp.handle;

import org.xml.sax.DTDHandler;
import org.xml.sax.SAXException;

public class MyDTDHandler implements DTDHandler {

	public MyDTDHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void notationDecl(String name, String publicId, String systemId)
			throws SAXException {
		// TODO Auto-generated method stub

	}

	@Override
	public void unparsedEntityDecl(String name, String publicId,
			String systemId, String notationName) throws SAXException {
		// TODO Auto-generated method stub

	}

}
