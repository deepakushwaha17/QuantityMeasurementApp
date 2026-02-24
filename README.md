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

### 1️⃣ UC1 – Feet Measurement Equality 
 
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

### 2️⃣ UC2 – Feet and Inches Measurement Equality 

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

### 3️⃣ UC3 – Generic Quantity Class for DRY Principle

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

### 4️⃣ UC4 – Extended Unit Support

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

### 5️⃣ UC5 – Unit-to-Unit Conversion

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
