package eu.hansolo.tilesfxdemo;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.Tile.ChartType;
import eu.hansolo.tilesfx.Tile.ImageMask;
import eu.hansolo.tilesfx.Tile.MapProvider;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.Tile.TileColor;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.TimeSection;
import eu.hansolo.tilesfx.TimeSectionBuilder;
import eu.hansolo.tilesfx.addons.Indicator;
import eu.hansolo.tilesfx.chart.ChartData;
import eu.hansolo.tilesfx.chart.RadarChart.Mode;
import eu.hansolo.tilesfx.chart.SunburstChart.TextOrientation;
import eu.hansolo.tilesfx.skins.BarChartItem;
import eu.hansolo.tilesfx.skins.LeaderBoardItem;
import eu.hansolo.tilesfx.tools.Country;
import eu.hansolo.tilesfx.tools.FlowGridPane;
import eu.hansolo.tilesfx.tools.Helper;
import eu.hansolo.tilesfx.tools.Location;
import eu.hansolo.tilesfx.tools.TreeNode;
import eu.hansolo.tilesfx.weather.DarkSky;
import eu.hansolo.tilesfx.weather.DarkSky.Language;
import eu.hansolo.tilesfx.weather.DarkSky.Unit;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.util.Locale;
import java.util.Random;


/**
 * Created by hansolo on 28.05.17.
 */
public class Overview extends Application {
    private static final    Random RND = new Random();
    private static final    double TILE_WIDTH  = 150;
    private static final    double TILE_HEIGHT = 150;
    private static          int    noOfNodes = 0;
    private BarChartItem    barChartItem1;
    private BarChartItem    barChartItem2;
    private BarChartItem    barChartItem3;
    private BarChartItem    barChartItem4;
    private LeaderBoardItem leaderBoardItem1;
    private LeaderBoardItem leaderBoardItem2;
    private LeaderBoardItem leaderBoardItem3;
    private LeaderBoardItem leaderBoardItem4;
    private ChartData       chartData1;
    private ChartData       chartData2;
    private ChartData       chartData3;
    private ChartData       chartData4;
    private ChartData       chartData5;
    private ChartData       chartData6;
    private ChartData       chartData7;
    private ChartData       chartData8;

    private ChartData       smoothChartData1;
    private ChartData       smoothChartData2;
    private ChartData       smoothChartData3;
    private ChartData       smoothChartData4;

    private Tile            percentageTile;
    private Tile            clockTile;
    private Tile            gaugeTile;
    private Tile            sparkLineTile;
    private Tile            areaChartTile;
    private Tile            lineChartTile;
    private Tile            highLowTile;
    private Tile            timerControlTile;
    private Tile            numberTile;
    private Tile            textTile;
    private Tile            plusMinusTile;
    private Tile            sliderTile;
    private Tile            switchTile;
    private Tile            worldTile;
    private Tile            weatherTile;
    private Tile            timeTile;
    private Tile            barChartTile;
    private Tile            customTile;
    private Tile            leaderBoardTile;
    private Tile            mapTile;
    private Tile            radialChartTile;
    private Tile            donutChartTile;
    private Tile            circularProgressTile;
    private Tile            stockTile;
    private Tile            gaugeSparkLineTile;
    private Tile            radarChartTile1;
    private Tile            radarChartTile2;
    private Tile            smoothAreaChartTile;
    private Tile            countryTile;
    private Tile            ephemerisTile;
    private Tile            characterTile;
    private Tile            flipTile;
    private Tile            switchSliderTile;
    private Tile            dateTile;
    private Tile            calendarTile;
    private Tile            sunburstTile;
    private Tile            matrixTile;
    private Tile            radialPercentageTile;
    private Tile            statusTile;
    private Tile            imageTile;


    private long           lastTimerCall;
    private AnimationTimer timer;
    private DoubleProperty value;


