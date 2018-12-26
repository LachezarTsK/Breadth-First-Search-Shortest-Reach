import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Solution {

	private static List<Edge>[] verticesAndEdges;
	private static int[] distanceFromStart;

	private static void addVertex(Integer nodeNumber) {
		if (verticesAndEdges[nodeNumber - 1] == null) {
			verticesAndEdges[nodeNumber - 1] = new ArrayList<Edge>();
		}
	}

	private static void addEdgeInBothDirections(Integer vertexOne, Integer vertexTwo, Integer edgeLength)
			throws IllegalArgumentException {
		verticesAndEdges[vertexOne - 1].add(new Edge(vertexTwo, edgeLength));
		verticesAndEdges[vertexTwo - 1].add(new Edge(vertexOne, edgeLength));
	}

	private static void breadthFirstSearch(Integer numberOfVertices, Integer startVertex) {
		Arrays.fill(distanceFromStart, Integer.MAX_VALUE);
		distanceFromStart[startVertex - 1] = 0;

		boolean[] visited = new boolean[numberOfVertices];

		List<Integer> queue = new LinkedList<Integer>();
		queue.add(startVertex);

		while (!queue.isEmpty()) {

			int current = queue.remove(0);

			if (visited[current - 1] == false) {
				visited[current - 1] = true;

				if (verticesAndEdges[current - 1] != null) {

					for (Edge edge : verticesAndEdges[current - 1]) {
						if (visited[edge.getEndVertex() - 1] == false) {

							int previousDistanceFromStart = distanceFromStart[edge.getEndVertex() - 1];
							int newDistanceFromStart = distanceFromStart[current - 1] + edge.getEdgeLength();
							queue.add(edge.getEndVertex());

							if (previousDistanceFromStart > newDistanceFromStart) {
								distanceFromStart[edge.getEndVertex() - 1] = newDistanceFromStart;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Prints minimum distances from start for all vertices except the start vertex.
	 */
	private static void printResults(Integer startVertex) {
		for (int r = 0; r < startVertex - 1; r++) {
			if (distanceFromStart[r] != Integer.MAX_VALUE) {
				System.out.print(distanceFromStart[r] + " ");
			} else {
				System.out.print("-1 ");
			}
		}

		for (int r = startVertex; r < distanceFromStart.length; r++) {
			if (distanceFromStart[r] != Integer.MAX_VALUE) {
				System.out.print(distanceFromStart[r] + " ");
			} else {
				System.out.print("-1 ");
			}
		}
		System.out.println();
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int numberOfTestCases = Integer.parseInt(st.nextToken());

		for (int i = 0; i < numberOfTestCases; i++) {

			st = new StringTokenizer(br.readLine());
			int numberOfVertices = Integer.parseInt(st.nextToken());
			int numberOfEdges = Integer.parseInt(st.nextToken());
			verticesAndEdges = new ArrayList[numberOfVertices];
			distanceFromStart = new int[numberOfVertices];

			for (int j = 0; j < numberOfEdges; j++) {
				st = new StringTokenizer(br.readLine());
				int vertexOne = Integer.parseInt(st.nextToken());
				int vertexTwo = Integer.parseInt(st.nextToken());
				// Each edge is 6 units by definition.
				int edgeLength = 6;
				addVertex(vertexOne);
				addVertex(vertexTwo);
				addEdgeInBothDirections(vertexOne, vertexTwo, edgeLength);
			}

			st = new StringTokenizer(br.readLine());
			int startVertex = Integer.parseInt(st.nextToken());

			breadthFirstSearch(numberOfVertices, startVertex);
			printResults(startVertex);
		}
	}
}

class Edge {

	private int edgeLength;
	private int endVertex;

	public Edge(Integer endVertex, Integer edgeLength) {
		this.endVertex = endVertex;
		this.edgeLength = edgeLength;
	}

	public int getEndVertex() {
		return this.endVertex;
	}

	public int getEdgeLength() {
		return edgeLength;
	}
}
