package org.ergoplatform.restapi.client;

import org.ergoplatform.restapi.client.ApiClient;
import org.ergoplatform.restapi.client.ApiError;
import org.ergoplatform.restapi.client.NodeInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

import java.io.IOException;

/**
 * API tests for InfoApi
 */
public class InfoApiTest extends PeerFinder {

    private InfoApi api;

    @Before
    public void setup() {
        ApiClient client = findPeer(true);
        api = client.createService(InfoApi.class);
    }


    /**
     * Get the information about the Node
     *
     * 
     */
    @Test
    public void getNodeInfoTest() throws IOException {
        NodeInfo response = api.getNodeInfo().execute().body();
        assertNotNull(response);
        assertTrue(response.getFullHeight() > 500000);
    }
}
