# Spring Boot Service Management System

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
- SwaggerHub Documentation: [https://app.swaggerhub.com/apis/BARSHAPAL-5db/ServiceBookingSystemManagement/1.0.0]
- Postman Collection: [https://www.postman.com/your-collection-link]

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
