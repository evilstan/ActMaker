package main.java.com.company;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class FileOpener {
    private final String path;
    private List<File> files;
    private List<XSSFWorkbook> workbooks;
    private List<String> filenames;

    public FileOpener(String path) {
        this.path = path;
        init();
        try {
            workbooks(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        files = new ArrayList<>();
        workbooks = new ArrayList<>();
        filenames = new ArrayList<>();
    }

    public List<XSSFWorkbook> workbooks(String path) throws Exception {
        final File folder = new File(path);
        List<File> files = parseFile(folder);
        for (File file : files) {
            workbooks.add(new XSSFWorkbook(file));
        }
        return workbooks;
    }

    private List<File> parseFile(final File folder) {
        for (final File excelFile : Objects.requireNonNull(folder.listFiles())) {
            if (excelFile.isDirectory()) {
                parseFile(excelFile);
            } else {
                System.out.println(excelFile.getName());
                if (checkExtension(excelFile)) {
                    files.add(excelFile);
                    filenames.add(excelFile.getName());
                }
            }
        }
        return files;
    }

    private boolean checkExtension(File file) {
        String extension = "";

        int i = file.getName().lastIndexOf('.');
        if (i > 0) {
            extension = file.getName().substring(i + 1);
        }
        return extension.toLowerCase(Locale.ROOT).contains("xls");
    }

    public List<String> getFilenames() {
        return filenames;
    }
}
