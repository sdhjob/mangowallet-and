package com.token.mangowallet.interact;

import android.content.Context;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.token.mangowallet.MyApplication;
import com.token.mangowallet.bean.AccountInfo;
import com.token.mangowallet.bean.AccountNames;
import com.token.mangowallet.db.MangoWallet;
import com.token.mangowallet.net.eosmgp.EOSNetWorkManager;
import com.token.mangowallet.net.eosmgp.EOSParams;
import com.token.mangowallet.repository.EMWalletRepository;
import com.token.mangowallet.repository.RepositoryFactory;
import com.token.mangowallet.utils.Constants;
import com.token.mangowallet.utils.MangoWalletUtils;
import com.token.mangowallet.utils.WalletDaoUtils;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import one.block.eosiojavarpcprovider.implementations.EosioJavaRpcProviderImpl;
import party.loveit.eosforandroidlibrary.Ecc;

import static com.token.mangowallet.utils.Constants.WalletType.ALL;
import static com.token.mangowallet.utils.Constants.WalletType.EOS;
import static com.token.mangowallet.utils.Constants.WalletType.MGP;

public class CreateWalletInteract {

    public EMWalletRepository emWalletRepository = null;

    public CreateWalletInteract() {
        emWalletRepository = new EMWalletRepository();
    }

    public Single<MangoWallet> createNotInsert(Context context, Constants.WalletType walletType, String pwd, String hint) {
        return Single.fromCallable(() -> {
            MangoWallet mangoWallet = MangoWalletUtils.newWallet(context, walletType, pwd, hint);
//            if (mangoWallet != null) {
//                if (walletType != ALL) {
//                    mangoWallet = WalletDaoUtils.insertNewWallet(mangoWallet);
//                }
//                SPUtils.getInstance(Constants.SP_WALLET).put(Constants.KEY_WALLET_ID, mangoWallet.getId());
//                SPUtils.getInstance(Constants.SP_WALLET).put(Constants.KEY_CURRENT_ACCOUNT_NAME, mangoWallet.getWalletAddress());
//                SPUtils.getInstance(Constants.SP_WALLET).put(Constants.KEY_CURRENT_WALLET_PRIVATEKEY, mangoWallet.getPrivateKey());
//            }
            return mangoWallet;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    public Single<MangoWallet> create(Context context, Constants.WalletType walletType, final String pwd, String hint) {
        return Single.fromCallable(() -> {
            MangoWallet mangoWallet = MangoWalletUtils.newWallet(context, walletType, pwd, hint);
            if (mangoWallet != null) {
//                if (walletType != ALL) {
//                    mangoWallet = WalletDaoUtils.insertNewWallet(mangoWallet);
//                }
//                SPUtils.getInstance(Constants.SP_WALLET).put(Constants.KEY_WALLET_ID, mangoWallet.getId());
//                SPUtils.getInstance(Constants.SP_WALLET).put(Constants.KEY_CURRENT_ACCOUNT_NAME, mangoWallet.getWalletAddress());
//                SPUtils.getInstance(Constants.SP_WALLET).put(Constants.KEY_CURRENT_WALLET_PRIVATEKEY, mangoWallet.getPrivateKey());
            }
            return mangoWallet;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<MangoWallet> loadWalletByMnemonic(Constants.WalletType walletType, List<String> mnemonicCode, String pwd, String hint) {
        return Single.fromCallable(() -> {
            MangoWallet mangoWallet = MangoWalletUtils.importMnemonicWallet(walletType
                    , mnemonicCode, pwd, hint);
            return mangoWallet;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


    }

    public Single<MangoWallet> loadWalletByPrivateKey(Constants.WalletType walletType, String privateKey, String pwd, String hint) {
        return Single.fromCallable(() -> {
                    MangoWallet mangoWallet = MangoWalletUtils.importPrivateKeyWallet(walletType, privateKey, pwd, hint);
                    return mangoWallet;
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<MangoWallet> loadWalletByKeystore(Constants.WalletType walletType, String keystore, String pwd, String hint) {
        return Single.fromCallable(() -> {
            MangoWallet mangoWallet = MangoWalletUtils.importKeystoreWallet(walletType, keystore, pwd, hint);
            return mangoWallet;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<String>> loadWalletAccount(String privatekey, Constants.WalletType walletType) {
        return Single.fromCallable(() -> {
            return emWalletRepository.getAccount(privatekey, walletType);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<AccountInfo> getVerifyWallet(String walletAddress, Constants.WalletType walletType) {
        return Single.fromCallable(() -> {
            return emWalletRepository.getAccountInfo(walletAddress, walletType);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
