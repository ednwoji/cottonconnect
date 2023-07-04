package com.cottonconnect.elearning.ELearning.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqQueryChart {
	private List<String> prevDates;
	private List<Integer> queriesReceived;
	private List<Integer> queriesResponded;
}
