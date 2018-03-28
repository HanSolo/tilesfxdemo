package eu.hansolo.tilesfxdemo;


import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.ValueObject;
import eu.hansolo.tilesfx.chart.ChartData;
import eu.hansolo.tilesfx.events.TileEvent;
import eu.hansolo.tilesfx.events.TileEvent.EventType;
import eu.hansolo.tilesfx.skins.BarChartItem;
import eu.hansolo.tilesfx.tools.Country;
import eu.hansolo.tilesfx.tools.FlowGridPane;
import eu.hansolo.tilesfx.tools.Helper;
import eu.hansolo.tilesfx.tools.LocationBuilder;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;


public class InteractiveDemo extends Application {
    private static final Random             RND       = new Random();
    private static final double             MIN_VALUE = 0;
    private static final double             MAX_VALUE = 100;
    private static       int                noOfNodes = 0;
    private              Tile               worldTile;
    private              Tile               countryTile;
    private              Tile               sliderTile;
    private              Tile               smoothAreaTile;
    private              Tile               donutTile;
    private              Tile               bargraphTile;
    private              List<ChartData>    data;
    private              List<BarChartItem> worldDataOfSelectedYear;


    @Override public void init() {
        // Data preparation
        data = new ArrayList<>(10);
        data.add(new ChartData("2008"));
        data.add(new ChartData("2009"));
        data.add(new ChartData("2010"));
        data.add(new ChartData("2011"));
        data.add(new ChartData("2012"));
        data.add(new ChartData("2013"));
        data.add(new ChartData("2014"));
        data.add(new ChartData("2015"));
        data.add(new ChartData("2016"));
        data.add(new ChartData("2017"));

        // Fill countries with random data for years from 2008 - 2017
        worldDataOfSelectedYear = new ArrayList<>(Country.values().length);
        for (int i = 0; i < Country.values().length ; i++) {
            CountryData countryData = new CountryData();
            double                                         value       = 0;
            for (int year = 2008 ; year < 2018 ; year++) {
                value = RND.nextDouble() * MAX_VALUE * 0.8 + RND.nextDouble() * MAX_VALUE * 0.2;
                countryData.setValueForYear(year, value);
            }

            Color[] colors  = getColorForValue(0, 100, value);
            Country country = Country.values()[i];
            country.setColor(colors[0]);
            country.setValueObject(countryData);

            worldDataOfSelectedYear.add(new BarChartItem(country.getDisplayName(), value, colors[0]));
        }

        // Create Tiles
        worldTile = TileBuilder.create()
                               .prefSize(600, 450)
                               .skinType(SkinType.WORLDMAP)
                               .title("World Data 2017")
                               .text("Some data")
                               .textVisible(false)
                               .pointsOfInterest(LocationBuilder.create()
                                                                .name("Home")
                                                                .latitude(51.9065938)
                                                                .longitude(7.6352688)
                                                                .build())
                               .build();

        countryTile = TileBuilder.create().skinType(SkinType.COUNTRY)
                                 .prefSize(400, 450)
                                 .decimals(0)
                                 .title("2017")
                                 .unit("k\u0024")
                                 .tooltipText("")
                                 .animated(true)
                                 .build();

        sliderTile = TileBuilder.create().skinType(SkinType.SLIDER)
                                .prefSize(600, 400)
                                .title("Selected Year")
                                .decimals(0)
                                .minValue(2008)
                                .maxValue(2017)
                                .value(2017)
                                .barColor(Color.web("#03a9f4"))
                                .snapToTicks(true)
                                .build();

        donutTile = TileBuilder.create().skinType(SkinType.DONUT_CHART)
                               .prefSize(400, 450)
                               .title("Distribution 2008 - 2017")
                               .decimals(0)
                               //.sortedData(false)
                               .chartData(data)
                               .build();

        smoothAreaTile = TileBuilder.create().skinType(SkinType.SMOOTH_AREA_CHART)
                                    .prefSize(400, 450)
                                    .title("2008 - 2017")
                                    .unit("k\u0024")
                                    .decimals(0)
                                    .animated(true)
                                    .smoothing(true)
                                    .dataPointsVisible(true)
                                    .snapToTicks(true)
                                    .chartData(data)
                                    .build();

        bargraphTile = TileBuilder.create().skinType(SkinType.BAR_CHART)
                                  .prefSize(400, 400)
                                  .title("Highest in 2017")
                                  .decimals(0)
                                  .animated(true)
                                  .barChartItems(worldDataOfSelectedYear)
                                  .build();


        // Event handling
        worldTile.setOnTileEvent(e -> {
            if (e.getEventType() == EventType.SELECTED_CHART_DATA) {
                ChartData   data         = e.getData();
                Country     country      = Country.valueOf(data.getName());
                CountryData countryData  = (CountryData) country.getValueObject();
                Color       countryColor = country.getColor();
                int         year         = (int) Helper.snapToTicks(sliderTile.getMinValue(), sliderTile.getMaxValue(), sliderTile.getValue(), 0, 1);

                countryTile.setCountry(country);
                countryTile.setBarColor(country.getColor());
                countryTile.setValue(countryData.getValueFromYear(year));
                countryTile.setTitle(Integer.toString(year));

                smoothAreaTile.setTitle("History " + country.getDisplayName() + " 2008 - 2017");
                smoothAreaTile.setBarColor(countryColor);

                donutTile.setTitle("Distribution " + country.getDisplayName() + " 2008 - 2017");
            }
        });

        countryTile.valueProperty().addListener(o -> {
            CountryData countryData = (CountryData) countryTile.getCountry().getValueObject();
            double  value;
            Color[] colors;

            for (int i = 0 ; i < 10 ; i++) {
                value  = countryData.getValueFromYear(i + 2008);
                colors = getColorForValue(MIN_VALUE, MAX_VALUE, value);
                ChartData yearData = data.get(i);
                yearData.setValue(value);
                yearData.setFillColor(colors[0]);
                yearData.setTextColor(colors[1]);
            }
        });

        sliderTile.setOnTileEvent(e -> {
            EventType type = e.getEventType();
            if (EventType.VALUE_CHANGED == type) {
                Country     country     = countryTile.getCountry();
                CountryData countryData = (CountryData) country.getValueObject();

                int year = (int) Helper.snapToTicks(sliderTile.getMinValue(), sliderTile.getMaxValue(), sliderTile.getValue(), 0, 1);
                Color[] colors = getColorForValue(MIN_VALUE, MAX_VALUE, countryData.getValueFromYear(year));
                countryTile.setBarColor(colors[0]);
                countryTile.setValue(countryData.getValueFromYear(year));
                countryTile.setTitle(Integer.toString(year));

                smoothAreaTile.setBarColor(colors[0]);

                for (int i = 0; i < Country.values().length ; i++) {
                    country     = Country.values()[i];
                    countryData = (CountryData) country.getValueObject();
                    String  countryName           = country.getDisplayName();
                    double  valueOfSelectedYear   = countryData.getValueFromYear(year);
                    Color[] colorsForSelectedYear = getColorForValue(MIN_VALUE, MAX_VALUE, valueOfSelectedYear);
                    country.setColor(colorsForSelectedYear[0]);
                    country.setValueObject(countryData);
                    Optional<BarChartItem> item = worldDataOfSelectedYear.stream().filter(barChartItem -> barChartItem.getName().equals(countryName)).findFirst();
                    if (item.isPresent()) {
                        item.get().setValue(valueOfSelectedYear);
                        item.get().setBarColor(colorsForSelectedYear[0]);
                    }
                }
                worldTile.setTitle("World Data " + year);
                worldTile.fireTileEvent(new TileEvent(EventType.REFRESH));

                bargraphTile.setTitle("Highest in " + year);
            }
        });
    }

