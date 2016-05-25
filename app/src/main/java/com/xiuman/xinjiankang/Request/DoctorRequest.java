package com.xiuman.xinjiankang.Request;


import android.content.Context;

import com.lidroid.xutils.HttpUtils;
import com.xiuman.xinjiankang.constant.Constant;
import com.xiuman.xinjiankang.net.HttpDataTask;
import com.xiuman.xinjiankang.net.HttpTaskListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：用户端请求
 * Created by hxy on 2015/8/20.
 */
public class DoctorRequest extends HttpUtils {
    private Context context;

    /**
     * 构造函数
     */
    public DoctorRequest(Context context) {
        this.context = context;
        configUserAgent("gzip");
        configRequestThreadPoolSize(5);
        configHttpCacheSize(0);
        configTimeout(10000);
    }

    /**
     * 科普知识列表
     *
     * @param context
     * @param listener
     * @param doctorId 医师ID
     */
    public void getDoctorWallet(Context context, HttpTaskListener listener, String doctorId) {
        String uri = Constant.httpDoctor + "jk_doctor!myWallet.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("doctorId", doctorId);
        task.post(uri, parametersPair);
    }

    /**
     * 我的病人，VIP咨询列表
     *
     * @param context
     * @param listener
     * @param doctorId 医师ID
     */
    public void getMyPaintPictureConsult(Context context, HttpTaskListener listener, String doctorId, int page, int pageNum) {
        String uri = Constant.httpDoctor + "jk_vip_consulting!getPicTextCounsel.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("doctorId", doctorId);
        parametersPair.put("pageApp", page);
        parametersPair.put("pageSizeApp", pageNum);
        task.post(uri, parametersPair);
    }

    /**
     * 诊所-出诊时间
     *
     * @param context
     * @param listener
     * @param doctorId 医师ID
     */
    public void setVisitTime(Context context, HttpTaskListener listener, String doctorId, int flag, int timePart, String beginTime, String endTime) {
        String uri = Constant.httpDoctor + "jk_call_time!updateCallTime.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("doctorId", doctorId);
        parametersPair.put("weekFlag", flag);
        parametersPair.put("timePart", timePart);
        parametersPair.put("beginTime", beginTime);
        parametersPair.put("timePart", timePart);
        parametersPair.put("endTime", endTime);
        task.post(uri, parametersPair);
    }

    /**
     * 出诊时间列表
     *
     * @param context
     * @param listener
     * @param doctorId 医师IDget
     */
    public void getVisitTimeList(Context context, HttpTaskListener listener, String doctorId) {
        String uri = Constant.httpDoctor + "jk_call_time!getCallTime.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("doctorId", doctorId);
        task.post(uri, parametersPair);
    }

    /**
     * 推荐医师列表
     *
     * @param context
     * @param listener
     */
    public void getRecommendDoctorList(Context context, HttpTaskListener listener) {
        String uri = Constant.httpDoctor + "jk_doctor!recommendedDoctor.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        task.post(uri, parametersPair);
    }

    /**
     * 电话咨询
     *
     * @param context
     * @param listener
     */
    public void getMyPatientPhone(Context context, HttpTaskListener listener, String doctorID, int page, int pageSize) {
        String uri = Constant.httpDoctor + "jk_tel_counsel!getTelCounsel.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("pageApp", page);
        parametersPair.put("pageSizeApp", pageSize);
        parametersPair.put("doctorId", doctorID);
        task.post(uri, parametersPair);
    }

    /**
     * 排行榜
     *
     * @param context
     * @param listener
     */
    public void getRankDoctorList(Context context, HttpTaskListener listener, int page, int pageSize) {
        String uri = Constant.httpDoctor + "jk_doctor!recommendedDoctor.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("pageApp", page);
        parametersPair.put("pageSizeApp", pageSize);
        task.post(uri, parametersPair);
    }

