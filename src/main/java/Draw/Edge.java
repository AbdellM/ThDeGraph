package Draw;

import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Edge {

    private static int index = 1;
    public static Line createEdge(StackPane startDot, StackPane endDot) {
        Line line = new Line();
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(3);
        line.startXProperty().bind(startDot.layoutXProperty().add(startDot.translateXProperty()).add(startDot.widthProperty().divide(2)));
        line.startYProperty().bind(startDot.layoutYProperty().add(startDot.translateYProperty()).add(startDot.heightProperty().divide(2)));
        line.endXProperty().bind(endDot.layoutXProperty().add(endDot.translateXProperty()).add(endDot.widthProperty().divide(2)));
        line.endYProperty().bind(endDot.layoutYProperty().add(endDot.translateYProperty()).add(endDot.heightProperty().divide(2)));
        line.setId("E" + String.valueOf(index++));

        line.toBack();

        return line;
    }
    public static Group createWEdge(StackPane startDot, StackPane endDot, Integer weight) {
        Line line = createEdge(startDot, endDot);

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
        line.toBack();

        return new Group(line, w);
    }

}
