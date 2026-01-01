import { marked } from 'marked'
import hljs from 'highlight.js'

// 配置marked
marked.setOptions({
  highlight: function(code, lang) {
    if (lang && hljs.getLanguage(lang)) {
      try {
        return hljs.highlight(code, { language: lang }).value
      } catch (err) {
        console.error(err)
      }
    }
    return hljs.highlightAuto(code).value
  },
  breaks: true,
  gfm: true
})

// 渲染Markdown
export function renderMarkdown(markdown) {
  return marked.parse(markdown)
}

// 生成目录
export function generateTOC(html) {
  const tempDiv = document.createElement('div')
  tempDiv.innerHTML = html
  
  const headings = tempDiv.querySelectorAll('h1, h2, h3, h4, h5, h6')
  const toc = []
  
  headings.forEach((heading, index) => {
    const id = `heading-${index}`
    heading.id = id
    
    const level = parseInt(heading.tagName.charAt(1))
    toc.push({
      id,
      level,
      text: heading.textContent
    })
  })
  
  return toc
}

