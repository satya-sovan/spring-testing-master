package example.person;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@DataJpaTest
public class PersonRepositoryIntegrationTest {

    @Autowired
    private PersonRepository subject;

    @AfterEach
    public void tearDown() throws Exception {
        subject.deleteAll();
    }

    @Test
    public void shouldSaveAndFetchPerson() throws Exception {
        var peter = new Person("Peter", "Pan");
        subject.save(peter);

        var maybePeter = subject.findByLastName("Pan");

        assertThat(maybePeter, is(Optional.of(peter)));
    }

    @Test
    public void testEqualsAndHashCode() {
        Person person1 = new Person("John", "Doe");
        Person person2 = new Person("John", "Doe");
        Person person3 = new Person("Jane", "Doe");

        // Since IDs are not set manually, they should be equal if firstName and lastName are equal.
        assertEquals(person1, person2);
        assertNotEquals(person1, person3);

        // Ensure hashCode is consistent with equals
        assertEquals(person1.hashCode(), person2.hashCode());
        assertNotEquals(person1.hashCode(), person3.hashCode());
    }

    @Test
    public void testToString() {
        Person person = new Person("John", "Doe");
        // ID will not be set, so it should default to '0'.
        String expectedString = "Person{id='0', firstName='John', lastName='Doe'}";
        assertEquals(expectedString, person.toString());
    }


}