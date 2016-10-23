package com.dimasco;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification;

public class S3EventLambda implements RequestHandler<S3Event,Object> {

    public Object handleRequest(S3Event s3Event, Context context) {
        context.getLogger().log("Handling an event!");
        context.getLogger().log("Input: " + s3Event);
        S3EventNotification.S3Entity s3Entity = s3Event.getRecords().get(0).getS3();
        context.getLogger().log("S3 object: " + s3Entity.getObject());
        context.getLogger().log("S3 object key: " + s3Entity.getObject().getKey());
        return s3Entity.getBucket().getName();
    }
}
