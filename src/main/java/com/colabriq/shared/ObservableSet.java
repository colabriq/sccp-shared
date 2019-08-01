package com.colabriq.shared;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public class ObservableSet<T> implements Set<T> {
	private Set<ObservableSetListener<T>> listeners = Collections.newSetFromMap(new WeakHashMap<>());
	
	private Set<T> set;
	private Class<T> cls;

	/**
	 * Construct an observable set.
	 * Requires the runtime class because #remove is broken in Java.
	 */
	public ObservableSet(Set<T> set, Class<T> cls) {
		this.set = set;
		this.cls = cls;
	}
	
	/** Add a listener for adds/removes from the Set */
	public void addListener(ObservableSetListener<T> listener) {
		this.listeners.add(listener);
	}
	
	/** Remove a listener previously added */
	public void removeListener(ObservableSetListener<T> listener) {
		this.listeners.remove(listener);
	}
	
	@Override
	public int size() {
		return set.size();
	}

	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return set.contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		var it = set.iterator();
		
		return new Iterator<>() {
			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public T next() {
				return it.next();
			}
		};
	}

	@Override
	public boolean add(T e) {
		if (set.add(e)) {
			listeners.forEach(listener -> listener.added(e));
			return true;
		}
		
		return false;
	}

	@Override
	public boolean remove(Object o) {
		if (cls.isInstance(o)) {
			T e = cls.cast(o);
			if (set.remove(e)) {
				for (var listener : listeners) {
					listener.removed(e);
				}
				
//				listeners.forEach(listener -> listener.removed(e));
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		var result = false;
		for (var o : c) {
			result |= add(o);
		}
		
		return result;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		var remove = new HashSet<T>();
		
		for (var o : set) {
			if (!c.contains(o)) {
				remove.add(o);
			}
		}
		
		return removeAll(remove);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		var result = false;
		for (var o : c) {
			result |= remove(o);
		}
		
		return result;
	}

	@Override
	public void clear() {
		// hmmm
	}
	
	@Override
	public Object[] toArray() {
		return set.toArray();
	}

	@Override
	public <A> A[] toArray(A[] a) {
		return set.toArray(a);
	}
}
