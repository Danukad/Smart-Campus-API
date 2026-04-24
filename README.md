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

