package eu.hansolo.tilesfxdemo;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.PerspectiveCamera;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.util.Duration;


/**
 * User: hansolo
 * Date: 28.03.18
 * Time: 17:36
 */
public class MiscDemo extends Application {
    private static final double TILE_SIZE = 400;
    private Tile      tileFront;
    private StackPane tileBack;
    private Rotate    rotateFront;
    private Rotate    rotateBack;
    private Timeline  timeline;

    @Override public void init() {
        Font customFont = Font.loadFont(MiscDemo.class.getResourceAsStream("digital-7.ttf"), 10);

        rotateFront = new Rotate(0, Rotate.X_AXIS);
        rotateFront.setPivotY(200);

        rotateBack  = new Rotate(180, Rotate.X_AXIS);
        rotateBack.setPivotY(200);

        timeline    = new Timeline();

        tileFront = TileBuilder.create()
                               //.skinType(SkinType.CUSTOM)
                               .skinType(SkinType.GAUGE)
                               .prefSize(TILE_SIZE, TILE_SIZE)
                               .title("Title")
                               .text("Text")
                               .description("Description")
                               .customFontEnabled(true)
                               .customFont(customFont)
                               .backgroundImage(new Image(MiscDemo.class.getResourceAsStream("JavaChampion.png")))
                               .backgroundImageOpacity(0.5)
                               .backgroundImageKeepAspect(true)
                               .infoRegionEventHandler(e -> {
                                  EventType type = e.getEventType();
                                  if (type.equals(MouseEvent.MOUSE_PRESSED)) { flipTile(); }
                              })
                               .infoRegionBackgroundColor(Tile.GREEN)
                               .infoRegionForegroundColor(Tile.PINK)
                               .notifyRegionBackgroundColor(Tile.DARK_BLUE)
                               .notifyRegionForegroundColor(Tile.YELLOW_ORANGE)
                               .infoRegionTooltipText("Info Region")
                               .showInfoRegion(true)
                               .showNotifyRegion(true)
                               .build();

        Button button = new Button("Flip back");
        button.setOnAction(e -> flipTile());
        tileBack = new StackPane(button);
        tileBack.setPrefSize(TILE_SIZE, TILE_SIZE);
        tileBack.setBackground(new Background(new BackgroundFill(Tile.BACKGROUND, new CornerRadii(10), Insets.EMPTY)));
        tileBack.setVisible(false);

        tileFront.getTransforms().add(rotateFront);
        tileBack.getTransforms().add(rotateBack);

        registerListeners();
    }

    private void registerListeners() {
        rotateFront.angleProperty().addListener((o, ov, nv) -> {
            if (nv.doubleValue() < 90) {
                tileFront.setVisible(true);
                tileBack.setVisible(false);
            } else {
                tileFront.setVisible(false);
                tileBack.setVisible(true);
            }
        });
    }

    private void flipTile() {
        //if (timeline.getCurrentRate() != 0) return;
        if (rotateFront.getAngle() > 0) {
            // Flip back to front
            KeyValue kv0Front = new KeyValue(rotateFront.angleProperty(), rotateFront.getAngle(), Interpolator.EASE_BOTH);
            KeyValue kv1Front = new KeyValue(rotateFront.angleProperty(), 0, Interpolator.EASE_BOTH);
            KeyValue kv0Back  = new KeyValue(rotateBack.angleProperty(), rotateBack.getAngle(), Interpolator.EASE_BOTH);
            KeyValue kv1Back  = new KeyValue(rotateBack.angleProperty(), 180, Interpolator.EASE_BOTH);
            KeyFrame kf0      = new KeyFrame(Duration.ZERO, kv0Front, kv0Back);
            KeyFrame kf1      = new KeyFrame(Duration.millis(2000), kv1Front, kv1Back);
            timeline.getKeyFrames().setAll(kf0, kf1);
        } else {
            // Flip front to back
            KeyValue kv0Front = new KeyValue(rotateFront.angleProperty(), rotateFront.getAngle(), Interpolator.EASE_BOTH);
            KeyValue kv1Front = new KeyValue(rotateFront.angleProperty(), 180, Interpolator.EASE_BOTH);
            KeyValue kv0Back  = new KeyValue(rotateBack.angleProperty(), rotateBack.getAngle(), Interpolator.EASE_BOTH);
            KeyValue kv1Back  = new KeyValue(rotateBack.angleProperty(), 0, Interpolator.EASE_BOTH);
            KeyFrame kf0      = new KeyFrame(Duration.ZERO, kv0Front, kv0Back);
            KeyFrame kf1      = new KeyFrame(Duration.millis(2000), kv1Front, kv1Back);
            timeline.getKeyFrames().setAll(kf0, kf1);
        }
        timeline.play();
    }

    @Override public void start(Stage stage) {
        StackPane pane = new StackPane(tileBack, tileFront);
        pane.setPrefSize(TILE_SIZE, TILE_SIZE);
        pane.setMinSize(TILE_SIZE, TILE_SIZE);
        pane.setMaxSize(TILE_SIZE, TILE_SIZE);

        Scene scene = new Scene(pane);
        scene.setCamera(new PerspectiveCamera());

        stage.setTitle("Misc Demo");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
