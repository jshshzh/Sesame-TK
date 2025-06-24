package fansirsqi.xposed.sesame.task.antSports;

import fansirsqi.xposed.sesame.hook.RequestManager;
import fansirsqi.xposed.sesame.util.RpcHelper;

/**
 * 蚂蚁运动RPC调用类
 */
public class AntSportsRpcCall {
    private static final String chInfo = "ch_appcenter__chsub_9patch",
            timeZone = "Asia\\/Shanghai", version = "3.0.1.2", alipayAppVersion = "0.0.852",
            cityCode = "330100", appId = "2021002116659397";

    // 统一的features字符串
    private static final String FEATURES = "[\"DAILY_STEPS_RANK_V2\",\"STEP_BATTLE\",\"CLUB_HOME_CARD\",\"NEW_HOME_PAGE_STATIC\",\"CLOUD_SDK_AUTH\",\"STAY_ON_COMPLETE\",\"EXTRA_TREASURE_BOX\",\"NEW_HOME_PAGE_STATIC\",\"SUPPORT_TAB3\",\"SUPPORT_FLYRABBIT\",\"PROP\",\"PROPV2\",\"ASIAN_GAMES\"]";
    
    // 新版features字符串
    private static final String NEW_FEATURES = "[\"DAILY_STEPS_RANK_V2\",\"STEP_BATTLE\",\"CLUB_HOME_CARD\",\"NEW_HOME_PAGE_STATIC\",\"CLOUD_SDK_AUTH\",\"STAY_ON_COMPLETE\",\"EXTRA_TREASURE_BOX\",\"SUPPORT_AI\",\"SUPPORT_FLYRABBIT\",\"SUPPORT_NEW_MATCH\",\"EXTERNAL_ADVERTISEMENT_TASK\",\"PROP\",\"PROPV2\",\"ASIAN_GAMES\"]";

    /**
     * 构建基础参数
     */
    private static String buildBaseParams() {
        return RpcHelper.buildParams("\"chInfo\":\"" + chInfo + "\",\"clientOS\":\"android\",\"features\":" + FEATURES);
    }
    
    /**
     * 构建新版基础参数
     */
    private static String buildNewBaseParams() {
        return RpcHelper.buildParams("\"chInfo\":\"" + chInfo + "\",\"clientOS\":\"android\",\"features\":" + NEW_FEATURES);
    }
    
    /**
     * 构建带时区的基础参数
     */
    private static String buildBaseParamsWithTimeZone() {
        return RpcHelper.buildParams("\"chInfo\":\"" + chInfo + "\",\"clientOS\":\"android\",\"features\":" + FEATURES + ",\"timeZone\":\"" + timeZone + "\"");
    }

    public static String queryCoinTaskPanel() {
        return RequestManager.requestString("alipay.antsports.walk.coin.task.queryCoinTaskPanel",
                buildBaseParams());
    }

    public static String completeExerciseTasks(String taskId) {
        String params = "\"chInfo\":\"" + chInfo + "\",\"clientOS\":\"android\",\"features\":" + FEATURES + ",\"taskId\":\"" + taskId + "\"";
        return RequestManager.requestString("alipay.antsports.walk.coin.task.completeExerciseTasks",
                RpcHelper.buildParams(params));
    }

    public static String sportsCheck_in() {
        String params = "\"chInfo\":\"" + chInfo + "\",\"clientOS\":\"android\",\"features\":" + FEATURES + ",\"timeZone\":\"" + timeZone + "\"";
        return RequestManager.requestString("alipay.antsports.walk.coin.task.sportsCheck_in",
                RpcHelper.buildParams(params));
    }

    public static String queryCoinBubbleModule() {
        return RequestManager.requestString("alipay.antsports.walk.coin.bubble.queryCoinBubbleModule",
                buildBaseParams());
    }

