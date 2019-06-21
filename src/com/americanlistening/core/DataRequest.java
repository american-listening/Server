package com.americanlistening.core;

import java.util.Arrays;

/**
 * Specifies a request of certain data.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class DataRequest {

	public static final int USER_PARAM_REQUEST = 0;							
	
	public int requestType;
	public String[] params;
	
	@Override
	public String toString() {
		return "DataRequest[requestType=" + requestType + ",params=" + Arrays.toString(params) + "]";
	}
}
