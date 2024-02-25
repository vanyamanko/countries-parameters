# Phone Number Lookup Service
Phone Number Lookup Service is a Java Maven server that accepts phone numbers or country codes as input and returns a JSON object containing information about the phone number.
## Technologies Used:
1. Java
2. Spring Boot
3. PostgreSQL
4. Maven
### Example
```
http://localhost:8080/api/v1/phone-code?country=canada
```
```json
{
"phone": "+1234567890",
"country": "Canada",
"code": 1
}
```