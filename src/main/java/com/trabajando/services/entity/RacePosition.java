package com.trabajando.services.entity;

import java.io.Serializable;

public class RacePosition implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2776688207743184897L;
	private Race race;
	private String horseName;
	private int horsePosition;
	private boolean isLastPosition;
	private int horseNumber;
	private int horseAge;
	private int horseWeight;
	private String jockeyName;
	private int jockeyWeight;
	private String trainerName;
	private double dividend;
	
	public RacePosition() {}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public String getHorseName() {
		return horseName;
	}

	public void setHorseName(String horseName) {
		this.horseName = horseName;
	}

	public int getHorsePosition() {
		return horsePosition;
	}

	public void setHorsePosition(int horsePosition) {
		this.horsePosition = horsePosition;
	}

	public boolean isLastPosition() {
		return isLastPosition;
	}

	public void setLastPosition(boolean isLastPosition) {
		this.isLastPosition = isLastPosition;
	}

	public int getHorseNumber() {
		return horseNumber;
	}

	public void setHorseNumber(int horseNumber) {
		this.horseNumber = horseNumber;
	}

	public int getHorseAge() {
		return horseAge;
	}

	public void setHorseAge(int horseAge) {
		this.horseAge = horseAge;
	}

	public int getHorseWeight() {
		return horseWeight;
	}

	public void setHorseWeight(int horseWeight) {
		this.horseWeight = horseWeight;
	}

	public String getJockeyName() {
		return jockeyName;
	}

	public void setJockeyName(String jockeyName) {
		this.jockeyName = jockeyName;
	}

	public int getJockeyWeight() {
		return jockeyWeight;
	}

	public void setJockeyWeight(int jockeyWeight) {
		this.jockeyWeight = jockeyWeight;
	}

	public String getTrainerName() {
		return trainerName;
	}

	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}

	public double getDividend() {
		return dividend;
	}

	public void setDividend(double dividend) {
		this.dividend = dividend;
	}
}
