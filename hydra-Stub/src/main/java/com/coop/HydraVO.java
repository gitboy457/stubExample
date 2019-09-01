package com.coop;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
public class HydraVO {
	
	private HydraVO() {
		//Implicit private constructor avoid instantiation of class 
	}
	
	/**
	 * Get Login Response object
	 */
	@Builder @Getter @Setter @NoArgsConstructor	@AllArgsConstructor
	public static class GetLoginHydraResponse {
		private Client client;
		private List<String> requested_scope;
	}
	
	@Builder @Getter @Setter @NoArgsConstructor	@AllArgsConstructor
	public static class Client {
		@JsonProperty("client_id")
		private String channel;
	}
	
	
	/**
	 * Accept Login Challenge Request and Response objects
	 */
	@Builder @Getter @Setter @NoArgsConstructor	@AllArgsConstructor
	public static class AcceptLoginChallengeRequest {
		private String acr;
		private String force_subject_identifier;
		private boolean remember;
		private int remember_for;
		private String subject;
	}
	
	@Getter	@Builder @Setter @NoArgsConstructor @AllArgsConstructor
	public static class AcceptLoginChallengeResponse {
		private String redirect_to;
	}
}
