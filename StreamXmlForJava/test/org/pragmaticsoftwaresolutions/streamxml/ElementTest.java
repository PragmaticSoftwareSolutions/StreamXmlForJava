/**
 * ElementTest
 *
 * 13 Sep 2014
 *
 * Copyright (c) Pragmatic Software Solutions. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Pragmatic Software Solutions BVBA. ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express written permission of Pragmatic Software Solutions BVBA.
 *
 */
package org.pragmaticsoftwaresolutions.streamxml;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Kris Leenaerts
 *
 */
public class ElementTest {

	ElementFactory factory;
	OutputStream baos;
	OutputStreamWriter osw;
	StringWriter sw;
	
	@Before
	public void setup() throws XMLStreamException, FactoryConfigurationError {
		factory = ElementFactory.getInstance();
		baos = new ByteArrayOutputStream();
		osw = new OutputStreamWriter(baos, Charset.forName("UTF-8"));
		sw = new StringWriter();
	}


	/**
	 * testLevel0Elements
	 * @throws IOException 
	 * @throws XMLStreamException 
	 */
	@Test
	public void testRoot() throws IOException, XMLStreamException {
		Element root = factory.root("root");
		root.stream(osw);
		String expected = "<root/>";
		assertEquals(expected, baos.toString());
	}
	
	
	@Test
	public void testRootNs() throws IOException, XMLStreamException {
		Element root = factory.root("ns", "root");
		root.stream(osw);
		osw.flush();
		String expected = "<ns:root/>";
		assertEquals(expected, baos.toString());		
	}
	
	@Test
	public void streamOrdersPartially() throws IOException {
		Element orders = factory.root("orders");
		Element order = orders.elem("order");
		StringBuilder expected = new StringBuilder(); 
		for(int i = 0;i<1;i++) {
			order.attr("id", Integer.toString(i));
			order.elem("number").pcdata("No" + Integer.toString(i)).stream(osw);
			expected.append("<orders><order id=\"0\"><number>No0</number>");
			assertEquals(expected.toString(), baos.toString());
			order.elem("name").pcdata("Kris Leenaerts");
			order.elem("address").pcdata("Baan 7").stream(osw);
			expected.append("<name>Kris Leenaerts</name><address>Baan 7</address>");
			assertEquals(expected.toString(), baos.toString());
		}
		orders.stream(osw);
		expected.append("</order></orders>");
		assertEquals(expected.toString(), baos.toString());
	}

//	private class Order {
//		String number;
//		String description;
//		String customer;
//	}
	
//	@Test
//	public void streamOrdersPartially2() throws IOException {
//		OutputStreamWriter osw;//the output stream
//		Iterable<Order> orders;//The list with orders from database
//		Element ordersXml = ElementFactory.getInstance().root("orders");
//		StringBuilder expected = new StringBuilder(); 
//		for(Order order:orders) {
//			Element orderXml = ordersXml.elem("order");
//			orderXml.attr("id", order.number);
//			orderXml.elem("number").pcdata(order.number);
//			orderXml.elem("customer").pcdata(order.customer);
//			orderXml.stream(osw);//stream order
//		}
//		ordersXml.stream(osw);//stream end tags
//	}

	
	@Test
	public void streamProcessingInstruction() throws IOException {
		Element root = factory.root("root");
		Element child = root.elem("child");
		child.procInstr("target", "content");
		child.stream(osw);
		String expected = "<root><child><?target content?></child>";
		assertEquals(expected, baos.toString());
		root.stream(osw);
		expected += "</root>";
		assertEquals(expected, baos.toString());
	}
	
	@Test
	public void streamOrders() throws IOException {
		Element orders = ElementFactory.getInstance().root("orders");
		Element order = orders.elem("order");
		StringBuilder expected = new StringBuilder(); 
		for(int i = 0;i<1;i++) {
			order.attr("id", Integer.toString(i));
			order.elem("number").pcdata("No" + Integer.toString(i)).stream(osw);
			expected.append("<orders><order id=\"0\"><number>No0</number>");
			assertEquals(expected.toString(), baos.toString());
			order.elem("name").pcdata("Kris Leenaerts");
			order.elem("address").pcdata("Baan 7").stream(osw);
			expected.append("<name>Kris Leenaerts</name><address>Baan 7</address>");
			assertEquals(expected.toString(), baos.toString());
		}
		orders.stream(osw);
		expected.append("</order></orders>");
		assertEquals(expected.toString(), baos.toString());
	}
	
	@Test
	public void streamHtml() throws IOException {
		Element html = factory.root("html");
		Element head = html.elem("head").attr("class", "head");
		head.elem("link").attr("type", "text/css");
		Element body = html.elem("body");
		body.elem("h1").pcdata("Hello World!");
		html.stream(osw);
		assertEquals("<html><head class=\"head\"><link type=\"text/css\"/></head><body><h1>Hello World!</h1></body></html>", baos.toString());
	}
	
	@Test
	public void streamAttributes() throws IOException {
		Element html = factory.root("html");
		html.attr("href", "test/test").attr("href", "tost/tost").attr("knal", "boom");
		String expected = "<html knal=\"boom\" href=\"tost/tost\"/>";
		html.stream(osw);
		assertEquals(expected, baos.toString());
	}
	

}
