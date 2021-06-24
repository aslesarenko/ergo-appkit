package org.ergoplatform.explorer.client;//retrofit2

import org.ergoplatform.explorer.client.CollectionFormats.*;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import org.ergoplatform.explorer.client.model.BadRequest;
import org.ergoplatform.explorer.client.model.Balance;
import org.ergoplatform.explorer.client.model.BlockSummary;
import org.ergoplatform.explorer.client.model.BoxQuery;
import org.ergoplatform.explorer.client.model.EpochParameters;
import org.ergoplatform.explorer.client.model.ItemsA;
import org.ergoplatform.explorer.client.model.ListOutputInfo;
import org.ergoplatform.explorer.client.model.NotFound;
import org.ergoplatform.explorer.client.model.OutputInfo;
import org.ergoplatform.explorer.client.model.TokenInfo;
import org.ergoplatform.explorer.client.model.TotalBalance;
import org.ergoplatform.explorer.client.model.TransactionInfo;
import org.ergoplatform.explorer.client.model.UnknownErr;


public interface DefaultApi {
  /**
   * 
   * 
   * @param p1  (required)
   * @param minConfirmations  (optional)
   * @return Call&lt;Balance&gt;
   */
  @GET("api/v1/addresses/{p1}/balance/confirmed")
  Call<Balance> getApiV1AddressesP1BalanceConfirmed(
            @retrofit2.http.Path("p1") String p1            ,     @retrofit2.http.Query("minConfirmations") Integer minConfirmations                
  );

  /**
   * 
   * 
   * @param p1  (required)
   * @return Call&lt;TotalBalance&gt;
   */
  @GET("api/v1/addresses/{p1}/balance/total")
  Call<TotalBalance> getApiV1AddressesP1BalanceTotal(
            @retrofit2.http.Path("p1") String p1            
  );

  /**
   * 
   * 
   * @param p1  (required)
   * @param offset  (optional)
   * @param limit  (optional)
   * @return Call&lt;ItemsA&gt;
   */
  @GET("api/v1/addresses/{p1}/transactions")
  Call<ItemsA> getApiV1AddressesP1Transactions(
            @retrofit2.http.Path("p1") String p1            ,     @retrofit2.http.Query("offset") Integer offset                ,     @retrofit2.http.Query("limit") Integer limit                
  );

  /**
   * 
   * Use &#x27;/tokens&#x27; instead
   * @param offset  (optional)
   * @param limit  (optional)
   * @param sortDirection  (optional)
   * @param hideNfts Exclude NFTs from result set (optional, default to false)
   * @return Call&lt;ItemsA&gt;
   */
  @GET("api/v1/assets")
  Call<ItemsA> getApiV1Assets(
        @retrofit2.http.Query("offset") Integer offset                ,     @retrofit2.http.Query("limit") Integer limit                ,     @retrofit2.http.Query("sortDirection") String sortDirection                ,     @retrofit2.http.Query("hideNfts") Boolean hideNfts                
  );

  /**
   * 
   * 
   * @param query  (required)
   * @param offset  (optional)
   * @param limit  (optional)
   * @return Call&lt;ItemsA&gt;
   */
  @GET("api/v1/assets/search/byTokenId")
  Call<ItemsA> getApiV1AssetsSearchBytokenid(
        @retrofit2.http.Query("query") String query                ,     @retrofit2.http.Query("offset") Integer offset                ,     @retrofit2.http.Query("limit") Integer limit                
  );

  /**
   * 
   * 
   * @param offset  (optional)
   * @param limit  (optional)
   * @param sortBy  (optional)
   * @param sortDirection  (optional)
   * @return Call&lt;ItemsA&gt;
   */
  @GET("api/v1/blocks")
  Call<ItemsA> getApiV1Blocks(
        @retrofit2.http.Query("offset") Integer offset                ,     @retrofit2.http.Query("limit") Integer limit                ,     @retrofit2.http.Query("sortBy") String sortBy                ,     @retrofit2.http.Query("sortDirection") String sortDirection                
  );

  /**
   * 
   * 
   * @param p1  (required)
   * @return Call&lt;BlockSummary&gt;
   */
  @GET("api/v1/blocks/{p1}")
  Call<BlockSummary> getApiV1BlocksP1(
            @retrofit2.http.Path("p1") String p1            
  );

