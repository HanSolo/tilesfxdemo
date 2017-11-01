package eu.hansolo.tilesfxdemo;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.chart.ChartData;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MatrixTileDemo extends Application {
    private static final Random          RND       = new Random();
    private static       int             noOfNodes = 0;
    private              List<ChartData> chartData;
    private              Tile            matrixTile;
    private              long            lastTimerCall;
    private              AnimationTimer  timer;


    @Override public void init() {
        chartData = new ArrayList<>(10);
        chartData.add(new ChartData("Item 1", RND.nextDouble() * 100, Tile.RED));
        chartData.add(new ChartData("Item 2", RND.nextDouble() * 100, Tile.GREEN));
        chartData.add(new ChartData("Item 3", RND.nextDouble() * 100, Tile.BLUE));
        chartData.add(new ChartData("Item 4", RND.nextDouble() * 100, Tile.LIGHT_RED));
        chartData.add(new ChartData("Item 5", RND.nextDouble() * 100, Tile.LIGHT_GREEN));
        chartData.add(new ChartData("Item 6", RND.nextDouble() * 100, Tile.DARK_BLUE));
        chartData.add(new ChartData("Item 7", RND.nextDouble() * 100, Tile.ORANGE));
        chartData.add(new ChartData("Item 8", RND.nextDouble() * 100, Tile.YELLOW_ORANGE));
        chartData.add(new ChartData("Item 9", RND.nextDouble() * 100, Tile.YELLOW));
        chartData.add(new ChartData("Item 10", RND.nextDouble() * 100, Tile.PINK));

        matrixTile  = TileBuilder.create().skinType(SkinType.MATRIX)
                                 .prefSize(400, 400)
                                 .title("MatrixTileSkin")
                                 .text("Any Text")
                                 .textVisible(false)
                                 .animated(true)
                                 .matrixSize(12, 50)
                                 .chartData(chartData)
                                 .build();

        lastTimerCall = System.nanoTime();
        timer = new AnimationTimer() {
            @Override public void handle(final long now) {
                if (now > lastTimerCall + 1_000_000_000l) {
                    chartData.forEach(data -> data.setValue(RND.nextDouble() * 100));
                    lastTimerCall = now;
                }
            }
        };
    }

    @Override public void start(Stage stage) {
        StackPane pane = new StackPane(matrixTile);
        pane.setPadding(new Insets(10));

        pane.setBackground(new Background(new BackgroundFill(Color.web("#101214"), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(pane);

        stage.setTitle("MatrixTile Demo");
        stage.setScene(scene);
        stage.show();

        timer.start();

        // Calculate number of nodes
        calcNoOfNodes(pane);
        System.out.println(noOfNodes + " Nodes in SceneGraph");
    }

    @Override public void stop() {
        System.exit(0);
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
