module Core {
    requires Common;
    requires javafx.graphics;
    requires spring.context;
    requires  spring.core;
    requires  spring.beans;
    opens dk.sdu.cbse to javafx.graphics, spring.core, spring.beans, spring.context;
    uses dk.sdu.cbse.common.services.IGamePluginService;
    uses dk.sdu.cbse.common.services.IEntityProcessingService;
    uses dk.sdu.cbse.common.services.IPostEntityProcessingService;
}