    @Override public void init() {
        Font customFont = Font.loadFont(MiscDemo.class.getResourceAsStream("digital-7.ttf"), 10);

        value = new SimpleDoubleProperty(0);

        // LineChart Data
        XYChart.Series<String, Number> series1 = new XYChart.Series();
        series1.setName("Whatever");
        series1.getData().add(new XYChart.Data("MO", 23));
        series1.getData().add(new XYChart.Data("TU", 21));
        series1.getData().add(new XYChart.Data("WE", 20));
        series1.getData().add(new XYChart.Data("TH", 22));
        series1.getData().add(new XYChart.Data("FR", 24));
        series1.getData().add(new XYChart.Data("SA", 22));
        series1.getData().add(new XYChart.Data("SU", 20));

        XYChart.Series<String, Number> series2 = new XYChart.Series();
        series2.setName("Inside");
        series2.getData().add(new XYChart.Data("MO", 8));
        series2.getData().add(new XYChart.Data("TU", 5));
        series2.getData().add(new XYChart.Data("WE", 0));
        series2.getData().add(new XYChart.Data("TH", 2));
        series2.getData().add(new XYChart.Data("FR", 4));
        series2.getData().add(new XYChart.Data("SA", 3));
        series2.getData().add(new XYChart.Data("SU", 5));

        XYChart.Series<String, Number> series3 = new XYChart.Series();
        series3.setName("Outside");
        series3.getData().add(new XYChart.Data("MO", 8));
        series3.getData().add(new XYChart.Data("TU", 5));
        series3.getData().add(new XYChart.Data("WE", 0));
        series3.getData().add(new XYChart.Data("TH", 2));
        series3.getData().add(new XYChart.Data("FR", 4));
        series3.getData().add(new XYChart.Data("SA", 3));
        series3.getData().add(new XYChart.Data("SU", 5));

        // WorldMap Data
        for (int i = 0; i < Country.values().length ; i++) {
            double value = RND.nextInt(10);
            Color  color;
            if (value > 8) {
                color = Tile.RED;
            } else if (value > 6) {
                color = Tile.ORANGE;
            } else if (value > 4) {
                color = Tile.YELLOW_ORANGE;
            } else if (value > 2) {
                color = Tile.GREEN;
            } else {
                color = Tile.BLUE;
            }
            Country.values()[i].setColor(color);
        }

        // TimeControl Data
        TimeSection timeSection = TimeSectionBuilder.create()
                                                    .start(LocalTime.now().plusSeconds(20))
                                                    .stop(LocalTime.now().plusHours(1))
                                                    //.days(DayOfWeek.MONDAY, DayOfWeek.FRIDAY)
                                                    .color(Tile.GRAY)
                                                    .highlightColor(Tile.RED)
                                                    .build();

        timeSection.setOnTimeSectionEntered(e -> System.out.println("Section ACTIVE"));
        timeSection.setOnTimeSectionLeft(e -> System.out.println("Section INACTIVE"));

        // Weather (You can get a DarkSky API key here: https://darksky.net/dev/ )
        DarkSky darkSky = new DarkSky("YOUR DARKSKY API KEY", Unit.CA, Language.ENGLISH, 51.911858, 7.632815);
        //darkSky.update();

        // BarChart Items
        barChartItem1 = new BarChartItem("Gerrit", 47, Tile.BLUE);
        barChartItem2 = new BarChartItem("Sandra", 43, Tile.RED);
        barChartItem3 = new BarChartItem("Lilli", 12, Tile.GREEN);
        barChartItem4 = new BarChartItem("Anton", 8, Tile.ORANGE);

        barChartItem1.setFormatString("%.1f kWh");

        // LeaderBoard Items
        leaderBoardItem1 = new LeaderBoardItem("Gerrit", 47);
        leaderBoardItem2 = new LeaderBoardItem("Sandra", 43);
        leaderBoardItem3 = new LeaderBoardItem("Lilli", 12);
        leaderBoardItem4 = new LeaderBoardItem("Anton", 8);

        // Chart Data
        chartData1 = new ChartData("Item 1", 24.0, Tile.GREEN);
        chartData2 = new ChartData("Item 2", 10.0, Tile.BLUE);
        chartData3 = new ChartData("Item 3", 12.0, Tile.RED);
        chartData4 = new ChartData("Item 4", 13.0, Tile.YELLOW_ORANGE);
        chartData5 = new ChartData("Item 5", 13.0, Tile.BLUE);
        chartData6 = new ChartData("Item 6", 13.0, Tile.BLUE);
        chartData7 = new ChartData("Item 7", 13.0, Tile.BLUE);
        chartData8 = new ChartData("Item 8", 13.0, Tile.BLUE);
        //ChartData.animated = false;

        smoothChartData1 = new ChartData("Item 1", RND.nextDouble() * 25, Tile.BLUE);
        smoothChartData2 = new ChartData("Item 2", RND.nextDouble() * 25, Tile.BLUE);
        smoothChartData3 = new ChartData("Item 3", RND.nextDouble() * 25, Tile.BLUE);
        smoothChartData4 = new ChartData("Item 4", RND.nextDouble() * 25, Tile.BLUE);

        // Creating Tiles
        percentageTile = TileBuilder.create()
                                    .skinType(SkinType.PERCENTAGE)
                                    .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                    .title("Percentage Tile")
                                    .unit("\u0025")
                                    .description("Test")
                                    .maxValue(60)
                                    .build();

        clockTile = TileBuilder.create()
                               .skinType(SkinType.CLOCK)
                               .prefSize(TILE_WIDTH, TILE_HEIGHT)
                               .title("Clock Tile")
                               .text("Whatever text")
                               .dateVisible(true)
                               .locale(Locale.US)
                               .running(true)
                               .build();

        gaugeTile = TileBuilder.create()
                               .skinType(SkinType.GAUGE)
                               .prefSize(TILE_WIDTH, TILE_HEIGHT)
                               .title("Gauge Tile")
                               .unit("V")
                               .threshold(75)
                               .build();

        sparkLineTile = TileBuilder.create()
                                   .skinType(SkinType.SPARK_LINE)
                                   .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                   .title("SparkLine Tile")
                                   .unit("mb")
                                   .gradientStops(new Stop(0, Tile.GREEN),
                                                  new Stop(0.5, Tile.YELLOW),
                                                  new Stop(1.0, Tile.RED))
                                   .strokeWithGradient(true)
                                   .build();

        //sparkLineTile.valueProperty().bind(value);

        areaChartTile = TileBuilder.create()
                                   .skinType(SkinType.SMOOTHED_CHART)
                                   .chartType(ChartType.AREA)
                                   .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                   .title("AreaChart Tile")
                                   .series(series1)
                                   .build();

        lineChartTile = TileBuilder.create()
                                   .skinType(SkinType.SMOOTHED_CHART)
                                   .chartType(ChartType.LINE)
                                   .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                   .title("LineChart Tile")
                                   .series(series2, series3)
                                   .build();

        highLowTile = TileBuilder.create()
                                 .skinType(SkinType.HIGH_LOW)
                                 .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                 .title("HighLow Tile")
                                 .unit("\u20AC")
                                 .description("Test")
                                 .text("Whatever text")
                                 .referenceValue(6.7)
                                 .value(8.2)
                                 .build();

        timerControlTile = TileBuilder.create()
                                      .skinType(SkinType.TIMER_CONTROL)
                                      .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                      .title("TimerControl Tile")
                                      .text("Whatever text")
                                      .secondsVisible(true)
                                      .dateVisible(true)
                                      .timeSections(timeSection)
                                      .running(true)
                                      .build();

        numberTile = TileBuilder.create()
                                .skinType(SkinType.NUMBER)
                                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                .title("Number Tile")
                                .text("Whatever text")
                                .value(13)
                                .unit("mb")
                                .description("Test")
                                .textVisible(true)
                                .build();

        textTile = TileBuilder.create()
                              .skinType(SkinType.TEXT)
                              .prefSize(TILE_WIDTH, TILE_HEIGHT)
                              .title("Text Tile")
                              .text("Whatever text")
                              .description("May the force be with you\n...always")
                              .descriptionAlignment(Pos.TOP_LEFT)
                              .textVisible(true)
                              .build();

        plusMinusTile = TileBuilder.create()
                                   .skinType(SkinType.PLUS_MINUS)
                                   .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                   .maxValue(30)
                                   .minValue(0)
                                   .title("PlusMinus Tile")
                                   .text("Whatever text")
                                   .description("Test")
                                   .unit("\u00B0C")
                                   .build();

        sliderTile = TileBuilder.create()
                                .skinType(SkinType.SLIDER)
                                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                .title("Slider Tile")
                                .text("Whatever text")
                                .description("Test")
                                .unit("\u00B0C")
                                .barBackgroundColor(Tile.FOREGROUND)
                                .build();

        switchTile = TileBuilder.create()
                                .skinType(SkinType.SWITCH)
                                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                .title("Switch Tile")
                                .text("Whatever text")
                                //.description("Test")
                                .build();

        switchTile.setOnSwitchPressed(e -> System.out.println("Switch pressed"));
        switchTile.setOnSwitchReleased(e -> System.out.println("Switch released"));

        worldTile = TileBuilder.create()
                               .prefSize(300, TILE_HEIGHT)
                               .skinType(SkinType.WORLDMAP)
                               .title("WorldMap Tile")
                               .text("Whatever text")
                               .textVisible(false)
                               .build();

        // Update the weather information by calling weatherTile.updateWeather()
        weatherTile = TileBuilder.create()
                                 .skinType(SkinType.WEATHER)
                                 .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                 .title("YOUR CITY NAME")
                                 .text("Whatever text")
                                 .darkSky(darkSky)
                                 .build();

        timeTile = TileBuilder.create()
                              .skinType(SkinType.TIME)
                              .prefSize(TILE_WIDTH, TILE_HEIGHT)
                              .title("Time Tile")
                              .text("Whatever text")
                              .duration(LocalTime.of(1, 22))
                              .description("Average reply time")
                              .textVisible(true)
                              .build();

        barChartTile = TileBuilder.create()
                                  .skinType(SkinType.BAR_CHART)
                                  .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                  .title("BarChart Tile")
                                  .text("Whatever text")
                                  .barChartItems(barChartItem1, barChartItem2, barChartItem3, barChartItem4)
                                  .decimals(0)
                                  .build();

        customTile = TileBuilder.create()
                                .skinType(SkinType.CUSTOM)
                                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                .title("Custom Tile")
                                .text("Whatever text")
                                .graphic(new Button("Click Me"))
                                .roundedCorners(false)
                                .build();

        leaderBoardTile = TileBuilder.create()
                                     .skinType(SkinType.LEADER_BOARD)
                                     .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                     .title("LeaderBoard Tile")
                                     .text("Whatever text")
                                     .leaderBoardItems(leaderBoardItem1, leaderBoardItem2, leaderBoardItem3, leaderBoardItem4)
                                     .build();

        mapTile = TileBuilder.create()
                             .skinType(SkinType.MAP)
                             .prefSize(TILE_WIDTH, TILE_HEIGHT)
                             .title("Map")
                             .text("Some text")
                             .description("Description")
                             .currentLocation(new Location(51.91178, 7.63379, "Home", TileColor.MAGENTA.color))
                             .pointsOfInterest(new Location(51.914405, 7.635732, "POI 1", TileColor.RED.color),
                                               new Location(51.912529, 7.631752, "POI 2", TileColor.BLUE.color),
                                               new Location(51.923993, 7.628906, "POI 3", TileColor.YELLOW_ORANGE.color))
                             .mapProvider(MapProvider.TOPO)
                             .build();

        radialChartTile = TileBuilder.create()
                                     .skinType(SkinType.RADIAL_CHART)
                                     .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                     .title("RadialChart")
                                     .text("Some text")
                                     .textVisible(false)
                                     .chartData(chartData1, chartData2, chartData3, chartData4)
                                     .build();

        donutChartTile = TileBuilder.create()
                                    .skinType(SkinType.DONUT_CHART)
                                    .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                    .title("DonutChart")
                                    .text("Some text")
                                    .textVisible(false)
                                    .chartData(chartData1, chartData2, chartData3, chartData4)
                                    .build();

        circularProgressTile = TileBuilder.create()
                                          .skinType(SkinType.CIRCULAR_PROGRESS)
                                          .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                          .title("CircularProgress")
                                          .text("Some text")
                                          .unit("\u0025")
                                          //.graphic(new WeatherSymbol(ConditionAndIcon.CLEAR_DAY, 48, Color.WHITE))
                                          .build();

        stockTile = TileBuilder.create()
                               .skinType(SkinType.STOCK)
                               .prefSize(TILE_WIDTH, TILE_HEIGHT)
                               .title("Stock")
                               .minValue(0)
                               .maxValue(1000)
                               .averagingPeriod(100)
                               .build();

        gaugeSparkLineTile = TileBuilder.create()
                                        .skinType(SkinType.GAUGE_SPARK_LINE)
                                        .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                        .title("GaugeSparkLine")
                                        .animated(true)
                                        .textVisible(false)
                                        .averagingPeriod(25)
                                        .autoReferenceValue(true)
                                        .barColor(Tile.YELLOW_ORANGE)
                                        .barBackgroundColor(Color.rgb(255, 255, 255, 0.1))
                                        .sections(new eu.hansolo.tilesfx.Section(0, 33, Tile.LIGHT_GREEN),
                                                  new eu.hansolo.tilesfx.Section(33, 67, Tile.YELLOW),
                                                  new eu.hansolo.tilesfx.Section(67, 100, Tile.LIGHT_RED))
                                        .sectionsVisible(true)
                                        .highlightSections(true)
                                        .strokeWithGradient(true)
                                        .gradientStops(new Stop(0.0, Tile.LIGHT_GREEN),
                                                       new Stop(0.33, Tile.LIGHT_GREEN),
                                                       new Stop(0.33,Tile.YELLOW),
                                                       new Stop(0.67, Tile.YELLOW),
                                                       new Stop(0.67, Tile.LIGHT_RED),
                                                       new Stop(1.0, Tile.LIGHT_RED))
                                        .build();

        radarChartTile1 = TileBuilder.create().skinType(SkinType.RADAR_CHART)
                                     .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                     .minValue(0)
                                     .maxValue(50)
                                     .title("RadarChart Sector")
                                     .unit("Unit")
                                     .radarChartMode(Mode.SECTOR)
                                     .gradientStops(new Stop(0.00000, Color.TRANSPARENT),
                                                    new Stop(0.00001, Color.web("#3552a0")),
                                                    new Stop(0.09090, Color.web("#456acf")),
                                                    new Stop(0.27272, Color.web("#45a1cf")),
                                                    new Stop(0.36363, Color.web("#30c8c9")),
                                                    new Stop(0.45454, Color.web("#30c9af")),
                                                    new Stop(0.50909, Color.web("#56d483")),
                                                    new Stop(0.72727, Color.web("#9adb49")),
                                                    new Stop(0.81818, Color.web("#efd750")),
                                                    new Stop(0.90909, Color.web("#ef9850")),
                                                    new Stop(1.00000, Color.web("#ef6050")))
                                     .text("Test")
                                     .chartData(chartData1, chartData2, chartData3, chartData4,
                                                chartData5, chartData6, chartData7, chartData8)
                                     .tooltipText("")
                                     .animated(true)
                                     .build();

        radarChartTile2 = TileBuilder.create().skinType(SkinType.RADAR_CHART)
                                     .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                     .minValue(0)
                                     .maxValue(50)
                                     .title("RadarChart Polygon")
                                     .unit("Unit")
                                     .radarChartMode(Mode.POLYGON)
                                     .gradientStops(new Stop(0.00000, Color.TRANSPARENT),
                                                    new Stop(0.00001, Color.web("#3552a0")),
                                                    new Stop(0.09090, Color.web("#456acf")),
                                                    new Stop(0.27272, Color.web("#45a1cf")),
                                                    new Stop(0.36363, Color.web("#30c8c9")),
                                                    new Stop(0.45454, Color.web("#30c9af")),
                                                    new Stop(0.50909, Color.web("#56d483")),
                                                    new Stop(0.72727, Color.web("#9adb49")),
                                                    new Stop(0.81818, Color.web("#efd750")),
                                                    new Stop(0.90909, Color.web("#ef9850")),
                                                    new Stop(1.00000, Color.web("#ef6050")))
                                     .text("Test")
                                     .chartData(chartData1, chartData2, chartData3, chartData4,
                                                chartData5, chartData6, chartData7, chartData8)
                                     .tooltipText("")
                                     .animated(true)
                                     .build();

        smoothAreaChartTile = TileBuilder.create().skinType(SkinType.SMOOTH_AREA_CHART)
                                         .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                         .minValue(0)
                                         .maxValue(40)
                                         .title("SmoothAreaChart")
                                         .unit("Unit")
                                         .text("Test")
                                         .chartData(smoothChartData1, smoothChartData2, smoothChartData3, smoothChartData4)
                                         .tooltipText("")
                                         .animated(true)
                                         .build();

        countryTile = TileBuilder.create().skinType(SkinType.COUNTRY)
                                 .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                 .minValue(0)
                                 .maxValue(40)
                                 .title("Country")
                                 .unit("Unit")
                                 .country(Country.DE)
                                 .tooltipText("")
                                 .animated(true)
                                 .build();

        ephemerisTile = TileBuilder.create().skinType(SkinType.EPHEMERIS)
                                   .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                   .title("Ephemeris")
                                   .currentLocation(new Location(51.911515,7.6340026, "Hiltrup"))
                                   .text("Hiltrup")
                                   .build();

        characterTile = TileBuilder.create().skinType(SkinType.CHARACTER)
                                   .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                   .title("Character")
                                   .titleAlignment(TextAlignment.CENTER)
                                   .description("G")
                                   .build();

        flipTile      = TileBuilder.create().skinType(SkinType.FLIP)
                                   .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                   .characters(Helper.TIME_0_TO_5)
                                   .flipTimeInMS(500)
                                   .flipText(" ")
                                   .build();

        switchSliderTile = TileBuilder.create()
                                      .skinType(SkinType.SWITCH_SLIDER)
                                      .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                      .title("SwitchSlider")
                                      .text("Test")
                                      .build();

        dateTile = TileBuilder.create()
                              .skinType(SkinType.DATE)
                              .prefSize(TILE_WIDTH, TILE_HEIGHT)
                              .build();

        calendarTile = TileBuilder.create()
                                  .skinType(SkinType.CALENDAR)
                                  .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                  .build();

        TreeNode tree   = new TreeNode(new ChartData("ROOT"));
        TreeNode first  = new TreeNode(new ChartData("1st", 8.3, Tile.BLUE), tree);
        TreeNode second = new TreeNode(new ChartData("2nd", 2.2, Tile.ORANGE), tree);
        TreeNode third  = new TreeNode(new ChartData("3rd", 1.4, Tile.PINK), tree);
        TreeNode fourth = new TreeNode(new ChartData("4th", 1.2, Tile.LIGHT_GREEN), tree);

        TreeNode jan = new TreeNode(new ChartData("Jan", 3.5), first);
        TreeNode feb = new TreeNode(new ChartData("Feb", 3.1), first);
        TreeNode mar = new TreeNode(new ChartData("Mar", 1.7), first);
        TreeNode apr = new TreeNode(new ChartData("Apr", 1.1), second);
        TreeNode may = new TreeNode(new ChartData("May", 0.8), second);
        TreeNode jun = new TreeNode(new ChartData("Jun", 0.3), second);
        TreeNode jul = new TreeNode(new ChartData("Jul", 0.7), third);
        TreeNode aug = new TreeNode(new ChartData("Aug", 0.6), third);
        TreeNode sep = new TreeNode(new ChartData("Sep", 0.1), third);
        TreeNode oct = new TreeNode(new ChartData("Oct", 0.5), fourth);
        TreeNode nov = new TreeNode(new ChartData("Nov", 0.4), fourth);
        TreeNode dec = new TreeNode(new ChartData("Dec", 0.3), fourth);


        sunburstTile = TileBuilder.create().skinType(SkinType.SUNBURST)
                                  .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                  .title("SunburstTile")
                                  .textVisible(false)
                                  .sunburstTree(tree)
                                  .sunburstBackgroundColor(Tile.BACKGROUND)
                                  .sunburstTextColor(Tile.BACKGROUND)
                                  .sunburstUseColorFromParent(true)
                                  .sunburstTextOrientation(TextOrientation.TANGENT)
                                  .build();

        matrixTile = TileBuilder.create().skinType(SkinType.MATRIX)
                                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                .title("MatrixTileSkin")
                                .text("Any Text")
                                .textVisible(false)
                                .animated(true)
                                .matrixSize(8, 50)
                                .chartData(chartData1, chartData2, chartData3, chartData4, chartData5, chartData6, chartData7, chartData8)
                                .build();

        radialPercentageTile = TileBuilder.create().skinType(SkinType.RADIAL_PERCENTAGE)
                                          .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                          .backgroundColor(Color.web("#26262D"))
                                          .maxValue(1000)
                                          .title("RadialPercentageSkin")
                                          .description("Product 1")
                                          .textVisible(false)
                                          .chartData(chartData1, chartData2, chartData3)
                                          .animated(true)
                                          .referenceValue(100)
                                          .value(chartData1.getValue())
                                          .descriptionColor(Tile.GRAY)
                                          //.valueColor(Tile.BLUE)
                                          //.unitColor(Tile.BLUE)
                                          .barColor(Tile.BLUE)
                                          .decimals(0)
                                          .build();

        Indicator leftGraphics = new Indicator(Tile.RED);
        leftGraphics.setOn(true);

        Indicator middleGraphics = new Indicator(Tile.YELLOW);
        middleGraphics.setOn(true);

        Indicator rightGraphics = new Indicator(Tile.GREEN);
        rightGraphics.setOn(true);

        statusTile = TileBuilder.create()
                                .skinType(SkinType.STATUS)
                                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                .title("Status Tile")
                                .description("Notifications")
                                .leftText("CRITICAL")
                                .middleText("WARNING")
                                .rightText("INFORMATION")
                                .leftGraphics(leftGraphics)
                                .middleGraphics(middleGraphics)
                                .rightGraphics(rightGraphics)
                                .text("Text")
                                .build();

        imageTile = TileBuilder.create()
                               .skinType(SkinType.IMAGE)
                               .prefSize(TILE_WIDTH, TILE_HEIGHT)
                               .image(new Image(Overview.class.getResourceAsStream("JavaChampion.png")))
                               .imageMask(ImageMask.ROUND)
                               .title("Image Tile")
                               .text("Java Champion")
                               .textAlignment(TextAlignment.CENTER)
                               .build();

        lastTimerCall = System.nanoTime();
        timer = new AnimationTimer() {
            @Override public void handle(long now) {
                if (now > lastTimerCall + 3_500_000_000L) {
                    percentageTile.setValue(RND.nextDouble() * percentageTile.getRange() * 1.5 + percentageTile.getMinValue());
                    gaugeTile.setValue(RND.nextDouble() * gaugeTile.getRange() * 1.5 + gaugeTile.getMinValue());

                    sparkLineTile.setValue(RND.nextDouble() * sparkLineTile.getRange() * 1.5 + sparkLineTile.getMinValue());
                    //value.set(RND.nextDouble() * sparkLineTile.getRange() * 1.5 + sparkLineTile.getMinValue());
                    //sparkLineTile.setValue(20);

                    highLowTile.setValue(RND.nextDouble() * 10);
                    series1.getData().forEach(data -> data.setYValue(RND.nextInt(100)));
                    series2.getData().forEach(data -> data.setYValue(RND.nextInt(30)));
                    series3.getData().forEach(data -> data.setYValue(RND.nextInt(10)));

                    chartData1.setValue(RND.nextDouble() * 50);
                    chartData2.setValue(RND.nextDouble() * 50);
                    chartData3.setValue(RND.nextDouble() * 50);
                    chartData4.setValue(RND.nextDouble() * 50);
                    chartData5.setValue(RND.nextDouble() * 50);
                    chartData6.setValue(RND.nextDouble() * 50);
                    chartData7.setValue(RND.nextDouble() * 50);
                    chartData8.setValue(RND.nextDouble() * 50);

                    barChartTile.getBarChartItems().get(RND.nextInt(3)).setValue(RND.nextDouble() * 80);

                    leaderBoardTile.getLeaderBoardItems().get(RND.nextInt(3)).setValue(RND.nextDouble() * 80);

                    circularProgressTile.setValue(RND.nextDouble() * 120);

                    stockTile.setValue(RND.nextDouble() * 50 + 500);

                    gaugeSparkLineTile.setValue(RND.nextDouble() * 100);

                    countryTile.setValue(RND.nextDouble() * 100);

                    smoothChartData1.setValue(smoothChartData2.getValue());
                    smoothChartData2.setValue(smoothChartData3.getValue());
                    smoothChartData3.setValue(smoothChartData4.getValue());
                    smoothChartData4.setValue(RND.nextDouble() * 25);

                    characterTile.setDescription(Helper.ALPHANUMERIC[RND.nextInt(Helper.ALPHANUMERIC.length - 1)]);

                    flipTile.setFlipText(Helper.TIME_0_TO_5[RND.nextInt(Helper.TIME_0_TO_5.length - 1)]);

                    if (statusTile.getLeftValue() > 1000) { statusTile.setLeftValue(0); }
                    if (statusTile.getMiddleValue() > 1000) { statusTile.setMiddleValue(0); }
                    if (statusTile.getRightValue() > 1000) { statusTile.setRightValue(0); }
                    statusTile.setLeftValue(statusTile.getLeftValue() + RND.nextInt(4));
                    statusTile.setMiddleValue(statusTile.getMiddleValue() + RND.nextInt(3));
                    statusTile.setRightValue(statusTile.getRightValue() + RND.nextInt(3));

                    lastTimerCall = now;
                }
            }
        };
    }

