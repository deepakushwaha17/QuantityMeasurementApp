# 📏 Quantity Measurement App

## 📝 Overview

The Quantity Measurement App is a full-stack application designed to handle and compare different quantities such as length, weight, and volume. The app provides:
 - Comparison of quantities in different units.
 - Unit conversion from one unit to another.
 - Arithmetic operations on quantities (planned for future versions).

The application is developed incrementally, starting with simple functionalities and progressively adding complexity, following best practices to keep the app maintainable and focused.

## 🎯 Goals
- Compare quantities of the same type across different units.
- Perform unit conversions.
- Enable arithmetic operations (add, subtract, multiply, divide).
- Follow object-oriented design, precision, and robust testing.

## 🛠️ Technologies Used

- Programming Language: Java
- Testing Framework: JUnit
- Design Principles: Encapsulation, Type Safety, DRY (to be improved)

## 📂 Use Cases (UCs Completed)

### UC1 – Feet Measurement Equality 
 
 **Description:** <br>
 Checks equality between two numeric values in feet.
 
 **What We Did:**
 - Created a Feet class with immutable value.
 - Overrode equals() for value-based comparison.
 - Handled null checks, type safety, and floating-point comparisons.
 - Added unit tests for same/different values, nulls, and reflexive property.
 
 **Example:**
 | Input 1 | Input 2 | Output  |
 | ------- | ------- | ------- |
 | 1.0 ft  | 1.0 ft  | ✅ true  |
 | 2.0 ft  | 3.0 ft  | ❌ false |
 
 **Concepts Learned:**
 - Object equality (equals)
 - Floating-point precision
 - Null and type safety
 - Unit testing best practices 

### UC2 – Feet and Inches Measurement Equality 

**Description:** <br>
Extends UC1 to include inches. Feet and inches are compared separately.

**What We Did:**
- Created Inches class similar to Feet.
- Overrode equals() to check equality within the same unit.
- Unit tests added for same/different values, nulls, reflexivity.

**Example:**
| Measurement | Input 1 | Input 2 | Output |
| ----------- | ------- | ------- | ------ |
| Inches      | 1.0     | 1.0     | ✅ true |
| Feet        | 1.0     | 1.0     | ✅ true |

**Concepts Learned:**
- DRY violation noticed (duplicated logic between Feet & Inches).
- Same UC1 concepts applied to inches.

### UC3 – Generic Quantity Class for DRY Principle

**Description:** <br>
Refactors Feet & Inches into a generic QuantityLength class with a LengthUnit enum to eliminate code duplication.

**What We Did:**
- Implemented QuantityLength class with value + unit type.
- Enum LengthUnit contains conversion factors for feet, inches.
- Overrode equals() to compare across units (1 ft = 12 in).
- Preserved all previous UC1 & UC2 functionality.
- Added tests for cross-unit equality, same-unit equality, nulls, and invalid units.

**Example:**
| Input 1               | Input 2                  | Output |
| --------------------- | ------------------------ | ------ |
| Quantity(1.0, "feet") | Quantity(12.0, "inches") | ✅ true |
| Quantity(1.0, "inch") | Quantity(1.0, "inch")    | ✅ true |

**Concepts Learned:**
- DRY principle
- Enum usage for type-safe units
- Encapsulation & single responsibility
- Cross-unit equality testing

### UC4 – Extended Unit Support

**Description:** <br>
Adds yards and centimeters to QuantityLength, demonstrating scalability of the generic design.

**What We Did:**
- Extended LengthUnit enum with YARDS (1 yd = 3 ft) and CENTIMETERS (1 cm = 0.393701 in).
- Existing equals() and conversion logic worked without modifying QuantityLength.
- Added unit tests for yard-yard, yard-feet, yard-inch, cm-cm, and cross-unit equality.

**Example:**
| Input 1                    | Input 2                    | Output |
| -------------------------- | -------------------------- | ------ |
| Quantity(1.0, YARDS)       | Quantity(3.0, FEET)        | ✅ true |
| Quantity(1.0, CENTIMETERS) | Quantity(0.393701, INCHES) | ✅ true |

