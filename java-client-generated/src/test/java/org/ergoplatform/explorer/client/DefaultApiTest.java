package org.ergoplatform.explorer.client;

import org.ergoplatform.ApiTestBase;
import org.ergoplatform.explorer.client.model.Balance;
import org.ergoplatform.explorer.client.model.BlockSummary;
import org.ergoplatform.explorer.client.model.BoxQuery;
import org.ergoplatform.explorer.client.model.EpochParameters;
import org.ergoplatform.explorer.client.model.ItemsA;
import org.ergoplatform.explorer.client.model.OutputInfo;
import org.ergoplatform.explorer.client.model.TokenInfo;
import org.ergoplatform.explorer.client.model.TotalBalance;
import org.ergoplatform.explorer.client.model.TransactionInfo;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * API tests for DefaultApi
 */
public class DefaultApiTest extends ApiTestBase {
    private DefaultApi api;

    @Before
    public void setup() {
        api = new ExplorerApiClient("https://api.ergoplatform.com")
            .createService(DefaultApi.class);
    }

    @Test
    public void getApiV1AddressesP1BalanceConfirmedTest() throws IOException {
        Integer minConfirmations = 1;
        Balance response = api.getApiV1AddressesP1BalanceConfirmed(address, minConfirmations).execute().body();
        assertNotNull(response);
        assertTrue(response.getNanoErgs() > 0);
    }

    @Test
    public void getApiV1AddressesP1BalanceTotalTest() throws IOException {
        TotalBalance response = api.getApiV1AddressesP1BalanceTotal(address).execute().body();
        assertTrue(response.getConfirmed().getNanoErgs() > 0);
    }

    @Test
    public void getApiV1AddressesP1TransactionsTest() throws IOException {
        Integer offset = 0;
        Integer limit = 10;
        ItemsA response = api.getApiV1AddressesP1Transactions(address, offset, limit).execute().body();
        assertTrue(response.getItems().size() > 0);
    }

    @Test
    public void getApiV1AssetsTest() throws IOException {
        Integer offset = 0;
        Integer limit = 10;
        String sortDirection = null;
        Boolean hideNfts = null;
        ItemsA response = api.getApiV1Assets(offset, limit, sortDirection, hideNfts).execute().body();
        assertTrue(response.getItems().size() > 0);
    }

    @Test
    public void getApiV1AssetsSearchBytokenidTest() throws IOException {
        Integer offset = 0;
        Integer limit = 10;
        ItemsA response = api.getApiV1AssetsSearchBytokenid(tokenId, offset, limit).execute().body();
        assertTrue(response.getItems().size() > 0);
    }

    @Test
    public void getApiV1BlocksTest() throws IOException {
        Integer offset = 0;
        Integer limit = 10;
        String sortBy = null;
        String sortDirection = null;
        ItemsA response = api.getApiV1Blocks(offset, limit, sortBy, sortDirection).execute().body();
        assertTrue(response.getItems().size() > 0);
    }

    @Test
    public void getApiV1BlocksP1Test() throws IOException {
        BlockSummary response = api.getApiV1BlocksP1(blockId).execute().body();
        assertTrue(response.getBlock().getBlockTransactions().size() > 0);
    }

    @Test
    public void getApiV1BoxesByaddressP1Test() throws IOException {
        Integer offset = 0;
        Integer limit = 10;
        ItemsA response = api.getApiV1BoxesByaddressP1(address, offset, limit).execute().body();
        assertTrue(response.getItems().size() > 0);
    }

    @Test
    public void getApiV1BoxesByergotreeP1Test() throws IOException {
        Integer offset = 0;
        Integer limit = 10;
        ItemsA response = api.getApiV1BoxesByergotreeP1(ergoTree, offset, limit).execute().body();
        assertTrue(response.getItems().size() > 0);
    }

    @Test
    public void getApiV1BoxesByergotreetemplatehashP1Test() throws IOException {
        Integer offset = 0;
        Integer limit = 10;
        // NOTE, this is actually negative test, because a hash is expected but not a tree.
        ItemsA response = api.getApiV1BoxesByergotreetemplatehashP1(ergoTree, offset, limit).execute().body();
        assertTrue(response.getItems().size() == 0);
    }

    @Test
    public void getApiV1BoxesByergotreetemplatehashP1StreamTest() {
        String p1 = null;
        Integer minHeight = null;
        Integer maxHeight = null;
        // NOTE, streamed methods are not supported by client generator
        // the generated classes are useless
//        ListOutputInfo response = api.getApiV1BoxesByergotreetemplatehashP1Stream(p1, minHeight, maxHeight);
    }

    @Test
    public void getApiV1BoxesBytokenidP1Test() throws IOException {
        Integer offset = 0;
        Integer limit = 10;
        ItemsA response = api.getApiV1BoxesBytokenidP1(tokenId, offset, limit).execute().body();
        assertTrue(response.getItems().size() > 0);
    }

