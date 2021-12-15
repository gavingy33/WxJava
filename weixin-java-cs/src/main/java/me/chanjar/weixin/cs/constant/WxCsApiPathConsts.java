package me.chanjar.weixin.cs.constant;

public interface WxCsApiPathConsts {
  String DEFAULT_CS_BASE_URL = "https://qyapi.weixin.qq.com";
  String GET_JSAPI_TICKET = "/cgi-bin/get_jsapi_ticket";
  String GET_AGENT_CONFIG_TICKET = "/cgi-bin/ticket/get?&type=agent_config";
  String GET_CALLBACK_IP = "/cgi-bin/getcallbackip";
  String BATCH_REPLACE_PARTY = "/cgi-bin/batch/replaceparty";
  String BATCH_REPLACE_USER = "/cgi-bin/batch/replaceuser";
  String BATCH_GET_RESULT = "/cgi-bin/batch/getresult?jobid=";
  String JSCODE_TO_SESSION = "/cgi-bin/miniprogram/jscode2session";
  String GET_TOKEN = "/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
  String WEBHOOK_SEND = "/cgi-bin/webhook/send?key=";

}
