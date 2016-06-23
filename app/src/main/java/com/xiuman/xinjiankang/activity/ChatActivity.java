package com.xiuman.xinjiankang.activity;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.exceptions.HyphenateException;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.adapter.CommonRecyclerViewAdapter;
import com.xiuman.xinjiankang.adapter.CommonViewHolder;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.base.BaseActivity;
import com.xiuman.xinjiankang.bean.BeanCommonViewType;
import com.xiuman.xinjiankang.bean.CaseConsultDetail;
import com.xiuman.xinjiankang.bean.User;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.utils.AppSpUtil;
import com.xiuman.xinjiankang.utils.DateUtils;
import com.xiuman.xinjiankang.utils.logger.Logger;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by PCPC on 2016/6/20.
 */
public class ChatActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, EMMessageListener {

    @Override
    protected int getView() {
        return R.layout.activity_chat;
    }

    public static final String paramQuestionID = "questionID";
    public static final String paramDoctorName = "doctorName";
    public static final String paramDoctorUserName = "doctorUserName";


    public String questionID = "";
    public String doctorName = "";
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.etMessage)
    EditText etMessage;
    @Bind(R.id.btSend)
    Button btSend;
    @Bind(R.id.ivAdd)
    ImageView ivAdd;

    CommonRecyclerViewAdapter mAdapter;
    List<BeanCommonViewType> mDatas = new ArrayList<>();
    int page = 1;

    BeanCommonViewType loadMore;
    int lastVisibleItem;
    LinearLayoutManager layoutManager;
    ArrayList<String> paths = new ArrayList<>();
    List<EMMessage> msgList = new ArrayList<>();

    User curUser = AppSpUtil.getInstance().getUserInfo();
    EMChatManager chatManager;

    @Override
    protected void initView() {
        questionID = getIntent().getStringExtra(paramQuestionID);
        doctorName = getIntent().getStringExtra(paramDoctorName);
        setupToolbar();
        chatManager = EMClient.getInstance().chatManager();
        chatManager.addMessageListener(this);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setSmoothScrollbarEnabled(true);

        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(layoutManager);
        mRecyclerview.setAdapter(mAdapter = new CommonRecyclerViewAdapter(mDatas) {

            @Override
            public void convert(CommonViewHolder holder, int position) {
                switch (getItemViewType(position)) {
                    //接收文本消息布局
                    case R.layout.hx_row_received_message:
                        EMMessage msgLeft = (EMMessage) mDatas.get(position).getBeanObj();
                        TextView tvTimeLeft = holder.getView(R.id.timestamp);
                        TextView tvMessageLeft = holder.getView(R.id.tv_chatcontent);
                        ImageView ivPhotoLeft = holder.getView(R.id.iv_userhead);
                        try {
                            tvTimeLeft.setText(DateUtils.dateFormat(msgLeft.getMsgTime()));
                            tvMessageLeft.setText(((EMTextMessageBody) msgLeft.getBody()).getMessage());
                            x.image().bind(ivPhotoLeft, msgLeft.getStringAttribute(param_avatar));
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                        break;
                    //发送文本消息布局
                    case R.layout.hx_row_sent_message:
                        EMMessage msg = (EMMessage) mDatas.get(position).getBeanObj();
                        TextView tvTime = holder.getView(R.id.timestamp);
                        TextView tvMessage = holder.getView(R.id.tv_chatcontent);
                        TextView tvStatus = holder.getView(R.id.tvStatus);
                        ImageView ivPhoto = holder.getView(R.id.iv_userhead);
                        ImageView ivError = holder.getView(R.id.msg_status);
                        final ProgressBar progressBar = holder.getView(R.id.pb_sending);
                        tvTime.setText(DateUtils.dateFormat(msg.getMsgTime()));
                        tvMessage.setText(((EMTextMessageBody) msg.getBody()).getMessage());
                        msg.setMessageStatusCallback(new EMCallBack() {
                            @Override
                            public void onSuccess() {
                                Logger.i("发送成功");
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(int i, String s) {
                                progressBar.setVisibility(View.GONE);
                                Logger.i(s);
                            }

                            @Override
                            public void onProgress(int i, String s) {
                                progressBar.setVisibility(View.VISIBLE);
                                Logger.i(s);
                            }
                        });
                        if (msg.getError() == 0) {
                            if (msg.isAcked()) {
                                tvStatus.setText("已读");
                            } else if (msg.isDelivered()){
                                tvStatus.setText("送达");
                            }else{
                                tvStatus.setText("");
                            }
                            progressBar.setVisibility(View.GONE);
                            tvStatus.setVisibility(View.VISIBLE);
                            ivError.setVisibility(View.INVISIBLE);
                        } else {
                            ivError.setVisibility(View.VISIBLE);
                            tvStatus.setVisibility(View.INVISIBLE);
                        }
                        try {
                            x.image().bind(ivPhoto, msg.getStringAttribute(param_avatar));
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }

                        break;
                    case R.layout.layout_caseinfo:
                        CaseConsultDetail caseInfo = (CaseConsultDetail) mDatas.get(position).getBeanObj();
                        TextView tvContetn = holder.getView(R.id.content);
                        TextView tvUserName = holder.getView(R.id.user_name);
                        TextView tvDoctorName = holder.getView(R.id.doctor_name);
                        TextView tvAge = holder.getView(R.id.age);
                        TextView sex = holder.getView(R.id.sex);
                        TextView tvDate = holder.getView(R.id.time);
                        tvContetn.setText(caseInfo.getDatasource().getContent());
                        tvAge.setText(String.valueOf(caseInfo.getDatasource().getAge()) + "岁");
                        tvDoctorName.setText(caseInfo.getDatasource().getDoctorName());
                        tvUserName.setText(caseInfo.getDatasource().getName());
                        tvDate.setText(caseInfo.getDatasource().getCreateDate());
                        if (caseInfo.getDatasource().isSex()) {
                            sex.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.xjk_man_all_blue, 0, 0, 0);
                            sex.setBackgroundResource(R.drawable.xjk_man_bg);
                            sex.setText("男");
                        } else {
                            sex.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.xjk_woman_all_blue, 0, 0, 0);
                            sex.setBackgroundResource(R.drawable.xjk_woman_bg);
                            sex.setText("女");
                        }
                        sex.setTextColor(0xffffffff);
                        x.image().bind((ImageView) holder.getView(R.id.ivIcon), caseInfo.getDatasource().getDoctorHead(), this.options);
                        GridLayout gridlayout = holder.getView(R.id.gridlayout);
                        if (gridlayout.getChildCount() == caseInfo.getDatasource().getImgUrl().size()) {
                            return;
                        }
                        for (int i = 0; i < caseInfo.getDatasource().getImgUrl().size(); i++) {
                            paths.add(caseInfo.getDatasource().getImgUrl().get(i));
                            View view = LayoutInflater.from(mActivity).inflate(R.layout.item_imageview_gridlayout, null);
                            ImageView imageView = (ImageView) view.findViewById(R.id.ivPhoto);
                            x.image().bind(imageView, caseInfo.getDatasource().getImgUrl().get(i), optionsRadius);
                            imageView.setOnClickListener(onImageViewClick);
                            gridlayout.addView(view);
                        }
                        break;
                }
            }

            /**
             * 图片点击事件
             */
            View.OnClickListener onImageViewClick = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(mActivity, PhotoPagerActivity.class);
//                    intent.putExtra(PhotoPagerActivity.EXTRA_CURRENT_ITEM, current);
//                    intent.putExtra(PhotoPagerActivity.EXTRA_PHOTOS, paths);
//                    intent.putExtra(PhotoPagerActivity.EXTRA_SHOW_DELETE, false);
//                    intent.putExtra(PhotoPagerActivity.EXTRA_TYPE, 2);
//                    startActivity(intent);
                }
            };
        });

        getQuestionInfo();
        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    ivAdd.setVisibility(View.GONE);
                    btSend.setVisibility(View.VISIBLE);
                } else {
                    btSend.setVisibility(View.GONE);
                    ivAdd.setVisibility(View.VISIBLE);
                }
            }
        });
        etMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mRecyclerview.scrollToPosition(mDatas.size() - 1);
                } else {
                    hideWindowSoft(etMessage);
                }
            }
        });
    }

    private void hideWindowSoft(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 请求问题详情
     */
    private void getQuestionInfo() {
        AppManager.getUserRequest().getCaseConsultDetail(this, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                CaseConsultDetail caseInfo = new Gson().fromJson(result, CaseConsultDetail.class);
                BeanCommonViewType viewCaseInfo = new BeanCommonViewType();
                viewCaseInfo.setViewType(R.layout.layout_caseinfo);
                viewCaseInfo.setBeanObj(caseInfo);
                mDatas.add(viewCaseInfo);
                mAdapter.setDatas(mDatas);
                swipeRefreshLayout.setRefreshing(false);
                //预留加载历史消息方法
                getHistoryData();
            }

            @Override
            public void dataError(String result) {

            }
        }, questionID);
    }

    private void getHistoryData() {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(doctorName);
        if (conversation == null) {
            return;
        }
        //获取此会话的所有消息
        List<EMMessage> messages = msgList = conversation.getAllMessages();
        //SDK初始化加载的聊天记录为20条，到顶时需要去DB里获取更多
        //获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中
//        List<EMMessage> messages = conversation.loadMoreMsgFromDB(msgID, 20);
        for (EMMessage msg : messages) {
            BeanCommonViewType oneMessage = new BeanCommonViewType();
            try {
                if ("1".equals(msg.getStringAttribute(param_identity))) {
                    oneMessage.setViewType(R.layout.hx_row_sent_message);
                } else {
                    oneMessage.setViewType(R.layout.hx_row_received_message);
                }
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
            oneMessage.setBeanObj(msg);
            Logger.d(msg.isDelivered() + "");
            mDatas.add(oneMessage);
        }

        mRecyclerview.scrollToPosition(mDatas.size());
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onRefresh() {
//        getHistoryData();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    @OnClick({R.id.btSend, R.id.ivAdd})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btSend:
                String msg = etMessage.getText().toString();
                if (TextUtils.isEmpty(msg)) {
                    return;
                }
                sendMessage(msg);
                etMessage.setText("");
                break;
            case R.id.ivAdd:

                break;
        }
    }

    public final String param_userId = "userId";
    public final String param_avatar = "avatar";
    public final String param_identity = "identity";
    public final String param_nickname = "nickname";
    public final String param_questionId = "questionId";


    private void sendMessage(String msg) {
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(msg, doctorName);
        //设置扩展消息
        message.setAttribute(param_userId, curUser.getUserId());
        if (curUser.isDoctor()) {
            message.setAttribute(param_avatar, curUser.getDoctorHead());
            message.setAttribute(param_identity, "2");
            message.setAttribute(param_nickname, curUser.getDoctorName());
        } else {
            message.setAttribute(param_avatar, curUser.getAvatar());
            message.setAttribute(param_identity, "1");
            message.setAttribute(param_nickname, curUser.getNickname());
        }
        message.setAttribute(param_questionId, questionID);
//        message.setChatType(EMMessage.ChatType.ChatRoom);

        BeanCommonViewType oneMessage = new BeanCommonViewType();
        oneMessage.setViewType(R.layout.hx_row_sent_message);
        oneMessage.setBeanObj(message);
        mDatas.add(oneMessage);
        mRecyclerview.scrollToPosition(mDatas.size() - 1);
        chatManager.sendMessage(message);
    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {
        Logger.i("onMessageReceived");
        for (EMMessage msg : list) {
            BeanCommonViewType oneMSG = new BeanCommonViewType();
            oneMSG.setViewType(R.layout.hx_row_received_message);
            oneMSG.setBeanObj(msg);
            mDatas.add(oneMSG);
        }
        mAdapter.setDatas(mDatas);
        mAdapter.notifyDataSetChanged();
        mRecyclerview.smoothScrollToPosition(mDatas.size()-1);
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {
        Logger.i("onCmdMessageReceived");
    }

    @Override
    public void onMessageReadAckReceived(List<EMMessage> list) {
        Logger.i("onMessageReadAckReceived");
        for (EMMessage msg : list) {
            //跳过顶部布局
            for (int i = 1; i < mDatas.size(); i++) {
                BeanCommonViewType view = mDatas.get(i);
                EMMessage mMesg = (EMMessage)view.getBeanObj();
                if (msg.getMsgId().equals(mMesg.getMsgId())){
                    view.setBeanObj(msg);
                    mDatas.set(i,view);
                    mAdapter.notifyItemChanged(i);
                }
            }
        }
    }

    @Override
    public void onMessageDeliveryAckReceived(List<EMMessage> list) {
        mAdapter.notifyDataSetChanged();
        Logger.i("onMessageDeliveryAckReceived");
        for (EMMessage msg : list) {
            //跳过顶部布局
            for (int i = 1; i < mDatas.size(); i++) {
                BeanCommonViewType view = mDatas.get(i);
                EMMessage mMesg = (EMMessage)view.getBeanObj();
                if (msg.getMsgId().equals(mMesg.getMsgId())){
                    view.setBeanObj(msg);
                    mDatas.set(i,view);
                    mAdapter.notifyItemChanged(i);
                };
            }
        }
    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {
        Logger.i("onMessageChanged");
    }

}
