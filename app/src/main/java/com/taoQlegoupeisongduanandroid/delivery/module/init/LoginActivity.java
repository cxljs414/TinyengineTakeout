package com.taoQlegoupeisongduanandroid.delivery.module.init;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;

import com.taoQlegoupeisongduanandroid.delivery.R;
import com.taoQlegoupeisongduanandroid.delivery.app.App;
import com.taoQlegoupeisongduanandroid.delivery.bean.User;
import com.taoQlegoupeisongduanandroid.delivery.bean.WorkStatusBean;
import com.taoQlegoupeisongduanandroid.delivery.module.base.BaseActivity;
import com.taoQlegoupeisongduanandroid.delivery.utils.http.RequestSubscriber;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.LogUtil;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.SP;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.TS;

import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class LoginActivity extends BaseActivity{

    @BindView(R.id.login_username) EditText username;
    @BindView(R.id.login_password) EditText password;
    String logs;
    @Override
    public void initConentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setToolBar(R.string.title_activity_login);
        setBackIsExit(true);
    }

    @OnClick(R.id.login_commit)
    public void login(){
        if(isRefreshing())return;
        if(check()){
            LinkedHashMap<String,String> map = new LinkedHashMap<>();
            map.put("i", App.API_UNIACID);
            map.put("c","entry");
            map.put("m","we7_wmall");
            map.put("do","dylogin");
            map.put("mobile",username.getText().toString());
            map.put("password",password.getText().toString());
            Calendar calendar = Calendar.getInstance();
            map.put("timestamp",calendar.getTimeInMillis()+"");
            map.put("nonce_str", UUID.randomUUID().toString().replace("-",""));
            toSubscribe(apiManager.getApiService().login(map).map(new HttpResultFunc<User>()),
                    new RequestSubscriber<User>(this,this){
                @Override
                public void onNext(User user) {
                    super.onNext(user);
                    LogUtil.d("user.getNickname()");
                    User.saveUsertoSp(mContext,user);
                    //记录是否是第一次打开的
                    SP.put(mContext,"HaveOpened",true);
                    //设置声音提醒
                    SP.put(mContext,"voiceNotice",true);
                    //设置震动提醒
                    SP.put(mContext,"vibrateNotice",true);
                    getAliasAndTags();

                }
            });

        }
    }

    /**
     * 检查用户名和密码
     */
    public boolean check(){
        if(username.getText().toString().equals("")){
            TS.show(mContext,"用户名不能为空！");
            return false;
        }else if(password.getText().toString().equals("")){
            TS.show(mContext,"密码不能为空！");
            return false;
        }
        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode  == KeyEvent.KEYCODE_BACK){
            app.exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void getAliasAndTags(){
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("i", App.API_UNIACID);
        map.put("c","entry");
        map.put("m","we7_wmall");
        map.put("do","dyset");
        map.put("op","relation");
        map.put("token", (String) SP.get(mContext,"token",""));
        toSubscribe(apiManager.getApiService().dyset(map).map(new HttpResultFunc<WorkStatusBean>()),
                new RequestSubscriber<WorkStatusBean>(this,this){
                    @Override
                    public void onNext(WorkStatusBean workStatusBean) {

                        Set<String> tags = new HashSet<String>();
                        for(int i=0;i<workStatusBean.getTags().size();i++){
                            tags.add(workStatusBean.getTags().get(i));
                        }
                        System.out.println(workStatusBean.getAlias()+workStatusBean.getTags().size());
                        JPushInterface.resumePush(LoginActivity.this);
                        //设置jpush别名
                        JPushInterface.setAliasAndTags(
                                mContext, workStatusBean.getAlias(),
                                tags,
                                new TagAliasCallback() {
                                    @Override
                                    public void gotResult(int code, String s, Set<String> set) {
                                        switch (code) {
                                            case 0:
                                                logs = "Set tag and alias success";
                                                System.out.println( logs);
                                                // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                                                break;
                                            case 6002:
                                                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                                                System.out.println( logs);
                                                // 延迟 60 秒来调用 Handler 设置别名
                                                //mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                                                break;
                                            default:
                                                logs = "Failed with errorCode = " + code;
                                                System.out.println( logs);
                                        }
                                    }
                                });
                        Intent intent = new Intent(mContext,HomeActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });
    }
}
