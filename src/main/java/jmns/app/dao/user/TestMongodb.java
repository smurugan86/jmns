package jmns.app.dao.user;

import jmns.app.util.Base64;


public class TestMongodb{
	
	 
	 public static void main(String arg[]){
		 
		 
		 
			//Object O = Base64.encodeObject("C7D9F36E-87EF-42D5-8AB0-DDA7B1BBD8C0");
			
			Object O = Base64.encodeObject("3898efe6-b736-4523-a313-4fb6cfc9ccc4");
		 System.out.println("encode objects => "+O);
		 
		 
			Object dO = Base64.decodeToObject("rO0ABXQAMWNrYXJsbWFuQGdoeC5jb206OTkwZTE1NjEyZmVlMTQ0ZjhjYTVhYTVmODNiOThmNDU=");
			System.out.println("decode objects => "+dO);
			
			
			Object d1 = Base64.decodeToObject("rO0ABXQAPHJvbi5rZWVmZXJAc3ZoZWFsdGhjYXJlLm9yZzo5OTBlMTU2MTJmZWUxNDRmOGNhNWFhNWY4M2I5OGY0NQ==");
			System.out.println("decode objects => "+d1);
			
			
			
			Object d2 = Base64.decodeToObject("rO0ABXQAMGJnaWJzb25AYXJoLm9yZzo5OTBlMTU2MTJmZWUxNDRmOGNhNWFhNWY4M2I5OGY0NQ==");
		 System.out.println("encode objects => "+d2);

					Object d3 = Base64.decodeToObject("rO0ABXQAPXNvaGFuLmJvYmhhdGVAdmlpdG9ubGluZS5jb206OTkwZTE1NjEyZmVlMTQ0ZjhjYTVhYTVmODNiOThmNDU=");
		 System.out.println("encode objects => "+d3);

							Object d4 = Base64.decodeToObject("rO0ABXQAOmFuZHJlYS53b29kaW5nQGhvd2FyZC5lZHU6OTkwZTE1NjEyZmVlMTQ0ZjhjYTVhYTVmODNiOThmNDU=");
		 System.out.println("encode objects => "+d4);

									Object d5 = Base64.decodeToObject("rO0ABXQAMWNrYXJsbWFuQGdoeC5jb206OTkwZTE1NjEyZmVlMTQ0ZjhjYTVhYTVmODNiOThmNDU=");
		 System.out.println("encode objects => "+d5);

											Object d6 = Base64.decodeToObject("rO0ABXQAPHJvbi5rZWVmZXJAc3ZoZWFsdGhjYXJlLm9yZzo5OTBlMTU2MTJmZWUxNDRmOGNhNWFhNWY4M2I5OGY0NQ==");
		 System.out.println("encode objects => "+d6);



													Object d7 = Base64.decodeToObject("rO0ABXQAM2FyaEB2ZW5kb3JtYXRlLmNvbTo5OTBlMTU2MTJmZWUxNDRmOGNhNWFhNWY4M2I5OGY0NQ==");
		 System.out.println("encode objects => "+d7);

															Object d8 = Base64.decodeToObject("rO0ABXQANmhvd2FyZEB2ZW5kb3JtYXRlLmNvbTo5OTBlMTU2MTJmZWUxNDRmOGNhNWFhNWY4M2I5OGY0NQ==");
		 System.out.println("encode objects => "+d8);
					
																	Object d9 = Base64.decodeToObject("rO0ABXQAPnBhbG1ldHRvaGVhbHRoQHZlbmRvcm1hdGUuY29tOjk5MGUxNTYxMmZlZTE0NGY4Y2E1YWE1ZjgzYjk4ZjQ1");
		 System.out.println("encode objects => "+d9);
			
			
		 /*String user = "ccxcmuser:ccxcmpass";
		 
		 ClientConfig clientConfig = new ClientConfig();
		 HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("username", "password");
		 clientConfig.register( feature) ;
		Client client = ClientBuilder.newClient( clientConfig );
	    WebTarget webTarget = client.target("http://localhost:6060/PCMServices").path("/rest/ccxcm/contracts/172347/provider/104049939/startDate/2015-04-01/endDate/2021-03-31/vendor/101044214/items/pdf/export");
	     
	    Invocation.Builder invocationBuilder =  webTarget.request("application/pdf");
	    Response response = invocationBuilder.get();
	    
		 
				 
		 MongoCollection<Document> users;
		 MongoCollection<Document> images;
		 MongoCollection<Document>  animals;
		 MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost"));
	     MongoDatabase blogDatabase = mongoClient.getDatabase("murugan");
	     users = blogDatabase.getCollection("users");
	     images = blogDatabase.getCollection("images");
	     
	     animals = blogDatabase.getCollection("animals");
	     
	     
	     Iterable<Document> userList = users.find();
         for (Document document : userList) {
        	 System.out.println("email ====" +  document.get("email").toString());
        	 users.updateOne(new Document("_id", document.get("_id").toString()),
      		        new Document("$set", new Document("status", "ACT").append("date", new Date()).append("createdDate", new Date())));
         }
         
         Date curr = Calendar.getInstance().getTime();
         

         Calendar cal = Calendar.getInstance();		
 		cal.add(Calendar.YEAR, 10);
 		Date expiryDate = cal.getTime();
 		
         
         System.out.println("curr Date" + curr);
         System.out.println("my date Date" +expiryDate);
         
         if(expiryDate.after(curr)){
        	 System.out.println("after Date");
         }else{
        	 System.out.println("Before  Date");
         }
         
        String group ="Test07Apr2016-1, 070420161,";
        String companyName = "Test";
        boolean compare = group.contains(companyName);
        

        System.out.println("compare = "+compare);
        
        
        String reportName=" String Value . $%^";
        reportName=reportName.trim();
        reportName=reportName.replaceAll("[^a-zA-Z0-9]+", "_");
        reportName=reportName.replaceAll(" ", "");
        reportName=reportName.replaceAll("(_)+", "_");
        
        System.out.println("reportName =" +reportName);
*/


       }
	 
}