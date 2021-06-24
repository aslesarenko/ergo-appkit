package org.ergoplatform.restapi.client;

import org.ergoplatform.restapi.client.ApiClient;
import org.ergoplatform.restapi.client.ApiError;

import java.io.IOException;
import java.math.BigDecimal;
import org.ergoplatform.restapi.client.NipopowProof;
import org.ergoplatform.restapi.client.PopowHeader;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * API tests for NipopowApi
 */
public class NipopowApiTest extends PeerFinder {

    private NipopowApi api;

    @Before
    public void setup() {
        api = findPeer(true).createService(NipopowApi.class);
    }


    /**
     * Construct PoPow header for best header at given height
     *
     * 
     */
    @Test
    public void getPopowHeaderByHeightTest() throws IOException {
        Integer height = 500000;
        PopowHeader response = api.getPopowHeaderByHeight(height).execute().body();
        assertNotNull(response);
        assertTrue(response.getInterlinks().size() > 1);
    }

    /**
     * Construct PoPow header according to given header id
     *
     * 
     */
    @Test
    public void getPopowHeaderByIdTest() throws IOException {
        String headerId = blockId;
        PopowHeader response = api.getPopowHeaderById(headerId).execute().body();
        assertNotNull(response);
        assertTrue(response.getInterlinks().size() > 1);
    }

    /**
     * Construct PoPoW proof for given min superchain length and suffix length
     *
     * 
     */
    @Test
    public void getPopowProofTest() throws IOException {
        BigDecimal minChainLength = BigDecimal.valueOf(1);
        BigDecimal suffixLength = BigDecimal.valueOf(1);
        NipopowProof response = api.getPopowProof(minChainLength, suffixLength).execute().body();
        assertNotNull(response);
        assertTrue(response.getPrefix().size() > 1);
    }

    /**
     * Construct PoPoW proof for given min superchain length, suffix length and header ID
     *
     * 
     */
    @Test
    public void getPopowProofByHeaderIdTest() throws IOException {
        BigDecimal minChainLength = BigDecimal.valueOf(1);
        BigDecimal suffixLength = BigDecimal.valueOf(1);
        String headerId = blockId;
        NipopowProof response = api.getPopowProofByHeaderId(minChainLength, suffixLength, headerId).execute().body();
        assertNotNull(response);
        assertTrue(response.getPrefix().size() > 1);
    }
}
