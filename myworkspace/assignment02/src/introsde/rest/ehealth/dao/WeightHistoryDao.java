package introsde.rest.ehealth.dao;

import introsde.rest.ehealth.model.HealthProfile;
import introsde.rest.ehealth.model.WeightHistory;

import java.util.HashMap;
import java.util.Map;

public enum WeightHistoryDao {
	instance;

	private Map<Long, WeightHistory> contentProvider = new HashMap<Long, WeightHistory>();

	private WeightHistoryDao() {
		
//		WeightHistory 1
		WeightHistory test = new WeightHistory(100);
		test.setMeasureId(new Long(1));
		
		contentProvider.put(test.getMeasureId(), test);
	}

	public Map<Long, WeightHistory> getDataProvider() {
		return contentProvider;
	}
}