package com.trabajando.services.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Race implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5917632947855823285L;
	
	private Long id;
	private Integer number;
	private Date date;
	private String link;
	private Integer distance;
	private String time;
	private String videoLink;
	private List<RacePosition> positions;
	
	public Race() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getVideoLink() {
		return videoLink;
	}

	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}

	public List<RacePosition> getPositions() {
		return positions;
	}

	public void setPositions(List<RacePosition> positions) {
		this.positions = positions;
	}
}
