package com.taoQlegoupeisongduanandroid.delivery.module.init;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.taoQlegoupeisongduanandroid.delivery.R;
import com.taoQlegoupeisongduanandroid.delivery.app.App;
import com.taoQlegoupeisongduanandroid.delivery.bean.Cash;
import com.taoQlegoupeisongduanandroid.delivery.bean.ChangePsdBean;
import com.taoQlegoupeisongduanandroid.delivery.module.base.BaseActivity;
import com.taoQlegoupeisongduanandroid.delivery.utils.http.RequestSubscriber;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.SP;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.TS;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordActivity extends BaseActivity {

    @BindView(R.id.changepassword_oldpassword)EditText oldPsd;
    @BindView(R.id.changepassword_newpassword)EditText newPsd;
    @BindView(R.id.changepassword_confirmpassword)EditText comfirmPsd;

    @Override
    protected void initConentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_changepassword);
        ButterKnife.bind(this);
        App.getInstance().addHomeTop(this);
        setToolBar(R.string.title_activity_changepassword);
    }

    @OnClick({R.id.changepassword_commit})
    void onClick(View view){
        if(view.getId() == R.id.changepassword_commit){
            if(check()){
                //i="+API_UNIACID+"&c=entry&do=dyset&op=password&m=we7_wmall
                LinkedHashMap<String,String> map = new LinkedHashMap<>();
                map.put("i", App.API_UNIACID);
                map.put("c","entry");
                map.put("m","we7_wmall");
                map.put("do","dyset");
                map.put("op","password");
                map.put("token", (String) SP.get(mContext,"token",""));
                map.put("oldpassword",oldPsd.getText().toString());
                map.put("password",newPsd.getText().toString());
                toSubscribe(apiManager.getApiService().changePsd(map).map(new HttpResultFunc<ChangePsdBean>()),
                        new RequestSubscriber<ChangePsdBean>(this,this){
                            @Override
                            public void onNext(ChangePsdBean o) {
                                super.onNext(o);
                                TS.show(mContext,"密码修改成功");
                                finish();
                            }
                        }
                );
            }
        }
    }

    /**
     * 检查输入是否合法
     * @return
     */
    private boolean check() {
        if(oldPsd.getText().toString().equals("")){
            TS.show(mContext,"请输入原密码！");
            return false;
        }else if(newPsd.getText().toString().equals("")){
            TS.show(mContext,"请输入新密码！");
            return false;
        }else if(newPsd.getText().toString().length()<6){
            TS.show(mContext,"密码不能少于六位");
            return false;
        }else if(!newPsd.getText().toString().equals(comfirmPsd.getText().toString().trim())){
            TS.show(mContext,"两次输入密码不一致！");
            return false;
        }
        return true;
    }
}
