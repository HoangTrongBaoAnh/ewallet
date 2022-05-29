package com.ewallet.service.impl;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ewallet.dto.Mail;
import com.ewallet.exception.BadRequestException;
import com.ewallet.service.mailservice;

@Service
public class MailServiceImpl implements mailservice{

	@Autowired
	private JavaMailSender  mailSender;
	@Override
	public void sendEmail(Mail mail) {
		// TODO Auto-generated method stub
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		 
        try {
 
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
 
            mimeMessageHelper.setSubject(mail.getMailSubject());
            InternetAddress address = new InternetAddress(mail.getMailFrom(), "E-Wallet");
            address.validate();
            mimeMessageHelper.setFrom(address);
            
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setText(mail.getMailContent());
 
            mailSender.send(mimeMessageHelper.getMimeMessage());
 
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
	}

}