  /**
   * 
   * 
   * @param p1  (required)
   * @param offset  (optional)
   * @param limit  (optional)
   * @return Call&lt;ItemsA&gt;
   */
  @GET("api/v1/boxes/byAddress/{p1}")
  Call<ItemsA> getApiV1BoxesByaddressP1(
            @retrofit2.http.Path("p1") String p1            ,     @retrofit2.http.Query("offset") Integer offset                ,     @retrofit2.http.Query("limit") Integer limit                
  );

  /**
   * 
   * 
   * @param p1  (required)
   * @param offset  (optional)
   * @param limit  (optional)
   * @return Call&lt;ItemsA&gt;
   */
  @GET("api/v1/boxes/byErgoTree/{p1}")
  Call<ItemsA> getApiV1BoxesByergotreeP1(
            @retrofit2.http.Path("p1") String p1            ,     @retrofit2.http.Query("offset") Integer offset                ,     @retrofit2.http.Query("limit") Integer limit                
  );

  /**
   * 
   * 
   * @param p1  (required)
   * @param offset  (optional)
   * @param limit  (optional)
   * @return Call&lt;ItemsA&gt;
   */
  @GET("api/v1/boxes/byErgoTreeTemplateHash/{p1}")
  Call<ItemsA> getApiV1BoxesByergotreetemplatehashP1(
            @retrofit2.http.Path("p1") String p1            ,     @retrofit2.http.Query("offset") Integer offset                ,     @retrofit2.http.Query("limit") Integer limit                
  );

  /**
   * 
   * 
   * @param p1  (required)
   * @param minHeight  (required)
   * @param maxHeight  (required)
   * @return Call&lt;ListOutputInfo&gt;
   */
  @GET("api/v1/boxes/byErgoTreeTemplateHash/{p1}/stream")
  Call<ListOutputInfo> getApiV1BoxesByergotreetemplatehashP1Stream(
            @retrofit2.http.Path("p1") String p1            ,     @retrofit2.http.Query("minHeight") Integer minHeight                ,     @retrofit2.http.Query("maxHeight") Integer maxHeight                
  );

  /**
   * 
   * 
   * @param p1  (required)
   * @param offset  (optional)
   * @param limit  (optional)
   * @return Call&lt;ItemsA&gt;
   */
  @GET("api/v1/boxes/byTokenId/{p1}")
  Call<ItemsA> getApiV1BoxesBytokenidP1(
            @retrofit2.http.Path("p1") String p1            ,     @retrofit2.http.Query("offset") Integer offset                ,     @retrofit2.http.Query("limit") Integer limit                
  );

  /**
   * 
   * 
   * @param p1  (required)
   * @return Call&lt;OutputInfo&gt;
   */
  @GET("api/v1/boxes/{p1}")
  Call<OutputInfo> getApiV1BoxesP1(
            @retrofit2.http.Path("p1") String p1            
  );

  /**
   * 
   * 
   * @param p1  (required)
   * @param offset  (optional)
   * @param limit  (optional)
   * @return Call&lt;ItemsA&gt;
   */
  @GET("api/v1/boxes/unspent/byAddress/{p1}")
  Call<ItemsA> getApiV1BoxesUnspentByaddressP1(
            @retrofit2.http.Path("p1") String p1            ,     @retrofit2.http.Query("offset") Integer offset                ,     @retrofit2.http.Query("limit") Integer limit                
  );

  /**
   * 
   * 
   * @param p1  (required)
   * @param offset  (optional)
   * @param limit  (optional)
   * @return Call&lt;ItemsA&gt;
   */
  @GET("api/v1/boxes/unspent/byErgoTree/{p1}")
  Call<ItemsA> getApiV1BoxesUnspentByergotreeP1(
            @retrofit2.http.Path("p1") String p1            ,     @retrofit2.http.Query("offset") Integer offset                ,     @retrofit2.http.Query("limit") Integer limit                
  );

  /**
   * 
   * 
   * @param p1  (required)
   * @param offset  (optional)
   * @param limit  (optional)
   * @return Call&lt;ItemsA&gt;
   */
  @GET("api/v1/boxes/unspent/byErgoTreeTemplateHash/{p1}")
  Call<ItemsA> getApiV1BoxesUnspentByergotreetemplatehashP1(
            @retrofit2.http.Path("p1") String p1            ,     @retrofit2.http.Query("offset") Integer offset                ,     @retrofit2.http.Query("limit") Integer limit                
  );

  /**
   * 
   * 
   * @param p1  (required)
   * @param minHeight  (required)
   * @param maxHeight  (required)
   * @return Call&lt;ListOutputInfo&gt;
   */
  @GET("api/v1/boxes/unspent/byErgoTreeTemplateHash/{p1}/stream")
  Call<ListOutputInfo> getApiV1BoxesUnspentByergotreetemplatehashP1Stream(
            @retrofit2.http.Path("p1") String p1            ,     @retrofit2.http.Query("minHeight") Integer minHeight                ,     @retrofit2.http.Query("maxHeight") Integer maxHeight                
  );

