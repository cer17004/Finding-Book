//package com.example.findingbook;
//
//// Base Stitch Packages
//import com.mongodb.client.FindIterable;
//import com.mongodb.stitch.android.core.Stitch;
//import com.mongodb.stitch.android.core.StitchAppClient;
//
//// Packages needed to interact with MongoDB and Stitch
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoCollection;
//
//// Necessary component for working with MongoDB Mobile
//import com.mongodb.stitch.android.services.mongodb.local.LocalMongoDbService;
//
//import org.bson.Document;
//
//import java.util.ArrayList;
//
//public class MGBHandler {
//
//    // Create the default Stitch Client
//    final StitchAppClient client =
//            Stitch.initializeDefaultAppClient("<APP ID>");
//
//    // Create a Client for MongoDB Mobile (initializing MongoDB Mobile)
//    final MongoClient mobileClient =
//            client.getServiceClient(LocalMongoDbService.clientFactory);
//
//    // Point to the target collection and insert a document
//    MongoCollection<Document> localCollection =
//            mobileClient.getDatabase("my_db").getCollection("my_collection");
//
//    Document document = new Document();
//
//    localCollection.insertOne(document);
//
//        // Find the first document
//        Document doc = localCollection.find().first();
//
//        //Find all documents that match the find criteria
//        Document query = new Document();
//        query.put("name", new BsonString("veirs"));
//
//        FindIterable<Document> cursor = localCollection.find(query);
//        ArrayList<Document> results =
//                (ArrayList<Document>) cursor.into(new ArrayList<Document>());
//}
