package me.chanjar.weixin.cs.api.impl;

import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.enums.WxType;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.error.WxRuntimeException;
import me.chanjar.weixin.cs.config.WxCsConfigStorage;
import me.chanjar.weixin.cs.constant.WxCsApiPathConsts;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.concurrent.locks.Lock;

/**
 * <pre>
 *  默认接口实现类，使用apache httpclient实现
 * Created by Binary Wang on 2017-5-27.
 * </pre>
 * <pre>
 * 增加分布式锁（基于WxCsConfigStorage实现）的支持
 * Updated by yuanqixun on 2020-05-13
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class WxCsServiceImpl extends WxCsServiceApacheHttpClientImpl {

  @Override
  public String getAccessToken(boolean forceRefresh) throws WxErrorException {
    final WxCsConfigStorage configStorage = getWxCsConfigStorage();
    if (!configStorage.isAccessTokenExpired() && !forceRefresh) {
      return configStorage.getAccessToken();
    }
    Lock lock = configStorage.getAccessTokenLock();
    lock.lock();
    try {
      // 拿到锁之后，再次判断一下最新的token是否过期，避免重刷
      if (!configStorage.isAccessTokenExpired() && !forceRefresh) {
        return configStorage.getAccessToken();
      }
      String url = String.format(configStorage.getApiUrl(WxCsApiPathConsts.GET_TOKEN),
        this.configStorage.getCorpId(), this.configStorage.getCorpSecret());
      try {
        HttpGet httpGet = new HttpGet(url);
        if (getRequestHttpProxy() != null) {
          RequestConfig config = RequestConfig.custom().setProxy(getRequestHttpProxy()).build();
          httpGet.setConfig(config);
        }
        String resultContent;
        try (CloseableHttpClient httpClient = getRequestHttpClient();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {
          resultContent = new BasicResponseHandler().handleResponse(response);
        } finally {
          httpGet.releaseConnection();
        }
        WxError error = WxError.fromJson(resultContent, WxType.CP);
        if (error.getErrorCode() != 0) {
          throw new WxErrorException(error);
        }

        WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
        configStorage.updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
      } catch (IOException e) {
        throw new WxRuntimeException(e);
      }
    } finally {
      lock.unlock();
    }
    return configStorage.getAccessToken();
  }
}
