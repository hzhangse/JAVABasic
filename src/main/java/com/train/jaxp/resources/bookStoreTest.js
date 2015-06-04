function createXMLDoc(xmlStr) {
	var xmlDoc;
	if (window.DOMParser) {
		// FF Chrome
		var parser = new DOMParser();
		xmlDoc = parser.parseFromString(xmlStr, "text/xml");
	} else if (window.ActiveXObject) {
		// Internet Explorer
		xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
		xmlDoc.async = "false";
		xmlDoc.loadXML(xmlStr);
	}
	return xmlDoc;
}

function transform(xmlDoc, xslDoc) {
	if (window.XSLTProcessor) {
		// chrome FF
		var xslp = new XSLTProcessor();
		xslp.importStylesheet(xslDoc);
		return xslp.transformToFragment(xmlDoc, document);
	} else if (window.ActiveXObject) {
		// IE
		return xmlDoc.transformNode(xslDoc);
	}
}

var xmlStr = [
		'<bookStore name="java" xmlns="http://joey.org/bookStore" xmlns:audlt="http://japan.org/book/audlt" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="bookStore.xsd">',
		'<keeper><name>Joey</name></keeper>',
		'<books>',
		'<book id="1"> <title>XML</title><author>Steve</author></book>',
		'<book id="2"><title>JAXP</title> <author>Bill</author></book>',
		'<book id="3" audlt:color="yellow"><audlt:age> &gt;18 </audlt:age> <title>Love</title><author>teacher</author></book>',
		'</books></bookStore>' ].join('');

var xslStr = [
		'<?xml version="1.0" encoding="UTF-8"?>',
		'<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:b="http://joey.org/bookStore" xmlns:a="http://japan.org/book/audlt">',
		'<xsl:output method="html" version="1.0" encoding="UTF-8" indent="yes" />',
		'<xsl:template match="/">',
		'<html>',
		'<body>',
		'<h2>Book Store&lt;&lt;<xsl:value-of select="/b:bookStore/@name"/>&gt;&gt;</h2>',
		'<div>There are <xsl:value-of select="count(/b:bookStore/b:books/b:book)"/> books.</div>',
		'<div>Keeper of this store is <xsl:value-of select="/b:bookStore/b:keeper/b:name"/></div>',
		'<xsl:for-each select="/b:bookStore/b:books/b:book">',
		'<div>Book: ',
		'<span>title=<xsl:value-of select="b:title"/></span>;<span>author=<xsl:value-of select="b:author"/></span>',
		'<xsl:if test="@a:color">',
		'<span color="yellow">H Book, require age<xsl:value-of select="a:age"/></span>',
		'</xsl:if>', '</div>', '</xsl:for-each>', '</body>', '</html>',
		'</xsl:template>', '</xsl:stylesheet>' ].join('');

var xmlDoc = createXMLDoc(xmlStr);
var xslDoc = createXMLDoc(xslStr);
var dom = transform(xmlDoc, xslDoc);
console.log(dom.childNodes[0].outerHTML);