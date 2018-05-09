package sample;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class WriteXmlToFile {

    public static void writeInputToFileInRomanian(String tagName, String content) {
        writeInputToAndroidFile(tagName, content, new File("romanian.xml"));
    }

    public static void writeInputToFileInEnglish(String tagName, String content) {
        writeInputToAndroidFile(tagName, content, new File("english.xml"));
    }

    private static void writeInputToAndroidFile(String tagName, String content, File file) {
        StringBuilder sb = new StringBuilder();
        sb.append("<string name=\"");
        sb.append(tagName);
        sb.append("\">");
        sb.append(content);
        sb.append("</string>");

        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
            bufferedWriter.write(sb.toString());
            bufferedWriter.newLine();
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

}
