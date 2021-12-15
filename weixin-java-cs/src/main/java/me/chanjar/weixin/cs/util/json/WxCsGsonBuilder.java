package me.chanjar.weixin.cs.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.util.json.WxErrorAdapter;

/**
 * @author Daniel Qian
 */
public class WxCsGsonBuilder {

  private static final GsonBuilder INSTANCE = new GsonBuilder();

  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(WxError.class, new WxErrorAdapter());
  }

  public static Gson create() {
    return INSTANCE.create();
  }

}
