package com.hoangminh.service.impl;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hoangminh.converter.BankConverter;
import com.hoangminh.converter.CardConverter;
import com.hoangminh.dto.BankDto;
import com.hoangminh.dto.CardDTO;
import com.hoangminh.dto.Mail;
import com.hoangminh.dto.OCBClientRequest;
import com.hoangminh.dto.SmsRequest;
import com.hoangminh.entity.Bank;
import com.hoangminh.entity.Card;
import com.hoangminh.entity.CustomerEntity;
import com.hoangminh.exception.NotFoundException;
import com.hoangminh.service.IBankService;
import com.hoangminh.service.ICardService;
import com.hoangminh.service.ITwilioService;
import com.hoangminh.service.OTPService;
import com.hoangminh.service.mailservice;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.hoangminh.repository.BankRepository;
import com.hoangminh.repository.CardRepository;
import com.hoangminh.repository.CustomerRepository;

@Service
public class BankServiceImpl implements IBankService{
	
	@Autowired
	private BaoKimJWT baoKimJWT;
	
	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private BankConverter bankConverter;
	
	@Autowired
	private ICardService cardService;
	
	@Autowired
	private CardConverter cardConverter;
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private ITwilioService twilioService;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private mailservice mailservice;
	
	@Autowired
	private OTPService otpService;
	
	private final String apikey = "a18ff78e7a9e44f38de372e093d87ca1";
	private final String sec = "9623ac03057e433f95d86cf4f3bef5cc";
	
	private int jwtExpirationMs = 60;
	
	@Override
	public Bank creBank(BankDto bankDto) throws IOException {
		Bank bank = bankConverter.toEntity(bankDto);
		bankRepository.save(bank);
		return bank;
	}

	@Override
	public List<Bank> getBanks() {
		// TODO Auto-generated method stub
		return bankRepository.findAll();
	}

	@Override
	public Bank upBank(BankDto bankDto, long id) throws IOException {
		// TODO Auto-generated method stub
		Bank bank = bankRepository.findById(id).orElseThrow(() -> new NotFoundException("bank id not found"));

		bankRepository.save(bankConverter.DtoToEntity(bank, bankDto));
		return bank;
	}

	@Override
	public void delBank(long id) {
		bankRepository.deleteById(id);
		
	}

	@Override
	public String getBankListInVN() throws InvalidKeyException, JsonProcessingException, NoSuchAlgorithmException {
		//String jwtString = generatetoken();
        String uriString = "https://sandbox-api.baokim.vn/payment/api/v4/bank/list?jwt="+baoKimJWT.generateToken();
        RestTemplate restTemplate = new RestTemplate();
        String resString = restTemplate.getForObject(uriString, String.class);
        return resString;
	}