  /**
   * 
   * 
   * @param lastEpochs  (required)
   * @return Call&lt;ListOutputInfo&gt;
   */
  @GET("api/v1/boxes/unspent/byLastEpochs/stream")
  Call<ListOutputInfo> getApiV1BoxesUnspentBylastepochsStream(
        @retrofit2.http.Query("lastEpochs") Integer lastEpochs                
  );

  /**
   * 
   * 
   * @param p1  (required)
   * @param offset  (optional)
   * @param limit  (optional)
   * @return Call&lt;ItemsA&gt;
   */
  @GET("api/v1/boxes/unspent/byTokenId/{p1}")
  Call<ItemsA> getApiV1BoxesUnspentBytokenidP1(
            @retrofit2.http.Path("p1") String p1            ,     @retrofit2.http.Query("offset") Integer offset                ,     @retrofit2.http.Query("limit") Integer limit                
  );

  /**
   * 
   * 
   * @param minHeight  (required)
   * @param maxHeight  (required)
   * @return Call&lt;ListOutputInfo&gt;
   */
  @GET("api/v1/boxes/unspent/stream")
  Call<ListOutputInfo> getApiV1BoxesUnspentStream(
        @retrofit2.http.Query("minHeight") Integer minHeight                ,     @retrofit2.http.Query("maxHeight") Integer maxHeight                
  );

  /**
   * 
   * 
   * @return Call&lt;EpochParameters&gt;
   */
  @GET("api/v1/epochs/params")
  Call<EpochParameters> getApiV1EpochsParams();
    

  /**
   * 
   * 
   * @param offset  (optional)
   * @param limit  (optional)
   * @param sortDirection  (optional)
   * @param hideNfts Exclude NFTs from result set (optional, default to false)
   * @return Call&lt;ItemsA&gt;
   */
  @GET("api/v1/tokens")
  Call<ItemsA> getApiV1Tokens(
        @retrofit2.http.Query("offset") Integer offset                ,     @retrofit2.http.Query("limit") Integer limit                ,     @retrofit2.http.Query("sortDirection") String sortDirection                ,     @retrofit2.http.Query("hideNfts") Boolean hideNfts                
  );

  /**
   * 
   * 
   * @param p1  (required)
   * @return Call&lt;TokenInfo&gt;
   */
  @GET("api/v1/tokens/{p1}")
  Call<TokenInfo> getApiV1TokensP1(
            @retrofit2.http.Path("p1") String p1            
  );

  /**
   * 
   * 
   * @param query  (required)
   * @param offset  (optional)
   * @param limit  (optional)
   * @return Call&lt;ItemsA&gt;
   */
  @GET("api/v1/tokens/search")
  Call<ItemsA> getApiV1TokensSearch(
        @retrofit2.http.Query("query") String query                ,     @retrofit2.http.Query("offset") Integer offset                ,     @retrofit2.http.Query("limit") Integer limit                
  );

  /**
   * 
   * 
   * @param p1  (required)
   * @param offset  (optional)
   * @param limit  (optional)
   * @param sortDirection  (optional)
   * @return Call&lt;ItemsA&gt;
   */
  @GET("api/v1/transactions/byInputsScriptTemplateHash/{p1}")
  Call<ItemsA> getApiV1TransactionsByinputsscripttemplatehashP1(
            @retrofit2.http.Path("p1") String p1            ,     @retrofit2.http.Query("offset") Integer offset                ,     @retrofit2.http.Query("limit") Integer limit                ,     @retrofit2.http.Query("sortDirection") String sortDirection                
  );

  /**
   * 
   * 
   * @param p1  (required)
   * @return Call&lt;TransactionInfo&gt;
   */
  @GET("api/v1/transactions/{p1}")
  Call<TransactionInfo> getApiV1TransactionsP1(
            @retrofit2.http.Path("p1") String p1            
  );

  /**
   * 
   * 
   * @param body  (required)
   * @param offset  (optional)
   * @param limit  (optional)
   * @return Call&lt;ItemsA&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("api/v1/boxes/search")
  Call<ItemsA> postApiV1BoxesSearch(
                    @retrofit2.http.Body BoxQuery body    ,     @retrofit2.http.Query("offset") Integer offset                ,     @retrofit2.http.Query("limit") Integer limit                
  );

}
