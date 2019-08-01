package com.goodforgoodbusiness.shared.treesort;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

class StringTreeNode implements TreeNode<String> {
	private final String value;
	private final Set<String> predecessors;
	
	protected StringTreeNode(String value, Set<String> predecessors) {
		this.value = value;
		this.predecessors = Collections.unmodifiableSet(predecessors);
	}
	
	@Override
	public String getValue() {
		return this.value;
	}
	
	@Override
	public Stream<String> getPredecessors() {
		return this.predecessors.stream();
	}
	
	@Override
	public String toString() {
		return this.value;
	}
}