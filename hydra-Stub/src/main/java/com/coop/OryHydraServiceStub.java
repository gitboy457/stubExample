package com.coop;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.coop.HydraVO.AcceptLoginChallengeRequest;
import com.coop.HydraVO.AcceptLoginChallengeResponse;
import com.coop.HydraVO.Client;
import com.coop.HydraVO.GetLoginHydraResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@Controller
@Api(value = "OryHydraServiceStub")
public class OryHydraServiceStub {

	private static String brand="coop";
	private static String login_challenge="d7001f73f61346a79cd7821e512c1fb5";
	@Autowired
	private RestTemplate restTemplet;
	
/*	@GetMapping(value="/entry-point")
	public ModelAndView  entryPoint(HttpServletRequest request,HttpServletResponse response){
	
		
		//String url="http://18.218.42.130:9073/authngw/entry-point/?brand={brand}&login_challenge={login_challenge}";
	//String str=	restTemplet.getForObject(url,String.class,"coop","d7001f73f61346a79cd7821e512c1fb5");
		String url="http://localhost:8070/oauth/idtoken/authngw/entry-point/?brand=coop&login_challenge=d7001f73f61346a79cd7821e512c1fb5";
    request.setAttribute("brand", "coop");
    request.setAttribute("login_challenge", "d7001f73f61346a79cd7821e512c1fb5");
	
		return new ModelAndView ("redirect:"+url);
		
	}*/
	
	
	@GetMapping(value="/entry-point")
	public ModelAndView  entryPoint(HttpServletRequest request,HttpServletResponse response){
	
		
		String url="http://localhost:8070/oauth/idtoken/authngw/entry-point/?brand="+brand+"&login_challenge="+login_challenge;
   
	
		return new ModelAndView ("redirect:"+url);
		
	}
	
	@GetMapping(value = "/login/{challenge}", produces = "application/json")
	public @ResponseBody GetLoginHydraResponse getLoginInformation(@PathVariable("challenge") String challenge) {

	
		GetLoginHydraResponse getLoginHydraResponse=null;
		String jsonString=null;
		ObjectMapper mapper = new ObjectMapper();
		if (challenge.equalsIgnoreCase(login_challenge)) {
			
			
			jsonString =getStringFromFile("stub/get-login-information/getLoginInformationSuccess.json");
			try {
				getLoginHydraResponse=	mapper.readValue(jsonString, GetLoginHydraResponse.class);
			
			} catch (JsonParseException e) {
			
				e.printStackTrace();
			} catch (JsonMappingException e) {
			
				e.printStackTrace();
			} catch (IOException e) {
			
				e.printStackTrace();
			}
			return getLoginHydraResponse;
			
		} else {
			/*jsonObject = new JSONObject(
					getStringFromFile("stubs/get-login-information/getLoginInformationFailed.json"));*/
			return null;
		}
		
		
	}

	@PutMapping(value = "/login/{challenge}/accept", produces = "application/json")
	public @ResponseBody AcceptLoginChallengeResponse acceptLoginChallenge(@PathVariable("challenge") String challenge
			) {

		String jsonString=null;
		ObjectMapper mapper = new ObjectMapper();
		AcceptLoginChallengeResponse acceptLoginChallengeResponse=null;
		if (challenge.equalsIgnoreCase(login_challenge)) {
			jsonString = getStringFromFile("stub/accept-login-challenge/acceptLoginChallengeSuccess.json");
			try {
		acceptLoginChallengeResponse = mapper.readValue(jsonString, AcceptLoginChallengeResponse.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return acceptLoginChallengeResponse;
		} else {
			/*jsonObject = new JSONObject(
					getStringFromFile("stubs/accept-login-challenge/acceptLoginChallengeFailed.json"));*/
			return null;
		}
	
	}

	private String getStringFromFile(String stubPath) {
		String filedata = null;
		try {
			ClassPathResource resource = new ClassPathResource(stubPath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
			filedata = reader.lines().collect(Collectors.joining("\n"));
			reader.close();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		return filedata;
	}
	
	GetLoginHydraResponse getLoginHydraResponse() {
		
		
		GetLoginHydraResponse getLoginHydraResponse=new GetLoginHydraResponse();
		
		Client client= new Client();
		client.setChannel("Personal Online Banking");
		getLoginHydraResponse.setClient(client);
		
		List< String> scope= new ArrayList<>();
		scope.add("Login");
		scope.add("Login");
		getLoginHydraResponse.setRequested_scope(scope);
		return getLoginHydraResponse;
	}
	
	AcceptLoginChallengeResponse getAcceptLoginChallengeResponse() {
		
		AcceptLoginChallengeResponse acceptLoginChallengeResponse= new AcceptLoginChallengeResponse();
		acceptLoginChallengeResponse.setRedirect_to("https://10.109.32.61:9443/CB/p");
		
		return acceptLoginChallengeResponse;
	}
}
