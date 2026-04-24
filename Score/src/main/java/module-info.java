import dk.sdu.cbse.common.services.IScoreService;

module Score {
    requires Common;
    provides IScoreService with dk.sdu.cbse.score.ScoreService;
}
