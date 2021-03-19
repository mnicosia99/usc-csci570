package net.nicosia.graph;

import java.util.LinkedList;
import java.util.Queue;

public class GraphUtil {

    // A DFS based function to find all reachable vertices from s. The function
    // marks visited[i] as true if i is reachable from s. The initial values in
    // visited[] must be false. We can also use BFS to find reachable vertices
    public static void dfs(int[][] rGraph, int s, boolean[] visited) {
        visited[s] = true;
        for (int i = 0; i < rGraph.length; i++) {
            if (rGraph[s][i] > 0 && !visited[i]) {
                dfs(rGraph, i, visited);
            }
        }
    }

    // Returns true if there is a path from source 's' to sink 't' in residual
    // graph. Also fills parent[] to store the path
    public static boolean bfs(int[][] rGraph, int s, int t, int[] parent) {

        // Create a visited array and mark all vertices as not visited
        boolean[] visited = new boolean[rGraph.length];

        // Create a queue, enqueue source vertex and mark source vertex as visited
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(s);
        visited[s] = true;
        parent[s] = -1;

        // Standard BFS Loop
        while (!q.isEmpty()) {
            int v = q.poll();
            for (int i = 0; i < rGraph.length; i++) {
                if (rGraph[v][i] > 0 && !visited[i]) {
                    q.offer(i);
                    visited[i] = true;
                    parent[i] = v;
                }
            }
        }

        // If we reached sink in BFS starting from source, then return true, else false
        return (visited[t] == true);
    }
}