	@Override
	public String baoKimToken() throws InvalidKeyException, JsonProcessingException, NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		return baoKimJWT.generateToken();
	}

	@Override
	public ResponseEntity<String> topUpBaoKim() throws InvalidKeyException, JsonProcessingException, NoSuchAlgorithmException {
		String uriString = "https://sandbox-api.baokim.vn/payment/api/v4/linked-wallet/topup-wallet?jwt="+baoKimJWT.generateToken();
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<String, Object>();
        //Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", 17);
        params.put("card_id", 2);
        params.put("fee_amount", 9);
        params.put("bank_fee_amount", 4);
        
        return restTemplate.postForEntity(uriString, params, String.class);
	}
	
	//function for getting oauth2 token from OCB Sandbox API
	@Override
	public String getOCBtoken() throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
				
				RequestBody body = RequestBody.create("grant_type=password&username=jka3375&password=hpp4483&client_secret=a24d4b06cc580b1aae8395880783edc5&scope=OCB&client_id=b72c9d9566abb4508f0647a8fd9fed93",mediaType);
				Request request = new Request.Builder()
				  .url("https://sandbox.api.ocb.com.vn/corporates/developer/ocb-oauth-provider/oauth2/token")
				  .method("POST", body)
				  .addHeader("Content-Type", "application/x-www-form-urlencoded")
				  .addHeader("Accept", "application/x-www-form-urlencoded")
				  .build();
				Response response = client.newCall(request).execute();
				//assertThat(response.code(), equalTo(200));
				try {
				    JSONObject json = new JSONObject(response.body().string());
				    String name = json.getString("access_token");

				    return name;

				} catch (JSONException e) {
				    e.printStackTrace();
				}
		return response.body().string();
	}

	@Override
	public String getsignature(String signature, String cardnumber) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				Request request = new Request.Builder()
						//.url("https://developer.api.ocb.com.vn/corporate/try_it_tab/sign?data={\"trace\":{\"clientTransId\":\""+signature+"\",\"clientTimestamp\":\"20190530113020123\"}}")
						.url("https://developer.api.ocb.com.vn/corporate/try_it_tab/sign?data={\"trace\":{\"clientTransId\":\""+ signature + "\",\"clientTimestamp\":\"20190530113020123\"},\"data\":{\"accountNumbers\":[\""+cardnumber+"\"]}}")
						.build();
				Response response = client.newCall(request).execute();

		return response.body().string();
	}

	@Override
	public String getaccount(OCBClientRequest ocbClientRequest) throws IOException {
		String tokenString = getOCBtoken();
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/json");
				//RequestBody body = RequestBody.create("{\"trace\":{\"clientTran		`		sId\":\"" + ocbClientRequest.getClientid()+"\",\"clientTimestamp\":\"20190530113020123\"}}",mediaType);

				RequestBody body = RequestBody.create("{\"trace\":{\"clientTransId\":\""+ ocbClientRequest.getClientid() + "\",\"clientTimestamp\":\"20190530113020123\"},\"data\":{\"accountNumbers\":[\""+ocbClientRequest.getAccountnumber()+"\"]}}",mediaType);
				Request request = new Request.Builder()
					.url("https://sandbox.api.ocb.com.vn/corporates/developer/v1/accounts/details")
				  //.url("https://sandbox.api.ocb.com.vn/corporates/developer/v1/customerInfo")
				  .method("POST", body)
				  .addHeader("Authorization", "Bearer "+tokenString)
				  .addHeader("Content-Type", "application/json")
				  .addHeader("X-Client-Certificate", "MIIEOzCCAyOgAwIBAgIJAJTFvdpeKAEDMA0GCSqGSIb3DQEBCwUAMIGuMQswCQYDVQQGEwJWTjEPMA0GA1UECAwGSGEgTm9pMQ8wDQYDVQQHDAZIYSBOb2kxEjAQBgNVBAoMCVNlYXRlY2hpdDEnMCUGA1UECwweQ09ORyBOR0hFIFRIT05HIFRJTiBEQU5HIE5BTSBBMRkwFwYDVQQDDBBzZWF0ZWNoaXQuY29tLnZuMSUwIwYJKoZIhvcNAQkBFhZ0YW5kbkBzZWF0ZWNoaXQuY29tLnZuMB4XDTE5MDYwNTA5NDAwOVoXDTI0MDYwMzA5NDAwOVowgZQxCzAJBgNVBAYTAlZOMQwwCgYDVQQIDANIQ00xDDAKBgNVBAcMA0hDTTEMMAoGA1UECgwDT0NCMSMwIQYDVQQLDBpOR0FOIEhBTkcgQ1BUTSBQSFVPTkcgRE9ORzETMBEGA1UEAwwKb2NiLmNvbS52bjEhMB8GCSqGSIb3DQEJARYSdGFuZG5nb2NAZ21haWwuY29tMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAo//zbAPzdwZow0+x6tf/O6ilctGFXm7XZmOPJwKSXEgMc/vud8sRhztK9uBhFBmDykA9Vky0twmoLLqkSz0aU351OAyEiNttX2ykYzJWsUXn9R9qK7BkMlBX8ork+BPGNgHjdJKJ77fjL8VEXStfINx/QT3Ox+QQLAstMSfIPPHAmWFNvki64uUQlffCClbNMVXPhwZncqTBh/ZMv3HPXixgORDNwyv5n9oxd8CTb1y0ezcg3Nv7xcSwSJPfyFw0IEM1wYQAg13KyIKv4wIfH/y3V4bslgME6xXQGwy/pwTwwR7nSKsdOC5JFlorPAgjLjtqlse9ub0/Cr2bAjtRUwIDAQABo3QwcjAfBgNVHSMEGDAWgBSMiEw3M7yeSzYZ6fF4W9SamxzuWzAJBgNVHRMEAjAAMAsGA1UdDwQEAwIE8DA3BgNVHREEMDAughJhcGktc2l0Lm9jYi5jb20udm6CGGFwaS1nd2FkbS1zaXQub2NiLmNvbS52bjANBgkqhkiG9w0BAQsFAAOCAQEARgrxqLK5YN7DtWAush5RY/ebDCekxu/8O90nwnaIiFdY9ShdfAFmvtCs1X+siBDpxx3Co2bhYKuaXT42X0Wuu/fVWOEpD2+18w3Sj22n6uZTq4vCZfvdX7N1nuSY6YBHvvqu6/A1eS0IEalEqYobp2CyF4AazP5O3u7dqQaElusqmddioDvVDiJcMbNkdOuQec4NMX6/u4lQaYk4RF0JoCF1Sa22lsWOCIc/HEGaeAlaLgRCFhPA+Vk0f54tOi+yF9zRnjYZO8HL9sT6d0UETcNuh5QUvfcK4Rhhq/oHsN6rvkA8Y3MYkvxWal1WpyTPlnUdfjrepzKID6jMYIFxIA==")
				  .addHeader("X-IBM-Client-Id", "b72c9d9566abb4508f0647a8fd9fed93")
				  .addHeader("X-IBM-Client-Secret", "a24d4b06cc580b1aae8395880783edc5")
				  .addHeader("X-Signature", ocbClientRequest.getSignature().trim())
				  .addHeader("Cookie", "TS01909f7e=01170496437c4035f2f958a3252fda9699c7ea34e53508e96726535b37ff0fa5b5a84e3acf84c444e9f552f34730486554824f8731")
				  .build();
				Response response = client.newCall(request).execute();
				
				return response.body().string();
	}

	@Override
	public Card StoreOcbAccToDatabase(CardDTO cardDTO) {
		Card card = cardConverter.cardDtoToEntity(cardDTO);
		if(cardRepository.findBycardnumber(cardDTO.getCardnumber()) == null) {
			card = cardConverter.cardDtoToEntity(cardDTO);
			cardService.createCard(cardDTO);
			
			//walletService.addCardToWallet(addCardToWalletRequest)
			return card;
		}
		
		return card;
	}

	@Override
	public String sentmail(String cardnumber) {
		int otp = otpService.generateOTP(cardnumber);
		UserDetails userDetails =
				(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomerEntity customerEntity = customerRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new NotFoundException("User's email not found"));
		
		
		Mail mail = new Mail();
		
        mail.setMailFrom("hoangtrongbaoanh@gmail.com");
        //mail.setMailTo("adhkjhkhkmin");
        mail.setMailTo(customerEntity.getEmail());
        mail.setMailSubject("Your OTP");
        mail.setMailContent("Your OTP is: " + otp +" !!\n\nThanks\nwww.technicalkeeda.com");
        mailservice.sendEmail(mail);
		return "sent to " + userDetails.getUsername() + " successfully" ;
	}
	
	@Override
	public String sendSMS(String cardnumber) {
		int otp = otpService.generateOTP(cardnumber);
		UserDetails userDetails =
				(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomerEntity customerEntity = customerRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new NotFoundException("User's email not found"));
		
		SmsRequest smsRequest = new SmsRequest();
		smsRequest.setPhonenumber("+84"+customerEntity.getPhonenumber());
		smsRequest.setMessage("Your OTP is: " + otp);
		
		twilioService.sendSMS(smsRequest);
		return "sent to " + userDetails.getUsername() + " successfully" ;
	}
	
	public String generatetoken() {
		byte[] b = new byte[32];
		SecureRandom random = new SecureRandom();
		random.nextBytes(b);
		String tokenId = bytesToHex(b);
		Base64 base64 = new Base64();
		tokenId = new String(base64.encode(tokenId.getBytes()));
		
		Date issuedAt = new Date();
		
		String jwt = Jwts.builder()
				.setId(tokenId)
				.setIssuer(apikey)
				.setIssuedAt(issuedAt)
				.setNotBefore(issuedAt)
				
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS256, sec).compact();
		
		return jwt;
	}
	
	public static String bytesToHex(byte[] bytes) {
	    final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	    char[] hexChars = new char[bytes.length * 2];
	    int v;
	    for ( int j = 0; j < bytes.length; j++ ) {
	        v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}

	

}
