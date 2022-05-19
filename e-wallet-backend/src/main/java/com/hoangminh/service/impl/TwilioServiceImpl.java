package com.hoangminh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoangminh.config.twilio.TwilioConfiguration;
import com.hoangminh.dto.SmsRequest;
import com.hoangminh.service.ITwilioService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

@Service
public class TwilioServiceImpl implements ITwilioService {

	
	private final TwilioConfiguration twilioConfiguration;
	
	@Autowired
	public TwilioServiceImpl(TwilioConfiguration twilioConfiguration) {
		this.twilioConfiguration = twilioConfiguration;
	}
	
	@Override
	public void sendSMS(SmsRequest smsRequest) {
		// TODO Auto-generated method stub
		PhoneNumber to = new PhoneNumber(smsRequest.getPhonenumber());
		PhoneNumber from = new PhoneNumber(twilioConfiguration.getNumber());
		String message = smsRequest.getMessage();
		
		MessageCreator creator = Message.creator(to, from, message);
		creator.create();
	}

}
