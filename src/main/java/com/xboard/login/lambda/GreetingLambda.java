package com.xboard.login.lambda;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.xboard.login.lambda.model.User;

public class GreetingLambda implements RequestHandler<User, String> {

    @Override
    public String handleRequest(User input, Context context) {

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion("ap-south-1")
                .build();

        DynamoDBMapper mapper = new DynamoDBMapper(client);

        return checkData(mapper,input);
    }

    private String checkData(DynamoDBMapper mapper, User input) {
        UserData userData=new UserData();
        userData.setName(input.getName());

        UserData result=mapper.load(userData);
        if(!(result==null)){
            String user= input.getName() + "##" + input.getPassword();
            if(result.getName().equals(input.getName()) && result.getUser().equals(user)) {
                return "Login Success";
            }
        }
        return "Login Failed";
    }

}
