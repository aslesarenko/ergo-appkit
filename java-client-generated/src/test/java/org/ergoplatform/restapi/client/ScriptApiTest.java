package org.ergoplatform.restapi.client;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;


/**
 * API tests for ScriptApi
 */
public class ScriptApiTest extends PeerFinder {

    private ScriptApi api;

    @Before
    public void setup() {
        api = findPeer(true).createService(ScriptApi.class);
    }


    /**
     * Convert an address to hex-encoded Sigma byte array constant which contains script bytes
     *
     * 
     */
    @Test
    public void addressToBytesTest() throws IOException {
        InlineResponse2008 response = api.addressToBytes(address).execute().body();
        assertTrue(response.getBytes().endsWith(ergoTree));
    }

    /**
     * Convert an address to hex-encoded serialized ErgoTree (script)
     *
     * 
     */
    @Test
    public void addressToTreeTest() throws IOException {
        InlineResponse2007 response = api.addressToTree(address).execute().body();
        assertEquals(ergoTree, response.getTree());
    }

    /**
     * Execute script with context
     *
     * 
     */
    @Test
    public void executeWithContextTest() {
        ExecuteScript body = null;
        // CryptoResult response = api.executeWithContext(body);

        // TODO: test validations
    }

    /**
     * Create P2SAddress from Sigma source
     *
     * 
     */
    @Test
    public void scriptP2SAddressTest() {
        SourceHolder body = null;
        // AddressHolder response = api.scriptP2SAddress(body);

        // TODO: test validations
    }

    /**
     * Create P2SHAddress from Sigma source
     *
     * 
     */
    @Test
    public void scriptP2SHAddressTest() {
        SourceHolder body = null;
        // AddressHolder response = api.scriptP2SHAddress(body);

        // TODO: test validations
    }
}
