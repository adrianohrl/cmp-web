# Stile
A Production Planning, Control, Monitoring and Reporting application developed in Java technologies.

## Deploying to the server

Use the following command in order to deploy this application to the server:

```bash
	git clone https://github.com/adrianohrl/stile-web
	cd stile-web
	mvn install
	export VERSION=<desired version>
	asadmin deploy --force=true --contextroot=stile --name=stile ./target/stile-web-${VERSION}.war
```

Notice that you need to inform the desired StileWeb version. For example, if it is desired the 1.3.6 version of StileWeb, you should export the following environment variable:

```bash
	export VERSION=1.3.6
```

If it is desired to drop-create the hibernate schema based on the annotated classes to the database, run the following command inside the cloned repository:

```bash
	mvn hibernate4:export -Dhibernate.schema.skip=false
```

Notice that the MySQL server must be running for that.

## Running the StileWeb application

Considering that the application is properly deployed in the server and the database schema is already created, start the MySQL server and a Glassfish domain. Then, open a browser and enter the following address:

```
	localhost:8080/stile
```

If you desire to use the application in another computer, enter the following address:

```
	<server ip>:8080/stile
```

where <server ip> is the IP address of the StileWeb hosting computer (for example, 192.168.0.100).