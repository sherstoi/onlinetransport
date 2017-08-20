All public transports has own geo devices and send current location to
server once per 10 seconds.

On server we put above coordinate to cache.

There is job which upload coordinates from cache to database. The job runs
once per 3 minute.

Stack of technologies.
Server side: Java 8, Dropwizard (Jetty, Guice, JAX-RS), Redis, MongoDB.

Before run this program please execute gradle task:
./gradlew awesomeFunJar

New jar file should appear in directory build/libs/
After that you can run jar file with below command:
java -jar build/libs/onlinetransport-1.0-SNAPSHOT-standalone.jar