    @Test
    public void getApiV1BoxesP1Test() throws IOException {
        OutputInfo response = api.getApiV1BoxesP1(boxId).execute().body();
        assertEquals(response.getBoxId(), boxId);
    }

    @Test
    public void getApiV1BoxesUnspentByaddressP1Test() throws IOException {
        Integer offset = 0;
        Integer limit = 10;
        ItemsA response = api.getApiV1BoxesUnspentByaddressP1(address, offset, limit).execute().body();
        assertTrue(response.getItems().size() > 0);
    }

    @Test
    public void getApiV1BoxesUnspentByergotreeP1Test() throws IOException {
        Integer offset = 0;
        Integer limit = 10;
        ItemsA response = api.getApiV1BoxesUnspentByergotreeP1(ergoTree, offset, limit).execute().body();
        assertTrue(response.getItems().size() > 0);
    }

    @Test
    public void getApiV1BoxesUnspentByergotreetemplatehashP1Test() throws IOException {
        Integer offset = 0;
        Integer limit = 10;
        // NOTE, this is actually negative test, because a hash is expected but not a tree.
        ItemsA response = api.getApiV1BoxesUnspentByergotreetemplatehashP1(ergoTree, offset, limit).execute().body();
        assertTrue(response.getItems().size() == 0);
    }

    @Test
    public void getApiV1BoxesUnspentByergotreetemplatehashP1StreamTest() {
        String p1 = null;
        Integer minHeight = null;
        Integer maxHeight = null;
        // NOTE, streamed methods are not supported by client generator
        // the generated classes are useless
        // ListOutputInfo response = api.getApiV1BoxesUnspentByergotreetemplatehashP1Stream(p1, minHeight, maxHeight);
    }

    @Test
    public void getApiV1BoxesUnspentBylastepochsStreamTest() {
        Integer lastEpochs = null;
        // NOTE, streamed methods are not supported by client generator
        // the generated classes are useless
        // ListOutputInfo response = api.getApiV1BoxesUnspentBylastepochsStream(lastEpochs);
    }

    @Test
    public void getApiV1BoxesUnspentBytokenidP1Test() throws IOException {
        Integer offset = 0;
        Integer limit = 10;
        ItemsA response = api.getApiV1BoxesUnspentBytokenidP1(tokenId, offset, limit).execute().body();
        assertTrue(response.getItems().size() > 0);
    }

    @Test
    public void getApiV1BoxesUnspentStreamTest() {
        Integer minHeight = null;
        Integer maxHeight = null;
        // NOTE, streamed methods are not supported by client generator
        // the generated classes are useless
        // ListOutputInfo response = api.getApiV1BoxesUnspentStream(minHeight, maxHeight);
    }

    @Test
    public void getApiV1EpochsParamsTest() throws IOException {
        EpochParameters response = api.getApiV1EpochsParams().execute().body();
        assertTrue(response.getMaxBlockCost() > 1000000);
    }

    @Test
    public void getApiV1TokensTest() throws IOException {
        Integer offset = 0;
        Integer limit = 10;
        String sortDirection = null;
        Boolean hideNfts = null;
        ItemsA response = api.getApiV1Tokens(offset, limit, sortDirection, hideNfts).execute().body();
        assertTrue(response.getItems().size() == 10);
    }

    @Test
    public void getApiV1TokensP1Test() throws IOException {
        TokenInfo response = api.getApiV1TokensP1(tokenId).execute().body();
        assertEquals(response.getId(), tokenId);
    }

    @Test
    public void getApiV1TokensSearchTest() {
        String query = null;
        Integer offset = null;
        Integer limit = null;
        //ItemsA response = api.getApiV1TokensSearch(query, offset, limit);

        // TODO: test validations
    }

    @Test
    public void getApiV1TransactionsByinputsscripttemplatehashP1Test() {
        String p1 = null;
        Integer offset = null;
        Integer limit = null;
        String sortDirection = null;
        // ItemsA response = api.getApiV1TransactionsByinputsscripttemplatehashP1(p1, offset, limit, sortDirection);

        // TODO: test validations
    }

    @Test
    public void getApiV1TransactionsP1Test() throws IOException {
        TransactionInfo response = api.getApiV1TransactionsP1(txId).execute().body();
        assertTrue(response.getOutputs().size() > 0);
    }

    @Test
    public void postApiV1BoxesSearchTest() throws IOException {
        HashMap<String, String> regs = new HashMap<>();
        regs.put("R4", "{\"serializedValue\": \"0702472963123ce32c057907c7a7268bc09f45d9ca57819d3327b9e7497d7b1cc347\"}");
        BoxQuery body = new BoxQuery()
           .registers(regs);
        Integer offset = 0;
        Integer limit = 10;
        // TODO: clarify how to use this method
//        ItemsA response = api.postApiV1BoxesSearch(body, offset, limit).execute().body();
//        assertTrue(response.getItems().size() == 10);
    }
}
