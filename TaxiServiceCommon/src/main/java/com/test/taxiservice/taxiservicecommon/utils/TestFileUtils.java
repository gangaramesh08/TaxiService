package com.test.taxiservice.taxiservicecommon.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;

public class TestFileUtils {

    public static String getFileContentAsString(String file) throws IOException {
        File testFile = TestFileUtils.getFile(file);
        return FileUtils.readFileToString(testFile, StandardCharsets.UTF_8);
    }

    public static File getFile(String file)  {
        ClassLoader classLoader = TestFileUtils.class.getClassLoader();
        return new File(classLoader.getResource(file).getFile());
    }


}
