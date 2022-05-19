package com.hoangminh.config.twilio;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;

@Configuration
public class TwilioInitiazer {
	
	private final static Logger LOGGER = Logger.getLogger(TwilioInitiazer.class);
	
	private final TwilioConfiguration twilioConfiguration;
	
	@Autowired
	public TwilioInitiazer(TwilioConfiguration twilioConfiguration) {
		this.twilioConfiguration = twilioConfiguration;
		Twilio.init(twilioConfiguration.getAccountSid(), twilioConfiguration.getAuthToken());
		LOGGER.info("Twilio initialized ... with account id: " + twilioConfiguration.getAccountSid() );
	}
}
