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

	@SuppressWarnings("unchecked")
	private static void initializeVerticesAndEdges(Integer numberOfVertices) {
		verticesAndEdges = new ArrayList[numberOfVertices];
		for (int i = 0; i < numberOfVertices; i++) {
			verticesAndEdges[i] = new ArrayList<Edge>();
		}
	}

	private static void addEdgeInBothDirections(Integer vertexOne, Integer vertexTwo, Integer edgeLength)
			throws IllegalArgumentException {
		verticesAndEdges[vertexOne - 1].add(new Edge(vertexTwo, edgeLength));
		verticesAndEdges[vertexTwo - 1].add(new Edge(vertexOne, edgeLength));
	}

	private static void breadthFirstSearch(Integer numberOfVertices, Integer startVertex) {
		distanceFromStart = new int[numberOfVertices];
		Arrays.fill(distanceFromStart, Integer.MAX_VALUE);
		distanceFromStart[startVertex - 1] = 0;

		boolean[] visited = new boolean[numberOfVertices];

		List<Integer> queue = new LinkedList<Integer>();
		queue.add(startVertex);

		while (!queue.isEmpty()) {

			int current = queue.remove(0);

			for (Edge edge : verticesAndEdges[current - 1]) {
				if (visited[edge.getEndVertex() - 1] == false) {
					visited[edge.getEndVertex() - 1] = true;

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

	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
		int numberOfTestCases = Integer.parseInt(stringTokenizer.nextToken());

		for (int i = 0; i < numberOfTestCases; i++) {

			stringTokenizer = new StringTokenizer(bufferedReader.readLine());
			int numberOfVertices = Integer.parseInt(stringTokenizer.nextToken());
			int numberOfEdges = Integer.parseInt(stringTokenizer.nextToken());
			initializeVerticesAndEdges(numberOfVertices);

			for (int j = 0; j < numberOfEdges; j++) {
				stringTokenizer = new StringTokenizer(bufferedReader.readLine());
				int vertexOne = Integer.parseInt(stringTokenizer.nextToken());
				int vertexTwo = Integer.parseInt(stringTokenizer.nextToken());
				/** Each edge is 6 units by definition. */
				int edgeLength = 6;
				addEdgeInBothDirections(vertexOne, vertexTwo, edgeLength);
			}

			stringTokenizer = new StringTokenizer(bufferedReader.readLine());
			int startVertex = Integer.parseInt(stringTokenizer.nextToken());

			breadthFirstSearch(numberOfVertices, startVertex);
			printResults(startVertex);
		}
		bufferedReader.close();
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
