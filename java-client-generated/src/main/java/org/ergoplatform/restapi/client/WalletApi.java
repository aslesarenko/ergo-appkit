package org.ergoplatform.restapi.client;//retrofit2

import org.ergoplatform.restapi.client.CollectionFormats.*;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import org.ergoplatform.restapi.client.ApiError;
import org.ergoplatform.restapi.client.BalancesSnapshot;
import org.ergoplatform.restapi.client.Body;
import org.ergoplatform.restapi.client.Body1;
import org.ergoplatform.restapi.client.Body2;
import org.ergoplatform.restapi.client.Body3;
import org.ergoplatform.restapi.client.Body4;
import org.ergoplatform.restapi.client.Body5;
import org.ergoplatform.restapi.client.BoxesRequestHolder;
import org.ergoplatform.restapi.client.ErgoTransaction;
import org.ergoplatform.restapi.client.GenerateCommitmentsRequest;
import org.ergoplatform.restapi.client.HintExtractionRequest;
import org.ergoplatform.restapi.client.InlineResponse200;
import org.ergoplatform.restapi.client.InlineResponse2001;
import org.ergoplatform.restapi.client.InlineResponse2002;
import org.ergoplatform.restapi.client.InlineResponse2003;
import org.ergoplatform.restapi.client.InlineResponse2004;
import org.ergoplatform.restapi.client.PaymentRequest;
import org.ergoplatform.restapi.client.RequestsHolder;
import org.ergoplatform.restapi.client.ScanIdsBox;
import org.ergoplatform.restapi.client.TransactionHintsBag;
import org.ergoplatform.restapi.client.TransactionSigningRequest;
import org.ergoplatform.restapi.client.UnsignedErgoTransaction;
import org.ergoplatform.restapi.client.WalletBox;
import org.ergoplatform.restapi.client.WalletTransaction;


public interface WalletApi {
  /**
   * Adds a box to scans, writes box to database if it is not there. You can use scan number 10 to add a box to the wallet.
   * 
   * @param body  (required)
   * @return Call&lt;String&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("scan/addBox")
  Call<String> addBox(
                    @retrofit2.http.Body ScanIdsBox body    
  );

  /**
   * Check whether mnemonic phrase is corresponding to the wallet seed
   * 
   * @param body  (required)
   * @return Call&lt;InlineResponse2001&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("wallet/check")
  Call<InlineResponse2001> checkSeed(
                    @retrofit2.http.Body Body2 body    
  );

  /**
   * Extract hints from a transaction
   * 
   * @param body  (required)
   * @return Call&lt;TransactionHintsBag&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("wallet/extractHints")
  Call<TransactionHintsBag> extractHints(
                    @retrofit2.http.Body HintExtractionRequest body    
  );

  /**
   * Generate signature commitments for inputs of an unsigned transaction
   * 
   * @param body  (required)
   * @return Call&lt;TransactionHintsBag&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("wallet/generateCommitments")
  Call<TransactionHintsBag> generateCommitments(
                    @retrofit2.http.Body GenerateCommitmentsRequest body    
  );

  /**
   * Get wallet status
   * 
   * @return Call&lt;InlineResponse2002&gt;
   */
  @GET("wallet/status")
  Call<InlineResponse2002> getWalletStatus();
    

  /**
   * Get wallet addresses
   * 
   * @return Call&lt;java.util.List&lt;String&gt;&gt;
   */
  @GET("wallet/addresses")
  Call<java.util.List<String>> walletAddresses();
    

  /**
   * Get total amount of confirmed Ergo tokens and assets
   * 
   * @return Call&lt;BalancesSnapshot&gt;
   */
  @GET("wallet/balances")
  Call<BalancesSnapshot> walletBalances();
    

  /**
   * Get summary amount of confirmed plus unconfirmed Ergo tokens and assets
   * 
   * @return Call&lt;BalancesSnapshot&gt;
   */
  @GET("wallet/balances/withUnconfirmed")
  Call<BalancesSnapshot> walletBalancesUnconfirmed();
    

