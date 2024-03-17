# Phone Number Lookup Service
Phone Number Lookup Service is a Java Maven server that accepts phone numbers or country codes as input and returns a JSON object containing information about the phone number.
## Technologies Used:
1. Java
2. Spring Boot
3. PostgreSQL
4. Maven
5. JPA (Java Persistence API)

## Relationship between Tables
The project includes the following relationships between tables:
#### Many-to-Many Relationship: TimeZone and Country
The Country table has a many-to-many relationship with the TimeZone table.
A country can have multiple time zones, and a time zone can be associated with multiple countries.
#### One-to-Many Relationship: Country and Region
The Country table has a one-to-many relationship with the Region table.
A country belongs to a specific region, and a region can contain multiple countries.

### Example
```
http://localhost:8080/api/v1/phone-code?country=canada
```
```json
[
  {
    "id": 1,
    "countryShortName": "CA",
    "country": "Canada",
    "code": 1,
    "region": "Nouth America",
    "timeZones": [
      "UTC-4",
      "UTC-5",
      "UTC-6",
      "UTC-7",
      "UTC-8",
      "UTC-9"
    ]
  }
]
```