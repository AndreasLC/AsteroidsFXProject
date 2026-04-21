import dk.sdu.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.common.services.IGamePluginService;

module Asteroids {
    requires Common;
    requires CommonAsteroids;
    provides IGamePluginService with dk.sdu.cbse.asteroid.AsteroidsPlugin;
    provides IEntityProcessingService with dk.sdu.cbse.asteroid.AsteroidProcessor;
    provides IAsteroidSplitter with dk.sdu.cbse.asteroid.AsteroidSplitterImpl;
}