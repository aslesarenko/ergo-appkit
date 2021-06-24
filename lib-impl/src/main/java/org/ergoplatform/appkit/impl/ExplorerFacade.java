package org.ergoplatform.appkit.impl;

import org.ergoplatform.appkit.ErgoClientException;
import org.ergoplatform.explorer.client.DefaultApi;
import org.ergoplatform.explorer.client.model.ItemsA;
import org.ergoplatform.explorer.client.model.OutputInfo;
import retrofit2.Retrofit;
import retrofit2.RetrofitUtil;

import java.lang.reflect.Method;
import java.util.List;

/**
 * This class implements typed facade with Ergo Explorer API invocation methods.
 * It allows to bypass dynamic {@link java.lang.reflect.Proxy } generation which doesn't work under
 * Graal native-image.
 */
public class ExplorerFacade extends ApiFacade {
    /**
     * Get unspent boxes containing given address @GET("transactions/boxes/byAddress/unspent/{id}")
     *
     * @param id  (required)
     * @return TransactionOutput
     */
    static public List<OutputInfo> transactionsBoxesByAddressUnspentIdGet(
            Retrofit r, String id) throws ErgoClientException {
        return execute(r, () -> {
            Method method = DefaultApi.class.getMethod(
              "getApiV1BoxesUnspentByaddressP1", String.class, Integer.class, Integer.class);
            ItemsA res =
                    RetrofitUtil.<ItemsA>invokeServiceMethod(r, method, new Object[]{id, null, null})
                            .execute().body();
            return res.getItems();
        });
    }

}
