package org.ergoplatform.restapi.client;//retrofit2

import org.ergoplatform.restapi.client.CollectionFormats.*;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import org.ergoplatform.restapi.client.ApiError;
import org.ergoplatform.restapi.client.BlacklistedPeers;
import org.ergoplatform.restapi.client.Peer;
import org.ergoplatform.restapi.client.PeersStatus;


public interface PeersApi {
  /**
   * Add address to peers list
   * 
   * @param body  (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("peers/connect")
  Call<Void> connectToPeer(
                    @retrofit2.http.Body String body    
  );

  /**
   * Get all known peers
   * 
   * @return Call&lt;java.util.List&lt;Peer&gt;&gt;
   */
  @GET("peers/all")
  Call<java.util.List<Peer>> getAllPeers();
    

  /**
   * Get blacklisted peers
   * 
   * @return Call&lt;BlacklistedPeers&gt;
   */
  @GET("peers/blacklisted")
  Call<BlacklistedPeers> getBlacklistedPeers();
    

  /**
   * Get current connected peers
   * 
   * @return Call&lt;java.util.List&lt;Peer&gt;&gt;
   */
  @GET("peers/connected")
  Call<java.util.List<Peer>> getConnectedPeers();
    

  /**
   * Get last incomming message timestamp and current network time
   * 
   * @return Call&lt;PeersStatus&gt;
   */
  @GET("peers/status")
  Call<PeersStatus> getPeersStatus();
    

}
