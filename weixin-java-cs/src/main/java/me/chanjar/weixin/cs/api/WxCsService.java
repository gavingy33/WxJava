package me.chanjar.weixin.cs.api;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.service.WxService;
import me.chanjar.weixin.common.session.WxSession;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.common.util.http.MediaUploadRequestExecutor;
import me.chanjar.weixin.common.util.http.RequestExecutor;
import me.chanjar.weixin.common.util.http.RequestHttp;
import me.chanjar.weixin.cs.config.WxCsConfigStorage;

public interface WxCsService extends WxService {
  /**
   * <pre>
   * 验证推送过来的消息的正确性
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=验证消息真实性
   * </pre>
   *
   * @param msgSignature 消息签名
   * @param timestamp    时间戳
   * @param nonce        随机数
   * @param data         微信传输过来的数据，有可能是echoStr，有可能是xml消息
   * @return the boolean
   */
  boolean checkSignature(String msgSignature, String timestamp, String nonce, String data);

  /**
   * 获取access_token, 不强制刷新access_token
   *
   * @return the access token
   * @throws WxErrorException the wx error exception
   * @see #getAccessToken(boolean) #getAccessToken(boolean)#getAccessToken(boolean)
   */
  String getAccessToken() throws WxErrorException;

  /**
   * <pre>
   * 获取access_token，本方法线程安全
   * 且在多线程同时刷新时只刷新一次，避免超出2000次/日的调用次数上限
   * 另：本service的所有方法都会在access_token过期是调用此方法
   * 程序员在非必要情况下尽量不要主动调用此方法
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=获取access_token
   * </pre>
   *
   * @param forceRefresh 强制刷新
   * @return the access token
   * @throws WxErrorException the wx error exception
   */
  String getAccessToken(boolean forceRefresh) throws WxErrorException;

  /**
   * 当不需要自动带accessToken的时候，可以用这个发起post请求
   *
   * @param url      接口地址
   * @param postData 请求body字符串
   * @return the string
   * @throws WxErrorException the wx error exception
   */
  String postWithoutToken(String url, String postData) throws WxErrorException;

  /**
   * <pre>
   * Service没有实现某个API的时候，可以用这个，
   * 比{@link #get}和{@link #post}方法更灵活，可以自己构造RequestExecutor用来处理不同的参数和不同的返回类型。
   * 可以参考，{@link MediaUploadRequestExecutor}的实现方法
   * </pre>
   *
   * @param <T>      请求值类型
   * @param <E>      返回值类型
   * @param executor 执行器
   * @param uri      请求地址
   * @param data     参数
   * @return the t
   * @throws WxErrorException the wx error exception
   */
  <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException;

  /**
   * <pre>
   * 设置当微信系统响应系统繁忙时，要等待多少 retrySleepMillis(ms) * 2^(重试次数 - 1) 再发起重试
   * 默认：1000ms
   * </pre>
   *
   * @param retrySleepMillis 重试休息时间
   */
  void setRetrySleepMillis(int retrySleepMillis);

  /**
   * <pre>
   * 设置当微信系统响应系统繁忙时，最大重试次数
   * 默认：5次
   * </pre>
   *
   * @param maxRetryTimes 最大重试次数
   */
  void setMaxRetryTimes(int maxRetryTimes);

  /**
   * 获取某个sessionId对应的session,如果sessionId没有对应的session，则新建一个并返回。
   *
   * @param id id可以为任意字符串，建议使用FromUserName作为id
   * @return the session
   */
  WxSession getSession(String id);

  /**
   * 获取某个sessionId对应的session,如果sessionId没有对应的session，若create为true则新建一个，否则返回null。
   *
   * @param id     id可以为任意字符串，建议使用FromUserName作为id
   * @param create 是否新建
   * @return the session
   */
  WxSession getSession(String id, boolean create);

  /**
   * 获取WxSessionManager 对象
   *
   * @return WxSessionManager session manager
   */
  WxSessionManager getSessionManager();

  /**
   * <pre>
   * 设置WxSessionManager，只有当需要使用个性化的WxSessionManager的时候才需要调用此方法，
   * WxCpService默认使用的是{@link me.chanjar.weixin.common.session.StandardSessionManager}
   * </pre>
   *
   * @param sessionManager 会话管理器
   */
  void setSessionManager(WxSessionManager sessionManager);

  /**
   * 初始化http请求对象
   */
  void initHttp();

  /**
   * 获取WxCpConfigStorage 对象
   *
   * @return WxCpConfigStorage wx cp config storage
   */
  WxCsConfigStorage getWxCsConfigStorage();

  /**
   * 注入 {@link WxCsConfigStorage} 的实现
   *
   * @param wxConfigProvider 配置对象
   */
  void setWxCsConfigStorage(WxCsConfigStorage wxConfigProvider);


  /**
   * http请求对象
   *
   * @return the request http
   */
  RequestHttp<?, ?> getRequestHttp();


}
