package com.hoangminh.service;

import java.util.List;

import com.hoangminh.dto.AddCardToWalletRequest;
import com.hoangminh.entity.Wallet;

public interface IWalletService {
	List<Wallet> getAllWallets();
	Wallet addCardToWallet(AddCardToWalletRequest addCardToWalletRequest);
}
