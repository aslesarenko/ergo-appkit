package org.ergoplatform.restapi.client;//retrofit2

import org.ergoplatform.restapi.client.CollectionFormats.*;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import org.ergoplatform.restapi.client.AddressHolder;
import org.ergoplatform.restapi.client.ApiError;
import org.ergoplatform.restapi.client.CryptoResult;
import org.ergoplatform.restapi.client.ExecuteScript;
import org.ergoplatform.restapi.client.InlineResponse2007;
import org.ergoplatform.restapi.client.InlineResponse2008;
import org.ergoplatform.restapi.client.SourceHolder;


public interface ScriptApi {
  /**
   * Convert an address to hex-encoded Sigma byte array constant which contains script bytes
   * 
   * @param address address to get a script from (required)
   * @return Call&lt;InlineResponse2008&gt;
   */
  @GET("script/addressToBytes/{address}")
  Call<InlineResponse2008> addressToBytes(
            @retrofit2.http.Path("address") String address            
  );

  /**
   * Convert an address to hex-encoded serialized ErgoTree (script)
   * 
   * @param address address to get a script from (required)
   * @return Call&lt;InlineResponse2007&gt;
   */
  @GET("script/addressToTree/{address}")
  Call<InlineResponse2007> addressToTree(
            @retrofit2.http.Path("address") String address            
  );

  /**
   * Execute script with context
   * 
   * @param body  (required)
   * @return Call&lt;CryptoResult&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("script/executeWithContext")
  Call<CryptoResult> executeWithContext(
                    @retrofit2.http.Body ExecuteScript body    
  );

  /**
   * Create P2SAddress from Sigma source
   * 
   * @param body  (required)
   * @return Call&lt;AddressHolder&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("script/p2sAddress")
  Call<AddressHolder> scriptP2SAddress(
                    @retrofit2.http.Body SourceHolder body    
  );

  /**
   * Create P2SHAddress from Sigma source
   * 
   * @param body  (required)
   * @return Call&lt;AddressHolder&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("script/p2shAddress")
  Call<AddressHolder> scriptP2SHAddress(
                    @retrofit2.http.Body SourceHolder body    
  );

}
