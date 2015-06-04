package com.train.jaxp.handle;

import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;

public class MyXMLResolver implements XMLResolver{

	

	@Override
	public Object resolveEntity(String publicID, String systemID,
			String baseURI, String namespace) throws XMLStreamException {
		 if (publicID.equals("bookStore.dtd")) {
             return this.getClass().getResourceAsStream("../resources/bookStore.dtd");
         }
         return null;
	}

}
