# akka-http-boopickle

[BooPickle](https://github.com/ochrons/boopickle) marshaller for [akka-http](https://github.com/akka/akka).


## Why?

From `BooPickle`'s `README`:

> BooPickle is the fastest and most size efficient serialization (aka pickling) library that works on both Scala and Scala.js. It encodes into a binary format instead of the more customary JSON. A binary format brings efficiency gains in both size and speed, at the cost of legibility of the encoded data.

## Usage

- Add dependencies:

  ```scala
  "com.muhuk" %% "akka-http-boopickle" % "0.1.0-SNAPSHOT",
  "io.suzaku" %%% "boopickle" % "1.3.1"
  ```

- To enable `BooPickle` format in `akka-http`:
  - Mix-in `akka.http.scaladsl.marshallers.boopickle.BooPickleSupport` trait
  - or import its `booPickleMarshaller` & `booPickleUnmarshaller` (implicit) functions.
- `BooPickle` content type is set to `"application/boopickle"` by default. Override `BooPickleSupport.booPickleMediaType` if you want to use a different content type.
- To (un)marshall `BooPickle` data use `booPickleMediaType` content type. For example, set `ACCEPT` header of client requests to `application/boopickle` to have the server return `BooPickle` data.

Note that you still need to create picklers for your types. See [BooPickle documentation](https://github.com/ochrons/boopickle) for more info.

## License

Copyright 2019 Atamert Ölçgen.

akka-http-boopickle released with Apache License, Version 2.0, same as BooPickle. See [LICENSE](LICENSE) file.
