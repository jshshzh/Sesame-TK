package fansirsqi.xposed.sesame.task.omegakoiTown;

import java.util.UUID;
import fansirsqi.xposed.sesame.hook.RequestManager;
import fansirsqi.xposed.sesame.util.RpcHelper;

/**
 * OmegakoiTown RPC调用类
 */
public class OmegakoiTownRpcCall {
    private static final String VERSION = "2.0";

    /**
     * 获取房屋产品信息
     */
    public static String houseProduct() {
        return RequestManager.requestString("com.alipay.omegakoi.town.v2.house.product",
                RpcHelper.buildParamsWithBizNo(""));
    }

    /**
     * 建造房屋
     */
    public static String houseBuild(String groundId, String houseId) {
        String params = "\"groundId\":\"" + groundId + "\",\"houseId\":\"" + houseId + "\"";
        return RequestManager.requestString("com.alipay.omegakoi.town.v2.house.build",
                RpcHelper.buildParamsWithBizNo(params));
    }

    /**
     * 获取用户分数
     */
    public static String getUserScore() {
        return RequestManager.requestString("com.alipay.omegakoi.town.v2.user.getUserScore",
                RpcHelper.buildParamsWithBizNo(""));
    }

    /**
     * 获取可收集的气球
     */
    public static String getBalloonsReadyToCollect() {
        return RequestManager.requestString("com.alipay.omegakoi.town.v2.balloon.getBalloonsReadyToCollect",
                RpcHelper.buildParamsWithBizNo(""));
    }

    /**
     * 获取用户任务
     */
    public static String getUserQuests() {
        String params = "\"disableQuests\":true,\"scenarioId\":\"shopNewestTips\"";
        return RequestManager.requestString("com.alipay.omegakoi.town.v2.scenario.getUserQuests",
                RpcHelper.buildParamsWithBizNo(params));
    }

    /**
     * 完成任务
     */
    public static String completeQuest(String questId) {
        String params = "\"questId\":\"" + questId + "\"";
        return RequestManager.requestString("com.alipay.omegakoi.town.v2.scenario.completeQuest",
                RpcHelper.buildParamsWithBizNo(params));
    }

    /**
     * 购买土地
     */
    public static String buyGround(String groundId) {
        String params = "\"groundId\":\"" + groundId + "\"";
        return RequestManager.requestString("com.alipay.omegakoi.town.v2.ground.buy",
                RpcHelper.buildParamsWithBizNo(params));
    }

    /**
     * 获取当前气球
     */
    public static String getCurrentBalloonsByTarget(String targetId) {
        String params = "\"targetId\":\"" + targetId + "\"";
        return RequestManager.requestString("com.alipay.omegakoi.town.v2.balloon.getCurrentBalloonsByTarget",
                RpcHelper.buildParamsWithBizNo(params));
    }

    /**
     * 获取用户任务列表
     */
    public static String getUserTasks() {
        return RequestManager.requestString("com.alipay.omegakoi.town.v2.task.getUserTasks",
                RpcHelper.buildParamsWithBizNo(""));
    }

    /**
     * 查询应用信息
     */
    public static String queryAppInfo() {
        return RequestManager.requestString("alipay.mappconfig.queryAppInfo",
                RpcHelper.buildParams(""));
    }

    /**
     * 触发任务奖励
     */
    public static String triggerTaskReward(String taskId) {
        String params = "\"taskId\":\"" + taskId + "\"";
        return RequestManager.requestString("com.alipay.omegakoi.town.v2.task.triggerTaskReward",
                RpcHelper.buildParamsWithBizNo(params));
    }

    /**
     * 获取分享ID
     */
    public static String getShareId() {
        return RequestManager.requestString("com.alipay.omegakoi.town.v2.user.getShareId",
                RpcHelper.buildParamsWithBizNo(""));
    }

    /**
     * 获取风蝶数据
     */
    public static String getFengdieData() {
        return RequestManager.requestString("com.alipay.omegakoi.town.v2.user.getFengdieData",
                RpcHelper.buildParamsWithBizNo(""));
    }

    /**
     * 获取签到状态
     */
    public static String getSignInStatus() {
        return RequestManager.requestString("com.alipay.omegakoi.town.v2.signIn.getSignInStatus",
                RpcHelper.buildParamsWithBizNo(""));
    }

    /**
     * 签到
     */
    public static String signIn() {
        return RequestManager.requestString("com.alipay.omegakoi.town.v2.signIn.signIn",
                RpcHelper.buildParamsWithBizNo(""));
    }

    /**
     * 获取产品信息
     */
    public static String getProduct() {
        return RequestManager.requestString("com.alipay.omegakoi.town.v2.shop.getProduct",
                RpcHelper.buildParamsWithBizNo(""));
    }

    /**
     * 获取用户土地
     */
    public static String getUserGrounds() {
        return RequestManager.requestString("com.alipay.omegakoi.town.v2.ground.getUserGrounds",
                RpcHelper.buildParamsWithBizNo(""));
    }

    /**
     * 获取用户房屋
     */
    public static String getUserHouses() {
        return RequestManager.requestString("com.alipay.omegakoi.town.v2.house.getUserHouses",
                RpcHelper.buildParamsWithBizNo(""));
    }

    /**
     * 收集房屋
     */
    public static String houseCollect(String houseId) {
        String params = "\"houseId\":\"" + houseId + "\"";
        return RequestManager.requestString("com.alipay.omegakoi.town.v2.house.collect",
                RpcHelper.buildParamsWithBizNo(params));
    }

    /**
     * 匹配人群
     */
    public static String matchCrowd(String crowdId) {
        String params = "\"crowdId\":\"" + crowdId + "\"";
        return RequestManager.requestString("com.alipay.omegakoi.common.user.matchCrowd",
                RpcHelper.buildParamsWithBizNo(params));
    }
}
