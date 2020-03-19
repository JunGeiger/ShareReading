package com.TheWorldFirst.ShareReading.service;

import java.util.HashMap;

public interface BookService {

    HashMap<String, Object> getBookInfoByIsbn(String isbn);
}
