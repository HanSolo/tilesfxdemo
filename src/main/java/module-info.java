module eu.hansolo.tilesfxdemo {

    // Java
    requires java.base;
    requires java.logging;

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

    requires com.almasb.fxgl.all;

    exports eu.hansolo.tilesfxdemo;
}