    @Override public void start(Stage stage) {
        FlowGridPane pane = new FlowGridPane(2, 3,
                                             sliderTile, bargraphTile,
                                             worldTile, countryTile,
                                             donutTile, smoothAreaTile);

        pane.setHgap(5);
        pane.setVgap(5);
        pane.setAlignment(Pos.CENTER);
        pane.setCenterShape(true);
        pane.setPadding(new Insets(5));
        pane.setBackground(new Background(new BackgroundFill(Color.web("#101214"), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(pane);

        stage.setTitle("Interactive TilesFX");
        stage.setScene(scene);
        stage.show();

        // Calculate number of nodes
        calcNoOfNodes(pane);
        System.out.println(noOfNodes + " Nodes in SceneGraph");
    }

    @Override public void stop() {
        System.exit(0);
    }

    private Color[] getColorForValue(final double MIN_VALUE, final double MAX_VALUE, final double VALUE) {
        double range = MAX_VALUE - MIN_VALUE;
        double step  = range / 10;
        Color  fillColor;
        Color  textColor;

        if (VALUE > 9 * step) {
            fillColor = Color.web("cde7f5");//.web("#e1f5fe");
            textColor = Color.BLACK;
        } else if (VALUE > 8 * step) {
            fillColor = Color.web("#b3e5fc");
            textColor = Color.BLACK;
        } else if (VALUE > 7 * step) {
            fillColor = Color.web("#81d4fa");
            textColor = Color.BLACK;
        } else if (VALUE > 6 * step) {
            fillColor = Color.web("#4fc3f7");
            textColor = Color.BLACK;
        } else if (VALUE > 5 * step) {
            fillColor = Color.web("#29b6f6");
            textColor = Color.BLACK;
        } else if (VALUE > 4 * step) {
            fillColor = Color.web("#03a9f4");
            textColor = Color.BLACK;
        } else if (VALUE > 3 * step) {
            fillColor = Color.web("#039be5");
            textColor = Tile.FOREGROUND;
        } else if (VALUE > 2 * step) {
            fillColor = Color.web("#0288d1");
            textColor = Tile.FOREGROUND;
        } else if (VALUE > 1 * step) {
            fillColor = Color.web("#0277bd");
            textColor = Tile.FOREGROUND;
        } else {
            fillColor = Color.web("#01579b");
            textColor = Tile.FOREGROUND;
        }
        return new Color[] { fillColor, textColor };
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


    // ******************** Inner Classes *************************************
    class CountryData implements ValueObject {
        private Map<Integer, Double> yearValueMap;


        // ******************** Constructors **********************************
        public CountryData() {
            yearValueMap = new HashMap<>();
        }


        // ******************** Methods ***************************************
        public Map<Integer, Double> getYearValueMap() { return yearValueMap; }
        public void setYearValueMap(final Map<Integer, Double> YEAR_VALUE_MAP) { yearValueMap = YEAR_VALUE_MAP; }

        public double getValueFromYear(final int YEAR) { return yearValueMap.containsKey(YEAR) ? yearValueMap.get(YEAR) : 0d; }
        public void setValueForYear(final int YEAR, final double VALUE) { yearValueMap.put(YEAR, VALUE); }
    }
}