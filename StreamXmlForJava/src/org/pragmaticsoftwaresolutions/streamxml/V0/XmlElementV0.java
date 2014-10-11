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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.pragmaticsoftwaresolutions.streamxml.Element;

/**
 * @author Kris Leenaerts
 *
 */
public class XmlElementV0 implements Element, XmlElement {

	/** the name of the tag */
    String name;
    
    /** the name space of the tag */
    String namespace;
    
    /** the children */
    private List<XmlNode> children = new LinkedList<XmlNode>();
    
    /** the parent tag */
    XmlElement parent;
    
    /** is start tag already streamed */
    private boolean isStartStreamed = false;
    
    /** is complete tag already streamed */
    private boolean isAlreadyStreamed = false;
    
    /** when is closing tag and has no children the only a closing tag is rendered */
    boolean isSelfClosingTag = true;
    
    /** has this element already streamed a child */
    private boolean hasStreamedAChild = false;

    /** the pool */
    Pool<XmlElementV0> pool;
    
    /** parsed character data factory */ 
    XmlPCData pcData;
    
    /** the character data factory */
    XmlCData cData;

    /** the comment factory */
    XmlComment comment;

    /** the processing instruction factory */
    XmlProcessingInstruction procInstr;
    
    /** the attributes */
    private Map<String, String> attributes = new HashMap<String, String>();
    
    /** constructor */
    public XmlElementV0() {
	}
 
	@Override
	public void stream(OutputStreamWriter writer) throws IOException {
		this.stream(writer, true);
	}
	
	@Override
	public void stream(OutputStreamWriter writer, boolean flush)
			throws IOException {
		if(getParent() != null) {
			getParent().streamXmlPartially(writer, this, this);
		} else {
			streamXml(writer);
		}
		if(flush) {
			writer.flush();
		}
	}
		
	/**
	 * @see org.pragmaticsoftwaresolutions.streamxml.V0.xml.core.XmlNode#stream(java.io.OutputStreamWriter)
	 */
	@Override
	public void streamXml(OutputStreamWriter streamWriter) throws IOException,
			IllegalStateException {
        if(isAlreadyStreamed) {
            throw new IllegalStateException("This tag is already streamed.");
        }
        if(getParent() != null) {
            throw new IllegalStateException("You cannot stream a tag that has a parent.");
        }
        isAlreadyStreamed = true;
        if(children.size() == 0 && !hasStreamedAChild && isSelfClosingTag) {
        	//stream end tag
        	streamWriter.append("<");
            if(namespace != null) {
            	streamWriter.append(namespace);
            	streamWriter.append(":");
            } 
        	streamWriter.append(name);
            streamAttributes(streamWriter);
        	streamWriter.append("/>");
        	return;
        }
        streamStart(streamWriter);
        for (Iterator<XmlNode> iter = children.iterator(); iter.hasNext();) {
            XmlNode child = iter.next();
            iter.remove();
            child.setParent(null);
            child.streamXml(streamWriter);
            hasStreamedAChild = true;
            child.release();
        }
        streamEnd(streamWriter);
	}

	private void streamAttributes(OutputStreamWriter streamWriter)
			throws IOException {
		for(Map.Entry<String, String> attribute: attributes.entrySet()) {
			streamWriter.append(' ');
			streamWriter.append(attribute.getKey());
			streamWriter.append("=\"");
			streamWriter.append(attribute.getValue());
			streamWriter.append('\"');
		}
	}

    private void streamStart(OutputStreamWriter writer) throws IOException {
        if(!isStartStreamed) {
            writer.append("<");
            if(namespace != null) {
            	writer.append(namespace);
            	writer.append(":");
            } 
        	writer.append(name);
            streamAttributes(writer);
            writer.append(">");
            isStartStreamed = true;
        }
    }
    

	private void streamEnd(OutputStreamWriter streamWriter) throws IOException {
		streamWriter.append("</");
        if(namespace != null) {
        	streamWriter.append(namespace);
        	streamWriter.append(":");
        } 
		streamWriter.append(name);
		streamWriter.append(">");
	}


	/**
	 * @see org.pragmaticsoftwaresolutions.Element.core.Tag#getParent()
	 */
	@Override
	public XmlElement getParent() {
		return parent;
	}

