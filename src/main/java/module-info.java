module eu.hansolo.tilesfxdemo {

    // Java
    requires java.base;
    requires java.logging;
    requires java.management;

    // Java-FX
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.web;
    requires javafx.media;
    requires javafx.fxml;

    // 3rd party
    requires eu.hansolo.medusa;
    requires eu.hansolo.tilesfx;
    requires eu.hansolo.regulators;
    requires eu.hansolo.fx.touchslider;
    requires transitive eu.hansolo.toolbox;
    requires transitive eu.hansolo.toolboxfx;
    requires transitive eu.hansolo.fx.heatmap;
    requires transitive eu.hansolo.fx.countries;

    requires com.almasb.fxgl.all;

    exports eu.hansolo.tilesfxdemo;
}