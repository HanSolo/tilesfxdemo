package eu.hansolo.tilesfxdemo;

import eu.hansolo.medusa.Clock;
import eu.hansolo.medusa.Clock.ClockSkinType;
import eu.hansolo.medusa.ClockBuilder;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Random;

import static org.kordamp.ikonli.weathericons.WeatherIcons.*;


/**
 * User: hansolo
 * Date: 08.01.17
 * Time: 07:33
 */
public class Main extends Application {
    private static final double TILE_SIZE = 200;
    private static final Random RND       = new Random();
    private FontIcon       icon;
    private Tile           ikonliTile;
    private Gauge          indicatorGauge;
    private Tile           indicatorTile;
    private Gauge          slimGauge;
    private Tile           slimTile;
    private Gauge          dashboardGauge;
    private Tile           dashboardTile;
    private Gauge          digitalGauge;
    private Tile           digitalTile;
    private Gauge          simpleDigitalGauge;
    private Tile           simpleDigitalTile;
    private Gauge          simpleSectionGauge;
    private Tile           simpleSectionTile;
    private Gauge          bulletChartGauge;
    private Tile           bulletChartTile;
    private Gauge          spaceXGauge;
    private Tile           spaceXTile;
    private Clock          clock;
    private Tile           clockTile;
    private long           lastTimerCall;
    private AnimationTimer timer;


    @Override public void init() {
        icon = new FontIcon(SNOW);
        icon.setIconSize((int) TILE_SIZE);
        icon.setFill(Tile.FOREGROUND);
        ikonliTile = TileBuilder.create()
                                .skinType(SkinType.CUSTOM)
                                .prefSize(TILE_SIZE, TILE_SIZE)
                                .title("Ikonli Icon")
                                .graphic(icon)
                                .text("Snow")
                                .build();

        slimGauge = createGauge(Gauge.SkinType.SLIM);
        slimTile  = TileBuilder.create()
                               .prefSize(TILE_SIZE, TILE_SIZE)
                               .skinType(SkinType.CUSTOM)
                               .title("Medusa Slim")
                               .text("Temperature")
                               .graphic(slimGauge)
                               .build();

        dashboardGauge = createGauge(Gauge.SkinType.DASHBOARD);
        dashboardTile  = TileBuilder.create()
                                    .prefSize(TILE_SIZE, TILE_SIZE)
                                    .skinType(SkinType.CUSTOM)
                                    .title("Medusa Dashboard")
                                    .text("Temperature")
                                    .graphic(dashboardGauge)
                                    .build();

        digitalGauge = createGauge(Gauge.SkinType.DIGITAL);
        digitalTile  = TileBuilder.create()
                                  .prefSize(TILE_SIZE, TILE_SIZE)
                                  .skinType(SkinType.CUSTOM)
                                  .title("Medusa Digital")
                                  .text("Temperature")
                                  .graphic(digitalGauge)
                                  .build();

        simpleDigitalGauge = createGauge(Gauge.SkinType.SIMPLE_DIGITAL);
        simpleDigitalTile  = TileBuilder.create()
                                        .prefSize(TILE_SIZE, TILE_SIZE)
                                        .skinType(SkinType.CUSTOM)
                                        .title("Medusa SimpleDigital")
                                        .text("Temperature")
                                        .graphic(simpleDigitalGauge)
                                        .build();

        indicatorGauge = createGauge(Gauge.SkinType.INDICATOR);
        indicatorTile  = TileBuilder.create()
                                    .prefSize(TILE_SIZE, TILE_SIZE)
                                    .skinType(SkinType.CUSTOM)
                                    .title("Medusa Indicator")
                                    .text("")
                                    .graphic(indicatorGauge)
                                    .build();
        indicatorTile.textProperty().bind(indicatorGauge.currentValueProperty().asString("%.0f"));

        simpleSectionGauge = createGauge(Gauge.SkinType.SIMPLE_SECTION);
        simpleSectionGauge.setBarColor(Tile.FOREGROUND);
        simpleSectionGauge.setSections(new Section(66, 100, Tile.BLUE));
        simpleSectionTile  = TileBuilder.create()
                                        .prefSize(TILE_SIZE, TILE_SIZE)
                                        .skinType(SkinType.CUSTOM)
                                        .title("Medusa SimpleSection")
                                        .text("Temperature")
                                        .graphic(simpleSectionGauge)
                                        .build();

        bulletChartGauge = createGauge(Gauge.SkinType.BULLET_CHART);
        bulletChartGauge.setThreshold(75);
        bulletChartGauge.setBarColor(Tile.FOREGROUND);
        bulletChartGauge.setThresholdColor(Tile.RED);
        bulletChartGauge.setSections(new Section(0, 33, Tile.BLUE.darker().darker()),
                                     new Section(33, 66, Tile.BLUE.darker()),
                                     new Section(66, 100, Tile.BLUE));
        bulletChartTile = TileBuilder.create()
                                     .prefSize(2 * TILE_SIZE + 10, TILE_SIZE)
                                     .skinType(SkinType.CUSTOM)
                                     .title("Medusa BulletChart")
                                     .text("Temperature")
                                     .graphic(bulletChartGauge)
                                     .build();

        clock = ClockBuilder.create()
                            .prefSize(TILE_SIZE, TILE_SIZE)
                            .skinType(ClockSkinType.SLIM)
                            .secondColor(Tile.FOREGROUND)
                            .minuteColor(Tile.BLUE)
                            .hourColor(Tile.FOREGROUND)
                            .dateColor(Tile.FOREGROUND)
                            .running(true)
                            .build();
        clockTile = TileBuilder.create()
                               .prefSize(TILE_SIZE, TILE_SIZE)
                               .skinType(SkinType.CUSTOM)
                               .title("Medusa Clock")
                               .graphic(clock)
                               .textVisible(false)
                               .build();

        spaceXGauge = createGauge(Gauge.SkinType.SPACE_X);
        spaceXGauge.setThresholdColor(Tile.BLUE);
        spaceXGauge.setThreshold(85);
        spaceXGauge.setBarColor(Tile.FOREGROUND);
        spaceXTile = TileBuilder.create()
                                .prefSize(TILE_SIZE, TILE_SIZE)
                                .skinType(SkinType.CUSTOM)
                                .title("Medusa SpaceX")
                                .text("Temperature")
                                .graphic(spaceXGauge)
                                .build();

        lastTimerCall = System.nanoTime();
        timer = new AnimationTimer() {
            @Override public void handle(final long now) {
                if (now > lastTimerCall + 2_000_000_000) {
                    slimGauge.setValue(RND.nextDouble() * 100);
                    dashboardGauge.setValue(RND.nextDouble() * 100);
                    digitalGauge.setValue(RND.nextDouble() * 100);
                    simpleDigitalGauge.setValue(RND.nextDouble() * 100);
                    indicatorGauge.setValue(RND.nextDouble() * 100);
                    simpleSectionGauge.setValue(RND.nextDouble() * 100);
                    bulletChartGauge.setValue(RND.nextDouble() * 100);
                    spaceXGauge.setValue(RND.nextDouble() * 100);
                    lastTimerCall = now;
                }
            }
        };
    }

