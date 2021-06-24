package org.ergoplatform.restapi.client;

import org.ergoplatform.restapi.client.ApiClient;
import org.ergoplatform.restapi.client.ApiError;
import org.ergoplatform.restapi.client.ErgoTransactionOutput;
import org.ergoplatform.restapi.client.SerializedBox;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * API tests for UtxoApi
 */
public class UtxoApiTest extends PeerFinder {

    private UtxoApi api;

    @Before
    public void setup() {
        api = findPeer(true).createService(UtxoApi.class);
    }


    /**
     * Get genesis boxes (boxes existed before the very first block)
     *
     * 
     */
    @Test
    public void genesisBoxesTest() throws IOException {
        java.util.List<ErgoTransactionOutput> response = api.genesisBoxes().execute().body();
        assertNotNull(response);
        assertTrue(response.size() > 0);
    }

    /**
     * Get box contents for a box by a unique identifier.
     *
     * 
     */
    @Test
    public void getBoxByIdTest() throws IOException {
        ErgoTransactionOutput response = api.getBoxById(boxId).execute().body();
        assertNotNull(response);
        assertEquals(boxId, response.getBoxId());
        assertEquals(ergoTree, response.getErgoTree());
    }

    /**
     * Get serialized box from UTXO pool in Base16 encoding by an identifier.
     *
     * 
     */
    @Test
    public void getBoxByIdBinaryTest() throws IOException {
        SerializedBox response = api.getBoxByIdBinary(boxId).execute().body();
        assertNotNull(response);
        assertTrue(response.getBytes().length() > 10);
    }

    /**
     * Get box contents for a box by a unique identifier, from UTXO set and also the mempool.
     *
     * 
     */
    @Test
    public void getBoxWithPoolByIdTest() throws IOException {
        ErgoTransactionOutput response = api.getBoxWithPoolById(boxId).execute().body();
        assertNotNull(response);
        assertEquals(ergoTree, response.getErgoTree());
    }

    /**
     * Get serialized box in Base16 encoding by an identifier, considering also the mempool.
     *
     * 
     */
    @Test
    public void getBoxWithPoolByIdBinaryTest() throws IOException {
        SerializedBox response = api.getBoxWithPoolByIdBinary(boxId).execute().body();
        assertNotNull(response);
        assertTrue(response.getBytes().length() > 10);
    }
}
