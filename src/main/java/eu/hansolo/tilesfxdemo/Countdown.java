package eu.hansolo.tilesfxdemo;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.fonts.Fonts;
import eu.hansolo.tilesfx.tools.Helper;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Countdown extends Application {
    private static int  noOfNodes = 0;
    private static final int            SECONDS_PER_DAY    = 86_400;
    private static final int            SECONDS_PER_HOUR   = 3600;
    private static final int            SECONDS_PER_MINUTE = 60;
    private Tile           days;
    private Tile           hours;
    private Tile           minutes;
    private Tile           seconds;
    private Tile           flipDays;
    private Tile           flipHours;
    private Tile           flipMinutes;
    private Tile           flipSeconds;
    private Label          daysLabel;
    private Label          hoursLabel;
    private Label          minutesLabel;
    private Label          secondsLabel;
    private Duration       duration;
    private int            d;
    private int            h;
    private int            m;
    private int            s;
    private long           lastTimerCall;
    private AnimationTimer timer;


    @Override public void init() {
        days     = createTile("DAYS", "0");
        hours    = createTile("HOURS", "0");
        minutes  = createTile("MINUTES", "0");
        seconds  = createTile("SECONDS", "0");

        flipDays     = createFlipTile("0", Helper.TIME_5_TO_0);
        flipHours    = createFlipTile("0", Helper.TIME_24_TO_0);
        flipMinutes  = createFlipTile("0", Helper.TIME_59_TO_00);
        flipSeconds  = createFlipTile("0", Helper.TIME_59_TO_00);

        daysLabel    = createLabel("DAYS");
        hoursLabel   = createLabel("HOURS");
        minutesLabel = createLabel("MINUTES");
        secondsLabel = createLabel("SECONDS");

        duration = Duration.hours(72);

        lastTimerCall = System.nanoTime();
        timer = new AnimationTimer() {
            @Override public void handle(final long now) {
                if (now > lastTimerCall + 1_000_000_000l) {
                    duration = duration.subtract(Duration.seconds(1));

                    int remainingSeconds = (int) duration.toSeconds();
                    int d = remainingSeconds / SECONDS_PER_DAY;
                    int h = (remainingSeconds % SECONDS_PER_DAY) / SECONDS_PER_HOUR;
                    int m = ((remainingSeconds % SECONDS_PER_DAY) % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE;
                    int s = (((remainingSeconds % SECONDS_PER_DAY) % SECONDS_PER_HOUR) % SECONDS_PER_MINUTE);

                    if (d == 0 && h == 0 && m == 0 && s == 0) { timer.stop(); }

                    days.setDescription(Integer.toString(d));
                    hours.setDescription(Integer.toString(h));
                    minutes.setDescription(String.format("%02d", m));
                    seconds.setDescription(String.format("%02d", s));

                    flipDays.setFlipText(Integer.toString(d));
                    flipHours.setFlipText(Integer.toString(h));
                    flipMinutes.setFlipText(String.format("%02d", m));
                    flipSeconds.setFlipText(String.format("%02d", s));

                    lastTimerCall = now;
                }
            }
        };
    }

    @Override public void start(Stage stage) {
        HBox upper = new HBox(20, days, hours, minutes, seconds);
        upper.setPadding(new Insets(10));

        HBox middle = new HBox(20, daysLabel, hoursLabel, minutesLabel, secondsLabel);
        middle.setPadding(new Insets(10));
        VBox.setMargin(middle, new Insets(20, 0, -10, 0));

        HBox lower = new HBox(20, flipDays, flipHours, flipMinutes, flipSeconds);
        lower.setPadding(new Insets(10));

        VBox pane = new VBox(10, upper, middle, lower);
        pane.setPadding(new Insets(10));
        pane.setBackground(new Background(new BackgroundFill(Tile.BACKGROUND.brighter(), CornerRadii.EMPTY, Insets.EMPTY)));

        PerspectiveCamera camera = new PerspectiveCamera();
        camera.setFieldOfView(7);

        Scene scene = new Scene(pane);
        scene.setCamera(camera);

        stage.setTitle("Countdown");
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

    private Tile createTile(final String TITLE, final String TEXT) {
        return TileBuilder.create().skinType(SkinType.CHARACTER)
                          .prefSize(200, 200)
                          .title(TITLE)
                          .titleAlignment(TextAlignment.CENTER)
                          .description(TEXT)
                          .build();
    }
    private Tile createFlipTile(final String TEXT, final String... CHARACTERS) {
        return TileBuilder.create().skinType(SkinType.FLIP)
                          .prefSize(200, 200)
                          .flipTimeInMS(250)
                          .flipText(TEXT)
                          .characters(CHARACTERS)
                          .build();
    }
    private Label createLabel(final String TEXT) {
        final Font  FONT  = Fonts.latoRegular(24);
        final Label LABEL = new Label(TEXT);
        LABEL.setFont(FONT);
        LABEL.setTextFill(Tile.FOREGROUND);
        LABEL.setAlignment(Pos.CENTER);
        LABEL.setPrefWidth(200);
        return LABEL;
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
