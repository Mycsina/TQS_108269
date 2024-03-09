a) 
  EmployeeRepositoryTest class in general (all tests use it)
    assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());
  EmployeeServiceUnitTest
  EmployeeRestControllerIT
  EmployeeRestControllerTemplateIT
b)
  In the unit test, we mock the EmployeeRepository
    @Mock( lenient = true)
    private EmployeeRepository employeeRepository;
c)
  @Mock - allows us to mock a service, needing to use when().thenReturn() to define the behavior of the mock
  @MockBean - allows us to mock a service/bean, telling Spring to replace the real bean with the mock and enabling integration with the Spring context
d)
  The file will be used in the integration tests if @TestPropertySource is used. It allows us to set different properties for the tests, for example using a different database.
e)
  Approach C: using @WebMvcTest we can test the API without having to start the whole application. It will only start the web layer, not the whole context.
  Approach D: using @SpringBootTest and MockMvc we can test the API with the whole context. It will start the whole application, but the API will be tested using a server-side servlet.
  Approach E: using @SpringBootTest and TestRestTemplate we can test the API with the whole context, including the database. It will start the whole application, but the API will be using an actual REST client.

  Approach C is faster than D and E, but it doesn't test the whole application, which may leave bugs undetected if they aren't in the API itself.
  Approach D is slower than C, but it tests the whole context together. It may leave issues that only arise when we try to access the API using a REST client undetected however.
  Approach E is the slowest, but it tests the whole application. It's the most realistic, while also being the slowest.