# Spring Security Authenticator

This application is built on Spring boot and uses h2 database as in memory database for storing username and passwords. It uses Spring security JWT token based authentication. The application runs on port 8080 by default.

## H2 console
H2 console can be accessed at /h2-console. credentials can be found in application.properties file.


## API Requests

### Authentication
##### 1. Authenticate User
The authenticate endpoint accepts username and password of the user and returns a valid authentication token to be used in headers of other requests.

**Endpoint:** ```/authenticate/```

**HTTP Method:** ```POST```

**Request Body**:
```
{
    "username" : "user1",
    "password" : "password1"
}
```

**Response Body**:
```
{
    "authenticationToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMyIsImV4cCI6MTYwMTIyMjIwNCwiaWF0IjoxNjAxMjA0MjA0fQ.PeOK1D7M7ClhMbkjpDqMGOq_7ri3yVVEOPVIiRDR3tG8k04o0-mIriSAwIyf_Q1NNRLrMMYBuG7U2QGNSahrUQ"
}
```


### User
##### 1. Welcome User

The welcome endpoint expects the authentication token in the headers. This endpoint is created to verify the authentication functionality.

**Endpoint:** ```/welcomeUser```

**Header:** ```{
                "Authorization" : "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMyIsImV4cCI6MTYwMTIyMjIwNCwiaWF0IjoxNjAxMjA0MjA0fQ.PeOK1D7M7ClhMbkjpDqMGOq_7ri3yVVEOPVIiRDR3tG8k04o0-mIriSAwIyf_Q1NNRLrMMYBuG7U2QGNSahrUQ"
                }```

**HTTP Method:** ```GET```


**Response Body**:
```
Welcome user3!
```

##### 2. Add New User

This endpoint adds a new user to the in memory database. It is created to ensure testing with new as well as existing users. It accepts authorization token of an existing user in the headers. 
**Endpoint:** ```/addUser```

**Header:** ```{
                "Authorization" : "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMyIsImV4cCI6MTYwMTIyMjIwNCwiaWF0IjoxNjAxMjA0MjA0fQ.PeOK1D7M7ClhMbkjpDqMGOq_7ri3yVVEOPVIiRDR3tG8k04o0-mIriSAwIyf_Q1NNRLrMMYBuG7U2QGNSahrUQ"
                }```

**HTTP Method:** ```POST```


**Request Body**:
```
{
    "username" : "user3",
    "password" : "password3"
}
```

**Response Body**:
```
Successfully added User user4!
```
