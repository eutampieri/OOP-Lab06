package it.unibo.oop.lab06.generics1;

import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.HashMap;

/**
 * This is <b>not</b> a <b>bidirectional</b> graph
 * @author Eugenio Tampieri
 *
 * @param <N> Node type
 */
public final class GraphImpl<N> implements Graph<N> {
	
	/*private final class Pair<N> {
		private final N first;
		private final int second;
		
		public Pair(N first, int second) {
			this.first = first;
			this.second = second;
		}
	}*/
	
	private Map<N, Set<N>> graph;
	
	public GraphImpl() {
		this.graph = new HashMap<>();
	}

	public void addNode(N node) {
		if(!graph.containsKey(node)) {
			graph.put(node, new HashSet<>());
		}
		
	}

	public void addEdge(N source, N target) {
		graph.get(source).add(target);
		//graph.get(target).add(source);
		
	}

	public Set<N> nodeSet() {
		return new HashSet<N>(this.graph.keySet());
	}

	public Set<N> linkedNodes(N node) {
		return new HashSet<N>(this.graph.get(node));
	}

	public List<N> getPath(N source, N target) {
		if(source.equals(target)) {
			System.out.println("Returning!!");
			return new ArrayList<>(List.of(target));
		}
		System.out.println("Getting path from " + source + " to " + target);
		List<N> optimalPath = this.graph.get(source)
				.stream()
				.map(n -> getPath(n, target))
				.min(new Comparator<List<N>>() {
					public int compare(List<N> o1, List<N> o2) {
						return Integer.valueOf(o1.size()).compareTo(o2.size());
					}
				})
				.orElse(List.of());
		optimalPath.add(0, source);
		return optimalPath;
	}

}