    /**
     * 排行榜
     *
     * @param context
     * @param listener
     */
    public void getRankHospitalList(Context context, HttpTaskListener listener, int page, int pageSize) {
        String uri = Constant.httpDoctor + "jk_hospital!recommendedHospital.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("pageApp", page);
        parametersPair.put("pageSizeApp", pageSize);
        task.post(uri, parametersPair);
    }

    /**
     * 义诊列表
     *
     * @param context
     * @param listener
     */
    public void getPatientConsultList(Context context, HttpTaskListener listener, int page, int pageSize) {
        String uri = Constant.httpDoctor + "jk_cases!getQuestionList.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("pageApp", page);
        parametersPair.put("pageSizeApp", pageSize);
        task.post(uri, parametersPair);
    }

    /**
     * 义诊列表
     *
     * @param context
     * @param listener
     */
    public void getMyPatientConsultList(Context context, HttpTaskListener listener, int page, int pageSize, String doctorID) {
        String uri = Constant.httpDoctor + "jk_cases!patientCounsel.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("pageApp", page);
        parametersPair.put("pageSizeApp", pageSize);
        parametersPair.put("doctorId", doctorID);
        task.post(uri, parametersPair);
    }

    /**
     * 出诊价格
     *
     * @param context
     * @param listener
     */
    public void getCallPrice(Context context, HttpTaskListener listener, String doctorID) {
        String uri = Constant.httpDoctor + "jk_doctor!getCallPrice.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("doctorId", doctorID);
        task.post(uri, parametersPair);
    }

    /**
     * 出诊价格
     *
     * @param context
     * @param listener
     */
    public void setCallPrice(Context context, HttpTaskListener listener, String doctorID, int flag, String price) {
        String uri = Constant.httpDoctor + "jk_doctor!editCallPrice.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("doctorId", doctorID);
        parametersPair.put("priceFlag", flag);
        parametersPair.put("price", price);
        task.post(uri, parametersPair);
    }

    /**
     * 义诊详情医师回复
     *
     * @param context
     * @param listener
     */
    public void getQuestionDetailDcotorReply(Context context, HttpTaskListener listener, String counselId, int page, int pageSize) {
        String uri = Constant.httpDoctor + "jk_vip_consulting!getDcotorReply.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("counselId", counselId);
        parametersPair.put("pageApp", page);
        parametersPair.put("pageSizeApp", pageSize);
        task.post(uri, parametersPair);
    }

    /**
     * 客诉列表
     *
     * @param context
     * @param listener
     */
    public void getComplaintList(Context context, HttpTaskListener listener, String doctorId, int page, int pageSize) {
        String uri = Constant.httpDoctor + "jk_doctor_comment!getComplaints.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("doctorId", doctorId);
        parametersPair.put("pageApp", page);
        parametersPair.put("pageSizeApp", pageSize);
        task.post(uri, parametersPair);
    }

    /**
     * 提交申诉
     *
     * @param context
     * @param listener
     */
    public void postAppeal(Context context, HttpTaskListener listener, String doctorId, String content, String complaintId) {
        String uri = Constant.httpDoctor + "jk_doctor_comment!addDoctorAppeal.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("doctorId", doctorId);
        parametersPair.put("complaintId", complaintId);
        parametersPair.put("content", content);
        task.post(uri, parametersPair);
    }

    /**
     * 提交申诉
     *
     * @param context
     * @param listener
     */
    public void getTelConsultDetail(Context context, HttpTaskListener listener, String complaintId) {
        String uri = Constant.httpDoctor + "jk_tel_counsel!getTelCounselDetail.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("counselId", complaintId);
        task.post(uri, parametersPair);
    }

    /**
     * 修改预约时间
     *
     * @param context
     * @param listener
     */
    public void updateTelTime(Context context, HttpTaskListener listener, String complaintId, int status, String doctorTime) {
        String uri = Constant.httpDoctor + "jk_tel_counsel!updateTelCounsel.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("counselId", complaintId);
        parametersPair.put("status", status);
        parametersPair.put("doctorTime", doctorTime);
        task.post(uri, parametersPair);
    }

