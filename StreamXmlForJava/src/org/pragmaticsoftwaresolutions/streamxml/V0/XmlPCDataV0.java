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

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author Kris Leenaerts
 *
 */
public class XmlPCDataV0 implements XmlPCData {

    /** parsed character pool */
    Pool<XmlPCDataV0> pool;

    /** The text */
	private String pcData;
    
    /** the parent tag */
    private XmlElement parent;

    public XmlPCDataV0() {
    }

	@Override
	public XmlElement getParent() {
		return parent;
	}
	
	@Override
	public void setParent(XmlElement parent) {
		this.parent = parent;
	}

	@Override
	public void streamXml(OutputStreamWriter writer) throws IOException {
		writer.append(pcData);
	}
	
	@Override
	public void release() {
		pool.add(this);
	}
	
	@Override
	public XmlPCData create(String pcData) {
		XmlPCDataV0 pcDataNode = pool.remove();
		if(pcDataNode == null) {
    		pcDataNode = new XmlPCDataV0();
    		pcDataNode.pool = pool;
    	}
		pcDataNode.pcData = pcData;
		return pcDataNode;
	}
	
}
