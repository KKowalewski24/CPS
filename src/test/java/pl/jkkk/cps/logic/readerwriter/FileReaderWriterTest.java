package pl.jkkk.cps.logic.readerwriter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jkkk.cps.logic.exception.FileOperationException;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileReaderWriterTest {

    /*------------------------ FIELDS REGION ------------------------*/
    private static final String filePath = "xxx.txt";
    private FileReaderWriter<InnerTestClass> fileReaderWriter = new FileReaderWriter<>(filePath);
    private InnerTestClass innerTestClass;

    static class InnerTestClass implements Serializable {

        int number;

        public InnerTestClass(int number) {
            this.number = number;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            InnerTestClass innerTestClass = (InnerTestClass) o;

            return new EqualsBuilder()
                    .append(number, innerTestClass.number)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(number)
                    .toHashCode();
        }
    }

    /*------------------------ METHODS REGION ------------------------*/
    @BeforeEach
    void setUp() {
        innerTestClass = new InnerTestClass(5);
    }

    @Test
    void writeReadTest() throws IOException {
        fileReaderWriter.write(innerTestClass);
        assertEquals(fileReaderWriter.read(), innerTestClass);

        Files.deleteIfExists(Paths.get(filePath));
    }

    @Test
    void writeReadExceptionTest() throws IOException {
        Files.deleteIfExists(Paths.get(filePath));

        assertThrows(FileOperationException.class, () -> fileReaderWriter.read());
    }
}
