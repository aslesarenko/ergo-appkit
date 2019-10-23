package org.ergoplatform.polyglot.ni;

import okhttp3.OkHttpClient;
import org.ergoplatform.api.client.ApiClient;
import org.ergoplatform.api.client.NodeInfo;
import org.ergoplatform.polyglot.*;
import retrofit2.Retrofit;

import java.util.Arrays;

public class ExampleScenarios {

    public SignedTransaction spendBoxes(BlockchainContext ctx, String... boxIds) throws ErgoClientException {
        UnsignedTransactionBuilder txB = ctx.newUnsignedTransaction();
        InputBox[] boxes = ctx.getBoxesById(boxIds);
        Long total = Arrays.stream(boxes).map(b -> b.getValue()).reduce(0L, (x, y) -> x + y);
        UnsignedTransaction tx = txB
                .boxesToSpend(boxes)
                .outputs(
                        txB.outBoxBuilder()
                                .value(total)
                                .contract(
                                        ConstantsBuilder.create().item("deadline", 10).build(),
                                        "{ HEIGHT > deadline }")
                                .build())
                .build();

        ErgoProverBuilder proverB = ctx.newProver();
        ErgoProver prover = proverB.withSeed("abc").build();
        SignedTransaction signed = prover.sign(tx);
        return signed;
    }

    public void request(String nodeUrl) {

        ApiClient client = new ApiClient(nodeUrl);
        OkHttpClient ok = client.getOkBuilder().build();
        Retrofit retrofit = client.getAdapterBuilder()
                .client(ok)
                .build();
        try {

            NodeInfo res = ErgoNodeFacade.getNodeInfo(retrofit);
            System.out.println(res);
        } catch (ErgoClientException e) {
            e.printStackTrace();
        }
    }

}