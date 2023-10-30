/*************************************************************************
 *  Compilation:  javac Biconnected.java
 *  Dependencies: Graph.java 
 *
 *  Identify articulation points and print them out.
 *  This can be used to decompose a graph into biconnected components.
 *  Runs in O(E + V) time.
 *
 *  http://www.cs.brown.edu/courses/cs016/book/slides/Connectivity2x2.pdf
 *
 *************************************************************************/
// CS 1501 Summer 2018 Trace Version of the Biconnected Components Program
// This is the author's code with trace additions from me.  Part of the trace code that
// I am using is ANSI escape codes to show the output in different colors on the console.

// These ANSI escape codes render fine on a Mac but they are somewhat problematic on
// Windows machines.  If any of you Windows users can get them to render please let me know
// how you did it and I will post it.

// In the meantime, this program will still run on Windows machines, but the escape codes
// will show up as odd looking text in the output.

// I have also posted the output in color -- see the CS 1501 Handouts page.

public class BiconnectedTrace {
  
  	// ANSI codes to show colors and highlights on terminal display.  Note that this may not work
  	// on all terminals (ex: some Windows terminals)
	public static final String RESET = "\u001B[0m";
	public static final String UNDER = "\u001B[4m";
	public static final String BOLD = "\u001B[1m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_MAGENTA = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
  
  	private static final String [] colors = {ANSI_RED, ANSI_GREEN, ANSI_YELLOW,
  									ANSI_BLUE, ANSI_MAGENTA, ANSI_CYAN};
    private int[] low;
    private int[] pre;
    private String [] col;	// color for vertices
    private String [] ind;  // indent level for vertices
    private int cnt;
    private int bridges;
    private boolean[] articulation;

    public BiconnectedTrace(Graph G) {
        low = new int[G.V()];
        pre = new int[G.V()];
        col = new String[G.V()];
        ind = new String[G.V()];
        articulation = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) low[v] = -1;
        for (int v = 0; v < G.V(); v++) pre[v] = -1;
        StringBuilder indent = new StringBuilder("");
        for (int v = 0; v < G.V(); v++)
        {
 			ind[v] = indent.toString();  
        	col[v] = colors[(v % colors.length)];
        	indent.append("     ");
        }
        
        for (int v = 0; v < G.V(); v++)
            if (pre[v] == -1)
                dfs(G, v, v);
    }

    private void dfs(Graph G, int u, int v) {
        int children = 0;
        System.out.println(ind[v] + col[v] + "Visiting: " + v + RESET);
        pre[v] = cnt++;
        low[v] = pre[v];
        System.out.println(ind[v] + col[v] + "Initially: pre[v] = " + pre[v] + " and low[v] = " + low[v] + RESET);
        for (int w : G.adj(v)) {  // iterate through neighbors of current vertex
            if (pre[w] == -1) {	  // if neighbor has not yet been visited
            	System.out.println(ind[v] + col[v] + "Recursing to child: " + w + RESET);
                children++;		  // parent has another childe
                dfs(G, v, w);     // visit that child
				System.out.println(ind[v] + col[v] + "Back to " + v + " after recursion " + RESET);
                // update low number to minimum of previous low
                // for v and low of child (obtained recursively).  
                // 	  In other words, if child can get "back up" the tree in some
                // other way, then this vertex can do the same by going down to
                // the child and then back around.
                System.out.println(ind[v] + col[v] + "Child " + w + " low[w]: " + low[w] + RESET);
                if (low[w] < low[v])
                {
                	System.out.println(ind[v] + UNDER + col[v] + low[w] + " < " + low[v] + " so update low[v] to " + low[w] + RESET);
                	low[v] = low[w];
                }
                //low[v] = Math.min(low[v], low[w]);

                // non-root of DFS is an articulation point if low[w] >= pre[v].
                //    In other words, the child vertex w cannot get to a vertex
                // above the parent vertex v without going through v.
                if (low[w] >= pre[v] && u != v)
                {
                	System.out.println(ind[v] + col[v] + BOLD + "Child " + w + " low[w] = " + low[w] + " >= pre[v] = " + pre[v] + RESET);
                	System.out.println(ind[v] + col[v] + BOLD + "     " + v + " is an articulation point" + RESET);
                    articulation[v] = true;
                }
            }

            // update low number - ignore reverse of edge leading to v
            // In other words, we are using a back edge to get around
            // the parent and reach a higher DFS number.  Note that the if test on this will prevent a node
            // from using the edge to its parent to update its low value.
           else if (w != u)
           {
           		System.out.println(ind[v] + col[v] + "Back edge to " + w + " with DFS# " + pre[w] + RESET);
           		if (pre[w] < low[v])
           		{
           				System.out.println(ind[v] + UNDER + col[v] + pre[w] + " < " + low[v] + " so update low[v] to " + pre[w] + RESET);
           				low[v] = pre[w];
           		}
           		//low[v] = Math.min(low[v], pre[w]);
           	}
                
        }

        // root of DFS is an articulation point if it has more than 1 child
        if (u == v && children > 1)
        {
        
            articulation[v] = true;
            System.out.println(ind[v] + col[v] + BOLD + "Root " + v + " has 2+ children" + RESET);
            System.out.println(ind[v] + col[v] + BOLD + "     " + v + " is an articulation point" + RESET);
        }
        
        System.out.println(ind[v] + col[v] + "Final low[v] value for " + v + ": " + low[v] + RESET);
    }

    // is vertex v an articulation point?
    public boolean isArticulation(int v) { return articulation[v]; }

    // test client
    public static void main(String[] args) {
	    In in = new In(args[0]);
        Graph G = new Graph(in);
        System.out.println(G);

        BiconnectedTrace bic = new BiconnectedTrace(G);

        // print out articulation points
        System.out.println();
        System.out.println("Articulation points");
        System.out.println("-------------------");
        for (int v = 0; v < G.V(); v++)
            if (bic.isArticulation(v)) System.out.println(v);
    }

}
