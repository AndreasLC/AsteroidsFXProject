import dk.sdu.cbse.common.services.IGamePluginService;

module Stars {
    requires Common;
    provides IGamePluginService with dk.sdu.cbse.stars.starPluggin;
}