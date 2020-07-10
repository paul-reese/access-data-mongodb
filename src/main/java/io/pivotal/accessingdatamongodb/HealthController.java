package io.pivotal.accessingdatamongodb;

import com.mongodb.client.MongoClients;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/access-data-mongodb/health")
public class HealthController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping("/mongodb")
    public String mongoDbHealthCheck() throws Exception {
        // add healthcheck to MongoDB
        doMongoHealthCheck();
        return "up";
    }

    @RequestMapping("/spring-cloud-services")
    public String sprinCloudServices() {
        // add health check against SCS config server
        return "up";
    }

    private boolean doMongoHealthCheck() throws Exception {
        Assert.notNull(mongoTemplate, "MongoOps must not be null");
        Document result = mongoTemplate.executeCommand("{ buildInfo: 1 }");
        // strip out the results to see the "version" in JSON result and return binary
        //String version = (String) result.get("version");
        System.out.println(result.toJson());
        return false;
    }
}
