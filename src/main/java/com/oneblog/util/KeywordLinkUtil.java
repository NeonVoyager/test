package com.oneblog.util;

import com.oneblog.entity.KeywordLink;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 关键字链接工具类
 */
public class KeywordLinkUtil {
    
    /**
     * 将文章内容中的关键字替换为超链接
     */
    public static String replaceKeywords(String content, List<KeywordLink> keywordLinks) {
        if (content == null || keywordLinks == null || keywordLinks.isEmpty()) {
            return content;
        }
        
        String result = content;
        for (KeywordLink keywordLink : keywordLinks) {
            if (keywordLink.getStatus() == 1) {
                String keyword = keywordLink.getKeyword();
                String linkUrl = keywordLink.getLinkUrl();
                
                // 使用正则表达式替换关键字，避免重复替换
                String pattern = "(?<!<a[^>]*>)(?<!\")(?<![\\w])" + Pattern.quote(keyword) + "(?![\\w])(?!</a>)";
                String replacement = "<a href=\"" + linkUrl + "\" target=\"_blank\">" + keyword + "</a>";
                result = result.replaceAll(pattern, replacement);
            }
        }
        
        return result;
    }
}

