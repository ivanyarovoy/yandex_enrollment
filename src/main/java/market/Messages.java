package market;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Messages {
	public static JsonNode getMessage400() throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readTree("{\"code\":400,\"message\":\"Validation failed\"}");
	}

	public static JsonNode getMessage404() throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readTree("{\"code\":404,\"message\":\"Item not found\"}");
	}
}
