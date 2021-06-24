package org.ergoplatform.restapi.client;

import org.ergoplatform.restapi.client.ApiClient;
import org.ergoplatform.restapi.client.ApiError;

import java.io.IOException;
import java.math.BigDecimal;
import org.ergoplatform.restapi.client.BlockHeader;
import org.ergoplatform.restapi.client.BlockTransactions;
import org.ergoplatform.restapi.client.FullBlock;
import org.ergoplatform.restapi.client.MerkleProof;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * API tests for BlocksApi
 */
public class BlocksApiTest extends PeerFinder {

    private BlocksApi api;

    @Before
    public void setup() {
        api = findPeer(true).createService(BlocksApi.class);
    }


    /**
     * Get the block header info by a given signature
     *
     * 
     */
    @Test
    public void getBlockHeaderByIdTest() throws IOException {
        String headerId = blockId;
        BlockHeader response = api.getBlockHeaderById(headerId).execute().body();
        assertNotNull(response);
        assertTrue(response.getHeight() > 1);
    }

    /**
     * Get the block transactions info by a given signature
     *
     * 
     */
    @Test
    public void getBlockTransactionsByIdTest() throws IOException {
        String headerId = blockId;
        BlockTransactions response = api.getBlockTransactionsById(headerId).execute().body();
        assertNotNull(response);
        assertTrue(response.getSize() > 0);
    }

    /**
     * Get headers in a specified range
     *
     * 
     */
    @Test
    public void getChainSliceTest() throws IOException {
        Integer fromHeight = 1;
        Integer toHeight = 20;
        java.util.List<BlockHeader> response = api.getChainSlice(fromHeight, toHeight).execute().body();
        assertNotNull(response);
        assertEquals(19, response.size());
    }

    /**
     * Get the header ids at a given height
     *
     * 
     */
    @Test
    public void getFullBlockAtTest() throws IOException {
        Integer blockHeight = 1;
        java.util.List<String> response = api.getFullBlockAt(blockHeight).execute().body();
        assertNotNull(response);
        assertEquals(1, response.size());
    }

    /**
     * Get the full block info by a given signature
     *
     * 
     */
    @Test
    public void getFullBlockByIdTest() throws IOException {
        String headerId = blockId;
        FullBlock response = api.getFullBlockById(headerId).execute().body();
        assertNotNull(response);
        assertEquals(headerId, response.getHeader().getId());
    }

    /**
     * Get the Array of header ids
     *
     * 
     */
    @Test
    public void getHeaderIdsTest() throws IOException {
        Integer limit = 10;
        Integer offset = 1;
        java.util.List<String> response = api.getHeaderIds(limit, offset).execute().body();
        assertNotNull(response);
        assertEquals(10, response.size());

    }

    /**
     * Get the last headers objects
     *
     * 
     */
    @Test
    public void getLastHeadersTest() throws IOException {
        BigDecimal count = BigDecimal.valueOf(10);
        java.util.List<BlockHeader> response = api.getLastHeaders(count).execute().body();
        assertNotNull(response);
        assertEquals(10, response.size());
    }

    /**
     * Get the persistent modifier by its id
     *
     * 
     */
    @Test
    public void getModifierByIdTest() throws IOException {
        String modifierId = txId;
        Void response = api.getModifierById(modifierId).execute().body();
        assertNull(response);
    }

    /**
     * Get Merkle proof for transaction
     *
     * 
     */
    @Test
    public void getProofForTxTest() throws IOException {
        String headerId = blockId;
        String txId = txFromTheBlockId;
        MerkleProof response = api.getProofForTx(headerId, txId).execute().body();
        assertNotNull(response);
        assertTrue(response.getLevels().size() == 2);
    }

    /**
     * Send a mined block
     *
     * 
     */
    @Test(expected = IllegalArgumentException.class)
    public void sendMinedBlockTest() throws IOException {
        FullBlock body = null;
        Void response = api.sendMinedBlock(body).execute().body();
    }
}
