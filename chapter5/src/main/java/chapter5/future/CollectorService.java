package chapter5.future;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import io.vertx.ext.web.codec.BodyCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CollectorService extends AbstractVerticle {

  private final Logger logger = LoggerFactory.getLogger(chapter5.callbacks.CollectorService.class);
  private WebClient webClient;

  @Override
  public void start(Promise<Void> promise) {
    webClient = WebClient.create(vertx);
    vertx.createHttpServer()
      .requestHandler(this::handleRequest)
      .listen(8080, ar -> {
        if (ar.succeeded()) {
          System.out.println("http://localhost:8080/");
          promise.complete();
        } else {
          promise.fail(ar.cause());
        }
      });
  }

  private void handleRequest(HttpServerRequest request) {
    CompositeFuture.all(
      fetchTemperature(3000),
      fetchTemperature(3001),
      fetchTemperature(3002))
      .flatMap(this::sendToSnapshot)
      .onSuccess(data -> request.response()
        .putHeader("Content-Type", "application/json")
        .end(data.encode()))
      .onFailure(err -> {
        logger.error("Something went wrong", err);
        request.response().setStatusCode(500).end();
      });
  }

  private Future<JsonObject> sendToSnapshot(CompositeFuture temps) {
    Promise<JsonObject> result = Promise.promise();
    List<JsonObject> tempData = temps.list();
    JsonObject data = new JsonObject()
      .put("data", new JsonArray()
        .add(tempData.get(0))
        .add(tempData.get(1))
        .add(tempData.get(2)));
    webClient
      .post(4000, "localhost", "/")
      .expect(ResponsePredicate.SC_SUCCESS)
      .as(BodyCodec.jsonObject())
      .sendJson(data, ar -> {
        if (ar.succeeded()) {
          result.complete(data);
        } else {
          result.fail(ar.cause());
        }
      });
    return result.future();
  }

  private Future<JsonObject> fetchTemperature(int port) {
    Promise<JsonObject> result = Promise.promise();
    webClient
      .get(port, "localhost", "/")
      .expect(ResponsePredicate.SC_SUCCESS)
      .as(BodyCodec.jsonObject())
      .send(ar -> {
        if (ar.succeeded()) {
          result.complete(ar.result().body());
        } else {
          result.fail(ar.cause());
        }
      });
    return result.future();
  }
}
