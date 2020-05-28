[![Build Status](https://travis-ci.com/jponge/qa-via-examples.svg?branch=3.9-on-v4-codebase)](https://travis-ci.com/jponge/qa-via-examples)

Some Vert.x 4 code running on top of 3.9.x

## Incompatible changes between 3.9.x and 4

* Future-based asynchronous methods are only available in the core APIs starting from 4 (e.g., `httpServer.listen(8080).onComplete(...)`).
* JUnit 5 RxJava 2 bindings are available in `vertx-junit5` in 3.9, and in `vertx-junit5-rx-java2` starting from 4.
* Authentication APIs have incompatible changes.
  In the 10k steps challenge app they appear as follows.
    * `MongoAuth` is replaced by `MongoAuthentication` and by `MongoUserUtil` for helper methods to add users (e.g., `MongoAuth#insertUser` becomes `MongoUserUtil#createUser`).
    * In 3.9 PEM files need to be stripped of encapsulating `----` begin / end lines, this is not the case in 4 anymore.
    * In 3.9 `PubSecKeyOptions` supports adding key pairs with `setPublicKey` and `.setSecretKey`.
      In 4 one needs to add 2 keys separately, and use `setBuffer` to pass each key.
