/*************************************************************************
 *  Compilation:  javac Graph.java        
 *  Execution:    java Graph V E
 *  Dependencies: Bag.java
 *
 *  A graph, implemented using an array of sets.
 *  Parallel edges and self-loops allowed.
 *  
 *************************************************************************/


/**
 *  The <tt>Graph</tt> class represents an undirected graph of vertices
 *  named 0 through V-1.
 *  It supports the following operations: add an edge to the graph,
 *  iterate over all of the neighbors adjacent to a vertex.
 *  Parallel edges and self-loops are permitted.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/51undirected">Section 5.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class Graph {
    private final int V;
    private int E;
    private Bag<Integer>[] adj;
    
   /**
     * Create an empty graph with V vertices.
     */
    public Graph(int V) {
        if (V < 0) throw new RuntimeException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }

   /**
     * Create a random graph with V vertices and E edges.
     * Expected running time is proportional to V + E.
     */
    public Graph(int V, int E) {
        this(V);
        if (E < 0) throw new RuntimeException("Number of edges must be nonnegative");
        for (int i = 0; i < E; i++) {
            int v = (int) (Math.random() * V);
            int w = (int) (Math.random() * V);
            addEdge(v, w);
        }
    }

   /**
     * Create an undirected graph from input stream using whitespace as delimiter.
     */
    public Graph(In in) {
        this(in.readInt());
        int E = in.readInt();
        String discard = in.readLine();
        while (!in.isEmpty()) {
            String line = in.readLine().trim();
            String[] list = line.split("\\s+");
            int v = Integer.parseInt(list[0]);
            for (int j = 1; j < list.length; j++) {
                int w = Integer.parseInt(list[j]);
                addEdge(v, w);
            }
        }
    }

   /**
     * Return the number of vertices in the graph.
     */
    public int V() { return V; }

   /**
     * Return the number of edges in the graph.
     */
    public int E() { return E; }


   /**
     * Add the edge v-w to graph.
     */
    public void addEdge(int v, int w) {
        E++;
        adj[v].add(w);
        adj[w].add(v);
    }


   /**
     * Return the list of neighbors of vertex v as in Iterable.
     */
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }


   /**
     * Return a string representation of the graph.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }


   /**
     * Test client.
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        StdOut.println(G);
    }

}
