package org.ergoplatform.restapi.client;//retrofit2

import org.ergoplatform.restapi.client.CollectionFormats.*;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import org.ergoplatform.restapi.client.ApiError;
import java.math.BigDecimal;
import org.ergoplatform.restapi.client.BlockHeader;
import org.ergoplatform.restapi.client.BlockTransactions;
import org.ergoplatform.restapi.client.FullBlock;
import org.ergoplatform.restapi.client.MerkleProof;


public interface BlocksApi {
  /**
   * Get the block header info by a given signature
   * 
   * @param headerId ID of a wanted block header (required)
   * @return Call&lt;BlockHeader&gt;
   */
  @GET("blocks/{headerId}/header")
  Call<BlockHeader> getBlockHeaderById(
            @retrofit2.http.Path("headerId") String headerId            
  );

  /**
   * Get the block transactions info by a given signature
   * 
   * @param headerId ID of a wanted block transactions (required)
   * @return Call&lt;BlockTransactions&gt;
   */
  @GET("blocks/{headerId}/transactions")
  Call<BlockTransactions> getBlockTransactionsById(
            @retrofit2.http.Path("headerId") String headerId            
  );

  /**
   * Get headers in a specified range
   * 
   * @param fromHeight Min header height (optional, default to 0)
   * @param toHeight Max header height (best header height by default) (optional, default to -1)
   * @return Call&lt;java.util.List&lt;BlockHeader&gt;&gt;
   */
  @GET("blocks/chainSlice")
  Call<java.util.List<BlockHeader>> getChainSlice(
        @retrofit2.http.Query("fromHeight") Integer fromHeight                ,     @retrofit2.http.Query("toHeight") Integer toHeight                
  );

  /**
   * Get the header ids at a given height
   * 
   * @param blockHeight Height of a wanted block (required)
   * @return Call&lt;java.util.List&lt;String&gt;&gt;
   */
  @GET("blocks/at/{blockHeight}")
  Call<java.util.List<String>> getFullBlockAt(
            @retrofit2.http.Path("blockHeight") Integer blockHeight            
  );

  /**
   * Get the full block info by a given signature
   * 
   * @param headerId ID of a wanted block (required)
   * @return Call&lt;FullBlock&gt;
   */
  @GET("blocks/{headerId}")
  Call<FullBlock> getFullBlockById(
            @retrofit2.http.Path("headerId") String headerId            
  );

  /**
   * Get the Array of header ids
   * 
   * @param limit The number of items in list to return (optional, default to 50)
   * @param offset The number of items in list to skip (optional, default to 0)
   * @return Call&lt;java.util.List&lt;String&gt;&gt;
   */
  @GET("blocks")
  Call<java.util.List<String>> getHeaderIds(
        @retrofit2.http.Query("limit") Integer limit                ,     @retrofit2.http.Query("offset") Integer offset                
  );

  /**
   * Get the last headers objects
   * 
   * @param count count of a wanted block headers (required)
   * @return Call&lt;java.util.List&lt;BlockHeader&gt;&gt;
   */
  @GET("blocks/lastHeaders/{count}")
  Call<java.util.List<BlockHeader>> getLastHeaders(
            @retrofit2.http.Path("count") BigDecimal count            
  );

  /**
   * Get the persistent modifier by its id
   * 
   * @param modifierId ID of a wanted modifier (required)
   * @return Call&lt;Void&gt;
   */
  @GET("blocks/modifier/{modifierId}")
  Call<Void> getModifierById(
            @retrofit2.http.Path("modifierId") String modifierId            
  );

  /**
   * Get Merkle proof for transaction
   * 
   * @param headerId ID of a wanted block transactions (required)
   * @param txId ID of a wanted transaction (required)
   * @return Call&lt;MerkleProof&gt;
   */
  @GET("blocks/{headerId}/proofFor/{txId}")
  Call<MerkleProof> getProofForTx(
            @retrofit2.http.Path("headerId") String headerId            ,         @retrofit2.http.Path("txId") String txId            
  );

  /**
   * Send a mined block
   * 
   * @param body  (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("blocks")
  Call<Void> sendMinedBlock(
                    @retrofit2.http.Body FullBlock body    
  );

}
