/**
 * 
 */
package com.train.jaxp.handle;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * @author hzhangse
 *
 */
public class MyContentHandler implements ContentHandler {
	private Locator locator;
	private Map namespaceMappings;
	/**
	 * 
	 */
	public MyContentHandler() {
		 this.namespaceMappings = new HashMap();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
	 */
	@Override
	public void setDocumentLocator(Locator locator) {
		this.locator = locator;
		System.out.println("set Document locator:");
		
		System.out.println("getPublicId:" + locator.getPublicId());
		System.out.println("getSystemId:" + locator.getSystemId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ContentHandler#startDocument()
	 */
	@Override
	public void startDocument() throws SAXException {
		System.out.println("Start Document :");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ContentHandler#endDocument()
	 */
	@Override
	public void endDocument() throws SAXException {
		System.out.println("endDocument :");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		 namespaceMappings.put(uri,prefix);
		System.out.println("strat prefix:" + prefix +" uri:"+uri);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
	 */
	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		
		for(Iterator i = namespaceMappings.keySet().iterator();i.hasNext();){
	        String uri = (String)i.next();
	        String thisPrefix = (String) namespaceMappings.get(uri);
	        if(prefix.equals(thisPrefix)){
	            namespaceMappings.remove(uri);
	            break;
	        }
	    }
		System.out.println("endPrefixMapping:" + prefix );

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
	 * java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		
		System.out.println("startElement:" );
		System.out.println("			" + "localName:"+localName +" qName:"+qName+" atts:");
		System.out.println("getColumnNumber:" + locator.getColumnNumber());
		System.out.println("getLineNumber:" + locator.getLineNumber());
		for (int index=0;index<atts.getLength();index++){
			
			System.out.println("												" 
					+" QName:"+atts.getQName(index)+" ;Type:"+atts.getType(index)+" ;LocalName:"+atts.getLocalName(index)+" ;Value:"+atts.getValue(index)+" ;URI:"+atts.getURI(index)+" ");
		}
		
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ContentHandler#endElement(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		System.out.println("endElement:");
		System.out.println("			" + "localName:"+localName +" qName:"+qName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ContentHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		
		System.out.println("characters:"+new String(ch,start,length));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
	 */
	@Override
	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		System.out.println("ignorableWhitespace:"+new String(ch,start,length));


	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void processingInstruction(String target, String data)
			throws SAXException {
		System.out.println("processingInstruction: target:"+ target + " data:"+data);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
	 */
	@Override
	public void skippedEntity(String name) throws SAXException {
		System.out.println("skippedEntity: "+ name);
	}

}
