# Getting Started

- [x] API to upload a file with a few meta-data fields. Persist meta-data in persistence store (In memory DB or file system and store the content on a file system)
- [x] API to get file meta-data
- [x] API to download content stream (Optional)
- [x] API to search for file IDs with a search criterion (Optional)
- [x] Write a scheduler in the same app to poll for new items in the last hour and send an email (Optional)


## AWS S3, SES Authentication

In order to mock up file system/storage and email sending service, you will need AWS access id and aws secret access key to 
make AWS SDK API call. I provide them in the source code which is not a good practice. Both id and key will expire on 3/17/2017
for security reason.

## File API

| Request   | Type | Param | Response |
| ------------- | ------------- | ------------- |------------- |
| GET  | /api/file  | n/a  |Returns all available file metadata |
| POST |  /api/file/upload	  | file(required)  |  Returns uploaded file link|
| GET |  /api/file/search?name=&size=	 | name(optional), size(optional)    | Returns all matched file metadata ids by given query params|
| GET |  /api/file/{id}	 | id(required)  | Returns file content by given file metadata id|
          	                                                   
## Scheduled Job

You will need to modify "email.to" values in application.property file in order to receive alert email for new files.


## Sample Alert Email template

We detect 2 new files have been added during past hour.

https://YOUR_DOMAIN/interview1.txt

https://YOUR_DOMAIN/interview2

