# interview

##Getting Started

**AWS S3, SES Authentication**

In order to mock up file system/storage and email sending service, you will need AWS access id and aws secret access key to 
make AWS SDK API call. I provide them in the source code which is not a good practice. Both id and key will expire on 3/17/2017
for security reason.

**File API**

Request           Type	                              param                     Response

GET	             /api/file	                          n/a                       Returns all available file metadata

POST	           /api/file/upload	                    file(required)            Returns uploaded file link

GET	             /api/file/search?name=&size=	        name(optional),           Returns all matched file metadata ids 

                                                      size(optional)            by given query params
                                                      
GET	             /api/file/{id}	                      id(required)              Returns file content by given file metadata id


**Scheduled Job**

You will need to modify "email.to" values in application.property file in order to receive alert email for new files.


**Sample Alert Email template**

We detect 2 new files have been added during past hour.

https://YOUR_DOMAIN/interview1.txt

https://YOUR_DOMAIN/interview2.

