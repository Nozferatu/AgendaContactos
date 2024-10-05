module com.cmj.agendacontactos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.kordamp.ikonli.core;
    requires java.desktop;

    opens com.cmj.agendacontactos to javafx.fxml;
    exports com.cmj.agendacontactos;
}