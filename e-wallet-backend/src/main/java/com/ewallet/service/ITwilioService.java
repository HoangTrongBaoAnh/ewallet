package com.ewallet.service;

import com.ewallet.dto.SmsRequest;

public interface ITwilioService {
	void sendSMS(SmsRequest smsRequest);
}
