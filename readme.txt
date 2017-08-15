All public transports has own geo devices and send current location to
server once per 10 seconds.

On server we put above coordinate to cache.

There is job which upload coordinates from cache to database. The job runs
once per 3 minute.


Stack of technologies.
Server side:
Java 8; REST - Dropwizard; Cache - Redis; Database - MongoDB

UI side:
ReactJS, React-Bootstrap