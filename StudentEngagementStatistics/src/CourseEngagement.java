import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class CourseEngagement {
    public final Course course;
    public final LocalDate enrollmentDate;
    public String engagementType;
    public int lastLecture;
    public LocalDate lastActivityDate;

    public CourseEngagement(Course course, LocalDate enrollmentDate, String engagementType) {
        this.course = course;
        this.enrollmentDate = this.lastActivityDate =  enrollmentDate;
        this.engagementType = engagementType;
    }

    public String getCourseCode() {
        return course.courseCode();
    }

    public int getEnrollmentYear() {
        return enrollmentDate.getYear();
    }

    public String getEngagementType() {
        return engagementType;
    }

    public int getLastLecture() {
        return lastLecture;
    }

    public int getLastActivityYear() {
        return lastActivityDate.getYear();
    }

    public String getLastActivityMonth() {
        return "%tb".formatted(lastActivityDate);
    }

    public double getPercentComplete() {
        return (lastLecture * 100.00/course.lectureCount());
    }

    public int getMonthsSinceActive() {
        LocalDate now = LocalDate.now();
        return (int) Period.between(lastActivityDate, now).toTotalMonths();
    }

    void watchLecture(int lectureNumber, LocalDate currentDate) {
        lastLecture = Math.max(lastLecture, lectureNumber);
        lastActivityDate = currentDate;
        engagementType = "Lecture " + lastLecture;
    }

    @Override
    public String toString() {
        return "%s: %s %d %s [%d]".formatted(course.courseCode(),
                getLastActivityMonth(),
                getLastActivityYear(),
                engagementType,
                getMonthsSinceActive());
    }
}
