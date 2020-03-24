package com.TheWorldFirst.ShareReading.service.ServiceImpl;

import com.TheWorldFirst.ShareReading.dao.BookDao;
import com.TheWorldFirst.ShareReading.service.BookService;
import com.TheWorldFirst.ShareReading.service.UserService;
import com.TheWorldFirst.ShareReading.util.CrawlerUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private UserService userService;

    @Override
    public HashMap<String, Object> getBookInfoByIsbn(String isbn, String session, String userId) {
        HashMap<String, Object> result = new HashMap<>();
        String permission = userService.verifyUserPermission(session, userId);
        if(!permission.equals("true")) {
            if (permission.equals("false")) {
                result.put("success", false);
                result.put("message", "用户信息验证错误，请退出重新登录！");
                return result;
            }
            result.put("success", false);
            result.put("message", permission);
            return result;
        }
        HashMap<String, Object> daoBookInfo = bookDao.getBookInfoByExist(isbn);
        if(daoBookInfo != null) {
            result.put("success", false);
            result.put("message", "书籍已存在无需重复添加！");
            return result;
        }
        HashMap<String, Object> book = new HashMap<>();
        String htmlUrl = "";
        try {
            try {
                htmlUrl = CrawlerUtil.getBookUrlByFirefox(isbn);
            } catch (Exception e) {
                e.printStackTrace();
                result.put("success", false);
                result.put("message", "书籍未找到！");
            }
            if(!htmlUrl.equals("")) {
                String htmlPage = CrawlerUtil.getBookInfoByFirefox(htmlUrl);
                if (htmlPage != null) {
                    Document document = Jsoup.parse(htmlPage);
                    //书籍url
                    String imgUrl = document.select("#mainpic").select(".nbg").select("img").attr("src");
                    //书籍图片（base64）
                    String bookImage = CrawlerUtil.getBookImageFirefox(imgUrl);
                    //书籍名称
                    String bookName = document.select("#mainpic").select(".nbg").select("img").attr("alt");
                    //书籍信息
                    String bookInfo = document.select("#info").text();
                    String[] bookInfoSplit = bookInfo.split(" ");
                    String bookAuthor = "";
                    String bookPublisher = "";
                    String bookPublished = "";
                    String bookIsbn = "";
                    for (int i = 0; i < bookInfoSplit.length; i++) {
                        if(bookInfoSplit[i].equals("作者:")) {
                            for (int j = i+1; j < bookInfoSplit.length; j++) {
                                if((bookInfoSplit[j].substring(bookInfoSplit[j].length()-1)).equals(":")) {
                                    break;
                                }
                                bookAuthor += bookInfoSplit[j];
                            }
                        }
                        if(bookInfoSplit[i].equals("出版社:")) {
                            for (int j = i+1; j < bookInfoSplit.length; j++) {
                                if((bookInfoSplit[j].substring(bookInfoSplit[j].length()-1)).equals(":")) {
                                    break;
                                }
                                bookPublisher += bookInfoSplit[j];
                            }
                        }
                        if(bookInfoSplit[i].equals("出版年:")) {
                            bookPublished = bookInfoSplit[i+1];
                        }
                        if (bookInfoSplit[i].equals("ISBN:")) {
                            bookIsbn = bookInfoSplit[i+1];
                        }
                    }
                    //书籍简介
                    String bookDescription = document.select("#link-report").select(".all ").select(".intro").text();
                    if(bookDescription.equals("")) {
                        bookDescription = document.select("#link-report").select("div").select(".intro").text();
                    }
                    book.put("bookIsbn", bookIsbn);
                    book.put("bookName", bookName);
                    book.put("bookAuthor", bookAuthor);
                    book.put("bookPublisher", bookPublisher);
                    book.put("bookPublished", bookPublished);
                    book.put("bookDescription", bookDescription);
                    book.put("bookImage", bookImage);
                    result.put("book", book);
                    result.put("success", true);
                    result.put("message", "获取成功！");
                } else {
                    result.put("success", false);
                    result.put("message", "获取失败！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "获取失败！");
        }
        return result;
    }

    @Override
    public HashMap<String, Object> getBookInfoByIsbnDatabase(String isbn, String session, String userId) {
        HashMap<String, Object> result = new HashMap<>();
        String permission = userService.verifyUserPermission(session, userId);
        if(!permission.equals("true")) {
            if (permission.equals("false")) {
                result.put("success", false);
                result.put("message", "用户信息验证错误，请退出重新登录！");
                return result;
            }
            result.put("success", false);
            result.put("message", permission);
            return result;
        }
        try {
            HashMap<String, Object> book = bookDao.getBookInfoByExist(isbn);
            result.put("success", true);
            result.put("message", "获取成功！");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "获取失败！");
        }
        return result;
    }

    @Override
    @Transactional
    public HashMap<String, Object> saveBook(HashMap<String, Object> params) {
        HashMap<String, Object> result = new HashMap<>();
        String permission = userService.verifyUserPermission((String) params.get("session"), (String) params.get("userId"));
        if(!permission.equals("true")) {
            if (permission.equals("false")) {
                result.put("success", false);
                result.put("message", "用户信息验证错误，请退出重新登录！");
                return result;
            }
            result.put("success", false);
            result.put("message", permission);
            return result;
        }
        HashMap<String, Object> daoBookInfo = bookDao.getBookInfoByExist((String) params.get("isbn"));
        if(daoBookInfo != null) {
            result.put("success", false);
            result.put("message", "书籍已存在无需重复添加！");
            return result;
        }
        try {
            bookDao.saveBook((HashMap<String, Object>) params.get("book"));
            result.put("success", true);
            result.put("message", "保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "保存失败！");
        }
        return result;
    }

    @Override
    public HashMap<String, Object> getBookList(Integer page, Integer limit, String keyword) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            ArrayList<HashMap<String, Object>> bookList = bookDao.getBookList(page, limit, keyword);
            result.put("bookList", bookList);
            result.put("success", true);
            result.put("message", "获取书籍列表成功！");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "获取书籍列表失败！");
        }
        return result;
    }

    @Override
    public HashMap<String, Object> getBookInfoById(String bookId) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            HashMap<String, Object> bookInfo = bookDao.getBookById(bookId);
            result.put("bookInfo", bookInfo);
            result.put("success", true);
            result.put("message", "获取书籍信息成功！");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "获取书籍信息失败！");
        }
        return result;
    }
}
