import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UsersRepositoryJdbcImpl implements UserRepository {

    private Connection connection;

    private static final String SQL_SELECT_ALL_FROM_DRIVER = "select * from driver";
    private  static final String SQL_SELECT_DRIVERS_WITH_RATING = "select * from driver where raiting = ?";
    private static final String  SQL_SELECT_DRIVERS_WITH_EXPERIENCE_GREATER_THAN = "SELECT * from driver where driving_experience > ?";
    private static final String SQL_SELECT_SEX_DRIVER = "select * from driver where sex = ?";
    private static final String SQL_SELECT_DRIVER_ID = "select * from driver where id = ?";
    private static final String SQL_SELECT_DRIVER_AGE = "select * from driver where AGE = ?";
    private static final String SQL_DELETE_DRIVER_ID = "DELETE * FROM driver where id = ?";
    private static final String SQL_INSERT = "insert into driver(first_name,last_name,age,rating,sex,driving_experience) values (?,?,?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE driver set first_name = ?,last_name = ?, rating = ?, sex = ?,driving_experience = ?, where id = ?";

    public UsersRepositoryJdbcImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<User> findDriversWithRating(Integer rating) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_DRIVERS_WITH_RATING);
            preparedStatement.setInt(1,rating);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<User> drivers = new ArrayList<>();

            while (resultSet.next()){
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("rating"), resultSet.getString("sex"), resultSet.getInt("driving_experience"));
                drivers.add(user);
            }
            return drivers;

        }
        catch(SQLException e){
            throw new IllegalStateException(e);

        }




    }

    @Override
    public List<User> findDriversWithExperienceGreaterThan(Integer age) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_DRIVERS_WITH_EXPERIENCE_GREATER_THAN);
            preparedStatement.setInt(1,age);

            List<User> drivers = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("rating"), resultSet.getString("sex"), resultSet.getInt("driving_experience"));
                drivers.add(user);
            }
            return drivers;

        }
        catch (SQLException e){
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void addUsersBatch(Integer countUsers) {
        Scanner scanner = new Scanner(System.in);

        for(int i = 0; i < countUsers; i++){
            System.out.println("Имя:");
            String firstName = scanner.nextLine();
            System.out.println("Фамилия:");
            String lastName = scanner.nextLine();
            System.out.println("Возраст:");
            int age= scanner.nextInt();
            System.out.println("Рейтинг:");
            int rating = scanner.nextInt();
            System.out.println("Пол:");
            String sex = scanner.nextLine();
            System.out.println("Опыт вождения:");
             int drivingExperience= scanner.nextInt();

            save(new User(null,firstName,lastName,age,rating,sex,drivingExperience));
        }




    }

    @Override
    public List<User> findSexDrivers(String sex) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_SEX_DRIVER);
            preparedStatement.setString(1,sex);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<User> drivers= new ArrayList<>();

            while(resultSet.next()){
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),


                        resultSet.getInt("rating"), resultSet.getString("sex"), resultSet.getInt("driving_experience"));
                drivers.add(user);
            }
            return drivers;



        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<User> findAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_FROM_DRIVER);

            List<User> result = new ArrayList<>();

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("rating"),
                        resultSet.getString("sex"),
                        resultSet.getInt("driving_experience"));

                result.add(user);
            }

            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_DRIVER_ID);
            preparedStatement.setLong(1,id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("rating"),
                        resultSet.getString("sex"),
                        resultSet.getInt("driving_experience")


                );
                return Optional.of(user);
            }
            else return Optional.empty();


        } catch (SQLException e){
            throw new IllegalStateException(e);
        }



    }

    @Override
    public void save(User user)  {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
            preparedStatement.setString(1,user.getFirstName());
            preparedStatement.setString(2,user.getLastName());
            preparedStatement.setInt(3,user.getAge());
            preparedStatement.setInt(4,user.getRating());
            preparedStatement.setString(5,user.getSex());
            preparedStatement.setInt(6,user.getDrivingExperience());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Пользователь успешно сохранён: " + user);
            } else {
                System.out.println("Не удалось сохранить пользователя: " + user);
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }


    }

    @Override
//
    public void update(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1,user.getFirstName());
            preparedStatement.setString(2,user.getLastName());
            preparedStatement.setInt(3,user.getAge());
            preparedStatement.setInt(4,user.getRating());
            preparedStatement.setString(5,user.getSex());
            preparedStatement.setInt(6,user.getDrivingExperience());
            preparedStatement.setLong(7,user.getId());

            if(preparedStatement.executeUpdate()>0){
                System.out.println("Данные о пользователе обновлены");
            }else System.out.println("Данные о пользователе не обновлены");
        }catch (SQLException e){
            throw new IllegalStateException(e);
        }

    }

    @Override
    public void remove(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_DRIVER_ID);
            preparedStatement.setLong(1,user.getId());

            if(preparedStatement.executeUpdate() > 0){
                System.out.println("Удаление успешно");
            }
            else System.out.println("Не существует пользователя с таким id");

        } catch (SQLException e){
            throw new IllegalStateException(e);
        }

    }

    @Override
    public void removeById(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_DRIVER_ID);
            preparedStatement.setLong(1,id);

            if(preparedStatement.executeUpdate() > 0){
                System.out.println("Удаление успешно");
            }
            else System.out.println("Не существует пользователя с таким id");

        } catch (SQLException e){
            throw new IllegalStateException(e);
        }

    }

    @Override
    public List<User> findAllByAge(Integer age) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_DRIVER_AGE);
            preparedStatement.setInt(1,age);

            List<User> users = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),

                        resultSet.getInt("rating"), resultSet.getString("sex"), resultSet.getInt("driving_experience"));
                 users.add(user);
            }
            return users;
        }
        catch (SQLException e){
            throw new IllegalStateException(e);
        }


    }




}
