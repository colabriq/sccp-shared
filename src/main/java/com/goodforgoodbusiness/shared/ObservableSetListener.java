package com.goodforgoodbusiness.shared;

public interface ObservableSetListener<T> {
	public void added(T e);
	public void removed(T e);
}
