package com.magc.sensecane.model.domain;

import com.magc.sensecane.framework.model.BaseEntity;

public class Message extends BaseEntity {

	private final Integer id;
	private final Integer from;
	private final Integer to;
	private final String message;
	private final Long timestamp;
	
	public Message(Integer id, Integer from, Integer to, String message, Long timestamp) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.message = message;
		this.timestamp = timestamp;
	}

	public Integer getId() {
		return id;
	}

	public Integer getFrom() {
		return from;
	}

	public Integer getTo() {
		return to;
	}

	public String getMessage() {
		return message;
	}

	public Long getTimestamp() {
		return timestamp;
	}
	
}