    /**
     * 退回
     *
     * @param context
     * @param listener
     */
    public void rejectTelCounsel(Context context, HttpTaskListener listener, String counselId, int causeItem, String causeContent) {
        String uri = Constant.httpDoctor + "jk_tel_counsel!rejectTelCounsel.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("counselId", counselId);
        parametersPair.put("causeItem", causeItem);
        parametersPair.put("causeContent", causeContent);
        task.post(uri, parametersPair);
    }

    /**
     * 抢答
     *
     * @param context
     * @param listener
     */
    public void getReplyVipCounselList(Context context, HttpTaskListener listener, String doctorId, int page, int pageSize, int status) {
        String uri = Constant.httpDoctor + "jk_vip_consulting!getPicTextCounsel.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("pageApp", page);
        parametersPair.put("pageSizeApp", pageSize);
        parametersPair.put("status", status);
        task.post(uri, parametersPair);
    }

    /**
     * 提现
     *
     * @param context
     * @param listener
     */
    public void getSettlements(Context context, HttpTaskListener listener, String doctorId, int page, int pageSize) {
        String uri = Constant.httpDoctor + "jk_settlement!getSettlements.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("doctorId", doctorId);
        parametersPair.put("pageApp", page);
        parametersPair.put("pageSizeApp", pageSize);
        task.post(uri, parametersPair);
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
     * 义诊咨询详情头
     *
     * @param context
     * @param listener
     */
    public void getQuestionDetailsHead(Context context, HttpTaskListener listener, String counselId) {
        String uri = Constant.httpDoctor + "jk_vip_consulting!getCounselDetail.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("counselId", counselId);
        task.post(uri, parametersPair);
    }

    /**
     * 义诊咨询详情医师回复
     *
     * @param context
     * @param listener
     */
    public void getQuestionDetailsDoctorReplyList(Context context, HttpTaskListener listener, String counselId, int pageApp, int pageSizeApp) {
        String uri = Constant.httpDoctor + "jk_vip_consulting!getDcotorReply.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("counselId", counselId);
        parametersPair.put("pageApp", pageApp);
        parametersPair.put("pageSizeApp", pageSizeApp);
        task.post(uri, parametersPair);
    }

    /**
     * 义诊咨询详情医师回复
     *
     * @param context
     * @param listener
     */
    public void getQuestionDetailsUserReplyList(Context context, HttpTaskListener listener, String counselId, int pageApp, int pageSizeApp) {
        String uri = Constant.httpDoctor + "jk_vip_consulting!getMemberReply.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("counselId", counselId);
        parametersPair.put("pageApp", pageApp);
        parametersPair.put("pageSizeApp", pageSizeApp);
        task.post(uri, parametersPair);
    }

    /**
     * 义诊咨询详情医师回复
     *
     * @param context
     * @param listener
     */
    public void postQuestionDetailsDoctorReply(Context context, HttpTaskListener listener, String doctorId, String casesId, String content) {
        String uri = Constant.httpDoctor + "jk_cases!doctorReplyConsult.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("doctorId", doctorId);
        parametersPair.put("casesId", casesId);
        parametersPair.put("content", content);
        task.post(uri, parametersPair);
    }

    /**
     * VIP咨询列表
     *
     * @param context
     * @param listener
     */
    public void getVIPConsultList(Context context, HttpTaskListener listener, int page, int pageSize) {
        String uri = Constant.httpDoctor + "jk_vip_consulting!getMyConsulting.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        //parametersPair.put("userId", AppSpUtil.getInstance().getUserInfo().getUserId());
        parametersPair.put("page", page);
        parametersPair.put("pageSizeApp", pageSize);
        task.post(uri, parametersPair);
    }

    /**
     * 地图数据
     *
     * @param context
     * @param listener
     */
    public void getMapRankDoctorList(Context context, HttpTaskListener listener, String cityId, double lat, double lng, int pageApp, int pageSizeApp) {
        String uri = Constant.httpDoctor + "jk_doctor!getDoctorTop.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("areaId", cityId);
        parametersPair.put("lat", lat);
        parametersPair.put("lng", lng);
        parametersPair.put("pageApp", pageApp);
        parametersPair.put("pageSizeApp", pageSizeApp);
        task.post(uri, parametersPair);
    }

    /**
     * 提交论文
     *
     * @param context
     * @param listener
     */
    public void postThesis(Context context, HttpTaskListener listener, String doctorId, ArrayList<String> content, String remark) {
        String uri = Constant.httpDoctor + "jk_doctor!getDoctorTop.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        StringBuffer sb = new StringBuffer();
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("doctorId", doctorId);
        for (int i = 0; i < content.size(); i++) {
            String str = content.get(i);
            sb.append(str + "|");
        }
        String substring = sb.substring(0, sb.length() - 2);
        parametersPair.put("content", substring);
        parametersPair.put("remark", remark);
        task.post(uri, parametersPair);
    }

    /**
     * 获取VIP列表
     *
     * @param context
     * @param listener
     */
    public void getVIPList(Context context, HttpTaskListener listener, int pageApp, int pageSizeApp) {
        String uri = Constant.http + "/jk_vip_consulting!getFinishList.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("pageApp", pageApp);
        parametersPair.put("pageSizeApp", pageSizeApp);
        task.post(uri, parametersPair);
    }


    /**
     * 完成问诊
     *
     * @param context
     * @param listener
     */
    public void completeConsult(Context context, HttpTaskListener listener, String counselId) {
        String uri = Constant.httpDoctor + "jk_tel_counsel!finish.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("counselId", counselId);
        task.post(uri, parametersPair);
    }

    /**
     * 获取认证资料里的医院。职称等信息
     *
     * @param context
     * @param listener
     */
    public void getApproveInfo(Context context, HttpTaskListener listener) {
        String uri = Constant.httpDoctor + "jk_doctor!getVerifyInfo.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        task.post(uri, null);
    }

    /**
     * 完善医生资料
     *
     * @param context
     * @param listener
     * @param userId    yh id
     * @param head      头像
     * @param gender    性别
     * @param hospital  医院id
     * @param docType   医生类型
     * @param position  医生职称
     * @param goodat    擅长
     * @param introduce 简介
     */
    public void completeProfile(Context context, HttpTaskListener listener, String userId, String head,
                                int gender, String hospital, int docType, String position, String goodat, String introduce,String name) {
        String uri = Constant.httpDoctor + "jk_doctor!docRegister.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("userId", userId);
        parametersPair.put("head", head);
        parametersPair.put("sex", gender);
        parametersPair.put("hospital", hospital);
        parametersPair.put("docType", docType);
        parametersPair.put("position", position);
        parametersPair.put("goodat", goodat);
        parametersPair.put("introduce", introduce);
        parametersPair.put("name",name);

        task.post(uri, parametersPair);
    }

    /**
     * 提交认证资料(职业资格证)
     * @param context
     * @param listener
     * @param userId
     */
    public void approve(Context context,HttpTaskListener listener,String userId,String occup){
        String uri = Constant.httpDoctor + "jk_doctor_images!docVerity.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("userId", userId);

        parametersPair.put("occup", occup);
        task.post(uri, parametersPair);
    }

    /**
     * 身份证上传
     * @param context
     * @param listener
     * @param userId
     * @param ident
     */
    public void approve2(Context context,HttpTaskListener listener,String userId,String ident){
        String uri = Constant.httpDoctor + "jk_doctor_images!docVerity.action";
        HttpDataTask task = new HttpDataTask(context, listener);
        Map<String, Object> parametersPair = new HashMap<String, Object>();
        parametersPair.put("userId", userId);
        parametersPair.put("ident", ident);
        task.post(uri, parametersPair);
    }
}

