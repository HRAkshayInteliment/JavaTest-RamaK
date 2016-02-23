package com.rest.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author rama krishna
 * 
 * Used to read the file
 *
 */
public class RestFullReadFile {
	
	private static final Logger logger = LoggerFactory.getLogger(RestFullReadFile.class);

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public String readParagraphFromFile() throws IOException{
		logger.info("Inside readParagraphFromFile() method...");
		ClassLoader classLoader = this.getClass().getClassLoader();
		File file = new File(classLoader.getResource("paragraph.txt").getFile());
		
		StringBuilder builder = new StringBuilder();
	
		String line="";
		try ( BufferedReader bufferedReader = new BufferedReader 
				(new  InputStreamReader (new FileInputStream(file))) ) 
		{
			while  ( ( line =bufferedReader.readLine()) !=null )
			{
				System.out.println(line);
				builder.append(line);
			}
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
			
		
		return builder.toString().replaceAll(",", "").replaceAll("\\.", "").replaceAll("\r", "").replaceAll("\n", "");
	}
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public static Map<Integer,List<String>> getWordCountMapList(String data) {
		logger.info("Inside getWordMapListCount() method...");
		TreeMap<Integer,List<String>> wordsCountMap = new TreeMap<Integer,List<String>>();
		
		String word[] = data.split(" ");
		List<String> wordList = Arrays.asList(word);
		
		Set<String> uniqueWords = new HashSet<String>(wordList);
		Iterator<String> iterator = uniqueWords.iterator(); 
		while (iterator.hasNext()){
			String wordValue = iterator.next();
			Integer wordCount = Collections.frequency(wordList, wordValue);
			if(wordsCountMap.containsKey(wordCount)){
				wordsCountMap.get(wordCount).add(wordValue);
			}else{
				List<String> wordValueList = new ArrayList<String>();
				wordValueList.add(wordValue);
				wordsCountMap.put(wordCount, wordValueList);
			}
		}
		return wordsCountMap.descendingMap();
	  }
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public static Map<String,Integer> getWordMap(String data) {
		logger.info("Inside getWordMap() method...");
		
		Map<String,Integer> wordsCountMap = new HashMap<String,Integer>();
		
		String word[] = data.split(" ");
		
		List<String> wordList = Arrays.asList(word);
		Set<String> uniqueWords = new HashSet<String>(wordList);
		
		Iterator<String> iterator = uniqueWords.iterator(); 
		while (iterator.hasNext()){
			String wordValue = iterator.next();
			Integer wordCount = Collections.frequency(wordList, wordValue);
			wordsCountMap.put(wordValue, wordCount);
		}
		return wordsCountMap;
	  }
	
	/**
	 * 
	 * @param wordMap
	 * @param myMap
	 * @return
	 */
	public static Map<String, Map<String, String>> getJSONMapData(Map<String,Integer> wordMap,Map<String, List<String>> myMap){
		logger.info("Inside getJSONMapData() method...");
		Map<String, Map<String, String>> map = new HashMap<String, Map<String,String>>();
		Map<String, String> resultMap = new HashMap<String, String>();
		
		Iterator<String> itr = myMap.keySet().iterator();
		
		while(itr.hasNext())
		{
			List<String>  wordList = (ArrayList<String>)myMap.get(itr.next());
			

			for (String keyElement : wordList) {
				if(wordMap.containsKey(keyElement))	{
					resultMap.put(keyElement, wordMap.get(keyElement).toString());
				}else{
					resultMap.put(keyElement, "0");
				}
				
			}
			
			break; // only one key (searchText) coming as a key. We can break after 1 loop.
		}
		
				
		
		map.put("counts", resultMap);
		return map;
	}
	
	/**
	 * 
	 * @param wordMap
	 * @param topCount
	 * @return
	 */
	public static String getTopData(Map<Integer,List<String>> wordMap,int topCount){
		logger.info("Inside getTopData() method...");
		StringBuilder topWord = new StringBuilder();
		int index=1;
			for (Integer wordCountValue : wordMap.keySet()) {
				List<String> topWordList = wordMap.get(wordCountValue);
				for (String wordValue : topWordList) {
					topWord.append(wordValue).append("|").append(wordCountValue).append("\r\n");
				}
				if(index==topCount){
					break;
				}
				index=index+1;
		}
		return topWord.toString();
	}
}
