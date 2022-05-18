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

            for (Lecture lecture : lectures) {
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

            for (Lecture lecture : lectures) {
                String dirPath = attendanceDirPath + "/" + student.id + "/" + lecture.id;
                File dir = new File(dirPath);
                File[] files = dir.listFiles();

                List<String> logList = new LinkedList<>();
                List<Pair<String, String>> logs = new LinkedList<>();
                double atten = 0.0;

                boolean excused = false;

                assert files != null;
                for (File file : files) {
                    try {
                        Scanner input = new Scanner(file);
                        while (input.hasNext()) {
                            String line = input.nextLine();
                            if (line.equals("Refused") || line.equals("Admitted")) {  //excused absence
                                excused = true;
                                if (line.equals("Refused")) {
                                    atten = 0.0;
                                } else atten = 1.0;
                                break;
                            }
                            logList.add(line);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (excused) break;

                    // add log to log pair list
                    for (int i = 0; i < logList.size() / 4; i++) {
                        String startTime = logList.get(4 * i + 1);
                        String endTime = logList.get(4 * i + 3);
                        if(endTime.compareTo(lecture.lectureStart)<0) {
                            continue;
                        }
                        if(logsToMinutes(startTime, lecture.lectureStart)>0){ // early entrance
                            startTime = lecture.lectureStart;
                        }
                        if(endTime.compareTo(startTime)<=0) {
                            continue;
                        }
                        logs.add(new Pair(startTime, logList.get(4 * i + 3)));
                    }
                }

                Collections.sort(logs);

                if (!excused) {
                    long time = 0;
                    time += logsToMinutes(logs.get(0).first, logs.get(0).second);
                    String maxEnd = logs.get(0).second;
                    for(int i=1; i<logs.size(); i++){
                        Pair<String, String> prev = logs.get(i-1);
                        if(prev.second.compareTo(maxEnd)>0) {
                            maxEnd = prev.second;
                        }
                        Pair<String, String> now = logs.get(i);

                        if(now.first.compareTo(maxEnd)<0) {
                            if(now.second.compareTo(maxEnd)>0) {
                                time += logsToMinutes(now.first, now.second);
                                time -= logsToMinutes(now.first, maxEnd);
                            }
                        } else {
                            time+=logsToMinutes(now.first, now.second);
                        }
                    }

                    long lectureTime = logsToMinutes(lecture.lectureStart, lecture.lectureEnd);

                    if (lectureTime * 0.1 <= time && lectureTime * 0.7 > time) {
                        atten = 0.5;
                    } else if (lectureTime * 0.7 <= time) {
                        atten = 1.0;
                    }
                }
                studentAttendance.put(lecture.id, atten);
            }

            attendance.put(student.id, studentAttendance);
        }

        return attendance;
    }

}

