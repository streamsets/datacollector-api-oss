/*
 * Copyright 2018 StreamSets Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.streamsets.pipeline.api.interceptor;

import com.streamsets.pipeline.api.BlobStore;
import com.streamsets.pipeline.api.DeliveryGuarantee;
import com.streamsets.pipeline.api.ExecutionMode;
import com.streamsets.pipeline.api.StageType;

import java.util.Map;

/**
 * Interceptor creator is used to create instance of interceptor for stages where they are required.
 */
public interface InterceptorCreator {

  /**
   * Type of the interceptor represents where exactly is the interceptor going to be executed.
   */
  public enum InterceptorType {
    // Interceptor will be called directly before passing data down to stage
    PRE_STAGE,
    // Interceptor will be called directly after receiving data from the stage
    POST_STAGE,
  }

  /**
   * Context that provides runtime information and services to the creator.
   */
  public interface Context {
    /**
     * Return value for given configuration option from data collector main configuration.
     *
     * @param configName Configuration option name
     * @return String representation of the value or null if it's not defined.
     */
    public String getConfig(String configName);

    /**
     * Returns SDC singleton instance for the BLOB store
     */
    public BlobStore getBlobStore();

    /**
     * Returns type of the stage for which the create() method is called.
     */
    public StageType getStageType();

    /**
     * Return type of the interceptor (where exactly it will be called).
     */
    public InterceptorType getInterceptorType();

    /**
     * Parameters that were generated for this pipeline.
     *
     * @return Map with parameters or null if there are no parameters available (such as in preview).
     */
    public Map<String, String> getParameters();

    /**
     * Return execution mode of the pipeline.
     */
    public ExecutionMode getExecutionMode();

    /**
     * Return configured delivery guarantee of the pipeline.
     */
    public DeliveryGuarantee getDeliveryGuarantee();
  }

  /**
   * Create interceptor if needed for given stage.
   *
   * @param context Creator context
   * @return Interceptor that should be used for given stage or null if no interceptor is needed.
   */
  public Interceptor create(Context context);

}
