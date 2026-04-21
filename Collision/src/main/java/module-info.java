import dk.sdu.cbse.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;
    requires CommonBullet;
    provides IPostEntityProcessingService with dk.sdu.cbse.collision.CollisionDetector;
}