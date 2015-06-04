package com.train.jaxp;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;

import com.train.jaxp.handle.MyXMLResolver;

public class MyStAX {
	 
    public static void main(String[] args) throws Exception {
      //  coursorAPIReadWrite();
        eventAPIReadWrite();
    }
     
    // use cursor API to read and write XML.
    public static void coursorAPIReadWrite() throws Exception {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        // Set properties for validation, namespace...
        // But, JDK embeded StAX parser does not support validation.
        //xif.setProperty(XMLInputFactory.IS_VALIDATING, true);
        xif.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);
         
        // Handle the external Entity.
        xif.setXMLResolver(new MyXMLResolver() );
         
        XMLOutputFactory xof = XMLOutputFactory.newInstance();
        // Set namespace repairable. Sometimes it will bring you bug. Use it carefully.
        // xof.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);
           
        InputStream sourceIn = MyStAX.class.getResourceAsStream("resources/bookStore.xml");
        OutputStream targetOut = System.out; //new FileOutputStream(new File("target.xml"));
         
        XMLStreamReader reader = xif.createXMLStreamReader(sourceIn);
        XMLStreamWriter writer = xof.createXMLStreamWriter(targetOut, reader.getEncoding());
        writer.writeStartDocument(reader.getEncoding(), reader.getVersion());
         
        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
            case XMLStreamConstants.DTD:
                out(reader.getText());
                writer.writeCharacters("\n");
                writer.writeDTD(reader.getText());
                writer.writeCharacters("\n");
                break;
            case XMLStreamConstants.PROCESSING_INSTRUCTION:
                out(reader.getPITarget());
                writer.writeCharacters("\n");
                writer.writeProcessingInstruction(reader.getPITarget(), reader.getPIData());
                break;
            case XMLStreamConstants.START_ELEMENT:
                out(reader.getName());
                NamespaceContext nc = reader.getNamespaceContext();
                writer.setNamespaceContext(reader.getNamespaceContext());
                writer.setDefaultNamespace(nc.getNamespaceURI(""));
                writer.writeStartElement(reader.getPrefix(), reader.getLocalName(), reader.getNamespaceURI());
 
                for (int i=0; i<reader.getAttributeCount(); i++) {
                    QName qname = reader.getAttributeName(i);
                    String name=qname.getLocalPart();
                    if (qname.getPrefix()!=null && !qname.getPrefix().equals("")) {
                        //name = qname.getPrefix()+":"+name;
                    }
                    writer.writeAttribute(name, reader.getAttributeValue(i));
                }
                for (int i=0; i<reader.getNamespaceCount(); i++) {
                    writer.writeNamespace(reader.getNamespacePrefix(i), reader.getNamespaceURI(i));
                }
                break;
            case XMLStreamConstants.ATTRIBUTE: 
                out(reader.getText());
                break;
            case XMLStreamConstants.SPACE:
                out("SPACE");
                writer.writeCharacters("\n");
                break;
            case XMLStreamConstants.CHARACTERS:
                out(reader.getText());
                writer.writeCharacters(reader.getText());
                break;
            case XMLStreamConstants.END_ELEMENT:
                out(reader.getName());
                writer.writeEndElement();
                break;
            case XMLStreamConstants.END_DOCUMENT:
                writer.writeEndDocument();
                break;
             default: 
                 out("other");
                 break;
             
            }
        }
        writer.close();
        reader.close();
         
    }
     
    public static void eventAPIReadWrite() throws Exception {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        xif.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);
        // Handle the external Entity.
        xif.setXMLResolver(new MyXMLResolver());
        XMLOutputFactory xof = XMLOutputFactory.newInstance();
         
        InputStream sourceIn = MyStAX.class.getResourceAsStream("resources/bookStore.xml");
        OutputStream targetOut = System.out;
        XMLEventReader reader = xif.createXMLEventReader(sourceIn);
        XMLEventWriter writer = xof.createXMLEventWriter(targetOut);
         
        while(reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            out(event.getEventType());
            writer.add(event);
        }
        reader.close();
        writer.close();
        out(" end ");
    }
     
    public static void out(Object o) {
        System.out.println(o);
    }
 
}