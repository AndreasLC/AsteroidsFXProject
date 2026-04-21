import dk.sdu.cbse.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;
    requires CommonBullet;
    requires CommonAsteroids;
    requires CommonPlayer;
    requires CommonEnemy;
    provides IPostEntityProcessingService with dk.sdu.cbse.collision.CollisionDetector;
    uses dk.sdu.cbse.common.asteroids.IAsteroidSplitter;
}