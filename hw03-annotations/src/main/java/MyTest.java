import annotations.*;

public class MyTest {
    // Подготовительные мероприятия.
    @Before
    public void setUp() {
        System.out.println("method: 'setUp'");
    }

    @Before
    @Test
    public void setUp_another() {
        System.out.println("method: 'setUp_another'");
    }

    // Тесты
    @Test
    public void FirstTest() {
        System.out.println("method: 'FirstTest'");
    }

    @Test
    public void SecondTest() {
        System.out.println("method: 'SecondTest'");

        throw new RuntimeException("I did it on purpose.");
    }

    @Test
    public void ThirdTest() {
        System.out.println("method: 'ThirdTest'");

        throw new RuntimeException("I did it on purpose.");

    }

    // Завершающие мероприятия. Метод выполнится после каждого теста
    @After
    public void tearDown() {
        System.out.println("method: 'tearDown'");
    }
}
