public class User {
    private Long id;
    private  String firstName;
    private String lastName;
    private Integer age;
    private final int rating;
    private final String sex;
    private final int drivingExperience;


    public User(Long id, String firstName, String lastName, Integer age, int rating, String sex, int drivingExperience) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.rating = rating;
        this.sex = sex;
        this.drivingExperience = drivingExperience;
    }

    public  Long getId(){
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }


    public int getDrivingExperience() {
        return drivingExperience;
    }

    public String getSex() {
        return sex;
    }

    public int getRating() {
        return rating;
    }
}
