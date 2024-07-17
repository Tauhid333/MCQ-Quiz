import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Quiz {
    public static void main(String[] args) throws IOException, ParseException {
        LogIn();

    }

    public static void LogIn() throws IOException, ParseException {
        String fileLoc ="./src/main/resources/users.json" ;
        JSONParser parser  = new JSONParser();
        JSONArray jsonUserList = (JSONArray) parser.parse(new FileReader(fileLoc));


        System.out.println("Enter your username: ");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        System.out.println("Enter Password: ");
        String password = scanner.nextLine();
        String adminLoginMsg  = "Welcome admin! Please create new questions in the question bank.";
        String studentLoginMsg ="Welcome " + username + " to the quiz! We will throw you 10 questions. Each MCQ mark is 1 and no negative marking. Are you ready? Press 's' to start." ;

        for (int i = 0; i < jsonUserList.size(); i++) {
            JSONObject userObj = (JSONObject) jsonUserList.get(i);
            String actualUserName = (String) userObj.get("username");
            String actualPassword = (String) userObj.get("password");
            String role           = (String) userObj.get("role");
            if (Objects.equals(actualUserName, username) && Objects.equals(actualPassword,password))
            {
                if(Objects.equals(role, "admin")){
                    System.out.println(adminLoginMsg);
                    addQuizQues();

                }else {
                    System.out.println(studentLoginMsg);
                    attendQuiz();

                }

                break;

            }else {
                if ( i == jsonUserList.size()-1){
                    System.out.println("Incorrect Credentials");
                }

            }


        }
    }
    public static void addQuizQues() throws IOException, ParseException {
        String fileLoc  ="./src/main/resources/quiz.json" ;
        Scanner scanner = new Scanner(System.in);
        JSONParser parser  = new JSONParser();
        JSONArray jsonQuizArray = (JSONArray) parser.parse(new FileReader(fileLoc));
        String question, option1, option2, option3, option4, answerkey;
        char c;
        c = scanner.next().charAt(0);
        scanner.nextLine();
        while ( c == 's'){
            JSONObject quizObj = new JSONObject();
            System.out.println("Input your question");
            question = scanner.nextLine();
            System.out.println("Input option 1:");
            option1 = scanner.nextLine();
            System.out.println("Input option 2:");
            option2 = scanner.nextLine();
            System.out.println("Input option 3:");
            option3 = scanner.nextLine();
            System.out.println("Input option 4:");
            option4 = scanner.nextLine();
            System.out.println("What is the answer key?");
            answerkey = scanner.nextLine();
            quizObj.put("question",question);
            quizObj.put("option1",option1);
            quizObj.put("option2",option2);
            quizObj.put("option3",option3);
            quizObj.put("option4",option4);
            quizObj.put("answerkey",answerkey);
            jsonQuizArray.add(quizObj);
            FileWriter fileWriter = new FileWriter(fileLoc);
            fileWriter.write(jsonQuizArray.toJSONString());
            fileWriter.flush();
            fileWriter.close();
            System.out.println("Saved successfully! Do you want to add more questions? (press s for start and q for quit)");
            c = scanner.next().charAt(0);
            scanner.nextLine();
        }


    }

    public static void attendQuiz() throws IOException, ParseException {
        String fileLoc  ="./src/main/resources/quiz.json" ;
        Scanner scanner = new Scanner(System.in);
        JSONParser parser  = new JSONParser();
        JSONArray jsonQuizArray = (JSONArray) parser.parse(new FileReader(fileLoc));
        Random rand = new Random();
        String question, option1, option2, option3, option4, answerkey;
        char c;
        int marks =0;
        String choice;
        c = scanner.next().charAt(0);
        scanner.nextLine();
        if(c == 's'){
            for (int i = 1; i <= 10 ; i++) {
                JSONObject quizObj = new JSONObject();
                int randomIndex = rand.nextInt(jsonQuizArray.size());
                quizObj  = (JSONObject) jsonQuizArray.get(randomIndex);
                question = (String) quizObj.get("question");
                option1  = (String) quizObj.get("option1");
                option2  = (String) quizObj.get("option2");
                option3  = (String) quizObj.get("option3");
                option4  = (String) quizObj.get("option4");
                answerkey= (String) quizObj.get("answerkey");
                System.out.println("Question["+ i + "] " + question);
                System.out.println("1. " + option1);
                System.out.println("2. " + option2);
                System.out.println("3. " + option3);
                System.out.println("4. " + option4);

                choice   = scanner.nextLine();
                if (Objects.equals(choice,answerkey))
                {
                    marks++;
                }

            }
        }
        if (marks>8){
            System.out.println("Excellent! You have got "+ marks + " out of 10");
        } else if (marks>5 && marks<8) {
            System.out.println("Good! You have got "+ marks + " out of 10");

        } else if (marks>2 && marks<5) {
            System.out.println("Very Poor! You have got "+ marks + " out of 10");
        } else if (marks<2) {
            System.out.println("Very sorry you are failed.You have got "+ marks + " out of 10");
        }


    }

}
