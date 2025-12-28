package com.tmdna.mbeer.service;

import java.io.File;
import java.util.List;

public interface CsvService<T> {
    List<T> parseCsv(File csvFile);
}
