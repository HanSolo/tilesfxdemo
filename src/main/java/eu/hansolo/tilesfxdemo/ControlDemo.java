package eu.hansolo.tilesfxdemo;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.addons.Indicator;
import eu.hansolo.tilesfx.addons.Switch;
import eu.hansolo.tilesfx.events.IndicatorEvent;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;


/**
 * User: hansolo
 * Date: 19.06.18
 * Time: 16:57
 */
public class ControlDemo extends Application {
    private static int  noOfNodes = 0;
    private    Tile switchTile;
    private    Tile indicatorTile;


    @Override public void init() {
        // Switch Tile
        HBox switchRow1 = createSwitchRow("Kitchen");
        HBox switchRow2 = createSwitchRow("Bath room");
        HBox switchRow3 = createSwitchRow("Living room");
        HBox switchRow4 = createSwitchRow("Sleeping room");

        VBox switches = new VBox(20, switchRow1, switchRow2, switchRow3, switchRow4);

        switchTile = TileBuilder.create()
                                .skinType(SkinType.CUSTOM)
                                .prefSize(600, 400)
                                .minValue(0)
                                .maxValue(5)
                                .title("Switch Tile")
                                .graphic(switches)
                                .build();

        // Control Tile
        HBox indicatorRow1 = createIndicatorRow("Kitchen ON", "Kitchen OFF");
        HBox indicatorRow2 = createIndicatorRow("Bath room ON", "Bath room OFF");
        HBox indicatorRow3 = createIndicatorRow("Living room ON", "Living room OFF");
        HBox indicatorRow4 = createIndicatorRow("Sleeping room ON", "Sleeping room OFF");

        VBox indicators = new VBox(20, indicatorRow1, indicatorRow2, indicatorRow3, indicatorRow4);

        indicatorTile = TileBuilder.create()
                                   .skinType(SkinType.CUSTOM)
                                   .prefSize(600, 400)
                                   .minValue(0)
                                   .maxValue(5)
                                   .title("Indicator Tile")
                                   .graphic(indicators)
                                   .build();
    }

    @Override public void start(Stage stage) {
        HBox pane = new HBox(10, switchTile, indicatorTile);

        Scene scene = new Scene(pane);

        stage.setTitle("Interactive Tiles");
        stage.setScene(scene);
        stage.show();

        // Calculate number of nodes
        calcNoOfNodes(pane);
        System.out.println(noOfNodes + " Nodes in SceneGraph");
    }

    @Override public void stop() {
        System.exit(0);
    }

    private HBox createIndicatorRow(final String TEXT_ON, final String TEXT_OFF) {
        Indicator indicator = new Indicator(Tile.BLUE, Tile.GRAY);
        Label     label     = new Label(TEXT_OFF);
        label.setFont(Font.font(36));
        label.setTextFill(Tile.FOREGROUND);
        label.setAlignment(Pos.CENTER_RIGHT);
        label.setPrefWidth(400);

        HBox box = new HBox(50, indicator, label);
        box.setAlignment(Pos.CENTER);

        indicator.setOnMousePressed(e -> indicator.setOn(!indicator.isOn()));
        indicator.addEventHandler(IndicatorEvent.INDICATOR_ON, e -> label.setText(TEXT_ON));
        indicator.addEventHandler(IndicatorEvent.INDICATOR_OFF, e -> label.setText(TEXT_OFF));

        return box;
    }

    private HBox createSwitchRow(final String TEXT) {
        Switch aSwitch = new Switch();
        Label  label   = new Label(TEXT);
        label.setFont(Font.font(36));
        label.setTextFill(Tile.FOREGROUND);
        label.setAlignment(Pos.CENTER_RIGHT);
        label.setPrefWidth(400);

        HBox box = new HBox(50, aSwitch, label);
        box.setAlignment(Pos.CENTER);

        return box;
    }


    // ******************** Misc **********************************************
    private static void calcNoOfNodes(Node node) {
        if (node instanceof Parent) {
            if (((Parent) node).getChildrenUnmodifiable().size() != 0) {
                ObservableList<Node> tempChildren = ((Parent) node).getChildrenUnmodifiable();
                noOfNodes += tempChildren.size();
                for (Node n : tempChildren) { calcNoOfNodes(n); }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
