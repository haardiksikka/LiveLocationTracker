package com.apache.kafkademo.controller;

public class Location {

	private float latitude;
	private float longitude;
	private float speed;
	private String user;
	
	
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	@Override
	public String toString() {
		return "{\"latitude\":  latitude  \", longitude\": longitude \", speed\":  speed \"}";
	}
	
	
}
