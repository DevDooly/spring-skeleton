package com.devdooly.skeleton.core.web;

import org.springframework.web.reactive.function.server.ServerRequest;

import java.net.InetSocketAddress;

public class WebUtils {

    public static String getRemoteHost(ServerRequest serverRequest) {
        return serverRequest.remoteAddress().map(InetSocketAddress::getHostName).orElse("unknown-host");
    }
}
