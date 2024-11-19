import java.sql.SQLException;
import java.util.List;

public interface UserRepository extends CrudRepository<User>{
    List<User> findSexDrivers(String sex);

    List<User> findAllByAge(Integer age);


    List<User> findDriversWithRating(Integer rating);


    List<User> findDriversWithExperienceGreaterThan(Integer age);

    void addUsersBatch(Integer countUsers);



}