	/**
	 * @see org.pragmaticsoftwaresolutions.Element.core.Tag#setParent(org.pragmaticsoftwaresolutions.streamxml.V0.xml.core.XmlNode)
	 */
	@Override
	public void setParent(XmlElement parent) {
		this.parent = parent;
	}

	/**
	 * @see org.pragmaticsoftwaresolutions.Element.core.Tag#add(org.pragmaticsoftwaresolutions.streamxml.V0.xml.core.XmlNode)
	 */
	public XmlNode add(XmlNode child) {
        children.add(child);
        child.setParent(this);
        return child;
	}

	/**
	 * @see org.pragmaticsoftwaresolutions.Element.core.Tag#streamXmlPartially(java.io.OutputStreamWriter, org.pragmaticsoftwaresolutions.streamxml.V0.xml.core.XmlNode)
	 */
	@Override
	public void streamXmlPartially(OutputStreamWriter writer, XmlNode child, XmlNode callingDecendentChild)
			throws IOException {
        boolean isChild = false;
        for (Iterator<XmlNode> iter = children.iterator(); iter.hasNext();) {
        	XmlNode aChild = iter.next();
            if(aChild == child) {
                isChild = true;
                break;
            }
        }
        if(!isChild) {
            throw new IllegalArgumentException("The passed element isn't a child of this element.");
        }
        if(getParent() != null) {
            getParent().streamXmlPartially(writer, this, callingDecendentChild);
        }
        streamStart(writer);
        for (Iterator<XmlNode> iter = children.iterator(); iter.hasNext();) {
        	XmlNode aChild = (XmlNode) iter.next();
            if(aChild == child && aChild != callingDecendentChild) {
                break;
            }
            aChild.setParent(null);
            iter.remove();
            aChild.streamXml(writer);
            hasStreamedAChild = true;
            aChild.release();
        }
	}
	
	@Override
	public Element attr(String name, String value) {
		attributes.put(name,value);
		return this;
	}

	@Override
	public Element elem(String name) {
		XmlElementV0 elem = create();
		elem.name = name;
		elem.namespace = this.namespace;
		elem.isSelfClosingTag = this.isSelfClosingTag;
		add(elem);
		return elem;
	}
	
	@Override
	public Element elem(String nameSpace, String name) {
		XmlElementV0 elem = create();
		elem.namespace = nameSpace;
		elem.name = name;
		elem.isSelfClosingTag = this.isSelfClosingTag;
		add(elem);
		return elem;
	}

	@Override
	public Element elem(String name, boolean selfClosing) {
		XmlElementV0 elem = create();
		elem.name = name;
		elem.namespace = this.namespace;
		elem.isSelfClosingTag = selfClosing;
		add(elem);
		return elem;
	}
	
	
	@Override
	public Element elem(String namespace, String name, boolean selfClosing) {
		XmlElementV0 elem = create();
		elem.name = name;
		elem.namespace = namespace;
		elem.isSelfClosingTag = selfClosing;
		add(elem);
		return elem;
	}
	
	@Override
	public Element procInstr(String target, String content) {
		XmlProcessingInstruction pi = procInstr.create(target, content);
		add(pi);
		return this;
	}
	
	@Override
	public Element cdata(String cdata) {
		XmlCData xmlCData = cData.create(cdata);
		add(xmlCData);
		return this;
	}
	
	@Override
	public Element comment(String comment) {
		XmlComment xmlComment = this.comment.create(comment);
		add(xmlComment);
		return this;
	}
	
	
	@Override
	public Element pcdata(String text) {
		XmlPCData pcdata = pcData.create(text);
		add(pcdata);
		return this;
	}
	
	private void init() {
		attributes.clear();
		children.clear();
		isAlreadyStreamed = false;
		isStartStreamed = false;
		hasStreamedAChild = false;
		isSelfClosingTag = true;
	}
	
	private XmlElementV0 create() {
		XmlElementV0 elem = pool.remove();
		if(elem != null) {
			elem.init();
			return elem;
		}
		elem = new XmlElementV0();
		elem.pool = pool;
		elem.cData = cData;
		elem.pcData = pcData;
		elem.comment = comment;
		elem.procInstr = procInstr;
		return elem;
	}
	
	@Override
	public void release() {
		pool.add(this);
	}
	
}
