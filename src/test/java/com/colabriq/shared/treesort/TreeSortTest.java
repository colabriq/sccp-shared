package com.colabriq.shared.treesort;

import static com.colabriq.shared.treesort.TreeSort.sort;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.System.out;
import static java.util.Arrays.asList;
import static java.util.Collections.shuffle;

public class TreeSortTest {
	public static void main(String [] args) {
		var graph1 = asList(
			new StringTreeNode("a", newHashSet()),
			new StringTreeNode("b", newHashSet("a")),
			new StringTreeNode("c", newHashSet("b", "a")),
			new StringTreeNode("d", newHashSet("a")),
			new StringTreeNode("e", newHashSet("d")),
			new StringTreeNode("f", newHashSet("e", "c"))
		);
		
		shuffle(graph1);
		out.println(graph1.toString());
		out.println(sort(graph1).toString());
		out.println();

		// cope with missing information (may be the case with claims)
		var graph2 = asList(
		    new StringTreeNode("a", newHashSet("x")),
		    new StringTreeNode("b", newHashSet("a")),
		    new StringTreeNode("c", newHashSet("b", "a")),
		    new StringTreeNode("d", newHashSet("a")),
		    new StringTreeNode("e", newHashSet("d", "q")),
		    new StringTreeNode("f", newHashSet("e", "c"))
		);

		shuffle(graph2);
		out.println(graph2.toString());
		out.println(sort(graph2, true).toString());
		out.println();

		// two separate graphs
		var graph3 = asList(
		    new StringTreeNode("a", newHashSet()),
		    new StringTreeNode("b", newHashSet("a")),
		    new StringTreeNode("c", newHashSet("b", "a")),
		    new StringTreeNode("w", newHashSet()),
		    new StringTreeNode("x", newHashSet()),
		    new StringTreeNode("y", newHashSet()),
		    new StringTreeNode("z", newHashSet("w", "x", "y"))
		);
		
		shuffle(graph3);
		out.println(graph3.toString());
		out.println(sort(graph3).toString());
		out.println();

		// jbo claims
		var graph4 = asList(
		    new StringTreeNode("a", newHashSet()),
		    new StringTreeNode("b", newHashSet()),
		    new StringTreeNode("c", newHashSet())
		);
		
		shuffle(graph4);
		out.println(graph4.toString());
		out.println(sort(graph4).toString());
		out.println();
		out.flush();
		
		// something with cycle
		// (show throw exception)
		var graph5 = asList(
		    new StringTreeNode("a", newHashSet("b")),
		    new StringTreeNode("b", newHashSet("c")),
		    new StringTreeNode("c", newHashSet("a"))
		);
		
		shuffle(graph5);
		out.println(graph5.toString());
		out.println(sort(graph5).toString());
	}
}
