package org.ergoplatform.restapi.client;

import org.ergoplatform.restapi.client.ApiClient;
import org.ergoplatform.restapi.client.ApiError;
import org.ergoplatform.restapi.client.ErgoTransaction;
import org.ergoplatform.restapi.client.InlineResponse2005;
import org.ergoplatform.restapi.client.InlineResponse2006;
import org.ergoplatform.restapi.client.PowSolutions;
import org.ergoplatform.restapi.client.WorkMessage;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * API tests for MiningApi
 */
public class MiningApiTest extends PeerFinder {

    private MiningApi api;

    @Before
    public void setup() {
        api = findPeer(true).createService(MiningApi.class);
    }


    /**
     * Read miner reward address
     *
     * 
     */
    @Test
    public void miningReadMinerRewardAddressTest() throws IOException {
        InlineResponse2005 response = api.miningReadMinerRewardAddress().execute().body();
        assertNull(response);
    }

    /**
     * Read public key associated with miner rewards
     *
     * 
     */
    @Test
    public void miningReadMinerRewardPubkeyTest() throws IOException {
        InlineResponse2006 response = api.miningReadMinerRewardPubkey().execute().body();
        assertNull(response);
    }

    /**
     * Request block candidate
     *
     * 
     */
    @Test
    public void miningRequestBlockCandidateTest() throws IOException {
        WorkMessage response = api.miningRequestBlockCandidate().execute().body();
        assertNull(response);
    }

    /**
     * Request block candidate
     *
     * 
     */
    @Test(expected = IllegalArgumentException.class)
    public void miningRequestBlockCandidateWithMandatoryTransactionsTest() throws IOException {
        java.util.List<ErgoTransaction> body = null;
        WorkMessage response = api.miningRequestBlockCandidateWithMandatoryTransactions(body).execute().body();
    }

    /**
     * Submit solution for current candidate
     *
     * 
     */
    @Test(expected = IllegalArgumentException.class)
    public void miningSubmitSolutionTest() throws IOException {
        PowSolutions body = null;
        Void response = api.miningSubmitSolution(body).execute().body();
    }
}