    @Override public void start(Stage stage) {
        FlowPane pane = new FlowPane(ikonliTile, slimTile, dashboardTile, digitalTile,
                                     simpleDigitalTile, indicatorTile, simpleSectionTile,
                                     bulletChartTile, clockTile, spaceXTile);
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setCenterShape(true);
        pane.setPrefSize(850,  440);
        pane.setBackground(new Background(new BackgroundFill(Tile.BACKGROUND.darker(), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(pane);

        stage.setTitle("TilesFX Dashboard using Medusa");
        stage.setScene(scene);
        stage.show();

        timer.start();
    }

    private Gauge createGauge(final Gauge.SkinType TYPE) {
        return GaugeBuilder.create()
                           .skinType(TYPE)
                           .prefSize(TILE_SIZE, TILE_SIZE)
                           .animated(true)
                           //.title("")
                           .unit("\u00B0C")
                           .valueColor(Tile.FOREGROUND)
                           .titleColor(Tile.FOREGROUND)
                           .unitColor(Tile.FOREGROUND)
                           .barColor(Tile.BLUE)
                           .needleColor(Tile.FOREGROUND)
                           .barColor(Tile.BLUE)
                           .barBackgroundColor(Tile.BACKGROUND.darker())
                           .tickLabelColor(Tile.FOREGROUND)
                           .majorTickMarkColor(Tile.FOREGROUND)
                           .minorTickMarkColor(Tile.FOREGROUND)
                           .mediumTickMarkColor(Tile.FOREGROUND)
                           .build();
    }

    @Override public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
