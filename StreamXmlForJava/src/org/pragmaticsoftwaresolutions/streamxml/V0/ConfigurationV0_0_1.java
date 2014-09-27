/**
 * Copyright 2014 Pragmatic Software Solutions bvba
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.pragmaticsoftwaresolutions.streamxml.V0;

import org.pragmaticsoftwaresolutions.streamxml.Element;
import org.pragmaticsoftwaresolutions.streamxml.ElementFactory;

/**
 * The configuration for version 0.0.1
 * 
 * @author Kris Leenaerts
 *
 */
public class ConfigurationV0_0_1 extends ElementFactory {
	
	public ConfigurationV0_0_1() {
	}
	
	@Override
	public Element root(String name) {
		return root(null, name, true);
	}

	@Override
	public Element root(String namespace, String name) {
		return root(namespace, name, true);
	}
	
	@Override
	public Element root(String name, boolean selfClosing) {
		return root(null, name, selfClosing);
	}
	
	@Override
	public Element root(String namespace, String name, boolean selfClosing) {
		XmlCDataV0 xmlCData = new XmlCDataV0(); 
		xmlCData.pool = new PoolV0<XmlCDataV0>();
		XmlPCDataV0 xmlPCData = new XmlPCDataV0();
		xmlPCData.pool = new PoolV0<XmlPCDataV0>();
		XmlCommentV0 xmlComment = new XmlCommentV0();
		xmlComment.pool = new PoolV0<XmlCommentV0>();
		XmlProcessingInstructionV0 xmlProcessingInstruction = new XmlProcessingInstructionV0();
		xmlProcessingInstruction.pool = new PoolV0<XmlProcessingInstructionV0>();
		XmlElementV0 elem = new XmlElementV0();
		elem.pool = new PoolV0<XmlElementV0>();
		elem.cData = xmlCData;
		elem.pcData = xmlPCData;
		elem.comment = xmlComment;
		elem.procInstr = xmlProcessingInstruction;
		elem.name = name;
		elem.namespace = namespace;
		elem.isSelfClosingTag = selfClosing;
		return elem;
	}
	
}
