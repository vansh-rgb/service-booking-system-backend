# Service Management System

## Team Details
| Name | BITS ID | Batch |
|------|------------|-------|
| Anurag Rawat | 2023SL93064 | 2023 2Y |
| Barsha Pal | 2023SL93076 | 2023 2Y |
| Isha Mishra | 2023SL93078 | 2023 2Y |
| Shivani Pandey | 2023SL93037 | 2023 2Y |
| Vansh Chaudhary | 2023SL93069 | 2023 2Y |

## Project Description
### Overview
A Spring Boot-based service booking platform that enables service providers to advertise their services and allows clients to discover, book, and review these services. The platform facilitates seamless interactions between service companies and clients through a RESTful API architecture.


## Logical WorkFlow

![image](https://github.com/user-attachments/assets/a4897e01-601e-4146-a897-476b72e265ed)

### Key Features

#### Authentication & User Management
- Two types of users: Clients and Companies
- Secure registration system with email validation
- JWT-based authentication
- Duplicate email prevention

#### Client Features
- Browse service advertisements with pagination
- Search for specific services by name
- View detailed service information
- Book services from companies
- Track booking history
- Submit service reviews

#### Company Features
- Create and manage service advertisements
- Media upload support for advertisements
- View and manage company listings
- Track received bookings
- Update booking statuses (accept/reject/complete)

### Technical Implementation
- Built with Spring Boot framework
- RESTful API architecture
- Pagination support for list endpoints
- File upload capabilities
- DTO pattern implementation
- Custom error handling with specific error codes
- JWT authentication
- Proper HTTP status code implementation

The platform serves as a marketplace where service companies can advertise their services, and clients can easily discover, book, and review these services. It's designed to handle the complete service booking lifecycle from listing to review submission.

## API Documentation
- SwaggerHub Documentation: [https://app.swaggerhub.com/apis-docs/BARSHAPAL-5db/ServiceBookingSystemManagement/1.0.0]
- Postman Collection: [https://www.postman.com/your-collection-link]

## Detailed Overview
https://sap-my.sharepoint.com/:w:/r/personal/shivani_pandey03_sap_com/_layouts/15/Doc.aspx?sourcedoc=%7B420F0F30-6217-4425-BF8E-22AD21C727A0%7D&file=WebServiceBooking.docx&wdLOR=cB2AD752B-AB19-7B46-906A-0A292380CF97&fromShare=true&action=default&mobileredirect=true

## Pagination Strategy

### Core Elements
- Default: 10 items per page, sorted by ID ascending
- Uses Spring Boot's `Pageable` interface and `@PageableDefault` annotation
- Returns `Page<T>` with data and metadata

### Current Implementation
- Applied to general advertisement listing and search results

### Technical Details
- Automatic URL parameter conversion to `Pageable`
- Response includes content, metadata, and navigation info
- Fixed sort by ID ascending

### Reliability Features
- Automatic parameter validation
- Consistent performance across varying data sizes
- Seamless integration with existing data retrieval methods
- Scalable design supporting future data growth

## Rate Limiting Strategy
### Core Elements
- Implements `Filter` interface for request interception
- Uses `ConcurrentHashMap` to track request counts per IP address
- Limits requests to 50 per minute per IP address

### Current Implementation
- Applied globally to all incoming HTTP requests

### Technical Details
- Utilizes `AtomicInteger` for thread-safe request counting
- Automatically initializes count for new IP addresses
- Increments request count for each incoming request
- Rejects requests exceeding the limit with 503 status code

### Reliability Features
- Thread-safe implementation using concurrent data structures
- Handles high concurrency with minimal performance impact
- Easily configurable request limit
- Scalable design supporting large number of unique IP addresses

## Error Codes and Messages

| HTTP Status | Error Code | Message | Description |
|------------|------------|---------|-------------|
| 400 | BAD_REQUEST | Invalid page number | Page number cannot be less than zero |
| 400 | BAD_REQUEST | Invalid page size | Page size must be between 1 and 100 |
| 400 | BAD_REQUEST | Invalid sort parameter | Sort parameter format should be property,(asc|desc) |
| 429 | TOO_MANY_REQUESTS | Rate limit exceeded | API rate limit has been exceeded |
| 500 | INTERNAL_SERVER_ERROR | Internal server error | Unexpected server error occurred |
| 404 | NOT_FOUND | Resource not found | The requested resource was not found |
| 403 | FORBIDDEN | Access denied | User doesn't have permission to access the resource |

## Custom Error Codes and Messages
| Code | Message                                                   | Description                                          |
|------|-----------------------------------------------------------|------------------------------------------------------|
| 1004 | No ads found for service name!                            | No advertisements found for the specified service    |
| 2100 | Client signed up successfully!                            | Successful client registration                       |
| 2101 | Company signed up successfully!                           | Successful company registration                      |
| 2200 | Logged in successfully!                                   | Successful user login                                |
| 2102 | Authentication successful!                                | User authentication succeeded                        |
| 2103 | Ad posted successfully!                                   | New advertisement was posted                         |
| 2104 | Ad updated successfully!                                  | Existing advertisement was updated                   |
| 2105 | Ad deleted successfully!                                  | Advertisement was removed                            |
| 2106 | Booking status updated successfully!                      | Booking status change succeeded                      |
| 3101 | Company already exists with this Email!                   | Attempt to register a company with an existing email |
| 3102 | Client already exists with this Email!                    | Attempt to register a client with an existing email  |
| 3102 | Invalid username or password                              | Login attempt with incorrect credentials             |
| 3103 | Failed to post ad                                         | Unable to create new advertisement                   |
| 3104 | Ad not found                                              | Requested advertisement does not exist               |
| 3105 | Failed to update ad                                       | Unable to modify existing advertisement              |
| 3106 | Failed to delete ad                                       | Unable to remove advertisement                       |
| 3107 | Booking status updated successfully                       | Booking status change succeeded                      |
| 3108 | Booking successful.                                       | Client booking was completed successfully            |
| 3109 | User not found.                                           | Requested client does not exist                      |
| 3110 | Booking conflict: The ad is already booked for this date. | Attempt to book an ad on an unavailable date         |
| 4000 | User not found                                            | Requested user does not exist                        |
| 5000 | Internal server error                                     | Unexpected server-side error occurred                |