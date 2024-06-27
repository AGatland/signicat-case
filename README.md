# File Zipper REST API

## Task

This project is a single REST API endpoint that allows users to upload multiple files with a single request, archive them and return a single zip file. Store upload statistics: IP address and usage count per day in the database. Save your time and do not implement a fully working SaaS solution. We want to evaluate your class design skills and Java knowledge. 

## Requirements

- Use Java as a server-side programming language
- Use a modern Java framework to complete this task. (Spring or Quarkus would work just fine)
- There is no need for a GUI. All interactions should be available through an API
- Max file upload size is 1 MB
- Code must be covered by tests - We use JUnit, but you can use any other testing framework sufficient for this task
- Provide a solution using Gitlab, GitHub or other source forge - It would be great to find the whole commit history, not only a single entry

## Technologies Used

- Java
- Spring Boot
- PostgreSQL
- Testing:
  - H2 Database
  - JUnit
  - MockMvc

## Class Structure

- **Controller**:
  - `FileController`: REST controller for endpoint
- **Service**:
  - `FileService`: Processes files for zipping and handles statistics
- **Utility**:
  - `FileValidator`: Validates uploaded files
  - `FileArchiver`: Archives files into a zip file
- **Model**:
  - `UsageStatistic`: Entity for storing upload statistics
- **Repository**:
  - `UsageStatisticRepository`: Repository for `UsageStatistic`
- **Exception**:
  - `FileUploadExceptionAdvice`: Only handles File Size exceptions atm
  - `FileValidationException`: Custom exception for file validation errors

## Testing

The project has a test coverage of 80%. The framework of the testing is JUnit, mock requests are sent using MockMvc while the database is a non production db only for testing using H2. With this setup, i can test the whole process of the api endpoint from start to finish with varying requests.

## Design Choices

### Adaptability to Changes

1. **Adding Multiple Archiving Methods (7z, etc.)**:
   - **Design Choice**: The `FileArchiver` class can either be extended to support multiple archiving formats, or new classes can be created for new archiving formats.
   - **Implementation**: Create an interface for `Archiver` and implement this interface for different formats (e.g., `zip` and `7z`). Or for a smaller application, add new methods to the existing FileArchiver. Which format is wished for can be included in the request sent to the API, or new endpoints for each format can be created.

2. **Handling Increased Request Count**:
   - **Simple Solution**: Increase number instances and load balance the incoming requests accross the instances. 
   - **Problems**: Might not be the most efficient solution which could lead to some resources being underutilized.

3. **Allowing 1GB Max File Size**:
   - **Simple Solution**: Increase the allowed amount in the application properties.
   - **Porblems**: Larger files take more processing to be archived. With the current approach with MultipartFile the files are just read into memory, this would lead to needing more memory being needed in the process as well. This could lead to out-of-memory errors.

Adding multiple archiving methods is a relatively simple task, while scaling up is where the problems arise. When scaling up, there will be an increase in both processing power and memory needed to handle all the requests. Ways to handle the increase in file size and request count could be:

1. **Asynchronous Processing**
   - Can offload processes to background tasks, which frees up the main server threads and keeps the API responsive.
2. **Streaming Uploads**
   - Streams the files as it is being processed, which avoids loading the whole files into memory at once, freeing up memory.
3. **Message Queues**
   - Decouples the tasks which allows for easy horizontal scaling by load balancing tasks.
4. **Chunked Uploads**
   - Uploading chunks of a file at the time minimizes memory usage by handling smaller chunks, and upload can be resumed more easily if interrupted.

In a real production environment, a combination of the above approaches would likely be used to maximize effieciency, scalability and reliability.