    public static String receiveCoinAsset(String assetId, int coinAmount) {
        String params = "\"assetId\":\"" + assetId + "\",\"chInfo\":\"" + chInfo + "\",\"clientOS\":\"android\",\"coinAmount\":" + coinAmount + ",\"features\":" + FEATURES;
        return RequestManager.requestString("alipay.antsports.walk.coin.bubble.receiveCoinAsset",
                RpcHelper.buildParams(params));
    }

    public static String queryMyHomePage() {
        return RequestManager.requestString("alipay.antsports.walk.user.queryMyHomePage",
                buildBaseParamsWithTimeZone());
    }

    public static String join(String pathId) {
        String params = "\"chInfo\":\"" + chInfo + "\",\"clientOS\":\"android\",\"features\":" + FEATURES + ",\"pathId\":\"" + pathId + "\"";
        return RequestManager.requestString("alipay.antsports.walk.path.join",
                RpcHelper.buildParams(params));
    }

    public static String openAndJoinFirst() {
        return RequestManager.requestString("alipay.antsports.walk.path.openAndJoinFirst",
                buildBaseParams());
    }

    public static String go(String day, String rankCacheKey, int stepCount) {
        String params = "\"chInfo\":\"" + chInfo + "\",\"clientOS\":\"android\",\"day\":\"" + day + "\",\"features\":" + FEATURES + ",\"rankCacheKey\":\"" + rankCacheKey + "\",\"stepCount\":" + stepCount + ",\"timeZone\":\"" + timeZone + "\"";
        return RequestManager.requestString("alipay.antsports.walk.path.go",
                RpcHelper.buildParams(params));
    }

    public static String openTreasureBox(String boxNo, String userId) {
        String params = "\"boxNo\":\"" + boxNo + "\",\"chInfo\":\"" + chInfo + "\",\"clientOS\":\"android\",\"features\":" + FEATURES + ",\"userId\":\"" + userId + "\"";
        return RequestManager.requestString("alipay.antsports.walk.path.openTreasureBox",
                RpcHelper.buildParams(params));
    }

    public static String queryBaseList() {
        return RequestManager.requestString("alipay.antsports.walk.charity.queryBaseList",
                buildBaseParams());
    }

    public static String queryProjectList(int index) {
        String params = "\"chInfo\":\"" + chInfo + "\",\"clientOS\":\"android\",\"features\":" + FEATURES + ",\"index\":" + index + ",\"projectListUseVertical\":true";
        return RequestManager.requestString("alipay.antsports.walk.charity.queryProjectList",
                RpcHelper.buildParams(params));
    }

    public static String donate(int donateCharityCoin, String projectId) {
        String params = "\"chInfo\":\"" + chInfo + "\",\"clientOS\":\"android\",\"donateCharityCoin\":" + donateCharityCoin + ",\"features\":" + FEATURES + ",\"projectId\":\"" + projectId + "\"";
        return RequestManager.requestString("alipay.antsports.walk.charity.donate",
                RpcHelper.buildParams(params));
    }

    public static String queryWalkStep() {
        return RequestManager.requestString("alipay.antsports.walk.user.queryWalkStep",
                buildBaseParamsWithTimeZone());
    }

    public static String walkDonateSignInfo(int count) {
        String params = "\"needDonateAction\":false,\"source\":\"walkDonateHome\",\"steps\":" + count + ",\"timezoneId\":\"" + timeZone + "\"";
        return RequestManager.requestString("alipay.charity.mobile.donate.walk.walkDonateSignInfo",
                RpcHelper.buildParams(params));
    }

    public static String donateWalkHome(int count) {
        String params = "\"module\":\"3\",\"steps\":" + count + ",\"timezoneId\":\"" + timeZone + "\"";
        return RequestManager.requestString("alipay.charity.mobile.donate.walk.home",
                RpcHelper.buildParams(params));
    }

    public static String exchange(String actId, int count, String donateToken) {
        String params = "\"actId\":\"" + actId + "\",\"count\":" + count + ",\"donateToken\":\"" + donateToken + "\",\"timezoneId\":\"" + timeZone + "\",\"ver\":0";
        return RequestManager.requestString("alipay.charity.mobile.donate.walk.exchange",
                RpcHelper.buildParams(params));
    }

