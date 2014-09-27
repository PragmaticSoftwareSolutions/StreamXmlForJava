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
 * 
 * First implementation
 * 
 * @author Kris Leenaerts
 *
 */
public class XmlCDataV0 implements XmlCData {

	private static final String CDATA_END_TAG = "]]>";

	private static final String CDATA_START_TAG = "<![CDATA[";

	/** parent */
	private XmlElement parent;
	
	/** the cdata */
	private String cdata;

    /** character data pool */
    Pool<XmlCDataV0> pool;

	@Override
	public void streamXml(OutputStreamWriter streamWriter) throws IOException,
			IllegalStateException {
		streamWriter.append(CDATA_START_TAG);
		streamWriter.append(cdata);
		streamWriter.append(CDATA_END_TAG);
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
	public void release() {
		pool.add(this);
	}
	
	@Override
	public XmlCData create(String cData) {
    	XmlCDataV0 cdata = pool.remove();
    	if(cdata == null) {
    		cdata = new XmlCDataV0();
    		cdata.pool = pool;
    	}
    	cdata.cdata = cData;
    	return cdata;
	}
	
}