  /**
   * Get a list of all wallet-related boxes, both spent and unspent. Set minConfirmations to -1 to get mempool boxes included.
   * 
   * @param minConfirmations Minimal number of confirmations (optional, default to 0)
   * @param minInclusionHeight Minimal box inclusion height (optional, default to 0)
   * @return Call&lt;java.util.List&lt;WalletBox&gt;&gt;
   */
  @GET("wallet/boxes")
  Call<java.util.List<WalletBox>> walletBoxes(
        @retrofit2.http.Query("minConfirmations") Integer minConfirmations                ,     @retrofit2.http.Query("minInclusionHeight") Integer minInclusionHeight                
  );

  /**
   * Get a list of collected boxes.
   * 
   * @param body This API method recieves balance and assets, according to which, it&#x27;s collecting result (required)
   * @return Call&lt;java.util.List&lt;WalletBox&gt;&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("wallet/boxes/collect")
  Call<java.util.List<WalletBox>> walletBoxesCollect(
                    @retrofit2.http.Body BoxesRequestHolder body    
  );

  /**
   * Derive new key according to a provided path
   * 
   * @param body  (required)
   * @return Call&lt;InlineResponse2003&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("wallet/deriveKey")
  Call<InlineResponse2003> walletDeriveKey(
                    @retrofit2.http.Body Body5 body    
  );

  /**
   * Derive next key
   * 
   * @return Call&lt;InlineResponse2004&gt;
   */
  @GET("wallet/deriveNextKey")
  Call<InlineResponse2004> walletDeriveNextKey();
    

  /**
   * Get wallet-related transaction by id
   * 
   * @param id Transaction id (required)
   * @return Call&lt;java.util.List&lt;WalletTransaction&gt;&gt;
   */
  @GET("wallet/transactionById")
  Call<java.util.List<WalletTransaction>> walletGetTransaction(
        @retrofit2.http.Query("id") String id                
  );

  /**
   * Initialize new wallet with randomly generated seed
   * 
   * @param body  (required)
   * @return Call&lt;InlineResponse200&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("wallet/init")
  Call<InlineResponse200> walletInit(
                    @retrofit2.http.Body Body body    
  );

  /**
   * Lock wallet
   * 
   * @return Call&lt;Void&gt;
   */
  @GET("wallet/lock")
  Call<Void> walletLock();
    

  /**
   * Generate and send payment transaction (default fee of 0.001 Erg is used)
   * 
   * @param body  (required)
   * @return Call&lt;String&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("wallet/payment/send")
  Call<String> walletPaymentTransactionGenerateAndSend(
                    @retrofit2.http.Body java.util.List<PaymentRequest> body    
  );

  /**
   * Rescan wallet (all the available full blocks)
   * 
   * @return Call&lt;Void&gt;
   */
  @GET("wallet/rescan")
  Call<Void> walletRescan();
    

