package com.train.jaxp.handle;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class MyEntityResolver implements EntityResolver {

	public MyEntityResolver() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public InputSource resolveEntity(String publicId, String systemId)
			throws SAXException, IOException {
        if ("bookStore.dtd".equals(publicId)) {
            InputStream in = this.getClass().getResourceAsStream("../resources/bookStore.dtd");
            InputSource is = new InputSource(in);
            return is;
        }
        return null;

	}

}
