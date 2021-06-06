package App;

import Draw.*;
import Graph.MyGraph;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.io.IOException;
import java.util.*;

public class MainController {

    int indexV = 1;
    int indexE = 1;
    private double sceneX, sceneY;
    private MyGraph myGraph;
    private HashMap<String, Integer> edgesToId = new HashMap<String, Integer>();
    private Vector <StackPane> TheTwoVertex = new Vector();
    private HashMap <Integer, EdgeOrianted> edgesOrientedList = new HashMap<Integer, EdgeOrianted>();
    private String id = new String();
    private boolean isorianted;
    private boolean isweight;


    @FXML
    private Accordion accord;

    @FXML
    private TitledPane matrice;

    @FXML
    private Pane EdgePane, VertexPane;

    @FXML
    public Button addVBtn, moveVBtn, deleteVBtn, addEBtn, deleteEBtn, deleteAllBtn, CompleteGBtn, saveBtn, loadBtn ;

    @FXML
    public RadioButton Orianted, Weighted;

    @FXML
    private Label VertexNbr, EdgeNbr, Type, Density;

    @FXML
    public void addV(ActionEvent event) {
        Node source = (Node) event.getSource();
        id = source.getId();

        VertexPane.setOnMousePressed(e -> {
            if(id.equals("addVBtn")){
                sceneX = (e.getSceneX()) - 121;
                sceneY = (e.getSceneY()) - 10;

                myGraph.addVertex(indexV);
                StackPane dot = Vertex.createVertex(indexV++, sceneX, sceneY);

                VertexPane.getChildren().add(dot);
            }
            setNewValues();
        });
    }

    @FXML
    public void moveV(ActionEvent event) {
        Node source = (Node) event.getSource();
        id = source.getId();

        VertexPane.setOnMousePressed(e -> {
            if(id.equals("moveVBtn")){

            }
        });

        System.out.println( Orianted.isSelected());
    }