**Concepts Learned:**
- Scalability of generic design
- Conversion factor management
- Multi-unit comparison & transitive property
- Enum extensibility & backward compatibility

### UC5 – Unit-to-Unit Conversion

**Description:** <br>
Exposes conversion operations between units instead of just equality.

**What We Did:**
- Added convert(value, sourceUnit, targetUnit) method to QuantityLength.
- Input validation for numeric values and supported units.
- Conversion logic normalizes to base unit then converts to target unit.
- Added overloaded demonstrateLengthConversion() for flexible usage.

**Example Usage:**

| Method Call                       | Output   |
| --------------------------------- | -------- |
| convert(1.0, FEET, INCHES)        | 12.0     |
| convert(3.0, YARDS, FEET)         | 9.0      |
| convert(1.0, CENTIMETERS, INCHES) | 0.393701 |
| convert(0.0, FEET, INCHES)        | 0.0      |

**Concepts Learned:**
- Base unit normalization
- Enum integration for conversion factors
- Polymorphism (Method Overloading)

### UC6 – Addition of Two Length Units

**Description:**<br>
Implements addition of two length quantities with automatic unit handling.

**What We Did:**
- Implemented addition of two Length objects
- Used base unit (inches) for internal calculation
- Converted both operands to base unit before addition
- Converted result back to the first operand’s unit
- Maintained immutability by returning a new object

**Example Usage:**

| Method Call      | Output |
| ---------------- | ------ |
| add(1 ft, 12 in) | 2 ft   |
| add(2 yd, 36 in) | 3 yd   |

**Concepts Learned:**
-Unit normalization
- Immutability
- Object-oriented design

### UC7 – Addition with Explicit Target Unit

**Description:**<br>
Allows addition of quantities with a specified target unit for output.

**What We Did:**
- Added method add(length1, length2, targetUnit)
- Converted both operands to base unit
- Converted result into desired target unit
- Improved flexibility of arithmetic operations

**Example Usage:**

| Method Call              | Output |
| ------------------------ | ------ |
| add(1 ft, 12 in, INCHES) | 24 in  |
| add(2 yd, 1 ft, FEET)    | 7 ft   |

**Concepts Learned:**
- Method overloading
- Flexible API design
- Unit conversion abstraction

### UC8 – Refactoring Unit Enum

**Description:**<br>
Improves code structure by separating unit logic into an enum.

**What We Did:**
- Extracted LengthUnit into a standalone enum
- Moved conversion logic from class to enum
- Applied Single Responsibility Principle
- Improved separation of concerns

**Example Usage:**

| Operation                   | Result |
| --------------------------- | ------ |
| LengthUnit.FEET.toInches(1) | 12     |
| LengthUnit.YARDS.toFeet(1)  | 3      |

**Concepts Learned:**
- Enum-based design
- Single Responsibility Principle (SRP)
- Clean code practices

### UC9 – Weight Measurement Support

**Description:**<br>
Introduces weight measurement with proper unit handling and validation.

**What We Did:**
- Added new category: Weight
- Created WeightUnit enum (kg, g, lb)
- Implemented equality, conversion, and addition
- Prevented comparison between different categories

**Example Usage:**

| Method Call        | Output  |
| ------------------ | ------- |
| add(1 kg, 1000 g)  | 2 kg    |
| compare(1 kg, 1 m) | Invalid |

**Concepts Learned:**
- Type safety
- Domain modeling
- Validation logic

### UC10 – Generic Quantity with Unit Interface

**Description:**<br>
Creates a generic and reusable measurement system using interfaces.

**What We Did:**
- Introduced IMeasurable interface
- Created generic Quantity class
- Replaced separate classes with one generic solution
- Ensured type safety across categories
- Applied DRY principle

**Example Usage:**

| Operation             | Output |
| --------------------- | ------ |
| new Quantity(1, FEET) | Valid  |
| new Quantity(1, KG)   | Valid  |

**Concepts Learned:**
- Generics in Java
- Interface-based design
- DRY (Don’t Repeat Yourself)

### UC11 – Volume Measurement

**Description:**<br>
Adds volume measurement support using the generic system.

