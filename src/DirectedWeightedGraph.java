import com.sun.javafx.geom.Edge;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

class DirectedWeightedGraph {
    HashMap<Vertex, ArrayList<Vertex>> adj = new HashMap<>();
    HashMap<Vertex, HashMap<Vertex, Integer>> weights = new HashMap<>();

    public DirectedWeightedGraph(Vertex[] vertices) {
        for (Vertex v : vertices) {
            this.adj.put(v, new ArrayList<Vertex>());
        }
    }

    // add edge from u to v
    public void addEdge(Vertex u, Vertex v, int weight) {
        this.adj.get(u).add(v);
        HashMap<Vertex, Integer> w1 = weights.get(u);
        if (w1 == null) {
            weights.put(u, new HashMap<Vertex, Integer>());
        }
        weights.get(u).put(v, weight);
    }

    public int getWeight(Vertex u, Vertex v) {
        return this.weights.get(u).get(v);
    }

    public static void main(String[] main) {
        Vertex[] vertices = { new Vertex(1, "One Street"), new Vertex(2, "Two Street"), new Vertex(3, "Three Street"), new Vertex(4, "Four Street") };
        DirectedWeightedGraph graph = new DirectedWeightedGraph(vertices);
        graph.addEdge(vertices[0], vertices[1], 6);
        graph.addEdge(vertices[1], vertices[2], 7);
        graph.addEdge(vertices[2], vertices[1], 4);
        graph.addEdge(vertices[2], vertices[0], 5);
        graph.addEdge(vertices[3], vertices[2], 10);
        int w = graph.getWeight(vertices[0], vertices[1]);
    }
}