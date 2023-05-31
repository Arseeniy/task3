package task3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TestObject {
    private int id;
    private String title;
    private String value;
    private List<TestObject> values;
}
