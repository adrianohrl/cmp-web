# Stile
A Production Planning, Control, Monitoring and Reporting application developed in Java technologies.

## Deploying to the server

Use the following command (inside of the cloned directory) in order to deploy this application to the server:

```bash
	export PROJECT_DIRECTORY=<stile-web cloned directory>
	export VERSION=<desired version>
	asadmin deploy --force=true --contextroot=stile --name=stile target/stile-web-${VERSION}.war
	
```