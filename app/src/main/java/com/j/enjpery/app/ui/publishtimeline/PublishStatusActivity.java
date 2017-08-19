package com.j.enjpery.app.ui.publishtimeline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVStatus;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.j.enjpery.R;
import com.j.enjpery.app.global.EnjperyApplication;
import com.j.enjpery.app.ui.mainactivity.mainfragment.timelinefragment_widget.FillContent;
import com.j.enjpery.app.ui.userinfo.UserInfoActivity;
import com.j.enjpery.app.util.CompressCompletedCB;
import com.j.enjpery.app.util.KeyBoardUtil;
import com.j.enjpery.app.util.SnackbarUtil;
import com.j.enjpery.app.util.StringUtils;
import com.j.enjpery.app.util.ToastUtil;
import com.j.enjpery.app.util.WeiBoContentTextUtil;
import com.j.enjpery.model.Comment;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.zxy.tiny.Tiny;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import top.zibin.luban.Luban;

import static com.j.enjpery.app.util.Constants.POST_SERVICE_COMMENT_STATUS;
import static com.j.enjpery.app.util.Constants.POST_SERVICE_CREATE_WEIBO;
import static com.j.enjpery.app.util.Constants.POST_SERVICE_REPLY_COMMENT;
import static com.j.enjpery.app.util.Constants.POST_SERVICE_REPOST_STATUS;

public class PublishStatusActivity extends BaseSwipeActivity implements ImgListAdapter.OnFooterViewClickListener {

    private static final int IMAGE_PICKER = 100;

