package com.xiuman.xinjiankang.Request;


import android.content.Context;

import com.xiuman.xinjiankang.app.MyApplication;
import com.xiuman.xinjiankang.constant.Constant;
import com.xiuman.xinjiankang.net.HttpDataTask;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.utils.AppSpUtil;

import java.util.HashMap;
import java.util.Map;
/*
 *				 			         _ooOoo_
 *				 			        o8888888o
 *				 			        88" . "88
 *				 			        (| -_- |)
 *				 			        O\  =  /O
 *				 		         ____/`---'\____
 *				 	           .'  \\|     |//  `.
 *				 	          /  \\|||  :  |||//  \
 *				 	         /  _||||| -:- |||||-  \
 *				 	         |   | \\\  -  /// |   |
 *				 	         | \_|  ''\---/''  |   |
 *				 	         \  .-\__  `-`  ___/-. /
 *				           ___`. .'  /--.--\  `. . __
 *				 	    ."" '<  `.___\_<|>_/___.'  >'"".
 *				 	  | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *				 	  \  \ `-.   \_ __\ /__ _/   .-` /  /
 *				 ======`-.____`-.___\_____/___.-`____.-'======
 *				 					`=---='
 *				 ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *						 佛祖保佑       永无BUG
 *				 		佛曰:
 *					 写字楼里写字间，写字间里程序员；
 *					 程序人员写程序，又拿程序换酒钱。
 *					 酒醒只在网上坐，酒醉还来网下眠；
 *					 酒醉酒醒日复日，网上网下年复年。
 *					 但愿老死电脑间，不愿鞠躬老板前；
 *					 奔驰宝马贵者趣，公交自行程序员。
 *					 别人笑我忒疯癫，我笑自己命太贱；
 *					 不见满街漂亮妹，哪个归得程序员？
 */

/**
 * 描述：用户端请求
 * Created by hxy on 2015/8/20.
 */
public class UserRequest {
    /**
     * 获取首页广告
     *
     * @param context
     * @param listener
     */
    public void getAdvertisement(Context context, HttpTaskListener listener) {
        String uri = Constant.http + "/jk_app_front!searchModuleInfo.action?moduleName=banner";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        task.get(uri, parametersPair);
    }

    /**
     * 推荐医师列表
     *
     * @param context
     * @param listener
     */
    public void getRecommendDoctorList(Context context, HttpTaskListener listener) {
        String uri = Constant.http + "/jk_doctor!recommendedDoctor.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        task.get(uri, parametersPair);
    }

    /**
     * 推荐医师列表
     *
     * @param context
     * @param listener
     */
    public void getRecommendHospitalList(Context context, HttpTaskListener listener) {
        String uri = Constant.http + "/jk_hospital!recommendedHospital.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        task.get(uri, parametersPair);
    }

    /**
     * 科普知识列表
     *
     * @param context
     * @param listener
     */
    public void getScienceTechologyList2(Context context, HttpTaskListener listener) {
        String uri = Constant.http + "/jk_article!knowledgeListByCategory.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        task.get(uri, parametersPair);
    }

