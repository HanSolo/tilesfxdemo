package eu.hansolo.tilesfxdemo;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.chart.ChartData;
import eu.hansolo.tilesfx.events.TileEvt;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * User: hansolo
 * Date: 01.11.17
 * Time: 14:08
 */
public class CalendarTileDemo extends Application {
    private static final   Random          RND       = new Random();
    private static         int             noOfNodes = 0;
    private                List<ChartData> chartData;
    private Tile           calendarTile;
    private long           lastTimerCall;
    private AnimationTimer timer;


    @Override public void init() {
        ZonedDateTime now = ZonedDateTime.now();
        chartData = new ArrayList<>(10);
        chartData.add(new ChartData("Item 1", now.minusDays(1).toInstant()));
        chartData.add(new ChartData("Item 2", now.plusDays(2).toInstant()));
        chartData.add(new ChartData("Item 3", now.plusDays(10).toInstant()));
        chartData.add(new ChartData("Item 4", now.plusDays(15).toInstant()));
        chartData.add(new ChartData("Item 5", now.plusDays(15).toInstant()));
        chartData.add(new ChartData("Item 6", now.plusDays(20).toInstant()));
        chartData.add(new ChartData("Item 7", now.plusDays(7).toInstant()));
        chartData.add(new ChartData("Item 8", now.minusDays(1).toInstant()));
        chartData.add(new ChartData("Item 9", now.toInstant()));
        chartData.add(new ChartData("Item 10", now.toInstant()));

        calendarTile  = TileBuilder.create().skinType(SkinType.CALENDAR)
                                   .prefSize(400, 400)
                                   .title("CalendarTileSkin")
                                   .text("Any Text")
                                   .chartData(chartData)
                                   .build();

        calendarTile.addTileObserver(TileEvt.SELECTED_CHART_DATA, e -> {
            System.out.println(e.getData().getName() + " : " + e.getData().getValue());
        });

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
        StackPane pane = new StackPane(calendarTile);
        pane.setPadding(new Insets(10));

        pane.setBackground(new Background(new BackgroundFill(Color.web("#101214"), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(pane);

        stage.setTitle("CalendarTile Demo");
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