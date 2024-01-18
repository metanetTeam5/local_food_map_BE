package com.metanet.amatmu.utils;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Component
public class SmsUtil {

	@Value(("${sms.api.from}"))
	private String fromNumber;

	@Autowired
	DefaultMessageService messageService;

	@Autowired
	RedisTemplate<String, String> redisTemplate;
	
	private final Long expiration = 2 * 60 * 1000L;
	
	public SingleMessageSentResponse sendAuthCode(String phoneNumber) {
        Message message = new Message();
        message.setFrom(fromNumber);
        message.setTo(phoneNumber);
        String code = "";
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
           code += Integer.toString(random.nextInt(10));
        }
        
        message.setText("[아맛무]\n인증번호 : " + code);
       
        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        
        redisTemplate.opsForValue().set(phoneNumber, code, expiration,TimeUnit.MILLISECONDS);

        return response;
    }
	
	public SingleMessageSentResponse sendPassword(String phoneNumber, String password) {
        Message message = new Message();
        message.setFrom(fromNumber);
        message.setTo(phoneNumber);
        message.setText("[아맛무]\n임시비밀번호 : " + password);
       
        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        
        return response;
    }
	
	public SingleMessageSentResponse sendReservationInfo(String phoneNumber, String resvHour, String restName) {
        Message message = new Message();
        message.setFrom(fromNumber);
        message.setTo(phoneNumber);
        message.setText("[아맛무]\n내일 " + resvHour + "에 " + restName + " 예약이 있습니다.");
       
        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        
        return response;
    }
}
