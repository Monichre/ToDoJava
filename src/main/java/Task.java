import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Task {

  private String description;
  private int id;

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
