package com.TheWorldFirst.ShareReading.service.ServiceImpl;

import com.TheWorldFirst.ShareReading.dao.BookDao;
import com.TheWorldFirst.ShareReading.service.BookService;
import com.TheWorldFirst.ShareReading.util.CrawlerUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public HashMap<String, Object> getBookInfoByIsbn(String isbn) {
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> book = new HashMap<>();
        String htmlUrl = "";
        try {
            try {
                htmlUrl = CrawlerUtil.getBookUrlByFirefox(isbn);
            } catch (Exception e) {
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
                            bookAuthor = bookInfoSplit[i+1];
                        }
                        if(bookInfoSplit[i].equals("出版社:")) {
                            bookPublisher = bookInfoSplit[i+1];
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
}
