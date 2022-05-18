package attendancechecker;

import attendancechecker.attendance.AttendanceList;
import attendancechecker.attendance.Student;
import attendancechecker.attendance.Lecture;

import attendancechecker.utils.Pair;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Grader {

    public long logsToMinutes(String startLog, String endLog) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        long minutes = 0;

        try {
            Date startTime = formatter.parse(startLog);
            Date endTime = formatter.parse(endLog);

            minutes = (endTime.getTime() - startTime.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return minutes;
    }

    public Map<String, Map<String, Double>> gradeSimple(AttendanceList attendanceList, String attendanceDirPath) {
        // TODO Problem 1-1
        Map<String, Map<String, Double>> attendance = new HashMap<>();
        List<Student> students = attendanceList.students;
        List<Lecture> lectures = attendanceList.lectures;
        for (Student student : students) {
            Map<String, Double> studentAttendance = new HashMap<>();

            for (Lecture lecture : lectures) { // 리스트에 저장된 순서대로 넣으면 되는지?
                String logPath = attendanceDirPath + "/" + student.id + "/" + lecture.id + "/" + "log0.txt";
                File logFile = new File(logPath);
                long time = 0;
                List<String> logList = new LinkedList<>();

                try {
                    Scanner input = new Scanner(logFile);
                    while (input.hasNext()) {
                        logList.add(input.nextLine());
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < logList.size() / 4; i++) {
                    time += logsToMinutes(logList.get(4 * i + 1), logList.get(4 * i + 3));
                }

                long lectureTime = logsToMinutes(lecture.lectureStart, lecture.lectureEnd);
                double atten = 0.0;
                if (lectureTime * 0.1 <= time && lectureTime * 0.7 > time) {
                    atten = 0.5;
                } else if (lectureTime * 0.7 <= time) {
                    atten = 1.0;
                }
                studentAttendance.put(lecture.id, atten);
            }

            attendance.put(student.id, studentAttendance);
        }

        return attendance;
    }


    public Map<String, Map<String, Double>> gradeRobust(AttendanceList attendanceList, String attendanceDirPath) {
        // TODO Problem 1-2
        Map<String, Map<String, Double>> attendance = new HashMap<>();
        List<Student> students = attendanceList.students;
        List<Lecture> lectures = attendanceList.lectures;
        for (Student student : students) {
            Map<String, Double> studentAttendance = new HashMap<>();

            for (Lecture lecture : lectures) { // 리스트에 저장된 순서대로 넣으면 되는지?
                String dirPath = attendanceDirPath + "/" + student.id + "/" + lecture.id;
                File dir = new File(dirPath);
                File[] files = dir.listFiles();
                long time = 0;

                List<Pair> logs = new LinkedList<>();

                // excused absence 확인
                // file 별로 log pair 만들어서 넣기 <- early entrance 처리 (시간 반전되면 LogsToMinutes 음수나옴)
                // 모든 file에 대해 pair 다 넣었으면 정렬 후 시간 계산

                assert files != null;
                for (File file : files) {
                    List<String> logList = new LinkedList<>();

                    try {
                        Scanner input = new Scanner(file);
                        while (input.hasNext()) {
                            logList.add(input.nextLine()); // 여기서 excused absence 확인
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    // log pair에 추가
                }


//                List<String> logList = new LinkedList<>();
//
//                try {
//                    Scanner input = new Scanner(logFile);
//                    while (input.hasNext()) {
//                        logList.add(input.nextLine()); // 여기서 excused absence 확인
//                    }
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//
//                for(int i=0; i<logList.size()/4; i++){
//                    time += logsToMinutes(logList.get(4*i+1), logList.get(4*i+3));
//                }

                long lectureTime = logsToMinutes(lecture.lectureStart, lecture.lectureEnd);
                double atten = 0.0;
                if (lectureTime * 0.1 <= time && lectureTime * 0.7 > time) {
                    atten = 0.5;
                } else if (lectureTime * 0.7 <= time) {
                    atten = 1.0;
                }
                studentAttendance.put(lecture.id, atten);
            }

            attendance.put(student.id, studentAttendance);
        }

        return attendance;
    }

}

