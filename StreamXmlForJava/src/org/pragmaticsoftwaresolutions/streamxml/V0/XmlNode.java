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
 * Responsible for holding the parent element
 * Streaming to OutputStreamWriter
 *
 */
public interface XmlNode {

    /**
     * 
     * Streams start, attributes, children and end.
     * When the tag hasn't got children, streams end tag 
     * 
     * @throws StreamException thrown by the writer
     * @throws IllegalStateException: when this node is already streamed
     */
    void streamXml(OutputStreamWriter streamWriter) throws IOException, IllegalStateException;

    /**
     * Returns the parent of this element or null.
     * 
     * @return the parent node
     */
	XmlElement getParent();
    
    /**
     * Sets the parent if this element
     * 
     * @param parent
     */
	void setParent(XmlElement parent);

	/**
	 * Called by the parent after this node is streamed and is removed as child. 
	 */
	void release();
	    
}
