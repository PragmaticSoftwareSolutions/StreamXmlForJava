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

import org.pragmaticsoftwaresolutions.streamxml.V0.ConfigurationV0_0_1;


/**
 * @author Kris Leenaerts
 *
 * The Element factory.
 * 
 */
public abstract class ElementFactory {

	private static class ElementFactoryHolder  {
		/** version 0.0.1 */
		private final static ElementFactory instance = new ConfigurationV0_0_1();
	}

	/**
	 * Get the instance of the element factory.
	 *  
	 * @return instance of the element factory
	 */
	public static ElementFactory getInstance() {
		return ElementFactoryHolder.instance;
	}

	/**
	 * <p>
	 * Creates a root element with the passed name.
	 * The namespace property will be null.
	 * The selfClosing property will be true.
	 * </p>
	 * 
	 * @param name the name of the created element
	 * @return the created root element
	 */
	public abstract Element root(String name);

	/**
	 * <p>
	 * Creates a root element with the passed name.
	 * The namespace property will be null.
	 * </p>
	 * 
	 * @param name the name of the created element
     * @param selfClosing when true streamed as an empty element when it doesn't has child nodes
	 * @return the created root element
	 */
	public abstract Element root(String name, boolean selfClosing);
	
	/**
	 * <p>
	 * Creates a root element with passed name and namespace.
     * An empty namespace can be obtained by passing null.
	 * The selfClosing property will be true.
	 * </p>
	 * 
	 * @param namespace the namespace of the created element
	 * @param name the name of the created element
	 * @return the created root element
	 */
	public abstract Element root(String namespace, String name);
	
	/**
	 * <p>
	 * Creates a root element with passed name and namespace.
     * An empty namespace can be obtained by passing null.
	 * </p>
	 * 
	 * @param namespace the namespace of the created element
	 * @param name the name of the created element
	 * @param selfClosing when true streamed as an empty element when it doesn't has child nodes
	 * @return the created root element
	 */
	public abstract Element root(String namespace, String name, boolean selfClosing);
	
}
