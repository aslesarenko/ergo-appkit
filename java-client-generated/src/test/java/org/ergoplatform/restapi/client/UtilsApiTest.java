package org.ergoplatform.restapi.client;

import org.ergoplatform.restapi.client.ApiClient;
import org.ergoplatform.restapi.client.AddressValidity;
import org.ergoplatform.restapi.client.ApiError;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;


/**
 * API tests for UtilsApi
 */
public class UtilsApiTest extends PeerFinder {

    private UtilsApi api;

    @Before
    public void setup() {
        api = findPeer(true).createService(UtilsApi.class);
    }


    /**
     * Convert Pay-To-Public-Key Address to raw representation (hex-encoded serialized curve point)
     *
     * 
     */
    @Test
    public void addressToRawTest() throws IOException {
        String response = api.addressToRaw(address).execute().body();
        assertNotNull(response);
        assertTrue(response.length() > 0);
        assertEquals("{\n" +
            "  \"raw\" : " +
             "\"036ba5cfbc03ea2471fdf02737f64dbcd58c34461a7ec1e586dcd713dacbf89a12\"\n" +
            "}", response);
    }

    /**
     * Check address validity
     *
     * 
     */
    @Test
    public void checkAddressValidityTest() throws IOException {
        AddressValidity response = api.checkAddressValidity(address).execute().body();
        assertNotNull(response);
        assertEquals(address, response.getAddress());
        assertTrue(response.isIsValid());
    }

    /**
     * Generate Ergo address from hex-encoded ErgoTree
     *
     * 
     */
    @Test
    public void ergoTreeToAddressTest() throws IOException {
        String response = api.ergoTreeToAddress(ergoTree).execute().body();
        assertNotNull(response);
        assertTrue(response.contains(address));
    }

    /**
     * Get random seed of 32 bytes
     *
     * 
     */
    @Test
    public void getRandomSeedTest() throws IOException {
        String response = api.getRandomSeed().execute().body();
        assertNotNull(response);
        assertTrue(response.length() > 1);
    }

    /**
     * Generate random seed of specified length in bytes
     *
     * 
     */
    @Test
    public void getRandomSeedWithLengthTest() throws IOException {
        String length = "10";
        String response = api.getRandomSeedWithLength(length).execute().body();
        assertNotNull(response);
        assertTrue(response.length() > 1);
    }

    /**
     * Return Blake2b hash of specified message
     *
     * 
     */
    @Test
    public void hashBlake2bTest() throws IOException {
        String body = "\"hello\"";
        String response = api.hashBlake2b(body).execute().body();
        assertEquals("\"324dcf027dd4a30a932c441f365a25e86b173defa4b8e58948253471b81b72cf\"", response);
    }

    /**
     * Generate Pay-To-Public-Key address from hex-encoded raw pubkey (secp256k1 serialized point)
     *
     * 
     */
    @Test
    public void rawToAddressTest() throws IOException {
        String pubkeyHex = "036ba5cfbc03ea2471fdf02737f64dbcd58c34461a7ec1e586dcd713dacbf89a12";
        String response = api.rawToAddress(pubkeyHex).execute().body();

        assertEquals("{\n" +
            "  \"address\" : \"9hHDQb26AjnJUXxcqriqY1mnhpLuUeC81C4pggtK7tupr92Ea1K\"\n" +
            "}", response);
    }
}