    // 运动币兑好礼
    public static String queryItemDetail(String itemId) {
        return RequestManager.requestString("com.alipay.sportshealth.biz.rpc.SportsHealthItemCenterRpc.queryItemDetail",
                RpcHelper.buildParams("\"itemId\":\"" + itemId + "\""));
    }

    public static String exchangeItem(String itemId, int coinAmount) {
        String params = "\"coinAmount\":" + coinAmount + ",\"itemId\":\"" + itemId + "\"";
        return RequestManager.requestString("com.alipay.sportshealth.biz.rpc.SportsHealthItemCenterRpc.exchangeItem",
                RpcHelper.buildParams(params));
    }

    public static String queryExchangeRecordPage(String exchangeRecordId) {
        return RequestManager.requestString("com.alipay.sportshealth.biz.rpc.SportsHealthItemCenterRpc.queryExchangeRecordPage",
                RpcHelper.buildParams("\"exchangeRecordId\":\"" + exchangeRecordId + "\""));
    }

    /*
     * 新版 走路线
     */
    // 查询用户
    public static String queryUser() {
        String params = "\"source\":\"ch_appcenter__chsub_9patch\",\"timeZone\":\"" + timeZone + "\"";
        return RequestManager.requestString("com.alipay.sportsplay.biz.rpc.walk.queryUser",
                RpcHelper.buildParams(params));
    }

    // 查询主题列表
    public static String queryThemeList() {
        return RequestManager.requestString("com.alipay.sportsplay.biz.rpc.walk.theme.queryThemeList",
                buildNewBaseParams());
    }

    // 查询世界地图
    public static String queryWorldMap(String themeId) {
        String params = "\"chInfo\":\"" + chInfo + "\",\"clientOS\":\"android\",\"features\":" + NEW_FEATURES + ",\"themeId\":\"" + themeId + "\"";
        return RequestManager.requestString("com.alipay.sportsplay.biz.rpc.walk.queryWorldMap",
                RpcHelper.buildParams(params));
    }

    // 查询城市路线
    public static String queryCityPath(String cityId) {
        String params = "\"chInfo\":\"" + chInfo + "\",\"clientOS\":\"android\",\"features\":" + NEW_FEATURES + ",\"cityId\":\"" + cityId + "\"";
        return RequestManager.requestString("com.alipay.sportsplay.biz.rpc.walk.queryCityPath",
                RpcHelper.buildParams(params));
    }

    // 查询路线
    public static String queryPath(String appId, String date, String pathId) {
        String wufuRewardType = "WUFU_CARD";
        String params = "\"appId\":\"" + appId + "\",\"date\":\"" + date + "\",\"pathId\":\"" + pathId + "\",\"source\":\"ch_appcenter__chsub_9patch\",\"timeZone\":\"" + timeZone + "\",\"wufuRewardType\":\"" + wufuRewardType + "\"";
        return RequestManager.requestString("com.alipay.sportsplay.biz.rpc.walk.queryPath",
                RpcHelper.buildParams(params));
    }

    // 加入路线
    public static String joinPath(String pathId) {
        String params = "\"pathId\":\"" + pathId + "\",\"source\":\"ch_appcenter__chsub_9patch\"";
        return RequestManager.requestString("com.alipay.sportsplay.biz.rpc.walk.joinPath",
                RpcHelper.buildParams(params));
    }

    // 行走路线
    public static String walkGo(String appId, String date, String pathId, int useStepCount) {
        String params = "\"appId\":\"" + appId + "\",\"date\":\"" + date + "\",\"pathId\":\"" + pathId + "\",\"source\":\"ch_appcenter__chsub_9patch\",\"timeZone\":\"" + timeZone + "\",\"useStepCount\":\"" + useStepCount + "\"";
        return RequestManager.requestString("com.alipay.sportsplay.biz.rpc.walk.go",
                RpcHelper.buildParams(params));
    }

