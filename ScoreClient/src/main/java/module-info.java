import dk.sdu.cbse.common.services.IScoreService;

module ScoreClient {
    requires Common;
    requires java.net.http;
    provides IScoreService with dk.sdu.cbse.scoreclient.RemoteScoreService;
}
