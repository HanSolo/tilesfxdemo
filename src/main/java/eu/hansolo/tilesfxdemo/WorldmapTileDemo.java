package eu.hansolo.tilesfxdemo;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.chart.ChartDataBuilder;
import eu.hansolo.tilesfx.events.LocationEvt;
import eu.hansolo.tilesfx.events.TileEvt;
import eu.hansolo.toolbox.evt.Evt;
import eu.hansolo.toolboxfx.geom.Location;
import eu.hansolo.toolboxfx.geom.LocationBuilder;
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
        SanFranciso = LocationBuilder.create().name("San Francisco").latitude(37.7576171).longitude(-122.5776844).fill(Color.MAGENTA).build();
        NewYork     = LocationBuilder.create().name("New York").latitude(40.7157216).longitude(-74.3036411).fill(Color.MAGENTA).build();
        Chicago     = LocationBuilder.create().name("Chicago").latitude(41.8333908).longitude(-88.0128341).fill(Color.MAGENTA).build();
        Home        = LocationBuilder.create().name("Home").latitude(51.9065938).longitude(7.6352688).fill(Color.CRIMSON).build();
        Moscow      = LocationBuilder.create().name("Moscow").latitude(55.751042).longitude(37.619060).fill(Color.MAGENTA).build();
        Singapore   = LocationBuilder.create().name("Singapore").latitude(1.3346428).longitude(103.8415972).fill(Color.MAGENTA).build();

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
        worldTile.addTileObserver(TileEvt.ANY, e -> {
            if (null == e.getData()) { return; }
            System.out.println(e.getData().getName() + " : " + e.getData().getValue());
        });

        Chicago.addLocationObserver(LocationEvt.ANY,e -> System.out.println(e.getLocation().getName()));
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
