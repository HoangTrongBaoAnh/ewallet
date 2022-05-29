package com.ewallet.service;

import java.util.List;

import com.ewallet.dto.AddCardToWalletRequest;
import com.ewallet.entity.Wallet;

public interface IWalletService {
	List<Wallet> getAllWallets();
	Wallet addCardToWallet(AddCardToWalletRequest addCardToWalletRequest);
}
