// package com.example.demo.filters;

// import java.net.URI;
// import lombok.extern.slf4j.Slf4j;
// import org.eclipse.jetty.client.HttpClient;
// import org.eclipse.jetty.client.api.Request;
// import org.eclipse.jetty.http.HttpField;
// import org.eclipse.jetty.util.ssl.SslContextFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;

// /**
// * author - ankit1.rai
// */

// @Component
// @Slf4j
// public class WebClientJettyReqResLog {

// SslContextFactory.Client sslContextFactory = new SslContextFactory.Client();
// HttpClient customeHttpClient = new HttpClient(sslContextFactory) {
// @Override
// public Request newRequest(URI uri) {
// Request request = super.newRequest(uri);
// return enhance(request);
// }
// };

// /**
// *
// * @param request
// * @return
// */
// Request enhance(Request request) {
// StringBuilder group = new StringBuilder();
// request.onRequestBegin(theRequest -> {
// // append request url and method to group
// group.append("uri: " + theRequest.getURI());
// });
// request.onRequestHeaders(theRequest -> {
// for (HttpField header : theRequest.getHeaders()) {
// group.append(header.getName() + " : " + header.getValue());
// }
// });
// request.onRequestContent((theRequest, content) -> {
// // group.append("body: ".
// });
// request.onRequestSuccess(theRequest -> {
// log.info(group.toString());
// group.delete(0, group.length());
// });
// group.append("\n");
// request.onResponseBegin(theResponse -> {
// // append response status to group
// });
// request.onResponseHeaders(theResponse -> {
// for (HttpField header : theResponse.getHeaders()) {
// // append response headers to group
// }
// });
// request.onResponseContent((theResponse, content) -> {
// // append content to group
// });
// request.onResponseSuccess(theResponse -> {
// log.info(group.toString());
// });
// return request;
// }
// }
