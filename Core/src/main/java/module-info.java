module Core {
    requires Common;
    requires javafx.graphics;
    requires spring.context;
    requires spring.core;
    requires spring.beans;
    opens dk.sdu.cbse to javafx.graphics, spring.core, spring.beans, spring.context;
}