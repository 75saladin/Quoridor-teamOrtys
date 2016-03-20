
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jed_lechner
 */
public interface LayoutInterface {

    // @param gridPane: The gridPane used to draw the board.
    // returns: the fully constructred board with rectangles.
    GridPane drawBoard(int numOfPlayers);

    // @param gridPane: The board for the game of Quoridor
    // returns a border pane to separate the page into top, bottom, middle, left
    // and right. The Left will be for a description of the Game. The right will
    // be for user controls. Top the title. And middle for the game. The bottom
    // is currently not being used.
    BorderPane setBorderPane(GridPane gridPane);

    
    
}
