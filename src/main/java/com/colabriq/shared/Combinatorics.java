package com.colabriq.shared;

public class Combinatorics {
	public static void main(String [] args) {
		// pick 1
		
		// (1 _ _ _)
		// (_ 2 _ _)
		// (_ _ 3 _)
		// (_ _ _ 4)
		
		// pick 2
		
		var input = new int [] { 1, 2, 3, 4 };
		
		// (1 2 _ _)
		// (1 _ 3 _)
		// (1 _ _ 4)
		
		// (_ 2 3 _)
		// (_ 2 _ 4)
		
		// (_ _ 3 4)
		
		for (int x = 0; x < input.length; x++) {
			for (int y = x + 1; y < input.length; y++) {
				for (int a = 0; a < input.length; a++) {
					if (a == x || a == y) {
						System.out.print(input[a] + " ");
					}
					else {
						System.out.print("  ");
					}
				}
				
				System.out.println();
			}
		}

	}
	
//	private static void round(int start, int count, int [] input, Deque<Integer> picked, List<Deque<Integer>> result) {
//		for (int x = start; x < input.length; x++) {
//			picked.addLast(input[x]);
//			if (picked.size() == count) {
//				result.add(picked);
//			}
//			else {
//				round(start + 1, count, input, picked, result);
//			}
//		}
//	}
}
