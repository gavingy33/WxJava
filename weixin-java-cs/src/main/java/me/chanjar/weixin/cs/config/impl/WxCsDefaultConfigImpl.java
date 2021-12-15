package me.chanjar.weixin.cs.config.impl;

import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.util.http.apache.ApacheHttpClientBuilder;
import me.chanjar.weixin.cs.config.WxCsConfigStorage;
import me.chanjar.weixin.cs.constant.WxCsApiPathConsts;
import me.chanjar.weixin.cs.util.json.WxCsGsonBuilder;

import java.io.File;
import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WxCsDefaultConfigImpl implements WxCsConfigStorage, Serializable {
  /**
   * The Access token.
   */
  protected volatile String accessToken;
  /**
   * The Access token lock.
   */
  protected transient Lock accessTokenLock = new ReentrantLock();
  /**
   * The Agent id.
   */
  protected volatile Integer agentId;
  /**
   * The Jsapi ticket lock.
   */
  protected transient Lock jsapiTicketLock = new ReentrantLock();
  /**
   * The Agent jsapi ticket lock.
   */
  protected transient Lock agentJsapiTicketLock = new ReentrantLock();
  private volatile String corpId;
  private volatile String corpSecret;
  private volatile String token;
  private volatile String aesKey;
  private volatile long expiresTime;
  private volatile String oauth2redirectUri;
  private volatile String httpProxyHost;
  private volatile int httpProxyPort;
  private volatile String httpProxyUsername;
  private volatile String httpProxyPassword;
  private volatile String jsapiTicket;
  private volatile long jsapiTicketExpiresTime;
  private volatile String agentJsapiTicket;
  private volatile long agentJsapiTicketExpiresTime;

  private volatile File tmpDirFile;

  private transient volatile ApacheHttpClientBuilder apacheHttpClientBuilder;

  private volatile String baseApiUrl;

  private volatile String webhookKey;

  @Override
  public void setBaseApiUrl(String baseUrl) {
    this.baseApiUrl = baseUrl;
  }

  @Override
  public String getApiUrl(String path) {
    if (baseApiUrl == null) {
      baseApiUrl = WxCsApiPathConsts.DEFAULT_CS_BASE_URL;
    }
    return baseApiUrl + path;
  }

  @Override
  public String getAccessToken() {
    return this.accessToken;
  }

  /**
   * Sets access token.
   *
   * @param accessToken the access token
   */
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  @Override
  public Lock getAccessTokenLock() {
    return this.accessTokenLock;
  }

  @Override
  public boolean isAccessTokenExpired() {
    return System.currentTimeMillis() > this.expiresTime;
  }

  @Override
  public void expireAccessToken() {
    this.expiresTime = 0;
  }

  @Override
  public synchronized void updateAccessToken(WxAccessToken accessToken) {
    updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
  }

  @Override
  public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
    this.accessToken = accessToken;
    this.expiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
  }


  /**
   * Sets jsapi ticket.
   *
   * @param jsapiTicket the jsapi ticket
   */
  public void setJsapiTicket(String jsapiTicket) {
    this.jsapiTicket = jsapiTicket;
  }


  /**
   * Gets jsapi ticket expires time.
   *
   * @return the jsapi ticket expires time
   */
  public long getJsapiTicketExpiresTime() {
    return this.jsapiTicketExpiresTime;
  }

  /**
   * Sets jsapi ticket expires time.
   *
   * @param jsapiTicketExpiresTime the jsapi ticket expires time
   */
  public void setJsapiTicketExpiresTime(long jsapiTicketExpiresTime) {
    this.jsapiTicketExpiresTime = jsapiTicketExpiresTime;
  }

  @Override
  public String getCorpId() {
    return this.corpId;
  }

  /**
   * Sets corp id.
   *
   * @param corpId the corp id
   */
  public void setCorpId(String corpId) {
    this.corpId = corpId;
  }

  @Override
  public String getCorpSecret() {
    return this.corpSecret;
  }

  /**
   * Sets corp secret.
   *
   * @param corpSecret the corp secret
   */
  public void setCorpSecret(String corpSecret) {
    this.corpSecret = corpSecret;
  }

  @Override
  public String getToken() {
    return this.token;
  }

  /**
   * Sets token.
   *
   * @param token the token
   */
  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public long getExpiresTime() {
    return this.expiresTime;
  }

  /**
   * Sets expires time.
   *
   * @param expiresTime the expires time
   */
  public void setExpiresTime(long expiresTime) {
    this.expiresTime = expiresTime;
  }

  @Override
  public String getAesKey() {
    return this.aesKey;
  }

  /**
   * Sets aes key.
   *
   * @param aesKey the aes key
   */
  public void setAesKey(String aesKey) {
    this.aesKey = aesKey;
  }

  @Override
  public Integer getAgentId() {
    return this.agentId;
  }

  /**
   * Sets agent id.
   *
   * @param agentId the agent id
   */
  public void setAgentId(Integer agentId) {
    this.agentId = agentId;
  }

  @Override
  public String getOauth2redirectUri() {
    return this.oauth2redirectUri;
  }

  /**
   * Sets oauth 2 redirect uri.
   *
   * @param oauth2redirectUri the oauth 2 redirect uri
   */
  public void setOauth2redirectUri(String oauth2redirectUri) {
    this.oauth2redirectUri = oauth2redirectUri;
  }

  @Override
  public String getHttpProxyHost() {
    return this.httpProxyHost;
  }

  /**
   * Sets http proxy host.
   *
   * @param httpProxyHost the http proxy host
   */
  public void setHttpProxyHost(String httpProxyHost) {
    this.httpProxyHost = httpProxyHost;
  }

  @Override
  public int getHttpProxyPort() {
    return this.httpProxyPort;
  }

  /**
   * Sets http proxy port.
   *
   * @param httpProxyPort the http proxy port
   */
  public void setHttpProxyPort(int httpProxyPort) {
    this.httpProxyPort = httpProxyPort;
  }

  @Override
  public String getHttpProxyUsername() {
    return this.httpProxyUsername;
  }

  /**
   * Sets http proxy username.
   *
   * @param httpProxyUsername the http proxy username
   */
  public void setHttpProxyUsername(String httpProxyUsername) {
    this.httpProxyUsername = httpProxyUsername;
  }

  @Override
  public String getHttpProxyPassword() {
    return this.httpProxyPassword;
  }

  /**
   * Sets http proxy password.
   *
   * @param httpProxyPassword the http proxy password
   */
  public void setHttpProxyPassword(String httpProxyPassword) {
    this.httpProxyPassword = httpProxyPassword;
  }

  @Override
  public String toString() {
    return WxCsGsonBuilder.create().toJson(this);
  }


  @Override
  public ApacheHttpClientBuilder getApacheHttpClientBuilder() {
    return this.apacheHttpClientBuilder;
  }

  /**
   * Sets apache http client builder.
   *
   * @param apacheHttpClientBuilder the apache http client builder
   */
  public void setApacheHttpClientBuilder(ApacheHttpClientBuilder apacheHttpClientBuilder) {
    this.apacheHttpClientBuilder = apacheHttpClientBuilder;
  }

  @Override
  public boolean autoRefreshToken() {
    return true;
  }


}
