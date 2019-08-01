package com.colabriq.shared.treesort;

import java.util.stream.Stream;

public interface TreeNode<T> {
	T getValue();
	Stream<T> getPredecessors();
}
