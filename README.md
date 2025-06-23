
# Travel with friends app
Over the years I have gone to a bunch of places with a bunch of friends.<br />
I usially save my photos from trips based on the date they were taken, but there are many more options to view the photos:<br />
at a place since specified date, for example can show you all the photos at Bass lake since 2010. Provided you went back a number of times results could be interesting :)
Or you can ask to see all the photos of a certain person, regardless of the place and date.<br />
Another thing I wanted to look at is the route I took each day, as those were long and interesting.<br />
Therefore you can select the day and the app will show you the route for that day, and the photos at your points of interest that day in that spot.
This is the backend written in <b>Java with light4j</b>, the front end is in another repo.<br />

### Technologies used:
<ul>
<li>OpenAPI Light-4J Server</li>
<li>myBatis</li>
</ul>

### Configuration:
To connect to the database please make sure to go to mybatis-config.xml and update your
<ul>
<li>url</li>
<li>username</li>
<li>password</li>
</ul>
as well as driver, if you are not using MSSQL.

Make sure to include the directory into which the photos will be saved,
and from which they will be shown in src/main/resources/config/values.yml
The value is called photoUploadDir.

### Note: 
OpenAPI 3.0 (which Light-4j uses) does not support multi-segment path parameters by default,<br />
the URL to the photos looks like photos?filepath=2023/01Jan-15/IMG_20230115_123327988.jpg <br />
photos/2023/01Jan-15/IMG_20230115_123327988.jpg  can not be configured with OpenAPI 3.0, 
because it can not take all the remaining path and put it into one parameter.

### Build and Start

The scaffolded project contains a single module. A fat jar server.jar will be generated in target directory after running the build command below.

```
./mvnw clean install -Prelease
```

With the fatjar in the target directory, you can start the server with the following command.

```
java -jar target/server.jar
```

To speed up the test, you can avoid the fat jar generation and start the server from Maven.

```
./mvnw clean install exec:exec
```




### Test

By default, the OAuth2 JWT security verification is disabled, so you can use Curl or Postman to test your service right after the server is started. For example, the petstore API has the following endpoint.

```
curl -k https://localhost:8443/v1/pets
```

For your API, you need to change the path to match your specifications.

### Tutorial

To explore more features, please visit the [petstore tutorial](https://doc.networknt.com/tutorial/rest/openapi/petstore/).
