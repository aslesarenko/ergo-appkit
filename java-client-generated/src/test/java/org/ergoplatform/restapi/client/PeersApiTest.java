package org.ergoplatform.restapi.client;

import org.ergoplatform.restapi.client.ApiClient;
import org.ergoplatform.restapi.client.ApiError;
import org.ergoplatform.restapi.client.Peer;
import org.ergoplatform.restapi.client.PeersStatus;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;


/**
 * API tests for PeersApi
 */
public class PeersApiTest extends PeerFinder {

    private PeersApi api;

    @Before
    public void setup() {
        api = findPeer(true).createService(PeersApi.class);
    }


    /**
     * Add address to peers list
     *
     * 
     */
    @Test(expected = IllegalArgumentException.class)
    public void connectToPeerTest() throws IOException {
        String body = null;
        Void response = api.connectToPeer(body).execute().body();
    }

    /**
     * Get all known peers
     *
     * 
     */
    @Test
    public void getAllPeersTest() throws IOException {
        java.util.List<Peer> response = api.getAllPeers().execute().body();
        assertNotNull(response);
        assertTrue(response.size() > 0);
    }

    /**
     * Get blacklisted peers
     *
     * 
     */
    @Test
    public void getBlacklistedPeersTest() throws IOException {
        BlacklistedPeers response = api.getBlacklistedPeers().execute().body();
        assertNotNull(response);
        assertTrue(response.getAddresses().size() > 0);
    }

    /**
     * Get current connected peers
     *
     * 
     */
    @Test
    public void getConnectedPeersTest() throws IOException {
        java.util.List<Peer> response = api.getConnectedPeers().execute().body();
        assertNotNull(response);
        assertTrue(response.size() > 0);
    }

    /**
     * Get last incomming message timestamp and current network time
     *
     * 
     */
    @Test
    public void getPeersStatusTest() throws IOException {
        PeersStatus response = api.getPeersStatus().execute().body();
        assertNotNull(response);
        assertTrue(response.getLastIncomingMessage() > 0);
    }
}
