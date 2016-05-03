import java.time.LocalDateTime;
import org.sql2o.*;
import java.util.List;
import java.sql.Timestamp;

import org.junit.*;
import static org.junit.Assert.*;

public class TaskTest {

  @Before
  public void setUp() {
      DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/to_do_test", null, null);
  }

  @After
  public void tearDown() {
      try(Connection con = DB.sql2o.open()) {
        String sql = "DELETE FROM tasks *;";
        con.createQuery(sql).executeUpdate();
      }
    }

  // @Rule
  // public ClearRule clearRule = new ClearRule();

  @Test
  public void task_instantiatesCorrectly_true() {
    Task myTask = new Task("Mow the lawn");
    assertEquals(true, myTask instanceof Task);
  }

  @Test
  public void task_instantiatesWithDescription_String() {
    Task myTask = new Task("Mow the lawn");
    assertEquals("Mow the lawn", myTask.getDescription());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Task.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Task firstTask = new Task("Mow the lawn");
    Task secondTask = new Task("Mow the lawn");
    assertTrue(firstTask.equals(secondTask));
  }

  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    Task myTask = new Task("Mow the lawn");
    Timestamp timestamp = Timestamp.valueOf("2007-09-23 10:10:10.0");
    myTask.setDueDate(timestamp);
    myTask.save();
    assertTrue(Task.all().get(0).equals(myTask));
  }

  @Test
  public void find_findsTaskInDatabase_true() {
    Task myTask = new Task("Mow the lawn");
    Timestamp timestamp = Timestamp.valueOf("2007-09-23 10:10:10.0");
    myTask.setDueDate(timestamp);
    myTask.save();
    Task savedTask = Task.find(myTask.getId());
    assertTrue(myTask.equals(savedTask));
  }

  @Test
  public void find_setDueDateInDatabase_true() {
    Task myTask1 = new Task("Mow the lawn");
    Task myTask2 = new Task("Walk the dog");
    Task myTask3 = new Task("clean");
    Timestamp timestamp = Timestamp.valueOf("2007-09-23 10:10:10.0");
    Timestamp timestamp2 = Timestamp.valueOf("2008-09-23 10:10:10.0");
    Timestamp timestamp3 = Timestamp.valueOf("2009-09-23 10:10:10.0");
    myTask1.setDueDate(timestamp);
    myTask2.setDueDate(timestamp2);
    myTask3.setDueDate(timestamp3);
    myTask2.save();
    myTask1.save();
    myTask3.save();
    List<Task> newList = Task.orderByDueDate();
    assertTrue(myTask1.getDescription().equals(newList.get(0).getDescription()));
  }

  // @Test
  // public void isCompleted_isFalseAfterInstantiation_false() {
  //   Task myTask = new Task("Mow the lawn");
  //   assertEquals(false, myTask.isCompleted());
  // }
  //
  // @Test
  // public void getCreatedAt_instantiatesWithCurrentTime_today() {
  //   Task myTask = new Task("Mow the lawn");
  //   assertEquals(LocalDateTime.now().getDayOfWeek(), myTask.getCreatedAt().getDayOfWeek());
  // }
  //
  // @Test
  // public void all_returnsAllInstancesOfTask_true() {
  //   Task firstTask = new Task("Mow the lawn");
  //   Task secondTask = new Task("Buy groceries");
  //   assertTrue(Task.all().contains(firstTask));
  //   assertTrue(Task.all().contains(secondTask));
  // }
  //
  // @Test
  // public void newId_tasksInstantiateWithAnID_true() {
  //   Task myTask = new Task("Mow the lawn");
  //   assertEquals(Task.all().size(), myTask.getId());
  // }
  //
  // @Test
  // public void find_returnsTaskWithSameId_secondTask() {
  //   Task firstTask = new Task("Mow the lawn");
  //   Task secondTask = new Task("Buy groceries");
  //   assertEquals(Task.find(secondTask.getId()), secondTask);
  // }
  //
  // @Test
  // public void find_returnsNullWhenNoTaskFound_null() {
  //   assertTrue(Task.find(999) == null);
  // }
  //
  // @Test
  // public void clear_emptiesAllTasksFromArrayList_0() {
  //   Task myTask = new Task("Mow the lawn");
  //   Task.clearTasksArray();
  //   assertEquals(Task.all().size(), 0);
  // }
}
