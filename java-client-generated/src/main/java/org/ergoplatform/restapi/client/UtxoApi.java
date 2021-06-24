package org.ergoplatform.restapi.client;//retrofit2

import org.ergoplatform.restapi.client.CollectionFormats.*;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import org.ergoplatform.restapi.client.ApiError;
import org.ergoplatform.restapi.client.ErgoTransactionOutput;
import org.ergoplatform.restapi.client.SerializedBox;


public interface UtxoApi {
  /**
   * Get genesis boxes (boxes existed before the very first block)
   * 
   * @return Call&lt;java.util.List&lt;ErgoTransactionOutput&gt;&gt;
   */
  @GET("utxo/genesis")
  Call<java.util.List<ErgoTransactionOutput>> genesisBoxes();
    

  /**
   * Get box contents for a box by a unique identifier.
   * 
   * @param boxId ID of a wanted box (required)
   * @return Call&lt;ErgoTransactionOutput&gt;
   */
  @GET("utxo/byId/{boxId}")
  Call<ErgoTransactionOutput> getBoxById(
            @retrofit2.http.Path("boxId") String boxId            
  );

  /**
   * Get serialized box from UTXO pool in Base16 encoding by an identifier.
   * 
   * @param boxId ID of a wanted box (required)
   * @return Call&lt;SerializedBox&gt;
   */
  @GET("utxo/byIdBinary/{boxId}")
  Call<SerializedBox> getBoxByIdBinary(
            @retrofit2.http.Path("boxId") String boxId            
  );

  /**
   * Get box contents for a box by a unique identifier, from UTXO set and also the mempool.
   * 
   * @param boxId ID of a box to obtain (required)
   * @return Call&lt;ErgoTransactionOutput&gt;
   */
  @GET("utxo/withPool/byId/{boxId}")
  Call<ErgoTransactionOutput> getBoxWithPoolById(
            @retrofit2.http.Path("boxId") String boxId            
  );

  /**
   * Get serialized box in Base16 encoding by an identifier, considering also the mempool.
   * 
   * @param boxId ID of a wanted box (required)
   * @return Call&lt;SerializedBox&gt;
   */
  @GET("utxo/withPool/byIdBinary/{boxId}")
  Call<SerializedBox> getBoxWithPoolByIdBinary(
            @retrofit2.http.Path("boxId") String boxId            
  );

}