  /**
   * Create new wallet from existing mnemonic seed
   * 
   * @param body  (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("wallet/restore")
  Call<Void> walletRestore(
                    @retrofit2.http.Body Body1 body    
  );

  /**
   * Generate arbitrary transaction from array of requests.
   * 
   * @param body This API method receives a sequence of requests as an input. Each request will produce an output of the resulting transaction (with fee output created automatically). Currently supported types of requests are payment and asset issuance requests. An example for a transaction with requests of both kinds is provided below. Please note that for the payment request &quot;assets&quot; and &quot;registers&quot; fields are not needed. For asset issuance request, &quot;registers&quot; field is not needed.
You may specify boxes to spend by providing them in &quot;inputsRaw&quot;. Please note you need to have strict equality between input and output total amounts of Ergs in this case. If you want wallet to pick up the boxes, leave &quot;inputsRaw&quot; empty. (required)
   * @return Call&lt;ErgoTransaction&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("wallet/transaction/generate")
  Call<ErgoTransaction> walletTransactionGenerate(
                    @retrofit2.http.Body RequestsHolder body    
  );

  /**
   * Generate and send arbitrary transaction
   * 
   * @param body See description of /wallet/transaction/generate (required)
   * @return Call&lt;String&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("wallet/transaction/send")
  Call<String> walletTransactionGenerateAndSend(
                    @retrofit2.http.Body RequestsHolder body    
  );

  /**
   * Sign arbitrary unsigned transaction with wallet secrets and also secrets provided.
   * 
   * @param body With this API method an arbitrary unsigned transaction can be signed with secrets provided or stored in the wallet. Both DLOG and Diffie-Hellman tuple secrets are supported.
Please note that the unsigned transaction contains only identifiers of inputs and data inputs. If the node holds UTXO set, it is able to extract boxes needed. Otherwise, input (and data-input) boxes can be provided in &quot;inputsRaw&quot; and &quot;dataInputsRaw&quot; fields. (required)
   * @return Call&lt;ErgoTransaction&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("wallet/transaction/sign")
  Call<ErgoTransaction> walletTransactionSign(
                    @retrofit2.http.Body TransactionSigningRequest body    
  );

  /**
   * Get a list of all wallet-related transactions
   * 
   * @param minInclusionHeight Minimal tx inclusion height (optional)
   * @param maxInclusionHeight Maximal tx inclusion height (optional)
   * @param minConfirmations Minimal confirmations number (optional)
   * @param maxConfirmations Maximal confirmations number (optional)
   * @return Call&lt;java.util.List&lt;WalletTransaction&gt;&gt;
   */
  @GET("wallet/transactions")
  Call<java.util.List<WalletTransaction>> walletTransactions(
        @retrofit2.http.Query("minInclusionHeight") Integer minInclusionHeight                ,     @retrofit2.http.Query("maxInclusionHeight") Integer maxInclusionHeight                ,     @retrofit2.http.Query("minConfirmations") Integer minConfirmations                ,     @retrofit2.http.Query("maxConfirmations") Integer maxConfirmations                
  );

  /**
   * Get scan-related transactions by scan id
   * 
   * @param scanId Scan id (required)
   * @return Call&lt;java.util.List&lt;WalletTransaction&gt;&gt;
   */
  @GET("wallet/transactionsByScanId")
  Call<java.util.List<WalletTransaction>> walletTransactionsByScanId(
        @retrofit2.http.Query("scanId") String scanId                
  );

  /**
   * Unlock wallet
   * 
   * @param body  (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("wallet/unlock")
  Call<Void> walletUnlock(
                    @retrofit2.http.Body Body3 body    
  );

  /**
   * Generate unsigned transaction from array of requests.
   * 
   * @param body The same as /wallet/transaction/generate but generates unsigned transaction. (required)
   * @return Call&lt;UnsignedErgoTransaction&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("wallet/transaction/generateUnsigned")
  Call<UnsignedErgoTransaction> walletUnsignedTransactionGenerate(
                    @retrofit2.http.Body RequestsHolder body    
  );

  /**
   * Get a list of unspent boxes. Set minConfirmations to -1 to have mempool boxes considered.
   * 
   * @param minConfirmations Minimal number of confirmations (optional, default to 0)
   * @param minInclusionHeight Minimal box inclusion height (optional, default to 0)
   * @return Call&lt;java.util.List&lt;WalletBox&gt;&gt;
   */
  @GET("wallet/boxes/unspent")
  Call<java.util.List<WalletBox>> walletUnspentBoxes(
        @retrofit2.http.Query("minConfirmations") Integer minConfirmations                ,     @retrofit2.http.Query("minInclusionHeight") Integer minInclusionHeight                
  );

  /**
   * Update address to be used to send change to
   * 
   * @param body  (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("wallet/updateChangeAddress")
  Call<Void> walletUpdateChangeAddress(
                    @retrofit2.http.Body Body4 body    
  );

}
