package org.istio;

import io.grpc.*;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

public class JWTClientInterceptor implements ClientInterceptor {

    private String tokenValue = "default";

    public static final Metadata.Key<String> JWT_KEY = Metadata.Key.of("Authorization", ASCII_STRING_MARSHALLER);


    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions,
            Channel channel) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(
                channel.newCall(methodDescriptor, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                //System.out.println(tokenValue);
                headers.put(JWT_KEY, "Bearer " + tokenValue);
                super.start(responseListener, headers);
            }
        };
    }

}
