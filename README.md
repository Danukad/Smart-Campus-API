# Smart Campus Sensor & Room Management API

## Overview
The Smart Campus API is a robust, highly available RESTful web service designed to manage university facilities and a diverse array of IoT sensors
(e.g., Temperature, CO2 monitors, Occupancy trackers). Built entirely using the JAX-RS 
(Jakarta RESTful Web Services) specification, this API provides a seamless interface for campus facilities managers to interact with campus data. 
It features deep resource nesting for historical sensor readings, custom exception handling, API observability through logging filters, and simulates data persistence using synchronized in-memory data structures.

---

## Build & Launch Instructions
This project is built using Java 17 and Maven, and is designed to run on a servlet container like Apache Tomcat.

### Prerequisites
* Java Development Kit (JDK) 17+
* Apache Tomcat (v10 or v11)
* NetBeans IDE (or equivalent Maven-compatible IDE)

### Step-by-Step Setup
1. **Clone the Repository:** ```bash
   git clone <your-github-repo-url>

2. **Open the Project:**
   Open NetBeans IDE, select File -> Open Project, and navigate to the cloned smart-campus-api folder.

3. **Configure the Server:**
   Right-click the project, select Properties -> Run, and ensure your Apache Tomcat server is selected. Verify the Context Path is set to /smart-campus-api.

4. **Build the Application:**
   Right-click the project in the navigation pane and select Clean and Build. Wait for the BUILD SUCCESS message.

5. **Deploy and Run:**
   Right-click the project and select Run. NetBeans will start Tomcat and deploy the .war file automatically.

Verify Deployment: Navigate to http://localhost:8081/smart-campus-api/api/v1/ in your browser to see the API discovery metadata.


---


# Sample cURL Commands

## Here are sample commands to demonstrate successful interactions with the API:

---

1. **Root Discovery Endpoint (GET)**
   
   curl -X GET http://localhost:8081/smart-campus-api/api/v1/

3. **Create a New Room (POST)**
   
   curl -X POST http://localhost:8081/smart-campus-api/api/v1/rooms \
     -H "Content-Type: application/json" \
     -d "{\"id\": \"LIB-301\", \"name\": \"Library Quiet Study\", \"capacity\": 50}"

4. **Register a New Sensor to the Room (POST)**
   
   curl -X POST http://localhost:8081/smart-campus-api/api/v1/sensors \
     -H "Content-Type: application/json" \
     -d "{\"id\": \"TEMP-001\", \"type\": \"Temperature\", \"status\": \"ACTIVE\", \"roomId\": \"LIB-301\"}"
   
5. **Add a Historical Sensor Reading (POST)**
   
   curl -X POST http://localhost:8081/smart-campus-api/api/v1/sensors/TEMP-001/readings \
     -H "Content-Type: application/json" \
     -d "{\"id\": \"READ-999\", \"timestamp\": 1713900000000, \"value\": 22.5}"

6. Retrieve Sensors Filtered by Type (GET)
  curl -X GET "http://localhost:8081/smart-campus-api/api/v1/sensors?type=Temperature"

7. Attempt to Delete an Occupied Room (DELETE - Triggers 409 Conflict)
   curl -i -X DELETE http://localhost:8081/smart-campus-api/api/v1/rooms/LIB-301



## 📘 Conceptual Report

---

### 🔹 Part 1: Service Architecture & Setup

#### ❓ JAX-RS Resource Lifecycle
**Question:**  
Explain the default lifecycle of a JAX-RS Resource class. Is a new instance created per request or treated as a singleton?

**Answer:**  
By default, JAX-RS creates a **new instance of a Resource class for every incoming request**.  
This means instance variables are **not shared across requests**.

To simulate persistence and avoid data loss:
- Shared data structures (e.g., `HashMap`) must be:
  - Declared as `static`, OR  
  - Managed via a **singleton data-access layer**

This ensures all requests interact with the same in-memory data and prevents inconsistencies or race conditions.

---

#### ❓ HATEOAS in REST APIs
**Question:**  
Why is Hypermedia (HATEOAS) considered a hallmark of advanced RESTful design?

**Answer:**  
HATEOAS (Hypermedia as the Engine of Application State) allows clients to:
- Discover available actions dynamically via links in responses  
- Avoid hardcoded endpoints  

**Benefits:**
- Reduces dependency on static API documentation  
- Makes APIs more flexible and self-descriptive  
- Allows backend changes without breaking clients  

---

### 🔹 Part 2: Room Management

#### ❓ IDs vs Full Objects
**Question:**  
What are the implications of returning only IDs vs full room objects?

**Answer:**  

| Approach | Pros | Cons |
|--------|------|------|
| Only IDs | Saves bandwidth, faster responses | Requires extra API calls |
| Full Objects | Ready-to-use data for UI | Larger payload size |

---

#### ❓ DELETE Idempotency
**Question:**  
Is DELETE idempotent in your implementation?

**Answer:**  
Yes, DELETE is **idempotent**.

- First request → Deletes resource → `204 No Content`  
- Repeated requests → Resource already missing → Still `204 No Content`  

✔ Server state remains unchanged regardless of repeated calls  

---

### 🔹 Part 3: Sensor Operations & Linking

#### ❓ Unsupported Media Types
**Question:**  
What happens if a client sends data in a format different from `@Consumes(MediaType.APPLICATION_JSON)`?

**Answer:**  
JAX-RS will:
- Intercept the request before method execution  
- Reject the request  
- Return:



---

#### ❓ QueryParam vs PathParam
**Question:**  
Why use query parameters for filtering instead of path parameters?

**Answer:**  

- **Path Parameters**
  - Used for unique resources  
  - Example: `/sensors/{id}`  

- **Query Parameters**
  - Used for filtering collections  
  - Example: `/sensors?type=CO2`  

✔ Query params are more flexible and REST-compliant for searching/filtering  

---

### 🔹 Part 4: Deep Nesting with Sub-Resources

#### ❓ Sub-Resource Locator Pattern
**Question:**  
What are the benefits of using sub-resource locators?

**Answer:**  

- Promotes **single responsibility principle**  
- Improves **code organization and readability**  
- Enables **modular development**  

Instead of one large controller:
- `SensorResource` → handles sensors  
- `SensorReadingResource` → handles readings  

✔ Easier to maintain, test, and scale  

---

### 🔹 Part 5: Error Handling, Exception Mapping & Logging

#### ❓ HTTP 422 vs 404
**Question:**  
Why is HTTP 422 more accurate than 404 in some cases?

**Answer:**  

- **404 Not Found**
  - Endpoint/resource does not exist  

- **422 Unprocessable Entity**
  - Request is valid JSON  
  - But contains invalid data (e.g., non-existent Room ID)  

✔ 422 provides better semantic meaning  

---

#### ❓ Stack Trace Exposure Risk
**Question:**  
Why should stack traces not be exposed?

**Answer:**  

Exposing stack traces can reveal:
- Internal architecture  
- Framework details  
- File paths  
- Library versions  

⚠️ This information can be used for targeted attacks and exploiting known vulnerabilities (CVEs)

---

#### ❓ Logging with Filters
**Question:**  
Why use JAX-RS filters for logging?

**Answer:**  

- Centralizes logging logic  
- Avoids duplicate code  
- Keeps business logic clean  

✔ Ensures consistent logging across all requests and responses  
✔ Easier to maintain and update  

---