    @Override public void start(Stage stage) {
        FlowGridPane pane = new FlowGridPane(7, 5,
                                             percentageTile, clockTile, gaugeTile, sparkLineTile, areaChartTile,
                                             lineChartTile, timerControlTile, numberTile, textTile,
                                             highLowTile, plusMinusTile, sliderTile, switchTile, timeTile,
                                             barChartTile, customTile, leaderBoardTile, worldTile, mapTile,
                                             radialChartTile, donutChartTile, circularProgressTile, stockTile,
                                             gaugeSparkLineTile, radarChartTile1, radarChartTile2,
                                             smoothAreaChartTile, countryTile, ephemerisTile, characterTile,
                                             flipTile, switchSliderTile, dateTile, calendarTile, sunburstTile,
                                             matrixTile, radialPercentageTile, statusTile, imageTile);//, weatherTile);

        pane.setHgap(5);
        pane.setVgap(5);
        pane.setAlignment(Pos.CENTER);
        pane.setCenterShape(true);
        pane.setPadding(new Insets(5));
        //pane.setPrefSize(800, 600);
        pane.setBackground(new Background(new BackgroundFill(Color.web("#101214"), CornerRadii.EMPTY, Insets.EMPTY)));

        PerspectiveCamera camera = new PerspectiveCamera();
        camera.setFieldOfView(10);

        Scene scene = new Scene(pane);
        scene.setCamera(camera);

        stage.setTitle("TilesFX");
        stage.setScene(scene);
        stage.show();

        // Calculate number of nodes
        calcNoOfNodes(pane);
        System.out.println(noOfNodes + " Nodes in SceneGraph");

        timer.start();

        mapTile.addPoiLocation(new Location(51.85, 7.75, "Test"));
        mapTile.removePoiLocation(new Location(51.85, 7.75, "Test"));
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