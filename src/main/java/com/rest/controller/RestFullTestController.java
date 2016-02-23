package com.rest.controller;
 
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.codehaus.jackson.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rest.client.RestFullTestClient;
import com.rest.util.RestFullReadFile;
 
/**
 * @author rama krishna
 * Having the basic controller part. for actions /search, /top, /paragraph
 * Returns the map/json based on the content type
 * 
 * Handles requests for the application home page.
 */
@RestController
public class RestFullTestController {
   
  private static final Logger logger = LoggerFactory.getLogger(RestController.class);
  
  RestFullReadFile commonParagraphUtil = new RestFullReadFile();
  
  @Autowired
  private RestFullTestClient restClient;
  
  public RestFullTestController() {
  }
   
  
  @RequestMapping(value="/search", method=RequestMethod.POST)
  public Map<String, Map<String,String>> getSearch(@RequestBody Map<String, List<String>> myMap) throws JsonProcessingException, IOException {
    logger.info("Inside getSearch() method...");
    Map<String,Integer> wordMap = RestFullReadFile.getWordMap(restClient.getParagraph());
    Map<String, Map<String, String>> resultMap = RestFullReadFile.getJSONMapData(wordMap,myMap);
    
    return resultMap;
  }
  
  @RequestMapping(value="/top/{countValue}", method=RequestMethod.GET)
  public String getTop(@PathVariable("countValue") String countValue) throws IOException {
    logger.info("Inside getTop() method...");
    Map<Integer,List<String>> wordMap = RestFullReadFile.getWordCountMapList(restClient.getParagraph());
    return RestFullReadFile.getTopData(wordMap,Integer.valueOf(countValue).intValue());
  }
  
  @RequestMapping(value = "/paragraph", method = RequestMethod.GET)
  public String getParagraph(Locale locale, Model model) throws IOException {
    return commonParagraphUtil.readParagraphFromFile();
  }

public RestFullTestClient getRestClient() {
	return restClient;
}

public void setRestClient(RestFullTestClient restClient) {
	this.restClient = restClient;
}

  
}
