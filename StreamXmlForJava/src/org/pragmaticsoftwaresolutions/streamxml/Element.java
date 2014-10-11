
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
package org.pragmaticsoftwaresolutions.streamxml;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author Kris Leenaerts
 * 
 * Represents an XML element.
 */
public interface Element {
	 
    /**
     * <p>
     * The path of elements from this element to the root is the ancestor path.
     * </p>
     * <p>
     * The start tag of an element is streamed only once.
     * </p>
     * <p>
     * When this element is not the root. 
     * The start tags of the ancestor path are streamed.
     * The start tags, child nodes and end tags of the elements before the ancestor path are streamed.
     * The start tag, child nodes and end tag of this element are streamed.
     * </p>
     * <p>
     * When this element is the root.  The start tag if not already streamed, the current child nodes and end tag of the root are streamed.
     * </p>
     * <p>
     * The stream is flushed.
     * </p>
     * 
     * @param writer the output stream writer
     * @throws IOException input or output exception
     */
    void stream(OutputStreamWriter writer) throws IOException;

    /**
     * <p>
     * The path of elements from this element to the root is the ancestor path.
     * </p>
     * <p>
     * The start tag of an element is streamed only once.
     * </p>
     * <p>
     * When this element is not the root.
     * The start tags of the ancestor path are streamed.
     * The start tags, child nodes and end tags of the elements before the ancestor path are streamed.
     * When this tag has child nodes. The start tag and child nodes of this element are streamed.
     * </p>
     * <p>
     * When this element is the root.  The start tag, child elements and end tag of the root are streamed.
     * </p>
     * <p>
     * The stream is flushed when the flush parameter is true.
     * </p>
     * 
     * @param writer the output stream writer
     * @param flush flushes the stream when it is true
     * @throws IOException input or output exception
     */
    void stream(OutputStreamWriter writer, boolean flush) throws IOException;

    /**
     * <p>
     * Creates a child element with the passed name.  
     * The namespace will be inherited from this element.
     * The selfClosing property will be inherited from this element.
     * </p>
     * 
     * @param name of the element
     * @return the created element
     */
    Element elem(String name);

    /**
     * <p>
     * Creates a child element with passed the name.
     * The namespace will be inherited from this element.
     * </p>
     * 
     * @param name of the element
     * @param selfClosing when true streamed as an empty element when it doesn't has child nodes
     * @return the created element
     */
    Element elem(String name, boolean selfClosing);

    /**
     * <p>
     * Creates a child element with passed name and namespace.
     * An empty namespace can be obtained by passing null.
     * The selfClosing property will be inherited from this element.
     * </p>
     * 
     * @param namespace the namespace of the element
     * @param name the name of the element
     * @return the created element
     */
    Element elem(String namespace, String name);

    /**
     * <p>
     * Creates a child element with passed name and namespace.
     * An empty namespace can be obtained by passing null.
     * </p>
     * 
     * @param namespace the namespace of the element
     * @param name the name of the element
     * @param selfClosing when true streamed as an empty element when it doesn't has child nodes
     * @return the created element
     */
    Element elem(String namespace, String name, boolean selfClosing);

    
    /**
     * <p>
     * Creates a PCData child node with the passed text as content.
     * </p>
     * 
     * @param text the content
     * @return this element
     */
    Element pcdata(String text);
    
    /**
     * <p>
     * Creates a processing instruction child node with the passed target and content
     * </p>
     * 
     * @param target the target of the processing instructing
     * @param content the content of the processing instruction
     * @return this element
     */
    Element procInstr(String target, String content);
    
    /**
     * <p>
     * Creates a CDATA child node with the passed text as content
     * </p>
     * 
     * @param text the data
     * @return this element
     */
    Element cdata(String text);

    /**
     * <p>
     * Creates a comment child node with the passed text as content
     * </p>
     * 
     * @param text the comment
     * @return this element
     */
    Element comment(String text);

    /**
     * <p>
     * Adds an attribute with name and value to this element.  A previous existing value is overridden.
     * </p>
     * 
     * @param name the name of the attribute
     * @param value the value of the attribute
     * @return this element
     */
    Element attr(String name, String value);
    
}
