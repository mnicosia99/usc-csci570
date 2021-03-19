package net.nicosia;

import net.nicosia.graph.GraphUtil;
import net.nicosia.visualization.GraphVisualization;

class FordFulkerson {

    // Prints the minimum s-t cut
    // Returns tne maximum flow from s to t in the given graph
    private static int fordFulkerson(int[][] graph, int s, int t) {
        int u,v;

        // Create a residual graph and fill the residual graph with given capacities
        // in the original graph as residual capacities in residual graph rGraph[i][j]
        // indicates residual capacity of edge i-j
        int[][] rGraph = new int[graph.length][graph.length];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                rGraph[i][j] = graph[i][j];
            }
        }

        // This array is filled by BFS and to store path
        int[] parent = new int[graph.length];

        int max_flow = 0; // There is no flow initially

        // Augment the flow while tere is path from source to sink
        while (GraphUtil.bfs(rGraph, s, t, parent)) {

            // Find minimum residual capacity of the edges along the path
            // filled by BFS. Or we can say find the maximum flow through the path found.
            int pathFlow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                pathFlow = Math.min(pathFlow, rGraph[u][v]);
            }

            // update residual capacities of the edges and reverse edges along the path
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                rGraph[u][v] = rGraph[u][v] - pathFlow;
                rGraph[v][u] = rGraph[v][u] + pathFlow;
            }
            // Add path flow to overall flow
            max_flow += pathFlow;
        }

        // Flow is maximum now, find vertices reachable from s
        boolean[] isVisited = new boolean[graph.length];
        GraphUtil.dfs(rGraph, s, isVisited);

        // Print all edges that are from a reachable vertex to non-reachable vertex in the original graph
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                if (graph[i][j] > 0 && isVisited[i] && !isVisited[j]) {
                    System.out.println(i + " - " + j);
                }
            }
        }
        GraphVisualization GA1 = new GraphVisualization();
        GA1.Visualize_Directed_Graph(rGraph);
        return max_flow;
    }

    // Driver program to test above functions
    public static void main(String[] args) throws java.lang.Exception {
        // Let us create a graph shown in the above example
        int graph[][] = new int[][] {
            { 0, 16, 13, 0, 0, 0 }, { 0, 0, 12, 14, 0, 0 },
            { 0, 4, 0, 0, 14, 0 },  { 0, 0, 9, 0, 0, 20 },
            { 0, 0, 0, 7, 0, 4 },   { 0, 0, 0, 0, 0, 0 }
        };

        System.out.println("The maximum possible flow is " + fordFulkerson(graph, 0, 5));
    }
}