    // 开启宝箱
    // eventBillNo = boxNo(WalkGo)
    public static String receiveEvent(String eventBillNo) {
        return RequestManager.requestString("com.alipay.sportsplay.biz.rpc.walk.receiveEvent",
                RpcHelper.buildParams("\"eventBillNo\":\"" + eventBillNo + "\""));
    }

    // 查询路线奖励
    public static String queryPathReward(String appId, String pathId) {
        String params = "\"appId\":\"" + appId + "\",\"pathId\":\"" + pathId + "\",\"source\":\"ch_appcenter__chsub_9patch\"";
        return RequestManager.requestString("com.alipay.sportsplay.biz.rpc.walk.queryPathReward",
                RpcHelper.buildParams(params));
    }

    /* 这个好像没用 */
    public static String exchangeSuccess(String exchangeId) {
        String params = "\"exchangeId\":\"" + exchangeId + "\",\"timezone\":\"GMT+08:00\",\"version\":\"" + version + "\"";
        return RequestManager.requestString("alipay.charity.mobile.donate.exchange.success",
                RpcHelper.buildParams(params));
    }

    /* 文体中心 */
    public static String userTaskGroupQuery(String groupId) {
        String params = "\"cityCode\":\"" + cityCode + "\",\"groupId\":\"" + groupId + "\"";
        return RequestManager.requestString("alipay.tiyubiz.sports.userTaskGroup.query",
                RpcHelper.buildParams(params));
    }

    public static String userTaskComplete(String bizType, String taskId) {
        String params = "\"bizType\":\"" + bizType + "\",\"cityCode\":\"" + cityCode + "\",\"completedTime\":" + System.currentTimeMillis() + ",\"taskId\":\"" + taskId + "\"";
        return RequestManager.requestString("alipay.tiyubiz.sports.userTask.complete",
                RpcHelper.buildParams(params));
    }

    public static String userTaskRightsReceive(String taskId, String userTaskId) {
        String params = "\"taskId\":\"" + taskId + "\",\"userTaskId\":\"" + userTaskId + "\"";
        return RequestManager.requestString("alipay.tiyubiz.sports.userTaskRights.receive",
                RpcHelper.buildParams(params));
    }

    public static String queryAccount() {
        return RequestManager.requestString("alipay.tiyubiz.user.asset.query.account",
                RpcHelper.buildParams("\"accountType\":\"TIYU_SEED\""));
    }

    public static String queryRoundList() {
        return RequestManager.requestString("alipay.tiyubiz.wenti.walk.queryRoundList",
                RpcHelper.buildParams(""));
    }

    public static String participate(int bettingPoints, String InstanceId, String ResultId, String roundId) {
        String params = "\"bettingPoints\":" + bettingPoints + ",\"guessInstanceId\":\"" + InstanceId + "\",\"guessResultId\":\"" + ResultId + "\",\"newParticipant\":false,\"roundId\":\"" + roundId + "\",\"stepTimeZone\":\"Asia/Shanghai\"";
        return RequestManager.requestString("alipay.tiyubiz.wenti.walk.participate",
                RpcHelper.buildParams(params));
    }

    public static String pathFeatureQuery() {
        String params = "\"appId\":\"" + appId + "\",\"features\":[\"USER_CURRENT_PATH_SIMPLE\"],\"sceneCode\":\"wenti_shijiebei\"";
        return RequestManager.requestString("alipay.tiyubiz.path.feature.query",
                RpcHelper.buildParams(params));
    }

    public static String pathMapJoin(String pathId) {
        String params = "\"appId\":\"" + appId + "\",\"pathId\":\"" + pathId + "\"";
        return RequestManager.requestString("alipay.tiyubiz.path.map.join",
                RpcHelper.buildParams(params));
    }

