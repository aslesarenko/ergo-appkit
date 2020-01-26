package org.ergoplatform.appkit.impl;

import org.ergoplatform.ErgoLikeTransaction;
import org.ergoplatform.appkit.*;
import org.ergoplatform.explorer.client.ExplorerApiClient;
import org.ergoplatform.explorer.client.model.TransactionOutput;
import org.ergoplatform.restapi.client.*;
import retrofit2.Retrofit;
import scorex.util.encode.Base16;
import sigmastate.Values;

import java.util.*;
import java.util.stream.Collectors;

public class BlockchainContextImpl implements BlockchainContext {

    private final ApiClient _client;
    private final Retrofit _retrofit;
    private ExplorerApiClient _explorer;
    private Retrofit _retrofitExplorer;
    private final NetworkType _networkType;
    private final NodeInfo _nodeInfo;
    private final List<BlockHeader> _headers;
    private ErgoWalletImpl _wallet;

    public BlockchainContextImpl(
            ApiClient client, Retrofit retrofit,
            ExplorerApiClient explorer, Retrofit retrofitExplorer,
            NetworkType networkType,
            NodeInfo nodeInfo, List<BlockHeader> headers) {
        _client = client;
        _retrofit = retrofit;
        _explorer = explorer;
        _retrofitExplorer = retrofitExplorer;
        _networkType = networkType;
        _nodeInfo = nodeInfo;
        _headers = headers;
    }

    @Override
    public UnsignedTransactionBuilder newTxBuilder() {
        return new UnsignedTransactionBuilderImpl(this);
    }

    @Override
    public InputBox[] getBoxesById(String... boxIds) throws ErgoClientException {
        List<InputBox> list = new ArrayList<>();
        for (String id : boxIds) {
            ErgoTransactionOutput boxData = ErgoNodeFacade.getBoxById(_retrofit, id);
            if (boxData == null) {
                throw new ErgoClientException("Cannot load UTXO box " + id, null);
            }
            list.add(new InputBoxImpl(this, boxData));
        }
        InputBox[] inputs = list.toArray(new InputBox[0]);
        return inputs;
    }

    @Override
    public ErgoProverBuilder newProverBuilder() {
        return new ErgoProverBuilderImpl(this);
    }

    @Override
    public NetworkType getNetworkType() {
        return _networkType;
    }

    @Override
    public int getHeight() { return _headers.get(0).getHeight(); }

    /*=====  Package-private methods accessible from other Impl classes. =====*/

    Retrofit getRetrofit() {
        return _retrofit;
    }

    ApiClient getApiClient() {
        return _client;
    }

    /** This method should be private. No classes of HTTP client should ever leak into interfaces. */
    private List<InputBox> getInputBoxes(List<TransactionOutput> boxes) {
        return boxes.stream().map(box -> {
            String boxId = box.getId();
            ErgoTransactionOutput boxInfo = ErgoNodeFacade.getBoxById(_retrofit, boxId);
            Optional<ErgoId> spentTxId = Optional.ofNullable(box.getSpentTransactionId()).map(ErgoId::create);
            return new InputBoxImpl(this, boxInfo, spentTxId);
        }).collect(Collectors.toList());
    }

    public NodeInfo getNodeInfo() {
        return _nodeInfo;
    }

    public List<BlockHeader> getHeaders() {
        return _headers;
    }

    @Override
    public String sendTransaction(SignedTransaction tx) {
        ErgoLikeTransaction ergoTx = ((SignedTransactionImpl)tx).getTx();
        List<ErgoTransactionDataInput> dataInputsData =
                Iso.JListToIndexedSeq(ScalaBridge.isoErgoTransactionDataInput()).from(ergoTx.dataInputs());
        List<ErgoTransactionInput> inputsData =
                Iso.JListToIndexedSeq(ScalaBridge.isoErgoTransactionInput()).from(ergoTx.inputs());
        List<ErgoTransactionOutput> outputsData =
                Iso.JListToIndexedSeq(ScalaBridge.isoErgoTransactionOutput()).from(ergoTx.outputs());
        ErgoTransaction txData = new ErgoTransaction()
                .id(ergoTx.id())
                .dataInputs(dataInputsData)
                .inputs(inputsData)
                .outputs(outputsData);
        String txId = ErgoNodeFacade.sendTransaction(_retrofit, txData);
        return txId;
    }

    @Override
    public ErgoWallet getWallet() {
        if (_wallet == null) {
            List<WalletBox> unspentBoxes = ErgoNodeFacade.getWalletUnspentBoxes(_retrofit, 0, 0);
            _wallet = new ErgoWalletImpl(unspentBoxes);
            _wallet.setContext(this);
        }
        return _wallet;
    }

    @Override
    public ErgoContract newContract(Values.ErgoTree ergoTree) {
        return new ErgoTreeContract(ergoTree);
    }

    @Override
    public ErgoContract compileContract(Constants constants, String ergoScript) {
        return ErgoScriptContract.create(constants, ergoScript, _networkType);
    }

    @Override
    public List<InputBox> getUnspentBoxesFor(Address address) {
        List<TransactionOutput> boxes = ExplorerFacade
                .transactionsBoxesByAddressUnspentIdGet(_retrofitExplorer, address.toString());
        return getInputBoxes(boxes);
    }

    @Override
    public List<InputBox> getUnspentBoxesForErgoTreeTemplate(ErgoTreeTemplate template) {
        List<TransactionOutput> boxes = ExplorerFacade
                .transactionsBoxesByErgoTreeTemplateUnspentErgoTreeTemplateGet(_retrofitExplorer,
                        template.getEncodedBytes());
        return getInputBoxes(boxes);
    }

    @Override
    public Optional<InputBox> getBoxByIdFromExplorer(ErgoId boxId) {
        Optional<TransactionOutput> res;
        try {
            res = Optional.ofNullable(ExplorerFacade
                    .transactionsBoxesIdGet(_retrofitExplorer, boxId.toString()));
        } finally {
            res = Optional.empty();
        }
        return res.flatMap(out -> getInputBoxes(Collections.singletonList(out)).stream().findFirst());
    }
}

