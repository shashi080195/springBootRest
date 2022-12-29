// package com.example.demo.filters.ReqResLogging;

// import static net.logstash.logback.argument.StructuredArguments.keyValue;

// import java.io.ByteArrayOutputStream;
// import java.io.IOException;
// import java.nio.channels.Channels;
// import lombok.extern.slf4j.Slf4j;
// import org.apache.commons.io.IOUtils;
// import org.reactivestreams.Publisher;
// import org.springframework.core.io.buffer.DataBuffer;
// import org.springframework.http.server.reactive.ServerHttpResponse;
// import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
// import reactor.core.publisher.Flux;
// import reactor.core.publisher.Mono;

// /**
// * author - ankit1.rai
// */

// @Slf4j
// public class ResponseLoggingInterceptor extends ServerHttpResponseDecorator {

// private long startTime;

// public ResponseLoggingInterceptor(final ServerHttpResponse delegate, final
// long startTime) {
// super(delegate);
// this.startTime = startTime;
// }

// @Override
// public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
// Flux<DataBuffer> buffer = Flux.from(body);
// return super.writeWith(buffer.doOnNext(dataBuffer -> {
// ByteArrayOutputStream baos = new ByteArrayOutputStream();
// try {
// Channels.newChannel(baos).write(dataBuffer.asByteBuffer().asReadOnlyBuffer());
// String bodyRes = IOUtils.toString(baos.toByteArray(), "UTF-8");
// // log.info("Response({X-Response-Time: {} ms): status: {}, payload: {}",
// // (System.currentTimeMillis() - startTime),
// // getStatusCode().value(), bodyRes);
// // Map<String, String> map = new HashMap<>();
// // map.put("status", String.valueOf(getStatusCode().value()));
// // map.put("payload", bodyRes);
// // map.put("responseTime", String.valueOf();
// // log.info(append("status", getStatusCode().value()).and(append("payload",
// // bodyRes))
// // .and(append("responseTime(ms)", System.currentTimeMillis() - startTime)),
// // "Response :{}");
// log.info("Response: {} {} {} {} ", keyValue("status",
// getStatusCode().value()),
// keyValue("payload", bodyRes),
// keyValue("headers", getHeaders()),
// keyValue("responseTime(ms)", System.currentTimeMillis() - startTime));
// } catch (IOException e) {
// log.error("error :{} ", e);
// } finally {
// try {
// baos.close();
// } catch (IOException e) {
// log.error("error :{} ", e);
// }
// }
// }));
// }

// }
