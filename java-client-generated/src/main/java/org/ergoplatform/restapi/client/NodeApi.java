package org.ergoplatform.restapi.client;//retrofit2

import org.ergoplatform.restapi.client.CollectionFormats.*;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import org.ergoplatform.restapi.client.ApiError;


public interface NodeApi {
  /**
   * Shuts down the node
   * 
   * @return Call&lt;Void&gt;
   */
  @POST("node/shutdown")
  Call<Void> nodeShutdown();
    

}
