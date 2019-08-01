package com.goodforgoodbusiness.shared.treesort;

import java.util.stream.Stream;

public interface TreeNode<T> {
	T getValue();
	Stream<T> getPredecessors();
}
