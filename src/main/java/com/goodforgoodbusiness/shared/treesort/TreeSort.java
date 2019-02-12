package com.goodforgoodbusiness.shared.treesort;

import java.util.LinkedList;
import java.util.List;
import static java.util.stream.Collectors.toMap;

import org.apache.commons.lang3.mutable.MutableInt;

/**
 * Implement Kahn's algorithm to sort trees
 */
public class TreeSort {	
	public static <T, X extends TreeNode<T>> List<X> sort(List<X> graph) {
		return sort(graph, false);
	}
	
	public static <T, X extends TreeNode<T>> List<X> sort(List<X> graph, boolean tolerateIncomplete) {
		//  build degree map and calculate degrees 
		var inDegree = graph.stream().collect(toMap(v -> v, v -> new MutableInt(0)));

		for (var u : graph) {
			u.getPredecessors().forEach(
				v -> {
			      var found = false;
			      
			      for (var s : inDegree.keySet()) {
			    	  if (s.getValue().equals(v)) {
			    		  found = true;
			    		  inDegree.get(s).increment();
			    		  break;
			    	  }
			      }
			      
			      if (!found && !tolerateIncomplete) {
			    	  throw new TreeSortMissingPredecessorException(u, v);
			      }
				}
			);
		}
		
		// collect initial nodes with zero degree
		var Q = new LinkedList<X>();
		
		for (var ud : inDegree.entrySet()) {
			if (ud.getValue().intValue() == 0) {
				Q.addFirst(ud.getKey());
			}
		}
		
		// list for order of nodes
		var result = new LinkedList<X>();
		
		// iterate over dequeue finding nodes to add to result
		while (Q.size() > 0) {
			// Q contains nodes of 0 degree
			// remove one and add it to the results
			var u = Q.removeLast();
			result.addFirst(u);
			
			// find 'clear' nodes to add to Q
		    u.getPredecessors().forEach(
		    	v -> {
			    	for (var sd : inDegree.entrySet()) {
			    		if (sd.getKey().getValue().equals(v)) {
			    			var degree = inDegree.get(sd.getKey());
			    			degree.decrement();
			    			if (degree.intValue() == 0) {
			    				Q.addFirst(sd.getKey());
			    			}
			    		}		    		
			    	}
			    }
		    );
		}
		
		if (result.size() != graph.size()) {
			throw new IllegalArgumentException("Graph contains cycle");
		}
		  
		return result;
	}
}
