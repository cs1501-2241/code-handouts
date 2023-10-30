/*************************************************************************
 *  Compilation:  javac FordFulkerson.java
 *  Execution:    java FordFulkerson V E
 *  Dependencies: FlowNetwork.java FlowEdge.java Queue.java
 *
 *  Ford-Fulkerson algorithm for computing a max flow and 
 *  a min cut using shortest augmenthing path rule.
 *
 *********************************************************************/

// Modified for CS 1501 Summer 2018
// I added the option of reading the FlowNetwork from a file, and to show
// each augmenting path as it is determined.

// I also added the option to use PFS for the augmenting path, and an option to
// choose which approach to use via a command line argument.  See more comments
// in the hasAugmentingPathPFS method below.

public class FordFulkerson {
    private boolean[] marked;     // marked[v] = true iff s->v path in residual graph
    private FlowEdge[] edgeTo;    // edgeTo[v] = last edge on shortest residual s->v path
    private double value;         // current value of max flow
    private IndexMaxPQ<Double> pq;  // for PFS
    private double[] distTo; 		// for PFS
  
    // max flow in flow network G from s to t
    public FordFulkerson(FlowNetwork G, int s, int t, String algo) {
        value = excess(G, t);
        if (!isFeasible(G, s, t)) {
            throw new RuntimeException("Initial flow is infeasible");
        }

        // while there exists an augmenting path, use it
        while (hasAugmentingPath(G, s, t, algo)) {

            // compute bottleneck capacity
            double bottle = Double.POSITIVE_INFINITY;
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
            }

			System.out.print("Augmenting Path (in reverse): ");
            // augment flow
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                edgeTo[v].addResidualFlowTo(v, bottle); 
                System.out.print(v + ", ");
            }
			System.out.println(s + " : " + bottle);
            value += bottle;
        }
        // check optimality conditions
        assert check(G, s, t);
    }

    // return value of max flow
    public double value()  {
        return value;
    }

    // is v in the s side of the min s-t cut?
    public boolean inCut(int v)  {
        return marked[v];
    }

	private boolean hasAugmentingPath(FlowNetwork G, int s, int t, String algo)
	{
		if (algo.equals("BFS"))
			return hasAugmentingPathBFS(G, s, t);
		else // PFS
			return hasAugmentingPathPFS(G, s, t);
	}

    // return an augmenting path if one exists, otherwise return null
    private boolean hasAugmentingPathBFS(FlowNetwork G, int s, int t) {
        edgeTo = new FlowEdge[G.V()];
        marked = new boolean[G.V()];

        // breadth-first search
        Queue<Integer> q = new Queue<Integer>();
        q.enqueue(s);
        marked[s] = true;
        while (!q.isEmpty()) {
            int v = q.dequeue();

            for (FlowEdge e : G.adj(v)) {
                int w = e.other(v);

                // if residual capacity from v to w
                if (e.residualCapacityTo(w) > 0) {
                    if (!marked[w]) {
                        edgeTo[w] = e;
                        marked[w] = true;
                        q.enqueue(w);
                    }
                }
            }
        }
        // is there an augmenting path?
        return marked[t];
    }

	// Find an augmenting path using PFS (Priority First Search).  This is
	// the same algorithm used in Eager Prim, but with a MaxPQ rather than
	// a MinPQ.  Essentially, it is finding a "Maximum Spanning Tree" for the
	// graph and using the tree edges for the path from the source to the sink.
    private boolean hasAugmentingPathPFS(FlowNetwork G, int s, int t) {
        edgeTo = new FlowEdge[G.V()];
        marked = new boolean[G.V()];
        distTo = new double[G.V()];
        pq = new IndexMaxPQ<Double>(G.V());
        
        // initialize distances to negative infinity (as small as possible)
        for (int v = 0; v < G.V(); v++) distTo[v] = Double.NEGATIVE_INFINITY;
		 
		// Since distTo represents the flow that can get to a vertex, we
		// initialize the distTo of the source to infinity (as large as possible),
		// since the source can generate an "unlimited" amount of flow.
		distTo[s] = Double.POSITIVE_INFINITY;
		
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMax();
			marked[v] = true;
            for (FlowEdge e : G.adj(v)) {
                int w = e.other(v);
               	// get residual capacity from v to w
                double rescap = e.residualCapacityTo(w);
                if (rescap > distTo[v])
                	rescap = distTo[v]; // flow cannot be more than the flow
                						// coming into v
               
               if (rescap > 0) {	// residual capcity must be > 0
                    if (!marked[w]) {  // only process vertices not yet in tree
                    	if (rescap > distTo[w])
                    	{		// better candidate edge is found for the vertex
                    			// so update the data for the vertex
                    		distTo[w] = rescap;
                        	edgeTo[w] = e;
                        	if (pq.contains(w)) pq.changeKey(w, distTo[w]);
                			else pq.insert(w, distTo[w]);
                		}
                    }
                }
            }
        }
        // is there an augmenting path?
        return marked[t];
    }

    // return excess flow at vertex v
    private double excess(FlowNetwork G, int v) {
        double excess = 0.0;
        for (FlowEdge e : G.adj(v)) {
            if (v == e.from()) excess -= e.flow();
            else               excess += e.flow();
        }
        return excess;
    }

    // return excess flow at vertex v
    private boolean isFeasible(FlowNetwork G, int s, int t) {
        double EPSILON = 1E-11;

        // check that capacity constraints are satisfied
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v)) {
                if (e.flow() < 0 || e.flow() > e.capacity()) {
                    System.err.println("Edge does not satisfy capacity constraints: " + e);
                    return false;
                }
            }
        }

        // check that net flow into a vertex equals zero, except at source and sink
        if (Math.abs(value + excess(G, s)) > EPSILON) {
            System.err.println("Excess at source = " + excess(G, s));
            System.err.println("Max flow         = " + value);
            return false;
        }
        if (Math.abs(value - excess(G, t)) > EPSILON) {
            System.err.println("Excess at sink   = " + excess(G, t));
            System.err.println("Max flow         = " + value);
            return false;
        }
        for (int v = 0; v < G.V(); v++) {
            if (v == s || v == t) continue;
            else if (Math.abs(excess(G, v)) > EPSILON) {
                System.err.println("Net flow out of " + v + " doesn't equal zero");
                return false;
            }
        }
        return true;
    }

    // check optimality conditions
    private boolean check(FlowNetwork G, int s, int t) {

        // check that flow is feasible
        if (!isFeasible(G, s, t)) {
            System.err.println("Flow is infeasible");
            return false;
        }

        // check that s is on the source side of min cut and that t is not on source side
        if (!inCut(s)) {
            System.err.println("source " + s + " is not on source side of min cut");
            return false;
        }
        if (inCut(t)) {
            System.err.println("sink " + t + " is on source side of min cut");
            return false;
        }

        // check that value of min cut = value of max flow
        double mincutValue = 0.0;
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v)) {
                if ((v == e.from()) && inCut(e.from()) && !inCut(e.to()))
                    mincutValue += e.capacity();
            }
        }

        double EPSILON = 1E-11;
        if (Math.abs(mincutValue - value) > EPSILON) {
            System.err.println("Max flow value = " + value + ", min cut value = " + mincutValue);
            return false;
        }

        return true;
    }

    // test client that creates random network, solves max flow, and prints results
    public static void main(String[] args) {
    	FlowNetwork G = null;
    	int V, E;
    	String algo;
    	if (args.length > 2)
    	{
        	// create flow network with V vertices and E edges
        	V = Integer.parseInt(args[0]);
        	E = Integer.parseInt(args[1]);
        	algo = args[2];
        	G = new FlowNetwork(V, E);
    	}
    	else
    	{
    		G = new FlowNetwork(new In(args[0]));
    		V = G.V();
    		E = G.E();
    		algo = args[1];
    	}
    	int s = 0, t = V-1;
        StdOut.println(G);

        // compute maximum flow and minimum cut
        FordFulkerson maxflow = new FordFulkerson(G, s, t, algo);
        StdOut.println("Max flow from " + s + " to " + t);
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v)) {
                if ((v == e.from()) && e.flow() > 0)
                    StdOut.println("   " + e);
            }
        }

        // print min-cut
        StdOut.print("Min cut: ");
        for (int v = 0; v < G.V(); v++) {
            if (maxflow.inCut(v)) StdOut.print(v + " ");
        }
        StdOut.println();

        StdOut.println("Max flow value = " +  maxflow.value());
    }
}