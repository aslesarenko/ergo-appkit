package org.ergoplatform.restapi.client;//retrofit2

import org.ergoplatform.restapi.client.CollectionFormats.*;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import org.ergoplatform.restapi.client.ApiError;
import org.ergoplatform.restapi.client.NodeInfo;


public interface InfoApi {
  /**
   * Get the information about the Node
   * 
   * @return Call&lt;NodeInfo&gt;
   */
  @GET("info")
  Call<NodeInfo> getNodeInfo();
    

}
