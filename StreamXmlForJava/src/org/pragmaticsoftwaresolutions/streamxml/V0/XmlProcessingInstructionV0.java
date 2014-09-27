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
 * @author Kris Leenaerts
 *
 */
public class XmlProcessingInstructionV0 implements XmlProcessingInstruction {

	/** the target */
	private String target;

	/** the content */
	private String content;
	
	/** the parent */ 
	private XmlElement parent;
	
    /** process instruction pool */
    Pool<XmlProcessingInstructionV0> pool;

	@Override
	public void streamXml(OutputStreamWriter streamWriter) throws IOException,
			IllegalStateException {
		streamWriter.append("<?");
		streamWriter.append(target);
		if(content != null) {
			streamWriter.append(' ');
			streamWriter.append(content);
		}
		streamWriter.append("?>");
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
	public XmlProcessingInstruction create(String target, String content) {
		XmlProcessingInstructionV0 pi = pool.remove();
		if(pi == null) {
			pi = new XmlProcessingInstructionV0();
			pi.pool = pool;
		}
		pi.target = target;
		pi.content = content;
		return pi;
	}

}
