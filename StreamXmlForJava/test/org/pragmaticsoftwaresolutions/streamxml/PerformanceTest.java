/**
 * PerformanceTest
 *
 * 03 Sep 2014
 *
 * Copyright (c) Pragmatic Software Solutions. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Pragmatic Software Solutions BVBA. ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express written permission of Pragmatic Software Solutions BVBA.
 *
 */
package org.pragmaticsoftwaresolutions.streamxml;

import java.io.IOException;
import java.io.OutputStreamWriter;

import org.junit.Test;

/**
 * 
 * @author Kris Leenaerts
 *
 */
public class PerformanceTest {

	@Test
	public void testPerformanceCreateElements() throws IOException {
//		long start = System.currentTimeMillis();
//		for(int i=0;i<100000000;i++) {
//			Element elem = new XmlElementV0("ee" + i, new LinkedList<XmlNode>(), new LinkedList<XmlAttribute>());
//		}
//		long end = System.currentTimeMillis();
//		System.out.println(end - start);
//		Element elem = new XmlElementV0("ee", new LinkedList<XmlNode>(), new LinkedList<XmlAttribute>());
//		List<Element> list = new ArrayList<Element>();
//		long start2 = System.currentTimeMillis();
//		for(int i=0;i<100000000;i++) {
//			list.add(elem);
//		}
//		long end2 = System.currentTimeMillis();
//		System.out.println(end2 - start2);
//		
		OutputStreamWriter osw = new OutputStreamWriter(System.out);
		Element root = ElementFactory.getInstance().root("root");
		long start3 = System.currentTimeMillis();
		String ns = "namespace";
		for(int i=0;i<10000;i++) {
			root.elem(ns, Integer.toString(i)).elem(ns, Integer.toString(-i)).stream(osw);
			osw.append(System.lineSeparator());
			osw.flush();
		}
		root.stream(osw);
		osw.flush();
		long end3 = System.currentTimeMillis();
		System.out.println(end3 - start3);
		
	}
	
	public static void main(String[] args) {
		try {
			new PerformanceTest().testPerformanceCreateElements();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
