import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;
import java.sql.Timestamp;

public class Task {

  private String description;
  private int id;
  private Timestamp due_date;

  @Override
  public boolean equals(Object otherTask) {
    if (!(otherTask instanceof Task)) {
      return false;
    } else {
      Task newTask = (Task) otherTask;
      return this.getDescription().equals(newTask.getDescription()) && this.getId() == newTask.getId();
    }
  }

  public Task(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public int getId(){
    return id;
  }

  public static List<Task> all() {
    String sql = "SELECT id, description FROM tasks";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Task.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tasks (description, due_date) VALUES (:description, :due_date)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", this.description)
        .addParameter("due_date", this.due_date)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Task> orderByDueDate() {
    String sql = "SELECT description, due_date FROM tasks ORDER BY due_date";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Task.class);
    }
  }

  public static Task find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks where id=:id";
      Task task = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Task.class);
      return task;
    }
  }

  public void setDueDate(Timestamp date) {
    this.due_date = date;
  }

  public Timestamp getDueDate() {
    return due_date;
  }



  // public void completeTask(){
  //   mCompleted = true;
  // }
  //
  // public boolean isCompleted() {
  //   return mCompleted;
  // }
  //
  // public LocalDateTime getCreatedAt() {
  //   return mCreatedAt;
  // }
  //
  // public static ArrayList<Task> all() {
  //   return instances ;
  // }
  //
  //
  //
  // public static Task find(int id){
  //   try{
  //     return instances.get(id - 1);
  //   } catch (IndexOutOfBoundsException e){
  //     return null;
  //   }
  // }
  //
  // public static void clearTasksArray() {
  //
  //   //METHOD BELOW IS A BUILT IN METHOD
  //   instances.clear();
  // }

}
