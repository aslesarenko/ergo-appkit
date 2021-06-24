package org.ergoplatform.restapi.client;

import org.ergoplatform.ApiTestBase;

public class PeerFinder extends ApiTestBase {
    public final String appVersion = "4.0.12";

    String[] knownPeers = {
        "46.4.112.10",
        "213.239.193.208",
        "51.75.52.63",
        "159.65.11.55",
        "165.227.26.175",
        "159.89.116.15",
        "83.212.114.255",
        "136.244.110.145",
        "91.199.118.161",
        "104.186.109.55",
        "209.217.206.254",
        "94.130.108.35"
    };

    ApiClient findPeer(boolean returnFirstFound) {
        ApiClient res = null;
        for (String peer : knownPeers) {
            try {
                String hostUrl = "http://" + peer + ":9053";
                System.out.println("Trying: " + hostUrl);

                ApiClient client = new ApiClient(hostUrl);
                InfoApi api = client.createService(InfoApi.class);
                NodeInfo response = api.getNodeInfo().execute().body();
//                System.out.println(response);
                if (response.getAppVersion().contains(appVersion))
                    if (returnFirstFound) return client;
                    else res = client;
            } catch (Throwable t) {
            }
        }
        return res;
    }

}
