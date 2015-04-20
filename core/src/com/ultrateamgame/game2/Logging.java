package com.ultrateamgame.game2;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpParametersUtils;

public class Logging {
	public static final int SMART_MOVE = 1; // Started up Logic Gate
	public static final int TRUE_FALSE = 2; // Completed the tutorial
	public static final int GETTING_STARTED = 3; // Completed Level 5
	public static final int QUITE_LOGICAL = 4; // Completed Level 5 with all gold stars
	public static final int LOGIC_CAPTAIN = 5; // Completed Level 10
	public static final int LOGIC_GENERAL = 6; // Completed Level 10 with all gold stars
	public static final int COMPUTATIONAL_WIZARD = 7; // Completed Level 15
	public static final int GOLDEN_GATE = 8; // Completed Level 15 with all gold stars
	
	public static void log(int achievement) {
		HttpRequest httpRequest = new HttpRequest(Net.HttpMethods.POST);
		httpRequest.setUrl("https://fenneclearning.com/achievement");
		httpRequest.setHeader("Content-Type", "text/plain");
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("user", getUserName());
		parameters.put("achievement", String.valueOf(achievement));
		
		httpRequest.setContent(HttpParametersUtils.convertHttpParameters(parameters));
		
		/*Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				final int statusCode = httpResponse.getStatus().getStatusCode();
				
				System.out.println("HTTP Request status: " + statusCode);
				System.out.println("Content:");
				System.out.println(httpResponse.getResultAsString());
			}
			
			@Override
			public void failed(Throwable t) {
				System.out.println("HTTP request failed!");
			}

			@Override
			public void cancelled() {
				System.out.println("HTTP request cancelled!");
			}
		});*/
	}
	
	public static void log(int level, int score) {
		HttpRequest httpRequest = new HttpRequest(Net.HttpMethods.POST);
		httpRequest.setUrl("https://fenneclearning.com/score");
		httpRequest.setHeader("Content-Type", "text/plain");
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("user", getUserName());
		parameters.put("level", String.valueOf(level));
		parameters.put("score", String.valueOf(score));
		
		httpRequest.setContent(HttpParametersUtils.convertHttpParameters(parameters));
		
		/*Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				final int statusCode = httpResponse.getStatus().getStatusCode();
				
				System.out.println("HTTP Request status: " + statusCode);
				System.out.println("Content:");
				System.out.println(httpResponse.getResultAsString());
			}
			
			@Override
			public void failed(Throwable t) {
				System.out.println("HTTP request failed!");
			}

			@Override
			public void cancelled() {
				System.out.println("HTTP request cancelled!");
			}
		});*/
	}
	
	private static String getUserName() {
		return "test";
	}
	
}
