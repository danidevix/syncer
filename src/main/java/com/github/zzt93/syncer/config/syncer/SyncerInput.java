package com.github.zzt93.syncer.config.syncer;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zzt
 */
@ConfigurationProperties(prefix = "syncer.input")
public class SyncerInput {

  private int maxRetry;
  private SyncerInputMeta inputMeta;

  public SyncerInputMeta getInputMeta() {
    return inputMeta;
  }

  public void setInputMeta(SyncerInputMeta inputMeta) {
    this.inputMeta = inputMeta;
  }

  public int getMaxRetry() {
    return maxRetry;
  }

  public void setMaxRetry(int maxRetry) {
    this.maxRetry = maxRetry;
  }
}
