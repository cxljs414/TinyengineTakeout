package com.taoQlegoupeisongduanandroid.delivery.bean;

import com.google.gson.Gson;

/**
 * Created by changxing on 2016/7/26.
 */
public class HttpResult<T> {


    /**
     * resultCode : 0
     * resultMessage : 调用成功
     * data : {"id":"10","uniacid":"1","title":"杨慧锋","nickname":"灯火阑珊的","openid":"o3nuitws-cow0IwCBnALUQChYEZc","avatar":"http://wx.qlogo.cn/mmopen/6Fz3aS6k35YD7gaLqD42qJ5xND4VAqGXUFu9oL80IselYY9oRJ1EvVWfNQlHhgJAyylZ2uWqIWPRjXvTtibPIp4vNGfE4mqoC/132","mobile":"13453222285","password":"7350567eb82ec8da5757e596b394511d","salt":"cl8Vl0","sex":"男","age":"20","addtime":"1463991444","credit1":"0.00","credit2":"105.10"}
     */

    private MessageEntity<T> message;
    /**
     * message : {"resultCode":0,"resultMessage":"调用成功","data":{"id":"10","uniacid":"1","title":"杨慧锋","nickname":"灯火阑珊的","openid":"o3nuitws-cow0IwCBnALUQChYEZc","avatar":"http://wx.qlogo.cn/mmopen/6Fz3aS6k35YD7gaLqD42qJ5xND4VAqGXUFu9oL80IselYY9oRJ1EvVWfNQlHhgJAyylZ2uWqIWPRjXvTtibPIp4vNGfE4mqoC/132","mobile":"13453222285","password":"7350567eb82ec8da5757e596b394511d","salt":"cl8Vl0","sex":"男","age":"20","addtime":"1463991444","credit1":"0.00","credit2":"105.10"}}
     * redirect :
     * type : ajax
     */

    private String redirect;
    private String type;

    public static HttpResult objectFromData(String str) {

        return new Gson().fromJson(str, HttpResult.class);
    }

    public MessageEntity<T> getMessage() {
        return message;
    }

    public void setMessage(MessageEntity<T> message) {
        this.message = message;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class MessageEntity<T> {
        private int resultCode;
        private String resultMessage;

        private T data;

        public static MessageEntity objectFromData(String str) {

            return new Gson().fromJson(str, MessageEntity.class);
        }

        public int getResultCode() {
            return resultCode;
        }

        public void setResultCode(int resultCode) {
            this.resultCode = resultCode;
        }

        public String getResultMessage() {
            return resultMessage;
        }

        public void setResultMessage(String resultMessage) {
            this.resultMessage = resultMessage;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }
}
