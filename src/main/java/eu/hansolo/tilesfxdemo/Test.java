package eu.hansolo.tilesfxdemo;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;


/**
 * User: hansolo
 * Date: 10.01.17
 * Time: 16:10
 */
public class Test extends Application {
    private Tile tile;


    @Override public void init() {
        tile = TileBuilder.create()
                          .skinType(SkinType.CUSTOM)
                          .prefSize(400, 400)
                          .title("Title")
                          .text("SubTitle")
                          .unit("°C")
                          .graphic(new Rectangle(400, 400))
                          .build();

    }

    @Override public void start(Stage stage) {
        StackPane pane = new StackPane(tile);
        pane.setPadding(new Insets(10));

        Scene scene = new Scene(pane);

        stage.setTitle("Title");
        stage.setScene(scene);
        stage.show();
    }

    @Override public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
