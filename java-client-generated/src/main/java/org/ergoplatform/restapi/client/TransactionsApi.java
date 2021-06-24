package org.ergoplatform.restapi.client;//retrofit2

import org.ergoplatform.restapi.client.CollectionFormats.*;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import org.ergoplatform.restapi.client.ApiError;
import org.ergoplatform.restapi.client.ErgoTransaction;
import org.ergoplatform.restapi.client.FeeHistogram;
import org.ergoplatform.restapi.client.Transactions;


public interface TransactionsApi {
  /**
   * Checks an Ergo transaction without sending it over the network. Checks that transaction is valid and its inputs are in the UTXO set. Returns transaction identifier if the transaction is passing the checks.
   * 
   * @param body  (required)
   * @return Call&lt;String&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("transactions/check")
  Call<String> checkTransaction(
                    @retrofit2.http.Body ErgoTransaction body    
  );

  /**
   * Get expected wait time for the transaction with specified fee and size
   * 
   * @param fee Transaction fee (in nanoErgs) (required)
   * @param txSize Transaction size (required)
   * @return Call&lt;Integer&gt;
   */
  @GET("transactions/waitTime")
  Call<Integer> getExpectedWaitTime(
        @retrofit2.http.Query("fee") Integer fee                ,     @retrofit2.http.Query("txSize") Integer txSize                
  );

  /**
   * Get histogram (waittime, (n_trans, sum(fee)) for transactions in mempool. It contains \&quot;bins\&quot;+1 bins, where i-th elements corresponds to transaction with wait time [i*maxtime/bins, (i+1)*maxtime/bins), and last bin corresponds to the transactions with wait time &gt;&#x3D; maxtime.
   * 
   * @param bins The number of bins in histogram (optional, default to 10)
   * @param maxtime Maximal wait time in milliseconds (optional, default to 60000)
   * @return Call&lt;FeeHistogram&gt;
   */
  @GET("transactions/poolHistogram")
  Call<FeeHistogram> getFeeHistogram(
        @retrofit2.http.Query("bins") Integer bins                ,     @retrofit2.http.Query("maxtime") Long maxtime                
  );

  /**
   * Get recommended fee (in nanoErgs) for a transaction with specified size (in bytes) to be proceeded in specified time (in minutes)
   * 
   * @param waitTime Maximum transaction wait time in minutes (required)
   * @param txSize Transaction size (required)
   * @return Call&lt;Integer&gt;
   */
  @GET("transactions/getFee")
  Call<Integer> getRecommendedFee(
        @retrofit2.http.Query("waitTime") Integer waitTime                ,     @retrofit2.http.Query("txSize") Integer txSize                
  );

  /**
   * Get current pool of the unconfirmed transactions pool
   * 
   * @param limit The number of items in list to return (optional, default to 50)
   * @param offset The number of items in list to skip (optional, default to 0)
   * @return Call&lt;Transactions&gt;
   */
  @GET("transactions/unconfirmed")
  Call<Transactions> getUnconfirmedTransactions(
        @retrofit2.http.Query("limit") Integer limit                ,     @retrofit2.http.Query("offset") Integer offset                
  );

  /**
   * Submit an Ergo transaction to unconfirmed pool to send it over the network
   * 
   * @param body  (required)
   * @return Call&lt;String&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("transactions")
  Call<String> sendTransaction(
                    @retrofit2.http.Body ErgoTransaction body    
  );

}