    /**
     * 统计咨询阅读量
     *
     * @param httpTaskListener
     */
    public void saveStatistics(Context context, HttpTaskListener httpTaskListener, String id) {
        String url = Constant.http + "/jk_cases!searchDisease.action";
        HttpDataTask httpDataTask = new HttpDataTask(context, httpTaskListener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("articleId", id);
        if (MyApplication.getInstance().isUserLogin()) {
            parametersPair.put("memberId", AppSpUtil.getInstance().getUserInfo().getUserId());
        }
        httpDataTask.get(url, parametersPair);
    }


    /**
     * 科普知识分类
     *
     * @param listener
     */
    public void getScienceTechologyClassify(Context context, HttpTaskListener listener) {
        String uri = Constant.http + "/jk_article!getArticleCategory.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        task.get(uri, parametersPair);
    }


    /**
     * 获取科普知识详情
     *
     * @param context
     * @param listener
     * @param id
     */
    public void getScienceTechologyDetail(Context context, HttpTaskListener listener, String id) {
        String uri = Constant.http + "/jk_article!knowledgeDetail.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("id", id);
        if (MyApplication.getInstance().isUserLogin()) {
            parametersPair.put("memberId", AppSpUtil.getInstance().getUserInfo().getUserId());
        }
        task.get(uri, parametersPair);
    }

    /**
     * 获取科普评论列表
     *
     * @param listener
     */
    public void getScienceTechologyCommentList(Context context, HttpTaskListener listener, String articleId, int page) {
        String uri = Constant.http + "/jk_article_comment!getCommentAction.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("articleId", articleId);
        parametersPair.put("flag", 0);
        parametersPair.put("type", "DESC");
        parametersPair.put("pageSizeApp", "10");
        parametersPair.put("pageApp", page);
        task.get(uri, parametersPair);
    }

    /**
     * 科普详情点赞
     *
     * @param listener
     */
    public void getScienceTechologyPraise(Context context, HttpTaskListener listener, String articleId) {
        String uri = Constant.http + "/jk_article_praise!praise.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("articleId", articleId);
        parametersPair.put("memberId", AppSpUtil.getInstance().getUserInfo().getUserId());
        task.get(uri, parametersPair);
    }

    /**
     * 科普详情发表评论
     *
     * @param listener
     */
    public void postScienceTechologyComment(Context context, HttpTaskListener listener, String articleId, String content) {
        String uri = Constant.http + "/jk_article_comment!comment.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("articleId", articleId);
        parametersPair.put("memberId", AppSpUtil.getInstance().getUserInfo().getUserId());
        parametersPair.put("content", content);
        task.post(uri, parametersPair);
    }

    /**
     * 获取分享链接
     *
     * @param listener
     */
    public void getScienceTechologyShareUrl(Context context, HttpTaskListener listener, String id) {
        String uri = Constant.http + "/jk_article!getArticleShareUrl.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("id", id);
        task.get(uri, parametersPair);
    }

    /**
     * 科普知识列表
     *
     * @param listener
     * @param page       页数
     * @param pageSize   每页个数
     * @param categoryId 分类Id
     */
    public void getScienceList(Context context, HttpTaskListener listener, int page, int pageSize, String categoryId) {
        String uri = Constant.http + "/jk_article!knowledgeListByCategory.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("pageApp", page);
        parametersPair.put("pageSizeApp", pageSize);
        parametersPair.put("category", categoryId);
        task.post(uri, parametersPair);
    }

    /**
     * 医师关注
     *
     * @param httpTaskListener
     */
    public void takeAttention(Context context, HttpTaskListener httpTaskListener, String jkId, int type) {
        String url = Constant.http + "/jk_follow!saveFollow.action";
        HttpDataTask httpDataTask = new HttpDataTask(context, httpTaskListener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("jkId", jkId);
        parametersPair.put("type", type);
        parametersPair.put("memberId", AppSpUtil.getInstance().getUserInfo().getUserId());
        httpDataTask.get(url, parametersPair);
    }


    /**
     * param mContext
     * param httpTaskListener
     * param singleurl
     * param verappusername   用户名
     * param verapppawss      密码 2014- -12
     * 描述：用户登录
     */
    public void getUserLogin(Context mContext, HttpTaskListener httpTaskListener,
                             String verappusername, String verapppawss) {
        String url = Constant.userLoogin + "/member!LonginVer.action?";
        HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("verappusername", verappusername);
        parametersPair.put("verapppawss", verapppawss);
        parametersPair.put("imie", MyApplication.getInstance().getIMEI());
        httpDataTask.post(url, parametersPair);
    }

    /**
     * 获取疾病类型列表
     *
     * @param context
     * @param listener
     */
    public void getSelfDiagnoseList(Context context, HttpTaskListener listener) {
        String uri = Constant.http + "/jk_cases!selfDiagnoseList.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        task.get(uri, parametersPair);
    }

    /**
     * 获取案例列表
     *
     * @param context
     * @param listener
     */
    public void getDiagnoseDetail(Context context, HttpTaskListener listener, String categoryId, int page, int pageSize) {
        String uri = Constant.http + "/jk_vip_consulting!getConsultingList.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("diseaseCategoryId", categoryId);
        parametersPair.put("pageApp", page);
        parametersPair.put("pageSizeApp", pageSize);
        task.get(uri, parametersPair);
    }

    /**
     * 获取病情详解
     *
     * @param context
     * @param listener
     */
    public void getPatientDetail(Context context, HttpTaskListener listener, String categoryId) {
        String uri = Constant.http + "/jk_cases!getDiseaseDetail.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("diseaseCategoryId", categoryId);
        task.get(uri, parametersPair);
    }

    /**
     * 获取VIP咨询详情
     *
     * @param context
     * @param listener
     */
    public void getCaseConsultDetail(Context context, HttpTaskListener listener, String casesId) {
        String uri = Constant.http + "/jk_vip_consulting!getCounselDetail.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("counselId", casesId);
        task.get(uri, parametersPair);
    }

    /**
     * 查找案例
     *
     * @param mContext
     * @param httpTaskListener
     */
    public void getCaseList(Context mContext, HttpTaskListener httpTaskListener, String search, int page, int pageSize) {
        String url = Constant.http + "/jk_cases!searchDisease.action";
        HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("search", search);
        parametersPair.put("pageApp", page);
        parametersPair.put("pageSizeApp", pageSize);
        httpDataTask.post(url, parametersPair);
    }

    /**
     * 搜索结果
     *
     * @param context
     * @param listener
     * @param page     页码
     * @param pageSize 每页长度
     * @param keywork  搜索关键字
     */
    public void getSearchResult(Context context, HttpTaskListener listener, int page, int pageSize, String keywork) {
        String uri = Constant.http + "/jk_article!knowledgeSearch.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("pageApp", page);
        parametersPair.put("pageSizeApp", pageSize);
        parametersPair.put("key", keywork);
        task.get(uri, parametersPair);
    }

    /**
     * VIP咨询详情
     *
     * @param context
     * @param listener
     */
    public void getVIPConsultDetail(Context context, HttpTaskListener listener, String counselId, int page, int pageSize) {
        String uri = Constant.httpDoctor + "jk_vip_reply!getReplyies.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("counselId", counselId);
        parametersPair.put("pageApp", page);
        parametersPair.put("pageSizeApp", pageSize);
        task.post(uri, parametersPair);
    }

    /**
     * 搜索历史
     *
     * @param context
     * @param listener
     */
    public void getSearchHistory(Context context, HttpTaskListener listener) {
        String uri = Constant.http + "/jk_article!knowledgeSearchPage.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        task.get(uri, parametersPair);
    }

    /**
     * 免费咨询数量
     *
     * @param mContext
     * @param httpTaskListener
     */
    public void getFreeListCount(Context mContext, HttpTaskListener httpTaskListener) {
        String url = Constant.http + "/jk_free_consult!getCasesCount.action";
        HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        httpDataTask.get(url, parametersPair);
    }

    /**
     * 获取科室列表
     *
     * @param httpTaskListener
     */
    public void getDepartmentList(Context context, HttpTaskListener httpTaskListener) {
        String url = Constant.http + "/jk_department!departmentList.action";
        HttpDataTask httpDataTask = new HttpDataTask(context, httpTaskListener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        httpDataTask.get(url, parametersPair);
    }

    /**
     * 获取区域列表
     *
     * @param httpTaskListener
     */
    public void getAreaList(Context context, HttpTaskListener httpTaskListener) {
        String url = Constant.http + "/area!areaList.action";
        HttpDataTask httpDataTask = new HttpDataTask(context, httpTaskListener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        httpDataTask.get(url, parametersPair);
    }

    /**
     * 查找医师列表
     *
     * @param context
     * @param listener
     * @param page
     * @param pageSize
     * @param lng
     * @param lat
     * @param areaId     区域id
     * @param sequence   排序方式
     * @param sections   科室
     * @param docService 服务类别
     */
    public void getDoctorList(Context context, HttpTaskListener listener, int page, int pageSize, double lng, double lat, String areaId, int sequence, String sections, int docService, String keyWord) {
        String uri = Constant.http + "/jk_doctor!searchDoctor.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("pageApp", page);
        parametersPair.put("pageSizeApp", pageSize);
        parametersPair.put("lng", lng);
        parametersPair.put("lat", lat);
        if (areaId != null && !areaId.endsWith(",") && !"".equals(areaId)) {
            parametersPair.put("area", areaId);
        }
        if (!"".equals(keyWord) && keyWord != null) {
            parametersPair.put("keyWord", keyWord);
        }
        parametersPair.put("sequence", sequence);
        if (!"".equals(sections) && sections != null) {
            parametersPair.put("sections", sections);
        }
        parametersPair.put("docService", docService);
        task.get(uri, parametersPair);
    }

    /**
     * 查找医师列表
     *
     * @param context
     * @param listener
     * @param page     页码
     * @param pageSize 条数
     * @param lng      精度
     * @param lat      纬度
     */
    public void getDoctorList(Context context, HttpTaskListener listener, int page, int pageSize, double lng, double lat) {
        String uri = Constant.http + "/jk_doctor!selectDoctorArea.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("pageApp", page);
        parametersPair.put("pageSizeApp", pageSize);
        parametersPair.put("lng", lng);
        parametersPair.put("lat", lat);
        task.get(uri, parametersPair);
    }

    /**
     * 获取送心意列表
     *
     * @param context
     * @param listener
     */
    public void getMindList(Context context, HttpTaskListener listener, String id) {
        String uri = Constant.http + "/jk_mind!getMindMessage.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("id", id);
        task.get(uri, parametersPair);
    }

    /**
     * 医师详情
     *
     * @param context
     * @param listener
     * @param doctorID 医师ID
     */
    public void getDoctorDetail(Context context, HttpTaskListener listener, String doctorID) {
        String uri = Constant.http + "/jk_doctor!getDoctorDetail.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("doctorId", doctorID);
        if (MyApplication.getInstance().isUserLogin()) {
            parametersPair.put("userId", AppSpUtil.getInstance().getUserInfo().getUserId());
        }
        task.get(uri, parametersPair);
    }

    /**
     * 医师问诊模块
     *
     * @param context
     * @param listener
     * @param doctorID 医师ID
     */
    public void getDoctorInquiryList(Context context, HttpTaskListener listener, String doctorID) {
        String uri = Constant.http + "/jk_inquiry!searchInquiryList.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("doctorId", doctorID);
        parametersPair.put("sortType", "ASC");
        task.get(uri, parametersPair);
    }

    /**
     * 医师用户反馈积分
     *
     * @param context
     * @param listener
     * @param doctorID 医师ID
     */
    public void getDoctorUserFreeback(Context context, HttpTaskListener listener, String doctorID) {
        String uri = Constant.http + "/jk_feedback!searchFeedbackList.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("doctorId", doctorID);
        task.get(uri, parametersPair);
    }
}
