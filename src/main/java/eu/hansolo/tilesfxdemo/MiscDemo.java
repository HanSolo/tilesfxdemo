package eu.hansolo.tilesfxdemo;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;


/**
 * User: hansolo
 * Date: 28.03.18
 * Time: 17:36
 */
public class MiscDemo extends Application {
    private Tile tile;

    @Override public void init() {
        Font customFont = Font.loadFont(MiscDemo.class.getResourceAsStream("digital-7.ttf"), 10);

        tile = TileBuilder.create()
                          .skinType(SkinType.CUSTOM)
                          .prefSize(400, 400)
                          .title("Title")
                          .text("Text")
                          .description("Description")
                          .customFontEnabled(true)
                          .customFont(customFont)
                          .build();
    }

    @Override public void start(Stage stage) {
        StackPane pane = new StackPane(tile);
        pane.setPadding(new Insets(10));

        Scene scene = new Scene(pane);

        stage.setTitle("Misc Demo");
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
