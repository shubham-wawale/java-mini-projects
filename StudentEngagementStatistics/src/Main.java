import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Course pymc = new Course("PYMC", "Python Masterclass");
        Course jmc = new Course("JMC", "Java Masterclass");
        Course jgames = new Course("JGMC", "Creating games in java");

        Student[] students= new Student[1000];
        Arrays.setAll(students, i-> Student.getRandomStudent(jmc, pymc));

        //NUMBER OF STUDENTS IN RESPECTIVE GENDERS
        for(String gender: List.of("M", "F", "U")) {
            var specificStudents = Arrays.stream(students)
                                            .filter(s->s.getGender().equals(gender));
            System.out.println("Number of " + gender + " students: " + specificStudents.count());
        }

        List<Predicate<Student>> conditions = List.of(
                (s)->s.getAge() < 30,
                (s)->s.getAge() >= 30 && s.getAge()<60
        );

        //NUMBER OF STUDENTS IN DIFFERENT AGE GROUPS
        for(int i=0; i<conditions.size(); i++) {
            var specificStudents = Arrays.stream(students).filter(conditions.get(i));
            System.out.println("# of Students in age" + (i==0 ? "< 30 ": ">=30 and < 60 " ) + specificStudents.count() );
        }

        //GET ENROLLED AGE STATISTICS
        var ageEnrolled = Arrays.stream(students).mapToInt(Student::getAgeEnrolled);
        System.out.println("Summary statistics for enrolled age: " + ageEnrolled.summaryStatistics());

        //CURRENT AGE STATISTICS
        var currentAge = Arrays.stream(students).mapToInt(Student::getAge);
        System.out.println("Summary statistics for current age: " + currentAge.summaryStatistics());

        //GET DISTINCT COUNTRY CODES
        Arrays.stream(students)
                .map(Student::getCountryCode)
                .distinct()
                .forEach(s->System.out.print(s + " "));

        // Number of students in within each country code
        var countryCodes = Arrays.stream(students)
                .map(Student::getCountryCode)
                .distinct()
                .toList();
        for(String code: countryCodes) {
            var count = Arrays.stream(students).filter(s->s.getCountryCode().equals(code)).count();
            System.out.println("Students in " + code + ": " + count);
        }

        //GET STUDENTS WHO HAVE BEEN ACTIVE FOR MORE THAN 7 YEARS
        var longTermStudents = Arrays.stream(students)
                .filter(s->s.getAge()-s.getAgeEnrolled() > 7 && s.getMonthsSinceActive()<12)
                .count();
        System.out.println("Students who have been active more than 7 years: " + longTermStudents);


        //PRACTICING REDUCE AND COLLECT IN STREAMS
        List<Student> students1 = IntStream
                .rangeClosed(1, 5000)
                .mapToObj(s->Student.getRandomStudent(jmc, pymc))
                .toList();

        double totalPercent = students1.stream()
                .mapToDouble(s->s.getPercentComplete("JMC"))
                .reduce(0, Double::sum);

        double avgPercent = totalPercent / students1.size();
        System.out.printf("Average percent complete: %.2f%% %n", avgPercent);

       int topPercent = (int)(1.25*avgPercent);
       Comparator<Student> longTermStudent = Comparator.comparing(Student::getYearEnrolled);

       List<Student> hardWorkers = students1.stream()
               .filter(s->s.getMonthsSinceActive("JMC")==0)
               .filter(s->s.getPercentComplete("JMC") >= topPercent)
               .sorted(longTermStudent)
               .limit(10)
               .toList();
       System.out.println("Students who 25 percent above than average = " + hardWorkers.size());

    }
}