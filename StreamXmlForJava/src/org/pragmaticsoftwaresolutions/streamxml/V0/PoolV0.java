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

import java.util.ArrayList;
import java.util.List;

/**
 * First pool implementation
 * 
 * @author Kris Leenaerts
 *
 * @param <T>
 */
public class PoolV0<T> implements Pool<T> {

	/** the size of the pool */
	private int size = 1000;

	/**position */
	private int position = -1;

	/** the pool */
	private List<T> pool = new ArrayList<T>(size);
	
	public PoolV0() {
		for(int i=0;i<=size;i++) {
			pool.add(null);
		}
	}
	
	@Override
	public Pool<T> add(T poolable) {
		if((position + 1) < size && poolable != null) {
			position++;
			pool.set(position, poolable);
		}
		return this;
	}
	
	@Override
	public T remove() {
		if(position > 0) {
			T poolable = pool.set(position, null);
			position--;
			return poolable;
		}
		return null;
	}
	
	
}
