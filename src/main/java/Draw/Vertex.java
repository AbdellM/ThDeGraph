package Draw;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Vertex {

    public static StackPane createVertex(int index, double x, double y) {

        Circle circle = new Circle(13);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);
        circle.setStrokeType(StrokeType.CENTERED);
        circle.setFill(Color.rgb(143,201,255));

        Text text = new Text(String.valueOf(index));
        text.setFont(new Font(20));
        text.setStyle("-fx-font-weight:bold;");

        StackPane stack = new StackPane();
        stack.getChildren().addAll(circle, text);
        stack.setLayoutX(x);
        stack.setLayoutY(y);
        stack.setId("V" + String.valueOf(index));

        return stack;
    }

    public static StackPane changeBorderColorV (StackPane V){
        Circle c = (Circle) V.getChildren().get(0);
        c.setStroke(Color.RED);
        V.getChildren().set(0, c);

        return V;
    }

    public static StackPane backToNormal (StackPane V){
        Circle c = (Circle) V.getChildren().get(0);
        c.setStroke(Color.BLACK);
        V.getChildren().set(0, c);

        return V;
    }
}
