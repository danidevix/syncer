package com.github.zzt93.syncer.config.pipeline.output;

import java.util.concurrent.TimeUnit;

/**
 * @author zzt
 */
public class PipelineBatch {

  /**
   * default is 100
   */
  private int size = 100;
  /**
   * delay in {@link TimeUnit#MILLISECONDS}, default is 100
   */
  private int delay = 100;

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getDelay() {
    return delay;
  }

  public void setDelay(int delay) {
    this.delay = delay;
  }
}
