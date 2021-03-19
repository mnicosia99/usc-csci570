package net.nicosia.visualization;

import java.awt.Dimension;
import java.util.Hashtable;
import java.util.LinkedList;
import javax.swing.JFrame;
import org.apache.commons.collections15.Transformer;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;

public class GraphVisualization {

    static int edgeCountDirected = 0;   // This works with the inner MyEdge class

    class MyNode {

        String id;

        public MyNode(String id) {
            this.id = id;
        }

        public String toString() {
            return "V"+id;
        }

        public String Node_Property() {
            String node_prop = id;
            return(node_prop);
        }
    }

    class MyLink {
        int weight;
        String Label;
        int id;

        public MyLink(int weight, String Label) {
            this.id = edgeCountDirected++;
            this.weight = weight;
            this.Label = Label;
        }

        public String toString() {
            return "E"+id;
        }

        public String Link_Property() {
            String Link_prop = Label;
            return(Link_prop);
        }

        public String Link_Property_wt() {
            String Link_prop_wt = ""+weight;
            return(Link_prop_wt);
        }
    }

    //used to construct graph and call graph algorithm used in JUNG
    public void Visualize_Directed_Graph(LinkedList<String> Distinct_nodes, LinkedList<String> source_vertex, LinkedList<String> target_vertex, LinkedList<Integer> Edge_Weight, LinkedList<String> Edge_Label) {

        //CREATING weighted directed graph
        Graph<MyNode, MyLink> g = new DirectedSparseGraph<GraphVisualization.MyNode, GraphVisualization.MyLink>();
        //create node objects
        Hashtable<String, MyNode> Graph_Nodes = new Hashtable<String, GraphVisualization.MyNode>();
        LinkedList<MyNode> Source_Node = new LinkedList<GraphVisualization.MyNode>();
        LinkedList<MyNode> Target_Node = new LinkedList<GraphVisualization.MyNode>();
        LinkedList<MyNode> Graph_Nodes_Only = new LinkedList<GraphVisualization.MyNode>();
        //LinkedList<MyLink> Graph_Links = new LinkedList<Graph_Algos.MyLink>();

        //create graph nodes
        for (int i=0;i<Distinct_nodes.size();i++) {
            String node_name = Distinct_nodes.get(i);
            MyNode data = new MyNode(node_name);
            Graph_Nodes.put(node_name, data);
            Graph_Nodes_Only.add(data);
        }

        //Now convert all source and target nodes into objects
        for (int t=0;t<source_vertex.size();t++) {
            Source_Node.add(Graph_Nodes.get(source_vertex.get(t)));
            Target_Node.add(Graph_Nodes.get(target_vertex.get(t)));
        }

        //Now add nodes and edges to the graph
        for (int i=0;i<Edge_Weight.size();i++) {
            g.addEdge(new MyLink(Edge_Weight.get(i),Edge_Label.get(i)),Source_Node.get(i), Target_Node.get(i), EdgeType.DIRECTED);
        }

        CircleLayout<MyNode, MyLink> layout1 = new CircleLayout<MyNode,MyLink>(g);
        layout1.setSize(new Dimension(600, 600));
        BasicVisualizationServer<MyNode, MyLink> viz = new BasicVisualizationServer<MyNode,MyLink>(layout1);
        viz.setPreferredSize(new Dimension(600, 600));
        Transformer<MyNode, String> vertexLabelTransformer = new Transformer<MyNode, String>() {
            public String transform(MyNode vertex) {
                return (String) vertex.Node_Property();
            }
        };
        Transformer<MyLink, String> edgeLabelTransformer = new Transformer<MyLink, String>() {
            public String transform(MyLink edge) {
                return edge.Link_Property_wt();
            }
        };
        viz.getRenderContext().setEdgeLabelTransformer(edgeLabelTransformer);
        viz.getRenderContext().setVertexLabelTransformer(vertexLabelTransformer);
        JFrame frame = new JFrame("A Directed Graph Visualization...");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(viz);
        frame.pack();
        frame.setVisible(true);
    }

    public void Visualize_Directed_Graph(int[][] rGraph) {

        LinkedList<String> Distinct_Vertex = new LinkedList<String>();//used to enter vertexes
        LinkedList<String> Source_Vertex = new LinkedList<String>();
        LinkedList<String> Target_Vertex = new LinkedList<String>();
        LinkedList<Integer> Edge_Weight = new LinkedList<Integer>();//used to enter edge weight
        LinkedList<String> Edge_Label = new LinkedList<String>(); //used to enter edge levels

        for (int i = 0; i < rGraph.length; i++) {
            Distinct_Vertex.add(String.valueOf(i));
            for (int j = 0; j < rGraph[i].length; j++) {
                if (rGraph[i][j] > 0) {
                    Source_Vertex.add(String.valueOf(i));
                    Target_Vertex.add(String.valueOf(j));
                    Edge_Weight.add(rGraph[i][j]);
                    Edge_Label.add(String.valueOf(i) + String.valueOf(j));
                }
            }
        }
        Visualize_Directed_Graph(Distinct_Vertex, Source_Vertex, Target_Vertex, Edge_Weight, Edge_Label);
    }
}
