package com.train.jaxp;

import java.io.IOException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

import com.train.jaxp.handle.MyEntityResolver;

public class MyXSLT {
    TransformerFactory tff;
     
    public static void main(String[] args) throws Exception {
        MyXSLT xslt = new MyXSLT();
        xslt.xml2html();
        xslt.str2xml();
    }
     
    public MyXSLT() {
        tff = TransformerFactory.newInstance();
    }
     
    public void xml2html() throws Exception {
        Transformer tr = tff.newTransformer(new SAXSource(new InputSource(this.getClass().getResourceAsStream("resources/bookStore.xsl"))));
         
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser parser = spf.newSAXParser();
        parser.getXMLReader().setEntityResolver(new MyEntityResolver());
        Source source = new SAXSource(parser.getXMLReader(), new InputSource(this.getClass().getResourceAsStream("resources/bookStore.xml")));
        Result target = new StreamResult(System.out);
        tr.transform(source, target);
    }
     
    // "[joey,bill,cat]" will be transformed to 
    // <test><name>joey</name><name>bill</name><name>cat</name></test>
    public void str2xml() throws Exception {
        final String[] names = new String[]{"joey","bill","cat"};
        Transformer tr = tff.newTransformer();
         
        Source source = new SAXSource(new XMLReader() {
            private ContentHandler handler;
             
            @Override
            public void parse(InputSource input) throws IOException,
                    SAXException {
                handler.startDocument();
                handler.startElement("", "test", "test", null);
                for (int i=0; i<names.length; i++) {
                    handler.startElement("", "name", "name", null);
                    handler.characters(names[i].toCharArray(), 0, names[i].length());
                    handler.endElement("", "name", "name");
                }
                handler.endElement("", "test", "test");
                handler.endDocument();
            }
 
            @Override
            public void parse(String systemId) throws IOException, SAXException {
            }
             
            @Override
            public boolean getFeature(String name)
                    throws SAXNotRecognizedException, SAXNotSupportedException {
                return false;
            }
 
            @Override
            public void setFeature(String name, boolean value)
                    throws SAXNotRecognizedException, SAXNotSupportedException {
            }
 
            @Override
            public Object getProperty(String name)
                    throws SAXNotRecognizedException, SAXNotSupportedException {
                return null;
            }
 
            @Override
            public void setProperty(String name, Object value)
                    throws SAXNotRecognizedException, SAXNotSupportedException {
            }
 
            @Override
            public void setEntityResolver(EntityResolver resolver) {
            }
 
            @Override
            public EntityResolver getEntityResolver() {
                return null;
            }
 
            @Override
            public void setDTDHandler(DTDHandler handler) {
            }
 
            @Override
            public DTDHandler getDTDHandler() {
                return null;
            }
 
            @Override
            public void setContentHandler(ContentHandler handler) {
                this.handler = handler;
            }
 
            @Override
            public ContentHandler getContentHandler() {
                return handler;
            }
 
            @Override
            public void setErrorHandler(ErrorHandler handler) {
            }
 
            @Override
            public ErrorHandler getErrorHandler() {
                return null;
            }
        }, new InputSource());
         
        Result target = new StreamResult(System.out);
        tr.transform(source, target);
    }
 
}