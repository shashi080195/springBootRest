package com.example.demo.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.MyEmailService;
import com.example.demo.service.OtpService;

/**
 * @author shashi shekhar
 * Aug 14, 2018
 */
@Controller
public class OtpController {
	
	// private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public OtpService otpService;
	
	@Autowired
	public MyEmailService myEmailService;

	/**
	 * for this otp service key will be the device id ,so that if otp is sent to a 
	 * device then it will be only validated from that particular device
	 */
	@RequestMapping(value = "/generateOtp", method = RequestMethod.GET)
	public ResponseEntity generateOtp(){
		try{
		int otp = otpService.generateOTP("shashi");
		 

		String message = String.valueOf(otp);
		
		myEmailService.sendOtpMessage("shashi.shekhar225@gmail.com", "OTP -SpringBoot", message);
			return ResponseEntity.status(200).body("sucess");
		}catch(Exception ex){
			return ResponseEntity.status(400).body("fail");
		}
		
	}
	
	@RequestMapping(value ="/validateOtp", method = RequestMethod.GET)
	public @ResponseBody String validateOtp(@RequestParam("otpnum") int otpnum){	
		final String SUCCESS = "Entered Otp is valid";	
		final String FAIL = "Entered Otp is NOT valid. Please Retry!";
		String username = "shashi";
		if(otpnum >= 0){
			int serverOtp = otpService.getOtp(username);
			
			if(serverOtp > 0){
				if(otpnum == serverOtp){
					otpService.clearOTP(username);
					return ("Entered Otp is valid");
				}else{
					return SUCCESS;	
				}
			}else {
				return FAIL;			
			}
		}else {
			return FAIL;	
		}
	}
}