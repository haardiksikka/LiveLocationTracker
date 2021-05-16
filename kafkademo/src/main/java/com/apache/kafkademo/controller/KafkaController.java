package com.apache.kafkademo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apache.kafkademo.service.ElasticSearchService;
import com.apache.kafkademo.service.KafkaProducerService;



@RestController
@CrossOrigin
public class KafkaController {
	
	@Autowired 
	KafkaProducerService service;
	
	@Autowired
	ElasticSearchService elasticService;
	
	
	@GetMapping("/hello")
	public String sendMessage() {
		
		service.sendMessage();
		return "OK";
	}
	
	@PostMapping("/producer")
	public Map<String, String> sendMessageWithCallback(@RequestBody String position){
		System.out.println("enetering..."+position);
		return service.sendMessageWithCallback(position);
	}
	
	@GetMapping("/user/locations")
	public List<Location> findAll() throws Exception{
		return elasticService.findAll();
	}
	
	@GetMapping("/user/locations/{user}")
	public List<Location> trackLocationByUser(@PathVariable String user) throws Exception{
		return elasticService.trackUserLocation(user);
	}
	
	//@PostMapping("/consumer")
	
//	@GetMapping("/form")
//	public ArrayList<Map<String, String>> getForm(){
//		ArrayList<Map<String, String>> form = new ArrayList<>();
//		Map<String, Object> hm = new HashMap<>();
//	//	 columnFactory.text("workflowName", "Workflow Name"),
//    //     columnFactory.text("workflowDescription", "Description"),
//		hm.put("name", "Workflow Name");
//		hm.put("id","workflowName");
//		hm.put("key", "workflowName");
//		hm.put("type", "() => {}");
//	}
	
	

}
