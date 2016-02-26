import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.*;
import javafx.stage.Stage;

/**
 *
 * @author jed_lechner
 */
public class Layout extends Application {
    
    @Override
    public void start(Stage stage) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setGridLinesVisible(true);
        Circle circle = new Circle(15.0);
        
       
        //gp.add(new Circle(20.0), 5, 5);
        for(int i = 0; i < 17; i++) {
            for(int j = 0; j < 17; j++) {
                if(i % 2 == 0 && j%2==0)
                    gridPane.add(new Rectangle(50, 50, Color.BROWN), i, j);
            }
        }
        Rectangle rect = new Rectangle(120, 20, Color.BROWN);
        gridPane.add(rect, 0, 1);
        GridPane.setColumnSpan(rect, 2);
        

        
//        Line line = new Line(0, 20, 10, 20);
//        line.setStrokeWidth(3.0);
//        gridPane.add(line, 1, 1, 2, 1);
//        Button down = new Button("Move the Circle Down");
//        
//        gridPane.add(down, 10, 5);
//        gridPane.add(circle, 0, 0);
//        down.setOnAction(new EventHandler() {
//            int down1 = 0; 
//            int down2 = 1; 
//            @Override
//            public void handle(Event event) {
//                if(down2 < 9) {
//                    Line line = new Line();
//                    line.setStrokeWidth(10.0);
//                    
//                    gridPane.add(new Rectangle(50, 50, Color.BROWN), 0, down1++);
//                    gridPane.add(line, 0, 3);                }
//                    event.consume();
//            }
//        });
//                
        
        //gridPane = createBoard(gridPane);
        

        gridPane.getChildren().stream().forEach((Node node) -> {
            GridPane.setHalignment((Node) node, HPos.CENTER);
            GridPane.setValignment((Node) node, VPos.CENTER);
        });
        
        
//
//
//
//
//        root.getChildren().addAll(gridPane, gp);
        
        Scene scene = new Scene(gridPane, 1000, 1000, Color.BLUE);
        stage.setTitle("QUORIDOR");
        stage.setScene(scene);
        
//        startAnimationTimer();
        
        stage.show();
    }
    
    private Region drawRegion(String myText) {
        Text text = new Text(myText);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        StackPane stackPane = new StackPane();
        stackPane.setPadding(new Insets(20, 20, 20, 20));
        //stackPane.setBackground(BackColor.CHARTREUSE);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(text);
        return stackPane;
    }
    
    
    
//    public void startAnimationTimer() {
//        final long startNanoTime = System.nanoTime();
//        new AnimationTimer() {
//            @Override
//            public void handle(long timeNow) {
//                double t = (timeNow - startNanoTime) / 1_000_000_000.0;
//                double x = 232 + 128 * Math.cos(t);
//                double y = 232 + 128 * Math.sin(t);
//                //Canvas canvas 
//                //GraphicsContext gc = new GraphicsContext2D;
//            }
//            
//            
//        }.start();
//    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
    
}