    private Boolean firstInitRecycleView = true;

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.statusContent)
    EditText statusContent;
    @BindView(R.id.ImgList)
    RecyclerView recyclerView;
    @BindView(R.id.idea_linearLayout)
    LinearLayout ideaLinearLayout;
    @BindView(R.id.blankspace)
    ImageView blankspace;
    @BindView(R.id.publicbutton)
    TextView publicbutton;
    @BindView(R.id.limitTextView)
    TextView limitTextView;
    @BindView(R.id.picture)
    ImageView picture;
    @BindView(R.id.mentionbutton)
    ImageView mentionbutton;
    @BindView(R.id.topicButton)
    ImageView topicButton;
    @BindView(R.id.emoticonbutton)
    ImageView emoticonbutton;
    @BindView(R.id.more_button)
    ImageView moreButton;
    @BindView(R.id.repost_img)
    ImageView repostImg;
    @BindView(R.id.repost_name)
    TextView repostName;
    @BindView(R.id.repost_content)
    TextView repostContent;
    @BindView(R.id.repost_layout)
    LinearLayout repostLayout;
    // private UsersAPI mUsersAPI;
    private Context mContext;

    private ArrayList<ImageItem> mSelectImgList = new ArrayList<>();
    private AVStatus mStatus;
    // 原创还是转发
    private String statusType;


    /**
     * 最多输入140个字符
     */
    private static final int TEXT_LIMIT = 140;

    /**
     * 在只剩下10个字可以输入的时候，做提醒
     */
    private static final int TEXT_REMIND = 10;
    private boolean mStartAlumbAcitivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compose_idea_layout);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mContext = this;
        statusType = getIntent().getStringExtra("type");
        tvDescription.setText("新的状态，新的心情");
        initContent();
        initImgList();
        setUpListener();
        //  Tag的作用就是设置标签，标签可以是任意。
        statusContent.setTag(false);

        mStartAlumbAcitivity = getIntent().getBooleanExtra("startAlumbAcitivity", false);
        if (mStartAlumbAcitivity == true) {
            Intent intent = new Intent(PublishStatusActivity.this, ImagePreviewDelActivity.class);
            // intent.putParcelableArrayListExtra("selectedImglist", mSelectImgList);
            startActivityForResult(intent, 0);
        }
        statusContent.post(() -> {
            if (mStartAlumbAcitivity == false) {
                setLimitTextColor(limitTextView, statusContent.getText().toString());
                KeyBoardUtil.openKeybord(statusContent, mContext);
                statusContent.requestFocus();
            }
        });
    }

    /**
     * 填充内容，
     * 1. 转发的内容是转发微博，
     * 2. 转发的内容是原创微博，
     */
    private void initContent() {
        switch (statusType) {
            case POST_SERVICE_CREATE_WEIBO:
                break;
            case POST_SERVICE_REPOST_STATUS:
                //填充转发的内容
                repostWeiBo();
                break;
            case POST_SERVICE_COMMENT_STATUS:
                break;
            case POST_SERVICE_REPLY_COMMENT:
                break;
        }
    }


    /**
     * 填充转发的内容
     */
    private void repostWeiBo() {
        if (mStatus == null) {
            return;
        }
        repostLayout.setVisibility(View.VISIBLE);
        statusContent.setHint("说说分享的心得");

        //1. 转发的内容是转发微博
        if (!StringUtils.isBlank((String) mStatus.get("retweetedStatus"))) {
            statusContent.setText(WeiBoContentTextUtil.getWeiBoContent("//@" + mStatus.getSource().get("account") + ":" + mStatus.getMessage(), mContext, statusContent));
            FillContent.fillMentionCenterContent((AVStatus) mStatus.get("retweetedStatus"), repostImg, repostName, repostContent);
            statusContent.setSelection(0);
        }
        //2. 转发的内容是原创微博
        else {
            FillContent.fillMentionCenterContent(mStatus, repostImg, repostName, repostContent);
        }
        changeSendButtonBg();
    }


    /**
     * 设置监听事件
     */
    private void setUpListener() {
        RxView.clicks(btnBack)
                .subscribe(aVoid -> {
                    ToastUtil.showShort(PublishStatusActivity.this, "回到主界面");
                    finish();
                });

        // 打开相册
        RxView.clicks(picture)
                .subscribe(Avoid -> {
                    ImagePicker.getInstance().setSelectLimit(9);
                    Intent intent = new Intent(this, ImageGridActivity.class);
                    ImagePicker.getInstance().setMultiMode(true);
                    startActivityForResult(intent, IMAGE_PICKER);
                    KeyBoardUtil.closeKeybord(statusContent, mContext);
                });

        // @符号
        RxView.clicks(mentionbutton)
                .subscribe(Avoid -> {
                    statusContent.getText().insert(statusContent.getSelectionStart(), "@");
                });

        // 话题
        RxView.clicks(topicButton)
                .subscribe(Avoid -> {
                    statusContent.getText().insert(statusContent.getSelectionStart(), "##");
                    statusContent.setSelection(statusContent.getSelectionStart() - 1);
                });

        RxView.clicks(emoticonbutton)
                .subscribe(aVoid -> {
                    ToastUtil.showShort(mContext, "正在开发此功能...");
                });

        RxView.clicks(moreButton)
                .subscribe(aVoid -> {
                    ToastUtil.showShort(mContext, "正在开发此功能...");
                });

        // 添加监听器
        statusContent.addTextChangedListener(watcher);

        RxView.clicks(btnOk)
                .subscribe(aVoid -> {
                    //在发微博状态下，如果发的微博没有图片，且也没有文本内容，识别为空
                    if (!isRetweetWeiBoState() && mStatus == null && mSelectImgList.size() == 0 && (statusContent.getText().toString().isEmpty() || statusContent.getText().toString().length() == 0)) {
                        ToastUtil.showShort(mContext, "发送的内容不能为空");
                        return;
                    }

                    if (calculateStatusLength(statusContent.getText().toString()) > TEXT_LIMIT) {
                        ToastUtil.showShort(mContext, "文本超出限制" + TEXT_LIMIT + "个字！请做调整");
                        return;
                    }
                    if (mSelectImgList.size() > 9) {
                        ToastUtil.showShort(mContext, "亲," + (String) AVUser.getCurrentUser().get("account") + ":最多只允许上传9张图片哦");
                        return;
                    }

                    // 开始发状态

                    switch (statusType) {
                        case POST_SERVICE_CREATE_WEIBO:
                            String message = statusContent.getText().toString();
                            showProgressDialog("正在与朋友分享中...");
                            if (mSelectImgList != null && mSelectImgList.size() > 0) {  // 有图片的情况
                                File[] imageFiles = new File[mSelectImgList.size()];
                                for (int i = 0; i < mSelectImgList.size(); ++i) {
                                    imageFiles[i] = new File(mSelectImgList.get(i).path);
                                }
                                Tiny.getInstance().source(imageFiles).batchAsFile().batchCompress(((isSuccess, outfile) -> {
                                    if (!isSuccess) {
                                        Timber.d("新的压缩工具压缩失败");
                                        // 直接上传原图
                                        return;
                                    }

                                    Timber.d("新的压缩工具压缩成功");
                                    // 成功压缩后进行上传
                                    StatusService.sendStatus(message, outfile, new SaveCallback() {
                                        @Override
                                        public void done(AVException e) {
                                            hideProgressDialog();
                                            if (StatusService.filterException(mContext, e)) {
                                                SnackbarUtil.show(btnOk, R.string.status_success);
                                                statusContent.setText("");
                                                statusContent.setHint("还可以再分享...");
                                                mSelectImgList.clear();
                                                imgListAdapter.notifyDataSetChanged();
                                            } else {
                                                SnackbarUtil.show(btnOk, R.string.status_failed);
                                            }
                                        }
                                    });
                                }));
                            } else { // 无图片的情况
                                StatusService.sendStatus(message, new SaveCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        hideProgressDialog();
                                        if (StatusService.filterException(mContext, e)) {
                                            SnackbarUtil.show(btnOk, R.string.status_success);
                                            statusContent.setText("");
                                            statusContent.setHint("还可以再分享...");
                                            mSelectImgList.clear();
                                            imgListAdapter.notifyDataSetChanged();
                                        } else {
                                            SnackbarUtil.show(btnOk, R.string.status_failed);
                                        }
                                    }
                                });
                            }
                            break;
                        case POST_SERVICE_REPOST_STATUS:
                            /*WeiBoCreateBean repostBean = new WeiBoCreateBean(statusContent.getText().toString(), mSelectImgList, mStatus);
                            intent.putExtra("postType", PostService.POST_SERVICE_REPOST_STATUS);
                            bundle.putParcelable("weiBoCreateBean", repostBean);
                            intent.putExtras(bundle);*/
                            break;
                        case POST_SERVICE_COMMENT_STATUS:
                            /*intent.putExtra("postType", PostService.POST_SERVICE_COMMENT_STATUS);
                            WeiBoCommentBean weiBoCommentBean = new WeiBoCommentBean(statusContent.getText().toString(), mStatus);
                            bundle.putParcelable("weiBoCommentBean", weiBoCommentBean);
                            intent.putExtras(bundle);*/
                            break;
                        case POST_SERVICE_REPLY_COMMENT:
                            /*intent.putExtra("postType", PostService.POST_SERVICE_REPLY_COMMENT);
                            CommentReplyBean commentReplyBean = new CommentReplyBean(statusContent.getText().toString(), mComment);
                            bundle.putParcelable("commentReplyBean", commentReplyBean);
                            intent.putExtras(bundle);*/
                            break;
                    }
                    KeyBoardUtil.closeKeybord(statusContent, mContext);
                });

        btnOk.setOnTouchListener((v, event) -> {
            if (!v.isEnabled()) {
                return false;
            }

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                btnOk.setPressed(true);
                // pressSendButton();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                changeSendButtonBg();
            }
            return false;
        });

        RxView.clicks(blankspace)
                .subscribe(aVoid -> {
                    KeyBoardUtil.openKeybord(statusContent, mContext);
                });
    }


    /**
     * 根据输入的文本数量，决定发送按钮的背景
     */
    private void changeSendButtonBg() {
        //如果有文本，或者有图片，或者是处于转发微博状态
        if (statusContent.getText().toString().length() > 0 || mSelectImgList.size() > 0 || (isRetweetWeiBoState())) {
            btnOk.setEnabled(true);
        } else {
            btnOk.setEnabled(false);
        }
    }

    private ImgListAdapter imgListAdapter = null;

    public void initImgList() {
        recyclerView.setVisibility(View.VISIBLE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1, OrientationHelper.HORIZONTAL, false);
        imgListAdapter = new ImgListAdapter(mSelectImgList, mContext);
        imgListAdapter.setOnFooterViewClickListener(this);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(imgListAdapter);
    }

    @Override
    public void OnFooterViewClick() {
        ImagePicker.getInstance().setSelectLimit(9 - mSelectImgList.size());
        Intent intent = new Intent(this, ImageGridActivity.class);
        ImagePicker.getInstance().setMultiMode(true);
        startActivityForResult(intent, IMAGE_PICKER);
    }

    /**
     * 输入监听器
     */
    private TextWatcher watcher = new TextWatcher() {
        private CharSequence inputString;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            inputString = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            changeSendButtonBg();
            setLimitTextColor(limitTextView, inputString.toString());
            btnOk.setVisibility(View.VISIBLE);
        }
    };


    /**
     * 计算微博文本的长度，统计是否超过140个字，其中中文和全角的符号算1个字符，英文字符和半角字符算半个字符
     *
     * @param c
     * @return 微博的长度，结果四舍五入
     */
    public long calculateStatusLength(CharSequence c) {
        double len = 0;
        for (int i = 0; i < c.length(); i++) {
            int temp = (int) c.charAt(i);
            if (temp > 0 && temp < 127) {
                len += 0.5;
            } else {
                len++;
            }
        }
        return Math.round(len);
    }

    public void setLimitTextColor(TextView limitTextView, String content) {
        long length = calculateStatusLength(content);
        if (length > TEXT_LIMIT) {
            long outOfNum = length - TEXT_LIMIT;
            limitTextView.setTextColor(getResources().getColor(R.color.limittext_text_outofrange));
            limitTextView.setText("-" + outOfNum + "");
        } else if (length == TEXT_LIMIT) {
            limitTextView.setText(0 + "");
            limitTextView.setTextColor(getResources().getColor(R.color.limittext_text_warning));
        } else if (TEXT_LIMIT - length <= TEXT_REMIND) {
            limitTextView.setText(TEXT_LIMIT - length + "");
            limitTextView.setTextColor(getResources().getColor(R.color.limittext_text_warning));
        } else {
            limitTextView.setText("");
        }
    }

    /**
     * 判断此页是处于转发微博还是发微博状态
     *
     * @return
     */
    public boolean isRetweetWeiBoState() {
        if (mStatus != null) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == IMAGE_PICKER) {
                List<ImageItem> tempIamgeList = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                mSelectImgList.addAll(tempIamgeList);
                if (mSelectImgList != null && !mSelectImgList.isEmpty()) {
                    if (firstInitRecycleView) {
                        btnOk.setVisibility(View.VISIBLE);
                        btnOk.setEnabled(true);
                        firstInitRecycleView = false;
                    } else {
                        btnOk.setVisibility(View.VISIBLE);
                        btnOk.setEnabled(true);
                        imgListAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }


    /**
     * 修改按钮的状态，根据内容动态变化
     *
     * @param deleteImageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void deleteImageEvent(DeleteImageEvent deleteImageEvent) {
        if (deleteImageEvent.getDeleteImage()) {
            if (!StringUtils.isBlank(statusContent.getText().toString()) || (mSelectImgList != null && !mSelectImgList.isEmpty())) {
                btnOk.setVisibility(View.VISIBLE);
                btnOk.setEnabled(true);
            } else {
                btnOk.setEnabled(false);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void postStatusEvent(PostStatusEvent postStatusEvent) {
        if (!postStatusEvent.getStatus()) {
            hideProgressDialog();
            SnackbarUtil.show(btnOk,R.string.status_failed);
        }
    }

}
