package com.train.jaxp;

import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.train.jaxp.handle.MyEntityResolver;
import com.train.jaxp.handle.MyErrorHandler;

public class MyDOM {

	public static void main(String[] args) throws Exception {
		new MyDOM();
	}

	public MyDOM() throws Exception {
		// Use "javax.xml.parsers.DocumentBuilderFactory" system property to
		// specify a Parser.
		// java -Djavax.xml.parsers.DocumentBuilderFactory=yourFactoryHere [...]
		// If property is not specified, use J2SE default Parser.
		// The default Parser is
		// "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl".
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		dbf.setIgnoringComments(false);
		dbf.setNamespaceAware(true);
		dbf.setIgnoringElementContentWhitespace(true);

		// Use XSD defined by JAXP 1.3, JAVA1.5
		// SchemaFactory sf =
		// SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		// dbf.setSchema(sf.newSchema(this.getClass().getResource("/jaxp/resources/bookStore.xsd")));
		// or Use old way defined by JAXP 1.2
		// dbf.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage","http://www.w3.org/2001/XMLSchema");
		// dbf.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource",
		// new File("schema.xsd"));
		// dbf.setSchema(schema);

		// XSD disabled, use DTD.
		dbf.setValidating(true);

		DocumentBuilder db = dbf.newDocumentBuilder();
		db.setErrorHandler(new MyErrorHandler());
		db.setEntityResolver(new MyEntityResolver());

		Document document = db.parse(this.getClass().getResourceAsStream(
				"resources/bookStore.xml"));

		// Operate on Document according to DOM module.
		NodeList booklist = document.getElementsByTagNameNS(
				"http://joey.org/bookStore", "book");
		for (int i = 0; i < booklist.getLength(); i++) {
			NodeList list = booklist.item(i).getChildNodes();
			{
				System.out.println("NodeName:" + list.item(i).getNodeName()
						+ " NodeValue:" + list.item(i).getTextContent()
						+ " NodeType:" + list.item(i).getNodeType());
			}
		}
		System.out.println(booklist.item(2).getAttributes().item(0).getLocalName());
		// Node that if you don't specify name space, you need to use Qualified
		// Name.
		System.out.println(document.getElementsByTagName("audlt:age").item(0)
				.getTextContent());

		// Use xpath to query xml
		XPathFactory xpf = XPathFactory.newInstance();
		XPath xp = xpf.newXPath();
		// Need to set a namespace context.
		NamespaceContext nc = new NamespaceContext() {

			@Override
			public String getNamespaceURI(String prefix) {
				if (prefix.equals("b"))
					return "http://joey.org/bookStore";
				if (prefix.equals("a"))
					return "http://japan.org/book/audlt";
				return null;
			}

			@Override
			public String getPrefix(String namespaceURI) {
				if (namespaceURI.equals("http://joey.org/bookStore"))
					return "b";
				if (namespaceURI.equals("http://japan.org/book/audlt"))
					return "a";
				return null;
			}

			@Override
			public Iterator getPrefixes(String namespaceURI) {
				return null;
			}

		};
		xp.setNamespaceContext(nc);
		System.out.println(xp.evaluate("/b:bookStore/@name", document));
		System.out.println(xp.evaluate(
				"/b:bookStore/b:books/b:book[@id=3]/@a:color", document));
	}
}