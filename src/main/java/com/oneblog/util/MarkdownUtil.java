package com.oneblog.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Arrays;
import java.util.List;

/**
 * Markdown工具类
 */
public class MarkdownUtil {
    
    public static String markdownToHtml(String markdown) {
        List<Extension> extensions = Arrays.asList(
            TablesExtension.create(),
            HeadingAnchorExtension.create()
        );
        
        Parser parser = Parser.builder()
            .extensions(extensions)
            .build();
        
        HtmlRenderer renderer = HtmlRenderer.builder()
            .extensions(extensions)
            .build();
        
        Node document = parser.parse(markdown);
        return renderer.render(document);
    }
}

