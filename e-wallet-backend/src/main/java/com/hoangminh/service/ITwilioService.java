package com.hoangminh.service;

import com.hoangminh.dto.SmsRequest;

public interface ITwilioService {
	void sendSMS(SmsRequest smsRequest);
}
