package eu.hansolo.tilesfxdemo;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.tools.FlowGridPane;
import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;


/**
 * User: hansolo
 * Date: 2019-07-04
 * Time: 16:43
 */
public class LayoutDemo extends Application {
    private GridPane pane;

    @Override public void init() {
        pane = new GridPane();
        pane.setHgap(5);
        pane.setVgap(5);

        for (int i = 0 ; i < 5 ; i++) {
            Tile tile = createTile();
            pane.add(tile, i, 0);
        }
        Tile tile1 = createWideTile();
        pane.add(tile1, 0, 1);

        Tile tile2 = createWideTile();
        pane.add(tile2, 2, 1);

        Tile tile3 = createTile();
        pane.add(tile3, 4, 1);

        for (int i = 0 ; i < 5 ; i++) {
            Tile tile = createTile();
            pane.add(tile, i, 2);
        }
    }

    @Override public void start(Stage stage) {
        Scene scene = new Scene(pane);

        stage.setTitle("Layout Demo");
        stage.setScene(scene);
        stage.show();
    }

    @Override public void stop() {
        System.exit(0);
    }

    private Tile createTile() {
        return TileBuilder.create()
                          .skinType(SkinType.TEXT)
                          .prefSize(200, 200)
                          .title("Hello World")
                          .build();
    }
    private Tile createWideTile() {
        Tile tile = TileBuilder.create()
                               .skinType(SkinType.TEXT)
                               .prefSize(405, 200)
                               .title("Hello Wide World")
                               .build();
        GridPane.setColumnSpan(tile, 2);
        return tile;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
