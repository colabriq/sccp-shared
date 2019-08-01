package com.colabriq.shared;

public interface ObservableSetListener<T> {
	public void added(T e);
	public void removed(T e);
}
