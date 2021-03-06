package amazon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Yelp can recommend businesses based on a distance you're willing to travel.

Given a distance input and a connected acyclic graph of businesses with edges as distances, return the list of names of businesses within the distance input.

Args:

starting business: a Business object to start from
distance: int
Output:

list of str: A list of Business names that are within the given distance of the starting Business
Distance is inclusive, meaning if a business is 5 away, then a distance input of 5 means that business IS reachable.

The return value should NOT have the name of the starting business. Therefore, if no businesses are within the given distance, return an empty list.

The return value is NOT required to be sorted.

Example:

Consider the following graph with distances where business A is the starting business.

           A
         /  \
      2 /    \ 4
       /      \
      B        C
       \
        \ 5
         \
          D

findReachableBusinesses(A, 1); // should return an empty list
findReachableBusinesses(A, 2); // should return ["B"]
findReachableBusinesses(A, 3); // should also return ["B"]
findReachableBusinesses(A, 4); // should return ["B", "C"]
findReachableBusinesses(A, 7); // should return ["B", "C", "D"]
 */
public class RecommendBusinesses {
	/**
	 * The name of the business.
	 */
	String name;

	/**
	 * A Map of nearbyBusinesses where the key is the nearby Business object
	 * and the value is distance from the current Business to the nearby Business.
	 */
	Map<RecommendBusinesses, Integer> nearbyBusinesses;

	public RecommendBusinesses(String name) {
		this.name = name;
		this.nearbyBusinesses = new HashMap<>();
	}

	public String getName() {
		return this.name;
	}

	public Map<RecommendBusinesses, Integer> getNearbyBusinesses() {
		return this.nearbyBusinesses;
	}
}

class Solution {
	public static List<String> findReachableBusinesses(RecommendBusinesses startingBusiness, int distance) {
		// todo
		return null;
	}
}
