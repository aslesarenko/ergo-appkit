package org.ergoplatform.restapi.client;

import org.ergoplatform.restapi.client.ApiClient;
import org.ergoplatform.restapi.client.ApiError;
import org.ergoplatform.restapi.client.ErgoTransaction;
import org.ergoplatform.restapi.client.FeeHistogram;
import org.ergoplatform.restapi.client.Transactions;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;


/**
 * API tests for TransactionsApi
 */
public class TransactionsApiTest extends PeerFinder {

    private TransactionsApi api;

    @Before
    public void setup() {
        api = findPeer(true).createService(TransactionsApi.class);
    }


    /**
     * Checks an Ergo transaction without sending it over the network. Checks that transaction is valid and its inputs are in the UTXO set. Returns transaction identifier if the transaction is passing the checks.
     *
     * 
     */
    @Test(expected = IllegalArgumentException.class)
    public void checkTransactionTest() throws IOException {
        ErgoTransaction body = null;
        String response = api.checkTransaction(body).execute().body();
    }

    /**
     * Get expected wait time for the transaction with specified fee and size
     *
     * 
     */
    @Test
    public void getExpectedWaitTimeTest() throws IOException {
        Integer fee = 1000000;
        Integer txSize = 1000;
        Integer response = api.getExpectedWaitTime(fee, txSize).execute().body();
        assertNotNull(response);
        assertTrue(response == 0);
    }

    /**
     * Get histogram (waittime, (n_trans, sum(fee)) for transactions in mempool. It contains \&quot;bins\&quot;+1 bins, where i-th elements corresponds to transaction with wait time [i*maxtime/bins, (i+1)*maxtime/bins), and last bin corresponds to the transactions with wait time &gt;&#x3D; maxtime.
     *
     * 
     */
    @Test
    public void getFeeHistogramTest() throws IOException {
        Integer bins = 10;
        Long maxtime = 1000L;
        FeeHistogram response = api.getFeeHistogram(bins, maxtime).execute().body();
        assertNotNull(response);
        assertTrue(response.size() > 0);
    }

    /**
     * Get recommended fee (in nanoErgs) for a transaction with specified size (in bytes) to be proceeded in specified time (in minutes)
     *
     * 
     */
    @Test
    public void getRecommendedFeeTest() throws IOException {
        Integer waitTime = 10;
        Integer txSize = 1000;
        Integer response = api.getRecommendedFee(waitTime, txSize).execute().body();
        assertNotNull(response);
        assertTrue(response > 0);
    }

    /**
     * Get current pool of the unconfirmed transactions pool
     *
     * 
     */
    @Test
    public void getUnconfirmedTransactionsTest() throws IOException {
        Integer limit = 10;
        Integer offset = 1;
        Transactions response = api.getUnconfirmedTransactions(limit, offset).execute().body();
        assertNotNull(response);
        assertTrue(response.size() >= 0);
    }

    /**
     * Submit an Ergo transaction to unconfirmed pool to send it over the network
     *
     * 
     */
    @Test(expected = IllegalArgumentException.class)
    public void sendTransactionTest() throws IOException {
        ErgoTransaction body = null;
        String response = api.sendTransaction(body).execute().body();
    }
}
