# Employees Inc.

## Tasks

* Add email field to Employee
  * email should be unique among employees
  * valid email
* Implement a restful API to:
  * Get all employees using pagination (default 10 items)
    * ability to sort using last name
    * ability to filter using first name and/or last name (partial search)
  * insert employees
    * implement correct validations 
  * get employee by id
  * update employee
    * update first name and/or last name (partial update)
  * delete employee by id
* Create a docker compose file inside `docker` folder to start a postgres container
  so the application running uses that postgres instance


Handle errors correctly. Return http codes according to the operation and errors.
For example, 404 when a resource is not found or 201 when a resource was created

API should always return json.

Keep using Spring Data JPA.