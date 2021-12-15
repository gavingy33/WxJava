package me.chanjar.weixin.cs.config;

import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.util.http.apache.ApacheHttpClientBuilder;

import java.util.concurrent.locks.Lock;

/**
 * 微信客户端配置存储.
 *
 * @author Daniel Qian
 */
public interface WxCsConfigStorage {

  /**
   * 设置企业微信服务器 baseUrl.
   * 默认值是 https://qyapi.weixin.qq.com , 如果使用默认值，则不需要调用 setBaseApiUrl
   *
   * @param baseUrl 企业微信服务器 Url
   */
  void setBaseApiUrl(String baseUrl);

  /**
   * 读取企业微信 API Url.
   * 支持私有化企业微信服务器.
   *
   * @param path the path
   * @return the api url
   */
  String getApiUrl(String path);

  /**
   * Gets access token.
   *
   * @return the access token
   */
  String getAccessToken();

  /**
   * Gets access token lock.
   *
   * @return the access token lock
   */
  Lock getAccessTokenLock();

  /**
   * Is access token expired boolean.
   *
   * @return the boolean
   */
  boolean isAccessTokenExpired();

  /**
   * 强制将access token过期掉.
   */
  void expireAccessToken();

  /**
   * Update access token.
   *
   * @param accessToken the access token
   */
  void updateAccessToken(WxAccessToken accessToken);

  /**
   * Update access token.
   *
   * @param accessToken the access token
   * @param expiresIn   the expires in
   */
  void updateAccessToken(String accessToken, int expiresIn);


  /**
   * Gets corp id.
   *
   * @return the corp id
   */
  String getCorpId();

  /**
   * Gets corp secret.
   *
   * @return the corp secret
   */
  String getCorpSecret();

  /**
   * Gets agent id.
   *
   * @return the agent id
   */
  Integer getAgentId();

  /**
   * Gets token.
   *
   * @return the token
   */
  String getToken();

  /**
   * Gets aes key.
   *
   * @return the aes key
   */
  String getAesKey();

  /**
   * Gets expires time.
   *
   * @return the expires time
   */
  long getExpiresTime();

  /**
   * Gets oauth 2 redirect uri.
   *
   * @return the oauth 2 redirect uri
   */
  String getOauth2redirectUri();

  /**
   * Gets http proxy host.
   *
   * @return the http proxy host
   */
  String getHttpProxyHost();

  /**
   * Gets http proxy port.
   *
   * @return the http proxy port
   */
  int getHttpProxyPort();

  /**
   * Gets http proxy username.
   *
   * @return the http proxy username
   */
  String getHttpProxyUsername();

  /**
   * Gets http proxy password.
   *
   * @return the http proxy password
   */
  String getHttpProxyPassword();


  /**
   * http client builder.
   *
   * @return ApacheHttpClientBuilder apache http client builder
   */
  ApacheHttpClientBuilder getApacheHttpClientBuilder();

  /**
   * 是否自动刷新token
   *
   * @return . boolean
   */
  boolean autoRefreshToken();

}
