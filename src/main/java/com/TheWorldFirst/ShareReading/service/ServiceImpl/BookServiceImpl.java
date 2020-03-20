package com.TheWorldFirst.ShareReading.service.ServiceImpl;

import com.TheWorldFirst.ShareReading.service.BookService;
import com.TheWorldFirst.ShareReading.util.CrawlerUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class BookServiceImpl implements BookService {

    @Override
    public HashMap<String, Object> getBookInfoByIsbn(String isbn) {
        HashMap<String, Object> result = new HashMap<>();
        String htmlUrl = "";
        try {
            try {
                htmlUrl = CrawlerUtil.getBookUrlByFirefox(isbn);
            } catch (Exception e) {
                result.put("success", false);
                result.put("message", "书籍未找到！");
            }
            if(htmlUrl != "") {
                String htmlPage = CrawlerUtil.getBookInfoByFirefox(htmlUrl);
                if (htmlPage != null) {
                    Document document = Jsoup.parse(htmlPage);
                    //爬取到的书籍信息
                    String imgUrl = document.select("#mainpic").select(".nbg").select("img").attr("src");
                    String bookName = document.select("#mainpic").select(".nbg").select("img").attr("alt");
                    String bookInfo = document.select("#info").text();
                    String bookBriefShort = document.select("#link-report").select(".short ").select(".intro").text();
                    String bookBriefAll = document.select("#link-report").select(".all ").select(".intro").text();

                    String img = CrawlerUtil.getBookImageFirefox(imgUrl);
                    result.put("htmlPage", "");
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
