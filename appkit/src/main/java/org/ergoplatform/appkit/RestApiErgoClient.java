package org.ergoplatform.appkit;

import org.ergoplatform.appkit.config.ErgoNodeConfig;
import org.ergoplatform.appkit.impl.BlockchainContextBuilderImpl;
import org.ergoplatform.explorer.client.ExplorerApiClient;
import org.ergoplatform.restapi.client.ApiClient;

import java.util.function.Function;

/**
 * This implementation of {@link ErgoClient} uses REST API of Ergo node for communication.
 */
public class RestApiErgoClient implements ErgoClient {
    private final String _nodeUrl;
    private final NetworkType _networkType;
    private final ApiClient _client;
    private final String _explorerUrl;
    private final ExplorerApiClient _explorer;

    public final static String defaultMainnetExplorerUrl = "https://api.ergoplatform.com";
    public final static String defaultTestnetExplorerUrl = "https://api-testnet.ergoplatform.com";

    /**
     * Create and initialize a new instance.
     *
     * @param nodeUrl     http url to Ergo node REST API endpoint of the form
     *                    `https://host[:port]` where port is optional.
     * @param networkType type of network (mainnet, testnet) the Ergo node is part of
     * @param apiKey      api key to authenticate this client
     * @param explorerUrl http url to Ergo Explorer REST API endpoint of the
     *                    form `https://host[:port]` where port is optional.
     */
    RestApiErgoClient(String nodeUrl, NetworkType networkType, String apiKey, String explorerUrl) {
        _nodeUrl = nodeUrl;
        _networkType = networkType;
        _client = new ApiClient(_nodeUrl, "ApiKeyAuth", apiKey);
        if (explorerUrl == null) {
            switch (networkType) {
            case MAINNET:
                _explorerUrl = defaultMainnetExplorerUrl;
                break;
            default:
                _explorerUrl = defaultTestnetExplorerUrl;
            }
        } else {
            _explorerUrl = explorerUrl;
        }
        _explorer = new ExplorerApiClient(_explorerUrl);
    }

    @Override
    public <T> T execute(Function<BlockchainContext, T> action) {
        BlockchainContext ctx = new BlockchainContextBuilderImpl(_client, _explorer, _networkType).build();
        T res = action.apply(ctx);
        return res;
    }

    /**
     * Creates a new {@link ErgoClient} instance connected to a given node of the given network type.
     *
     * @param nodeUrl     http url to Ergo node REST API endpoint of the form `https://host:port/`
     * @param networkType type of network (mainnet, testnet) the Ergo node is part of
     * @param apiKey      api key to authenticate this client
     * @return a new instance of {@link ErgoClient} connected to a given node
     */
    public static ErgoClient create(String nodeUrl, NetworkType networkType, String apiKey, String explorerUrl) {
        return new RestApiErgoClient(nodeUrl, networkType, apiKey, explorerUrl);
    }

    /**
     * Create a new {@link ErgoClient} instance using node configuration parameters.
     */
    public static ErgoClient create(ErgoNodeConfig nodeConf, String explorerUrl) {
        return RestApiErgoClient.create(
                nodeConf.getNodeApi().getApiUrl(),
                nodeConf.getNetworkType(),
                nodeConf.getNodeApi().getApiKey(),
                explorerUrl);
    }

    /**
     * Get underlying Ergo node REST API typed client.
     */
    ApiClient getNodeApiClient() {
        return _client;
    }

    /**
     * Get underlying Ergo Network Explorer REST API typed client.
     */
    ExplorerApiClient getExplorerApiClient() {
        return _explorer;
    }

}
