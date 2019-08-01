package com.colabriq.shared;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.jena.util.iterator.ExtendedIterator;

public class IteratorExtendedIterator<T> implements ExtendedIterator<T> {
	private Iterator<? extends T> it;

	public IteratorExtendedIterator(Iterator<? extends T> it) {
		this.it = it;
	}
	
	@Override
	public void close() {
	}

	@Override
	public boolean hasNext() {
		return it.hasNext();
	}

	@Override
	public T next() {
		return it.next();
	}

	@Override
	public T removeNext() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <X extends T> ExtendedIterator<T> andThen(Iterator<X> other) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ExtendedIterator<T> filterKeep(Predicate<T> f) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ExtendedIterator<T> filterDrop(Predicate<T> f) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <U> ExtendedIterator<U> mapWith(Function<T, U> map1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<T> toList() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<T> toSet() {
		throw new UnsupportedOperationException();
	}
}
