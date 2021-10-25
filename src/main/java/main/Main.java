package main;

import jakarta.xml.bind.JAXBException;
import lombok.Cleanup;
import main.model.Questions;
import main.util.jaxb.JAXBUtil;

import java.io.*;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Questions questions;

        try (Reader csvFile = new FileReader("q.csv")) {
            questions = main.util.csv.CsvUtils.csvToQuestions(csvFile);
            JAXBUtil.toXML(questions, new FileOutputStream("q.xml"));
        } catch (IOException e) {
            System.out.println("Hiba lépett fel a csv fájl feldolgozása során");
            exit(2);
        } catch (JAXBException e) {
            System.out.println("Hiba lépett fel az xml fájl feldolgozása során");
            exit(3);
        }
    }
}
