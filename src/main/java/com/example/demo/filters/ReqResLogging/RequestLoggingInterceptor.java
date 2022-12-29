// package com.example.demo.filters.ReqResLogging;

// import static net.logstash.logback.argument.StructuredArguments.keyValue;

// import java.io.ByteArrayOutputStream;
// import java.io.IOException;
// import java.nio.channels.Channels;
// import lombok.extern.slf4j.Slf4j;
// import org.apache.commons.io.IOUtils;
// import org.springframework.core.io.buffer.DataBuffer;
// import org.springframework.http.server.reactive.ServerHttpRequest;
// import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
// import reactor.core.publisher.Flux;

// /**
// * author - ankit1.rai
// */

// @Slf4j
// public class RequestLoggingInterceptor extends ServerHttpRequestDecorator {

// public RequestLoggingInterceptor(final ServerHttpRequest delegate) {
// super(delegate);
// }

// @Override
// public Flux<DataBuffer> getBody() {
// ByteArrayOutputStream baos = new ByteArrayOutputStream();
// return super.getBody().doOnNext(dataBuffer -> {
// try {
// Channels.newChannel(baos).write(dataBuffer.asByteBuffer().asReadOnlyBuffer());
// String body = IOUtils.toString(baos.toByteArray(), "UTF-8");
// // log.info("Request: method: {}, uri: {}, payload: {}",
// // getDelegate().getMethod(),
// // getDelegate().getPath(), body);
// // log.info(append("method", getDelegate().getMethod()).and(append("uri",
// // getDelegate().getPath()))
// // .and(append("payload", body), "Request :{}");
// log.info("Request: {} {} {} {} ", keyValue("method",
// getDelegate().getMethod()),
// keyValue("uri", getDelegate().getPath().value()),
// keyValue("headers", getDelegate().getHeaders()),
// keyValue("payload", body));
// } catch (IOException e) {
// log.error("error :{} ", e);
// } finally {
// try {
// baos.close();
// } catch (IOException e) {
// log.error("error :{} ", e);
// }
// }
// });
// }

// }