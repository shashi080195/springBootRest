// package com.example.demo.filters.ReqResLogging;

// import static net.logstash.logback.argument.StructuredArguments.keyValue;

// import java.util.List;
// import lombok.NoArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.server.reactive.ServerHttpRequest;
// import org.springframework.http.server.reactive.ServerHttpResponse;
// import org.springframework.web.server.ServerWebExchange;
// import org.springframework.web.server.ServerWebExchangeDecorator;
// import org.springframework.web.server.WebFilter;
// import org.springframework.web.server.WebFilterChain;
// import reactor.core.publisher.Mono;

// @Slf4j
// @NoArgsConstructor
// public class ReactiveSpringLoggingFilter implements WebFilter {

// private static final String IGNORE_PATTERNS = "actuator";

// @Override
// public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
// if (IGNORE_PATTERNS != null &&
// exchange.getRequest().getURI().getPath().toLowerCase()
// .contains(IGNORE_PATTERNS)) {
// return chain.filter(exchange);
// } else {
// final long startTime = System.currentTimeMillis();
// List<String> header =
// exchange.getRequest().getHeaders().get(HttpHeaders.CONTENT_LENGTH);
// if (header == null || header.get(0).equals("0")) {
// log.info("Request: {} {} {} ", keyValue("method",
// exchange.getRequest().getMethod()),
// keyValue("uri", exchange.getRequest().getURI().getPath()),
// keyValue("headers", exchange.getRequest().getHeaders()));
// }
// ServerWebExchangeDecorator exchangeDecorator = new
// ServerWebExchangeDecorator(exchange) {
// @Override
// public ServerHttpRequest getRequest() {
// return new RequestLoggingInterceptor(super.getRequest());
// }

// @Override
// public ServerHttpResponse getResponse() {
// return new ResponseLoggingInterceptor(super.getResponse(), startTime);
// }
// };
// return chain.filter(exchangeDecorator)
// .doOnSuccess(aVoid -> {
// logResponse(startTime, exchangeDecorator.getResponse(),
// exchangeDecorator.getResponse().getStatusCode().value());
// })
// .doOnError(throwable -> {
// logResponse(startTime, exchangeDecorator.getResponse(), 500);
// });
// }
// }

// private void logResponse(long startTime, ServerHttpResponse response, int
// overriddenStatus) {
// final long duration = System.currentTimeMillis() - startTime;
// List<String> header = response.getHeaders().get("Content-Length");
// if (header == null || header.get(0).equals("0")) {
// // log.info("Response({} ms): status: {}, headers: {}",
// value("X-Response-Time",
// // duration),
// // value("X-Response-Status", overriddenStatus), response.getHeaders());
// log.info("Response: {} {} {} ", keyValue("status", overriddenStatus),
// keyValue("headers", response.getHeaders()),
// keyValue("responseTime(ms)", duration));
// }
// }
// }
