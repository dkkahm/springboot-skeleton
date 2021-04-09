### Token Validation
- Interceptor (com.example.homegym.interceptor.CheckTokenInterceptor)
- WebMvcConfigurer (com.example.homegym.configuration.WebMvcConfig)

### Default Language
- Interceptor (com.example.homegym.interceptor.LanguageInterceptor)
- WebMvcConfigurer

### Global Excepion Handler
- ExceptionAdvice (com.example.homegym.advice.ExceptionAdvice)
- with validationException (MethodArgumentNotValidException)

### Profile
- default profile is local
- main/resources/application.properties has default settings
- main/resources/application-local.properties has local specific settings
- test/resources/application-test.properteis has test specific settings
- TestClasses are annotated with @ActiveProfiles("test")
- Bootstrap Classes are annotated with @Profile({"local", "test"})
- for staging and production, deploy application.properties with jar
- for jenkis use 'Invoke top-level Maven targets / advanced / Properties
```
spring.profiles.active=test
spring.datasource.url=...
spring.datasource.username=...
spring.datasource.password=...
spring.jpa.hibernate.ddl-auto=...
```

### Database
- local
  - service
    - for manual functionality checking
    - update
  - service_test
    - for automative test
    - create-drop
- jenkins
  - service_test
    - for automative test
    - create-drop
- staging
  - service
  - update
- production
  - service
  - validate