**What We Did:**
- Added Volume category
- Created VolumeUnit enum (L, mL, gallon)
- Supported equality, conversion, and addition
- No changes required in generic logic

**Example Usage:**

| Method Call          | Output  |
| -------------------- | ------- |
| add(1 L, 1000 mL)    | 2 L     |
| convert(1 gallon, L) | 3.785 L |

**Concepts Learned:**
- Extensibility
- Reusability of generic code
- Open/Closed Principle

### UC12 – Subtraction and Division

**Description:**<br>
Extends arithmetic capabilities to subtraction and division.

**What We Did:**
- Added subtraction and division methods
- Supported cross-unit arithmetic within same category
- Maintained immutability and consistency

**Example Usage:**

| Method Call           | Output |
| --------------------- | ------ |
| subtract(2 ft, 12 in) | 1 ft   |
| divide(2 ft, 2)       | 1 ft   |

**Concepts Learned:**
- Arithmetic abstraction
- Code consistency
- Immutable operations

### UC13 – Centralized Arithmetic Logic

**Description:**<br>
Refactors arithmetic operations into a shared logic block.

**What We Did:**
- Centralized arithmetic logic into common method
- Removed duplicate code from operations
- Improved maintainability and readability

**Example Usage:**

| Operation         | Benefit               |
| ----------------- | --------------------- |
| add(), subtract() | Uses shared logic     |
| division()        | Reuses same structure |

**Concepts Learned:**
- Code reusability
- Refactoring techniques
- Maintainability

### UC14 – Temperature Measurement

**Description:**<br>
Adds temperature support with restricted operations.

**What We Did:**
- Added Temperature category
- Created units (Celsius, Fahrenheit, Kelvin)
- Supported only conversion and comparison
- Restricted invalid arithmetic operations

**Example Usage:**

| Method Call     | Output  |
| --------------- | ------- |
| convert(0°C, F) | 32°F    |
| add(30°C, 20°C) | Invalid |

**Concepts Learned:**
- Domain constraints
- Validation rules
- Specialized logic handling

### UC15 – N-Tier Architecture Refactoring

**Description:**<br>
Improves project structure using layered architecture.

**What We Did:**
- Introduced Controller, Service, Repository layers
- Applied SOLID principles
- Improved scalability and separation of concerns
  
**Example Usage:**

| Layer      | Responsibility |
| ---------- | -------------- |
| Controller | API handling   |
| Service    | Business logic |
| Repository | Data access    |

**Concepts Learned:**
- N-tier architecture
- SOLID principles
- Scalable design

### UC16 – Database Integration (JDBC)

**Description:**<br>
Adds persistent storage using JDBC.

**What We Did:**
- Integrated database with JDBC
- Replaced in-memory storage
- Implemented CRUD operations
- Enabled data persistence
  
**Example Usage:**

| Operation | Result            |
| --------- | ----------------- |
| save()    | Data stored in DB |
| fetch()   | Data retrieved    |

**Concepts Learned:**
- JDBC connectivity
- CRUD operations
- Persistence layer

### UC17 – Spring Boot Integration

**Description:**<br>
Converts the project into a RESTful Spring Boot application.

**What We Did:**
- Migrated project to Spring Boot
- Exposed REST APIs (GET, POST, PUT, DELETE)
- Integrated Spring Data JPA
- Added Swagger and Actuator
  
**Example Usage:**

| API              | Description |
| ---------------- | ----------- |
| GET /quantities  | Fetch data  |
| POST /quantities | Add data    |

**Concepts Learned:**
- REST API design
- Spring Boot framework
- JPA integration

### UC18 – Spring Security with OAuth2 & JWT

**Description:**<br>
Implements secure authentication and authorization.

**What We Did:**
- Added Spring Security
- Implemented JWT-based authentication
- Integrated OAuth2 login
- Secured APIs with role-based access
  
**Example Usage:**

| Feature           | Result         |
| ----------------- | -------------- |
| JWT Login         | Secure token   |
| OAuth2 Login      | Google login   |
| Role-based access | API protection |

**Concepts Learned:**
- Authentication & Authorization
- JWT tokens
- OAuth2 integration
- API security
