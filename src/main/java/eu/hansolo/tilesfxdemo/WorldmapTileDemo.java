package eu.hansolo.tilesfxdemo;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.chart.ChartDataBuilder;
import eu.hansolo.tilesfx.tools.Location;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;


/**
 * User: hansolo
 * Date: 07.11.17
 * Time: 14:45
 */
public class WorldmapTileDemo extends Application {
    private Tile     worldTile;
    private Location SanFranciso;
    private Location NewYork;
    private Location Chicago;
    private Location Home;
    private Location Moscow;
    private Location Singapore;


    @Override public void init() {
        SanFranciso = new Location(37.7576171, -122.5776844, "San Francisco", Color.MAGENTA);
        NewYork     = new Location(40.7157216,-74.3036411, "New York", Color.MAGENTA);
        Chicago     = new Location(41.8333908,-88.0128341, "Chicago", Color.MAGENTA);
        Home        = new Location(51.9065938,7.6352688, "Hause", Color.CRIMSON);
        Moscow      = new Location(55.751042, 37.619060, "Moscow", Color.MAGENTA);
        Singapore   = new Location(1.3346428,103.8415972, "Singapore", Color.MAGENTA);

        worldTile = TileBuilder.create()
                               .skinType(SkinType.WORLDMAP)
                               .title("World")
                               .textVisible(false)
                               .prefSize(850, 600)
                               .pointsOfInterest(SanFranciso, Chicago, NewYork, Moscow)
                               .chartData(ChartDataBuilder.create()
                                                          .name("Home")
                                                          .fillColor(Color.RED)
                                                          .value(20)
                                                          .location(Home)
                                                          .build())
                               .build();


        // Register listeners
        worldTile.setOnTileEvent(e -> {
            if (null == e.getData()) { return; }
            System.out.println(e.getData().getName() + " : " + e.getData().getValue());
        });

        Chicago.setOnLocationEvent(e -> System.out.println(e.getLocation().getName()));
    }

    @Override public void start(Stage stage) {
        StackPane pane = new StackPane(worldTile);

        Scene scene = new Scene(pane);

        stage.setTitle("Worldmap Tile");
        stage.setScene(scene);
        stage.show();

        worldTile.addPoiLocation(Singapore);
    }

    @Override public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
