package com.graphAnalysis.amlitudeRate;

public class AmplitudeRateDao {
	private static AmplitudeRateDao dao = null;

	public static AmplitudeRateDao getInstance() {
		if (dao == null)
			dao = new AmplitudeRateDao();
		return dao;
	}

}
