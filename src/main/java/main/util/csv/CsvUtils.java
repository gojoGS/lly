package main.util.csv;

import main.model.AnswerId;
import main.model.Question;
import main.model.Questions;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

public class CsvUtils {
    public static Questions csvToQuestions(Reader in) throws IOException {
        String[] headers = {"question", "A", "B", "C", "D", "correctId", "theme"};
        var format = CSVFormat.newFormat('|').withHeader(headers).withFirstRecordAsHeader();
        Iterable<CSVRecord> records;

        records = format.parse(in);

        var questionList = new ArrayList<Question>();

        for(var record :records) {
            String question = record.get("question");
            String answerA =  record.get("A");
            String answerB =  record.get("B");
            String answerC =  record.get("C");
            String answerD =  record.get("D");

            var answerMapping = new HashMap<AnswerId, String>();

            answerMapping.put(AnswerId.A, answerA);
            answerMapping.put(AnswerId.B, answerB);
            answerMapping.put(AnswerId.C, answerC);
            answerMapping.put(AnswerId.D, answerD);

            AnswerId correctId = AnswerId.valueOf(record.get("correctId"));

            var frequencyMapping = main.util.random.RandomUtil.generateRandomAnswerFrequency(correctId);

            questionList.add(new Question(question, answerMapping, frequencyMapping, correctId));
        }

        return new Questions(questionList);
    }
}
