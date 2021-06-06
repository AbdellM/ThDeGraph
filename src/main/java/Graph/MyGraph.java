package Graph;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MyGraph {

    private Graph myGraph;
    private int vertexNbr = 0;
    private int edgeNbr = 0;
    private boolean oriantation;

    public MyGraph(Boolean orianted) {
        if (this.oriantation = orianted)
            this.myGraph = new SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        else
            this.myGraph = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    }

    public int getVertexNbr() {
        return vertexNbr;
    }
    public int getEdgeNbr() {
        try {
            return getAllEdge().size();
        }catch (Exception e){
            return 0;
        }
    }

    public boolean isOriantation() {
        return oriantation;
    }

    public Boolean addVertex(int index){
        vertexNbr++;
        return this.myGraph.addVertex(index);
    }

    public DefaultWeightedEdge addEdge(int index1, int index2){
        return (DefaultWeightedEdge) this.myGraph.addEdge(index1, index2);
    }

    public DefaultWeightedEdge addEdge(int index1, int index2, int weight){
        DefaultWeightedEdge e = (DefaultWeightedEdge) this.myGraph.addEdge(index1, index2);
        this.myGraph.setEdgeWeight(e, weight);
        return e;
    }

    public String getEdge(DefaultWeightedEdge edge){
        if(this.oriantation)
            return "[ " + this.myGraph.getEdgeSource(edge) + " => " + this.myGraph.getEdgeTarget(edge) + " : " + this.myGraph.getEdgeWeight(edge) +" ]";
        else
            return "[ " + this.myGraph.getEdgeSource(edge) + " <=> " + this.myGraph.getEdgeTarget(edge) + " : " + this.myGraph.getEdgeWeight(edge) +" ]";
    }

    public Set<DefaultWeightedEdge> outgoingEdgesOf(int index){
        return myGraph.outgoingEdgesOf(index);
    }

    public Set<DefaultWeightedEdge> incomingEdgesOf(int index){
        return myGraph.incomingEdgesOf(index);
    }

    public boolean removeEdge(DefaultWeightedEdge e){
        return this.myGraph.removeEdge(e);
    }

    public boolean removeVertex(int v){
        vertexNbr--;
        return this.myGraph.removeVertex(v);
    }

    public DefaultWeightedEdge getEdge(int v1, int v2){
        return (DefaultWeightedEdge) this.myGraph.getEdge(v1, v2);
    }

    public HashSet getAllEdge(){
        HashSet<DefaultWeightedEdge> allEdges = new HashSet<DefaultWeightedEdge>();
        for (int i = 2; i <= vertexNbr + 1; i++)
            for(Object edge : this.myGraph.outgoingEdgesOf(i - 1))
                allEdges.add((DefaultWeightedEdge) edge);
        return allEdges;
    }


    @Override
    public String toString() {
        return "MyGraph{" +
                "myGraph=" + myGraph +
                '}';
    }

}

