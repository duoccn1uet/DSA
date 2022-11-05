import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Graph;

public class SAP {

    private final Digraph graph;
    private final int V;

    public SAP(Digraph graph) {
        if (graph == null)
            throw new IllegalArgumentException("null graph passed in constructor SAP(Digraph)");
        this.graph = new Digraph(graph);
        V = graph.V();
    }

    private boolean isValidVertex(Integer u) {
        if (u == null)
            return false;
        return 0 <= u && u < V;
    }

    private boolean isValidVertexList(Iterable<Integer> vertexList) {
        if (vertexList == null)
            return false;
        for (Integer vertex : vertexList)
            if (!isValidVertex(vertex))
                return false;
        return true;
    }

    // length of the shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (!isValidVertex(v) || !isValidVertex(w))
            throw new IllegalArgumentException("Invalid vertex passed in function SAP::length");

        int ancestor = ancestor(v, w);
        if (ancestor == -1)
            return -1;

        BreadthFirstDirectedPaths vPath = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths wPath = new BreadthFirstDirectedPaths(graph, w);
        return vPath.distTo(ancestor) + wPath.distTo(ancestor);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (!isValidVertex(v) || !isValidVertex(w))
            throw new IllegalArgumentException("Invalid vertex passed in function SAP::ancestor");

        BreadthFirstDirectedPaths vPath = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths wPath = new BreadthFirstDirectedPaths(graph, w);
        
        int ancestor = -1;
        int length = Integer.MAX_VALUE;
        for (int u = 0; u < V; ++u)
            if (vPath.hasPathTo(u) && wPath.hasPathTo(u)) {
                int tmp = vPath.distTo(u) + wPath.distTo(u);
                if (tmp < length) {
                    length = tmp;
                    ancestor = u;
                }
            }
        return ancestor;
    }

    // length of the shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (!isValidVertexList(v) || !isValidVertexList(w))
            throw new IllegalArgumentException("Invalid vertex list passed in function SAP::length");

        int ancestor = ancestor(v, w);
        if (ancestor == -1)
            return -1;

        BreadthFirstDirectedPaths vPath = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths wPath = new BreadthFirstDirectedPaths(graph, w);
        return vPath.distTo(ancestor) + wPath.distTo(ancestor);
    }

    // a common ancestor that participates in the shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (!isValidVertexList(v) || !isValidVertexList(w))
            throw new IllegalArgumentException("Invalid vertex list passed in function SAP::ancestor");

        BreadthFirstDirectedPaths vPath = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths wPath = new BreadthFirstDirectedPaths(graph, w);

        int ancestor = -1;
        int length = Integer.MAX_VALUE;
        for (int u = 0; u < V; ++u)
            if (vPath.hasPathTo(u) && wPath.hasPathTo(u)) {
                int tmp = vPath.distTo(u) + wPath.distTo(u);
                if (tmp < length) {
                    length = tmp;
                    ancestor = u;
                }
            }
        return ancestor;
    }

    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {

    }
}