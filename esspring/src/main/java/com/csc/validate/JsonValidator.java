package com.csc.validate;

import static java.lang.System.err;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.csc.enums.JsonMessageType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

@Component
public class JsonValidator {

	public String convertToJsonString(Object obj) {

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = null;
		try {
			jsonInString = mapper.writeValueAsString(obj);

		} catch (JsonMappingException jsonEx) {
			err.println("Unable to map JSON: " + jsonEx);
		} catch (JsonProcessingException jsonEx) {
			err.println("Unable to process JSON: " + jsonEx);
		} 
		return jsonInString;
	}

	public boolean validate(Object obj, JsonMessageType type) {
		boolean validationStatus = false;
		try {
			Resource resource = null;
			ProcessingReport report;
			JsonNode draftv3;
			JsonNode draftv4;
			if (type == JsonMessageType.REQUEST) {
				resource = new ClassPathResource(
						"/com/csc/validate/empSearchRequest.json");
			} else if (type == JsonMessageType.RESPONCE) {
				resource = new ClassPathResource(
						"/com/csc/validate/empSearchResponse.json");
			}

			File files = resource.getFile();

			draftv3 = JsonLoader.fromFile(files);
			draftv4 = JsonLoader.fromString(convertToJsonString(obj));
			final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
			final JsonSchema schema = factory.getJsonSchema(draftv3);

			report = schema.validate(draftv4);
			System.out.println(report);
			validationStatus = report.isSuccess();
		} catch (JsonMappingException jsonEx) {
			err.println("Unable to map JSON: " + jsonEx);
		} catch (JsonProcessingException jsonEx) {
			err.println("Unable to process JSON: " + jsonEx);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return validationStatus;
	}
	/*
	 * public void validate(Map<String, String> requestMap){
	 * 
	 * 
	 * try{ ProcessingReport report; JsonNode draftv3; JsonNode draftv4;
	 * 
	 * Path currentDir = Paths.get("./src/com/mysearch/validation");
	 * System.out.println(currentDir.toAbsolutePath()); Path ss
	 * =currentDir.normalize(); System.out.println(ss.toAbsolutePath()); String
	 * schemaloc = ss.toAbsolutePath().toString()+"/emps.json"; // String
	 * sourceloc = ss.toAbsolutePath().toString()+"/empssource.json";
	 * 
	 * draftv3 = JsonLoader.fromString("emps.json"); draftv4 =
	 * JsonLoader.fromString(convertToJsonString(obj)); final JsonSchemaFactory
	 * factory = JsonSchemaFactory.byDefault();
	 * 
	 * final JsonSchema schema = factory.getJsonSchema(draftv3);
	 * 
	 * 
	 * report = schema.validate(draftv4); System.out.println(report); }catch
	 * (JsonMappingException jsonEx) { err.println("Unable to map JSON: " +
	 * jsonEx); } catch (JsonProcessingException jsonEx) {
	 * err.println("Unable to process JSON: " + jsonEx); } catch (IOException e)
	 * { // TODO Auto-generated catch block e.printStackTrace(); } catch
	 * (ProcessingException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 */

	/*
	 * public String getSchema(String type){
	 * 
	 * String requestjson = null; if( type.equals("requestjson")){
	 * 
	 * requestjson="{" + type
	 * +":"+object+","+id+":"+"urn:jsonschema:com:mysearch:validation:EmpSearch"
	 * +"," + "properties"+":"
	 * +"{"freeText":"+" {"id": "http:"+"//jsonschema.net
	 * /searchvalue","type": "string"}," +"retrieveFromDate": {"id":
	 * "http://jsonschema.net/retrieveFromDate","type": "string","pattern":
	 * "^(([0-9])|([0-2][0-9])|([3][0-1]))\/(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\/[0-9]{4}$"},
	 * +"retriveToDate": {"id": "http://jsonschema.net/retriveToDate","type":
	 * "string","pattern":
	 * "^(([0-9])|([0-2][0-9])|([3][0-1]))\/(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\/[0-9]{4}$"}},"required":["freeText","retrieveFromDate","retriveToDate"]}"
	 * 
	 * }
	 * 
	 * }
	 */
}
