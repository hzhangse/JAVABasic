package com.train.jaxp;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.train.jaxp.handle.MyContentHandler;
import com.train.jaxp.handle.MyDTDHandler;
import com.train.jaxp.handle.MyEntityResolver;
import com.train.jaxp.handle.MyErrorHandler;

public class MySAX {
	static String xsdName = "resources/bookStore.xsd";
	static String xsdName2 = "resources/book_adult.xsd";
	static String xml = "resources/bookStore.xml";
	private SAXParser parser;

	public static void main(String[] args) throws Exception {
//		xsdName = "resources/family.xsd";
//		 xml = "resources/myFamily.xml";
		new MySAX(xsdName,xml);
	}

	public void validByDTD(SAXParser parser){
		
	}
	
	public void validByXSD(SAXParser parser){
		
	}
	public MySAX(String xsdName,String xml) throws ParserConfigurationException, SAXException,
			IOException {
		// Use "javax.xml.parsers.SAXParserFactory" system property to specify a
		// Parser.
		// java -Djavax.xml.parsers.SAXParserFactory=yourFactoryHere [...]
		// If property is not specified, use J2SE default Parser.
		// The default Parser is
		// "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl".
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);

		// Use XSD defined by JAXP 1.3, JAVA1.5
		SchemaFactory sf = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Source schemaSource1 = new StreamSource(this.getClass().getResourceAsStream(xsdName) );
		Source schemaSource2 = new StreamSource(this.getClass().getResourceAsStream(xsdName2) );
		Source[] sarrya= {schemaSource1,schemaSource2};
		Schema schema = sf.newSchema(sarrya);
		Validator validator = schema.newValidator();
		InputStream inXml = this.getClass().getResourceAsStream(
				xml);
		//validator.validate(new StreamSource(inXml));
		
		spf.setSchema(schema);
//		spf.setSchema(sf.newSchema(this.getClass().getResource(
//				xsdName2)));
		// or Use old way defined by JAXP 1.2
		// parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage","http://www.w3.org/2001/XMLSchema");
		// parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource",
		// new File("schema.xsd"));
		//
		// XSD disabled, use DTD. <span style="font-size:9pt;line-height:1.5;">
		//spf.setValidating(true);
		// style="font-size:9pt;line-height:1.5;"> </span><span
		// style="font-size:9pt;line-height:1.5;">
		this.parser = spf.newSAXParser();
		// You can directly use SAXParser to parse XML. Or use XMLReader.
		// SAXParser warps and use XMLReader internally.
		// I will use XMLReader here.
		// this.parser.parse(InputStrean, DefaultHandler);
		XMLReader reader = this.parser.getXMLReader();
		reader.setContentHandler(new MyContentHandler());
		reader.setDTDHandler(new MyDTDHandler());
		reader.setErrorHandler(new MyErrorHandler());
		reader.setEntityResolver(new MyEntityResolver());
		
		InputStream in = this.getClass().getResourceAsStream(
				xml);
		InputSource is = new InputSource(in);
		is.setEncoding("UTF-8");
		reader.parse(is);
	}
}