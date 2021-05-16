package com.apache.kafkademo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apache.kafkademo.controller.Location;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticSearchService {

	 	private static final String TYPE = "locations";


		private static final String INDEX = "user";


		private RestHighLevelClient client;


	    private ObjectMapper objectMapper;

	    @Autowired
	    public ElasticSearchService(RestHighLevelClient client, ObjectMapper objectMapper) {
	        this.client = client;
	        this.objectMapper = objectMapper;
	    }
	    
	    @SuppressWarnings("deprecation")
		public void sendMessageToElasticSearch(String message) throws IOException {
	    	UUID uuid = UUID.randomUUID();
	    	//we can have consumer here 
	    	//it will consume stream of messages 
	    	//we will send those message to elastic search db
	    	//to make consumer idempotent
	    	//we will generate unique id using == topic+partion+offset
	    	//and send that id to index request as parameter.
	    	String id = uuid.toString();
	    	IndexRequest indexRequest = new IndexRequest(INDEX, TYPE,id )
	                .source(message, XContentType.JSON);

	        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
	    }
	    
	    public List<Location> findAll() throws Exception {


	        SearchRequest searchRequest = buildSearchRequest(INDEX,TYPE);
	        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
	        searchRequest.source(searchSourceBuilder);

	        SearchResponse searchResponse =
	                client.search(searchRequest, RequestOptions.DEFAULT);

	        return getSearchResult(searchResponse);
	    }
	    
	    public List<Location> trackUserLocation(String user) throws Exception{


	        SearchRequest searchRequest = new SearchRequest();
	        searchRequest.indices(INDEX);
	        searchRequest.types(TYPE);

	        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

	        MatchQueryBuilder matchQueryBuilder = QueryBuilders
	                .matchQuery("user",user)
	                .operator(Operator.AND);

	        searchSourceBuilder.query(matchQueryBuilder);

	        searchRequest.source(searchSourceBuilder);

	        SearchResponse searchResponse =
	                client.search(searchRequest, RequestOptions.DEFAULT);

	        return getSearchResult(searchResponse);

	    }
	    
	    private List<Location> getSearchResult(SearchResponse response) {

	        SearchHit[] searchHit = response.getHits().getHits();

	        List<Location> locations = new ArrayList<>();

	        for (SearchHit  hit : searchHit){
	        	locations
	                    .add(objectMapper
	                            .convertValue(hit
	                                    .getSourceAsMap(), Location.class));
	        }
	        
	        

	        return locations;
	    }
	    
	    private SearchRequest buildSearchRequest(String index, String type) {

	        SearchRequest searchRequest = new SearchRequest();
	        searchRequest.indices(index);
	        searchRequest.types(type);

	        return searchRequest;
	    }
}
