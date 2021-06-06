package Draw;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class EdgeOrianted extends Group{

    private Polyline mainLine = new Polyline();
    private Polyline headA = new Polyline();
    private Polyline headB = new Polyline();
    private SimpleDoubleProperty x1 = new SimpleDoubleProperty();
    private SimpleDoubleProperty y1 = new SimpleDoubleProperty();
    private SimpleDoubleProperty x2 = new SimpleDoubleProperty();
    private SimpleDoubleProperty y2 = new SimpleDoubleProperty();
    private static int index = 1;
    private SimpleBooleanProperty headAVisible = new SimpleBooleanProperty(true);
    private SimpleBooleanProperty headBVisible = new SimpleBooleanProperty(true);
    private final double ARROW_SCALER = 20;
    private final double ARROWHEAD_ANGLE = Math.toRadians(20);
    private final double ARROWHEAD_LENGTH = 10;

    public EdgeOrianted(StackPane from, StackPane to){
        this.x1.set(from.layoutXProperty().doubleValue() + 13);
        this.y1.set(from.layoutYProperty().doubleValue() + 13);
        this.x2.set(to.layoutXProperty().doubleValue() + 13);
        this.y2.set(to.layoutYProperty().doubleValue() + 13);


        getChildren().addAll(mainLine, headA, headB);


        for(SimpleDoubleProperty s : new SimpleDoubleProperty[]{this.x1,this.y1,this.x2,this.y2}){
            s.addListener( (l,o,n) -> update() );
        }

        mainLine.getStyleClass().setAll("arrow");
        headA.getStyleClass().setAll("arrow");
        headB.getStyleClass().setAll("arrow");

        headA.getStyleClass().add("arrowhead");
        headB.getStyleClass().add("arrowhead");

        getStyleClass().addListener( (ListChangeListener<? super String>) c -> {
            c.next();
            for(Polyline p : new Polyline[]{mainLine, headA, headB}){
                p.getStyleClass().addAll(c.getAddedSubList());
                p.getStyleClass().removeAll(c.getRemoved());
            }
        });

        headA.visibleProperty().bind(headAVisible);
        headB.visibleProperty().bind(headBVisible);
        update();
        mainLine.setStrokeWidth(3);
        headA.setStrokeWidth(3);
        headB.setStrokeWidth(3);

        mainLine.setId("E" + String.valueOf(index));
        headA.setId("E" + String.valueOf(index));
        headB.setId("E" + String.valueOf(index));
        index++;
    }

    private void update() {
        double[] start = scale(x1.get(), y1.get(), x2.get(), y2.get());
        double[] end = scale(x2.get(), y2.get(), x1.get(), y1.get());

        double x1 = start[0];
        double y1 = start[1];
        double x2 = end[0];
        double y2 = end[1];

        mainLine.getPoints().setAll(x1, y1, x2, y2);

        double theta = Math.atan2(y2 - y1, x2 - x1);

        double x,y;


        x = x2 - Math.cos(theta + ARROWHEAD_ANGLE) * ARROWHEAD_LENGTH;
        y = y2 - Math.sin(theta + ARROWHEAD_ANGLE) * ARROWHEAD_LENGTH;
        headB.getPoints().setAll(x,y,x2,y2);
        x = x2 - Math.cos(theta - ARROWHEAD_ANGLE) * ARROWHEAD_LENGTH;
        y = y2 - Math.sin(theta - ARROWHEAD_ANGLE) * ARROWHEAD_LENGTH;
        headB.getPoints().addAll(x,y);


    }

    private double[] scale(double x1, double y1, double x2, double y2){
        double theta = Math.atan2(y2-y1, x2-x1);
        return new double[]{
                x1 + Math.cos(theta) * ARROW_SCALER,
                y1 + Math.sin(theta) * ARROW_SCALER
        };
    }

    public Text getweight(StackPane startDot, StackPane endDot, int weight){
        double startX = startDot.layoutXProperty().doubleValue();
        double startY = startDot.layoutYProperty().doubleValue();
        double endX = endDot.layoutXProperty().doubleValue();
        double endY = endDot.layoutYProperty().doubleValue();

        double x = startX > endX ? startX - endX : endX - startX;
        double y = startY > endY ? startY - endY : endY - startY;


        double middleX = startX > endX ? x / 2 + endX : x / 2 + startX;
        double middleY = startY > endY ? y / 2 + endY : y / 2 + startY;

        Text w = new Text(String.valueOf(weight));
        w.setFont(new Font(15));
        w.setStyle("-fx-font-weight:bold;");
        w.setX(middleX);
        w.setY(middleY);

        return w;
    }
}