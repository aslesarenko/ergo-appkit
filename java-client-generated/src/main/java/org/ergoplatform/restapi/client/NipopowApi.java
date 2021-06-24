package org.ergoplatform.restapi.client;//retrofit2

import org.ergoplatform.restapi.client.CollectionFormats.*;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import org.ergoplatform.restapi.client.ApiError;
import java.math.BigDecimal;
import org.ergoplatform.restapi.client.NipopowProof;
import org.ergoplatform.restapi.client.PopowHeader;


public interface NipopowApi {
  /**
   * Construct PoPow header for best header at given height
   * 
   * @param height Height of a wanted header (required)
   * @return Call&lt;PopowHeader&gt;
   */
  @GET("nipopow/popowHeaderByHeight/{height}")
  Call<PopowHeader> getPopowHeaderByHeight(
            @retrofit2.http.Path("height") Integer height            
  );

  /**
   * Construct PoPow header according to given header id
   * 
   * @param headerId ID of wanted header (required)
   * @return Call&lt;PopowHeader&gt;
   */
  @GET("nipopow/popowHeaderById/{headerId}")
  Call<PopowHeader> getPopowHeaderById(
            @retrofit2.http.Path("headerId") String headerId            
  );

  /**
   * Construct PoPoW proof for given min superchain length and suffix length
   * 
   * @param minChainLength Minimal superchain length (required)
   * @param suffixLength Suffix length (required)
   * @return Call&lt;NipopowProof&gt;
   */
  @GET("nipopow/proof/{minChainLength}/{suffixLength}")
  Call<NipopowProof> getPopowProof(
            @retrofit2.http.Path("minChainLength") BigDecimal minChainLength            ,         @retrofit2.http.Path("suffixLength") BigDecimal suffixLength            
  );

  /**
   * Construct PoPoW proof for given min superchain length, suffix length and header ID
   * 
   * @param minChainLength Minimal superchain length (required)
   * @param suffixLength Suffix length (required)
   * @param headerId ID of wanted header (required)
   * @return Call&lt;NipopowProof&gt;
   */
  @GET("nipopow/proof/{minChainLength}/{suffixLength}/{headerId}")
  Call<NipopowProof> getPopowProofByHeaderId(
            @retrofit2.http.Path("minChainLength") BigDecimal minChainLength            ,         @retrofit2.http.Path("suffixLength") BigDecimal suffixLength            ,         @retrofit2.http.Path("headerId") String headerId            
  );

}
