package com.mstr.domain;

import java.util.Date;

import lombok.Data;

@Data
public class BoardVO {
	private Date time;
	private double open;
	private double high;
	private double  low;
	private double close;
	private double volume;
}