    @FXML
    public void deleteV(ActionEvent event) {
        Node source = (Node) event.getSource();
        id = source.getId();

        VertexPane.setOnMousePressed(e -> {
            if(id.equals("deleteVBtn")) {
                Object v = e.getTarget();
                if (v.getClass().getName().equals("javafx.scene.text.Text")){
                    if(VertexPane.getChildren().size() != 2){
                        int id = getId(v, 11);
                        removeEdgesAllInRealation(id);
                        VertexPane.getChildren().remove(VertexPane.lookup("#V" + String.valueOf(id)));
                        myGraph.removeVertex(id);
                    }
                    else{
                        try {
                            RestartPopup(event);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            }
            setNewValues();
        });
    }

    @FXML
    public void addE(ActionEvent event) {
        Node source = (Node) event.getSource();
        id = source.getId();

        VertexPane.setOnMousePressed(e -> {
            if(id.equals("addEBtn")){

                Object v = e.getTarget();
                if (v.getClass().getName().equals("javafx.scene.text.Text")){
                    int id = getId(v, 11);
                    if(TheTwoVertex.size() == 1 && TheTwoVertex.get(0) != VertexPane.lookup("#V" + String.valueOf(id))){

                        int id1 = Integer.valueOf(TheTwoVertex.get(0).getId().substring(1));
                        int id2 = id;
                        String edge;

                        if(!Orianted.isSelected()){

                            edge = didEdgeExicte(id1, id2, false);
                            if(edge != null){

                                TheTwoVertex.add((StackPane)VertexPane.lookup("#V" + String.valueOf(id)));
                                edgesToId.put(edge, indexE++);

                                if(Weighted.isSelected()){
                                    int weight = 0;
                                    try {
                                        weight = WeightPopupController.getWeight();
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
                                    myGraph.addEdge(id1, id2, weight);
                                    Group a = Draw.Edge.createWEdge(TheTwoVertex.get(0), TheTwoVertex.get(1), weight); // WEIGHT
                                    EdgePane.getChildren().add(a);
                                }
                                else {
                                    myGraph.addEdge(id1, id2);
                                    Line a = Draw.Edge.createEdge(TheTwoVertex.get(0), TheTwoVertex.get(1));
                                    EdgePane.getChildren().add(a);
                                }
                            }
                        }
                        else if(Orianted.isSelected()){
                            edge = didEdgeExicte(id1, id2, true);
                            if(edge != null){
                                TheTwoVertex.add((StackPane)VertexPane.lookup("#V" + String.valueOf(id)));
                                edgesToId.put(edge, indexE);

                                if(Weighted.isSelected()){
                                    int weight = 0;
                                    try {
                                        weight = WeightPopupController.getWeight();
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
                                    myGraph.addEdge(id1, id2, weight);
                                    EdgeOrianted a = new EdgeOrianted(TheTwoVertex.get(0), TheTwoVertex.get(1));
                                    Text t = a.getweight(TheTwoVertex.get(0), TheTwoVertex.get(1), weight);
                                    edgesOrientedList.put(indexE++, a);
                                    EdgePane.getChildren().addAll(a, t);
                                }
                                else{
                                    myGraph.addEdge(id1, id2);
                                    EdgeOrianted a = new EdgeOrianted(TheTwoVertex.get(0), TheTwoVertex.get(1));
                                    edgesOrientedList.put(indexE++, a);
                                    EdgePane.getChildren().add(a);
                                }
                            }
                        }

                        Draw.Vertex.backToNormal(TheTwoVertex.get(0));
                        TheTwoVertex.clear();


                    }
                    else if (TheTwoVertex.size() == 1){
                        Draw.Vertex.backToNormal(TheTwoVertex.get(0));
                        TheTwoVertex.clear();
                    }
                    else{
                        TheTwoVertex.add((StackPane)VertexPane.lookup("#V" + String.valueOf(id)));
                        Draw.Vertex.changeBorderColorV(TheTwoVertex.get(0));
                    }
                }
                else if(TheTwoVertex.size() == 1){
                    Draw.Vertex.backToNormal(TheTwoVertex.get(0));
                    TheTwoVertex.clear();
                }
            }
            setNewValues();
        });
    }

    @FXML
    public void deleteE(ActionEvent event) {
        Node source = (Node) event.getSource();
        id = source.getId();

        VertexPane.setOnMousePressed(e -> {
            if(id.equals("deleteEBtn")){
                Object v = e.getTarget();
                System.out.println(v);

                if (v.getClass().getName().equals("javafx.scene.shape.Line")){
                    int id = getId(v, 9);
                    EdgePane.getChildren().remove(EdgePane.lookup("#E" + String.valueOf(id)));

                    for (Map.Entry<String, Integer> entry : edgesToId.entrySet()) {
                        if (entry.getValue().equals(id)) {
                            int v1 = Integer.valueOf(entry.getKey().charAt(1)) - 48;
                            int v2 = Integer.valueOf(entry.getKey().charAt(5)) - 48;
                            edgesToId.remove(entry.getKey());
                            myGraph.removeEdge(myGraph.getEdge(v1, v2));
                            break;
                        }
                    }
                }

                if(v.getClass().getName().equals("javafx.scene.shape.Polyline")){
                    int id = getId(v, 13);

                    EdgePane.getChildren().remove(edgesOrientedList.get(id));
                    edgesOrientedList.remove(id);

                    for (Map.Entry<String, Integer> entry : edgesToId.entrySet()) {
                        if (entry.getValue().equals(id)) {
                            int v1 = Integer.valueOf(entry.getKey().charAt(1)) - 48;
                            int v2 = Integer.valueOf(entry.getKey().charAt(5)) - 48;
                            edgesToId.remove(entry.getKey());
                            myGraph.removeEdge(myGraph.getEdge(v1, v2));
                            break;
                        }
                    }
                }
            }
            setNewValues();
        });
    }

    @FXML
    public void CompleteG(ActionEvent event) {

        System.out.println(myGraph);
        //System.out.println(myGraph.getEdge(myGraph.getEdge(1,2)));
        //System.out.println( Orianted.isSelected());
    }

    @FXML
    public void save(ActionEvent event) {


        System.out.println( Orianted.isSelected());
    }

    @FXML
    public void load(ActionEvent event) {


        System.out.println( Orianted.isSelected());
    }

    @FXML
    public void RestartPopup(ActionEvent event) throws IOException {
        RestartPopupController.display();
        Orianted.setSelected(isorianted);
        Weighted.setSelected(isweight);
    }

    public int getId(Object text, int position){
        if(Character.isDigit(text.toString().charAt(position + 1))){
            return (text.toString().charAt(position) - 48) * 10 + (text.toString().charAt(position + 1) - 48);
        }
        return text.toString().charAt(position) - 48;
    }

    public String didEdgeExicte(int from, int to, boolean orienated){
        String edge = "(" + from + " : " + to + ")";
        String edgeNO = "(" + to + " : " + from + ")";

        if(orienated)
            if(edgesToId.get(edge) == null)
                return edge;
            else
                return null;
        else
            if(edgesToId.get(edge) == null && edgesToId.get(edgeNO) == null)
                return edge;
            else
                return null;
    }

    public void setNewValues(){
        int nbrV = myGraph.getVertexNbr();
        int nbrE = myGraph.getEdgeNbr();
        double density = Math.floor(((double)(2*nbrE)/(nbrV*(nbrV-1))) * 10000) / 100;

        VertexNbr.setText(String.valueOf(nbrV));
        EdgeNbr.setText(String.valueOf(nbrE));
        Type.setText(isorianted ? "Orianted" : "No Orianted");
        Density.setText(String.valueOf(density) + " %");

        //set matrix
    }

    public boolean removeEdgesAllInRealation (int id){
        if(!isorianted){
            for(DefaultWeightedEdge ed : myGraph.outgoingEdgesOf(id)){
                if(edgesToId.get(ed.toString()) != null){
                    EdgePane.getChildren().remove(EdgePane.lookup("#E" + edgesToId.get(ed.toString())));
                }
            }
        }
        else{
            String edge, edgeNO;
            int idEdge;
            for(DefaultWeightedEdge ed1 : myGraph.outgoingEdgesOf(id)){
                edge = "(" +  getId(ed1.toString(),1) + " : " + getId(ed1.toString(), 5) + ")";
                try {
                    idEdge = edgesToId.get(edge);
                    EdgePane.getChildren().remove(edgesOrientedList.get(idEdge));
                    edgesOrientedList.remove(idEdge);
                    edgesToId.remove(edge);
                }catch (Exception e){}
            }

            for(DefaultWeightedEdge ed2 : myGraph.incomingEdgesOf(id)){
                edge = "(" +  getId(ed2.toString(),1) + " : " + getId(ed2.toString(), 5) + ")";
                try {
                    idEdge = edgesToId.get(edge);
                    EdgePane.getChildren().remove(edgesOrientedList.get(idEdge));
                    edgesOrientedList.remove(idEdge);
                    edgesToId.remove(edge);
                }catch (Exception e){}
            }
        }
        return true;
    }


    public void setOriantationAndWeight (Boolean orianted, Boolean weight){

        accord.setExpandedPane(matrice);

        this.isorianted = orianted;
        Orianted.setSelected(orianted);

        this.isweight = weight;
        Weighted.setSelected(weight);
        myGraph = new MyGraph(orianted);

        moveVBtn.setDisable(true);
       // CompleteGBtn.setDisable(true);
        saveBtn.setDisable(true);
        loadBtn.setDisable(true);

    }
}








