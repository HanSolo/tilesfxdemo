package eu.hansolo.tilesfxdemo;

import eu.hansolo.fx.touchslider.TouchSlider;
import eu.hansolo.fx.touchslider.TouchSliderBuilder;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;


/**
 * User: hansolo
 * Date: 12.06.20
 * Time: 15:07
 */
public class TouchSliderDemo extends Application {
    private TouchSlider slider1;
    private TouchSlider slider2;
    private TouchSlider slider3;
    private TouchSlider slider4;
    private HBox        sliderBox;
    private Tile        sliderTile;

    @Override public void init() {
        slider1 = createSlider("Bass", 0, 100, Tile.GREEN);
        slider2 = createSlider("Treble", 0, 100, Tile.BLUE);
        slider3 = createSlider("Balance", -100, 200, Tile.RED);
        slider4 = createSlider("Volume", 0, 100, Tile.YELLOW_ORANGE);

        slider3.setStartFromZero(true);
        slider3.setShowZero(true);
        slider3.setSliderValue(0);
        slider3.setSnapToZero(true);

        sliderBox = new HBox(10, slider1, slider2, slider3, slider4);
        sliderBox.setAlignment(Pos.CENTER);

        sliderTile = TileBuilder.create()
                                .skinType(SkinType.CUSTOM)
                                .prefSize(500, 300)
                                .title("Slider Tile")
                                .graphic(sliderBox)
                                .text("Here we go")
                                .build();
    }

    private TouchSlider createSlider(final String title, final double minValue, final double range, final Color color) {
        return TouchSliderBuilder.create()
                                 .prefSize(100, 300)
                                 .name(title)
                                 .orientation(Orientation.VERTICAL)
                                 .minValue(minValue)
                                 .range(range)
                                 .formatString("%.0f")
                                 .barBackgroundColor(Color.color(color.getRed(), color.getGreen(), color.getBlue(), 0.2))
                                 .barColor(color)
                                 .thumbColor(color.darker())
                                 .valueTextColor(Color.WHITE)
                                 .nameTextColor(Color.WHITE)
                                 .valueVisible(true)
                                 .nameVisible(true)
                                 .onTouchSliderEvent(e -> System.out.println(title + ": " + e.getValue()))
                                 .build();
    }

    @Override public void start(Stage stage) {
        StackPane pane = new StackPane(sliderTile);
        pane.setPadding(new Insets(20));

        Scene scene = new Scene(pane);

        stage.setTitle("Custom TileSkin");
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
