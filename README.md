# CMP
A Control and Monitoring of Production application developed in Java technologies.

# Deploying to Server

Use the following command (inside of the cloned directory) in order to deploy this application to the server:

	PROJECT_DIRECTORY=<cmp-web cloned directory>
	VERSION=<desired version>
	asadmin deploy --force=true --contextroot cmp --name cmp cmp-web-$(VERSION).war