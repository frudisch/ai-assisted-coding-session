# User Management API

This project provides a RESTful API for managing user core data. The API allows for the creation, editing, and deletion of user data, adhering to OpenAPI specifications.

## API Overview

The User Management API includes the following features:

- **User Properties**: Each user has the following properties:
  - ID (formatted as UUID)
  - First Name
  - Last Name
  - Birth Date

- **OAuth2 Authorization**: The API uses OAuth2 for secure access.

## File Structure

The project consists of the following files:

- `src/openapi.yaml`: Contains the OpenAPI specification for the user management API.
- `src/schemas/user.yaml`: Defines the user schema used across the API.
- `package.json`: Configuration file for npm, listing dependencies and scripts.
- `README.md`: Documentation for the project.

## Getting Started

To get started with the User Management API, follow these steps:

1. Clone the repository:
   ```
   git clone <repository-url>
   ```

2. Navigate to the project directory:
   ```
   cd openapi-user-management
   ```

3. Install dependencies:
   ```
   npm install
   ```

4. Start the API server (if applicable):
   ```
   npm start
   ```

## API Endpoints

Refer to the `src/openapi.yaml` file for detailed information about the available API endpoints, including request and response formats.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.