    public static String pathMapHomepage(String pathId) {
        String params = "\"appId\":\"" + appId + "\",\"pathId\":\"" + pathId + "\"";
        return RequestManager.requestString("alipay.tiyubiz.path.map.homepage",
                RpcHelper.buildParams(params));
    }

    public static String stepQuery(String countDate, String pathId) {
        String params = "\"appId\":\"" + appId + "\",\"countDate\":\"" + countDate + "\",\"pathId\":\"" + pathId + "\",\"timeZone\":\"Asia/Shanghai\"";
        return RequestManager.requestString("alipay.tiyubiz.path.map.step.query",
                RpcHelper.buildParams(params));
    }

    public static String tiyubizGo(String countDate, int goStepCount, String pathId, String userPathRecordId) {
        String params = "\"appId\":\"" + appId + "\",\"countDate\":\"" + countDate + "\",\"goStepCount\":" + goStepCount + ",\"pathId\":\"" + pathId + "\",\"timeZone\":\"Asia/Shanghai\",\"userPathRecordId\":\"" + userPathRecordId + "\"";
        return RequestManager.requestString("alipay.tiyubiz.path.map.go",
                RpcHelper.buildParams(params));
    }

    public static String rewardReceive(String pathId, String userPathRewardId) {
        String params = "\"appId\":\"" + appId + "\",\"pathId\":\"" + pathId + "\",\"userPathRewardId\":\"" + userPathRewardId + "\"";
        return RequestManager.requestString("alipay.tiyubiz.path.map.reward.receive",
                RpcHelper.buildParams(params));
    }

    /* 抢好友大战 */
    public static String queryClubHome() {
        String params = "\"chInfo\":\"healthstep\",\"timeZone\":\"Asia/Shanghai\"";
        return RequestManager.requestString("alipay.antsports.club.home.queryClubHome",
                RpcHelper.buildParams(params));
    }

    public static String collectBubble(String bubbleId) {
        String params = "\"bubbleId\":\"" + bubbleId + "\",\"chInfo\":\"healthstep\"";
        return RequestManager.requestString("alipay.antsports.club.home.collectBubble",
                RpcHelper.buildParams(params));
    }

    public static String queryTrainItem() {
        return RequestManager.requestString("alipay.antsports.club.train.queryTrainItem",
                RpcHelper.buildParams("\"chInfo\":\"healthstep\""));
    }

    public static String trainMember(String itemType, String memberId, String originBossId) {
        String params = "\"chInfo\":\"healthstep\",\"itemType\":\"" + itemType + "\",\"memberId\":\"" + memberId + "\",\"originBossId\":\"" + originBossId + "\"";
        return RequestManager.requestString("alipay.antsports.club.train.trainMember",
                RpcHelper.buildParams(params));
    }

    public static String queryMemberPriceRanking(String coinBalance) {
        String params = "\"buyMember\":\"true\",\"chInfo\":\"healthstep\",\"coinBalance\":\"" + coinBalance + "\"";
        return RequestManager.requestString("alipay.antsports.club.ranking.queryMemberPriceRanking",
                RpcHelper.buildParams(params));
    }

    public static String queryClubMember(String memberId, String originBossId) {
        String params = "\"chInfo\":\"healthstep\",\"memberId\":\"" + memberId + "\",\"originBossId\":\"" + originBossId + "\"";
        return RequestManager.requestString("alipay.antsports.club.trade.queryClubMember",
                RpcHelper.buildParams(params));
    }

    public static String buyMember(String currentBossId, String memberId, String originBossId, String priceInfo, String roomId) {
        String params = "\"chInfo\":\"healthstep\",\"currentBossId\":\"" + currentBossId + "\",\"memberId\":\"" + memberId + "\",\"originBossId\":\"" + originBossId + "\",\"priceInfo\":" + priceInfo + ",\"roomId\":\"" + roomId + "\"";
        return RequestManager.requestString("alipay.antsports.club.trade.buyMember",
                RpcHelper.buildParams(params));
    }
}