import java.time.LocalDate;
import java.util.List;
import static java.util.stream.Collectors.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamToMap {
    public static void main(String[] args) {
        Course pymc = new Course("PYMC", "Python Masterclass", 50);
        Course jmc = new Course("JMC", "Java Masterclass", 100);
        Course jgames = new Course("JGMC", "Creating games in java", 40);

        List<Student> students = Stream
                .generate(()-> Student.getRandomStudent(jmc, pymc, jgames))
                .filter(s->s.getAgeEnrolled() >= LocalDate.now().getYear()-4)
                .limit(10000)
                .toList();

        System.out.println(students.subList(1,20));
        // NUMBER OF STUDENTS IN EACH COURSE
//        var studentsInCourseMap = students.stream()
//                .flatMap(s->s.getEngagementMap().values().stream())
//                .collect(groupingBy(CourseEngagement::getCourseCode, counting()));
//        studentsInCourseMap.forEach((k,v)->System.out.println(k + ": " + v));

//        var mappedByCountry = students.stream()
//                .collect(Collectors.groupingBy(Student::getCountryCode));
//
//        mappedByCountry.forEach((k,v)->System.out.println(k+ ": " + v.size()));
//
//        var mappedByCountryWithYounger = students.stream()
//                .collect(Collectors.groupingBy(Student::getCountryCode,
//                        Collectors.filtering(s->s.getAge() <= 25, Collectors.toList())));
//
//        mappedByCountryWithYounger.forEach((k,v)->System.out.println(k+ ": " + v.size()));
//
//        var experiencedStudents = students.stream()
//                .collect(Collectors.partitioningBy(Student::hasProgrammingExperience, Collectors.counting()));
//        System.out.println("Experienced Students: " + experiencedStudents.get(true));
    }
}
