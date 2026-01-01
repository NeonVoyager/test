// 公共JavaScript函数

// 格式化日期
function formatDate(dateStr) {
    if (!dateStr) return '';
    const date = new Date(dateStr);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${year}-${month}-${day} ${hours}:${minutes}`;
}

// 格式化日期（仅日期）
function formatDateOnly(dateStr) {
    if (!dateStr) return '';
    const date = new Date(dateStr);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

// 相对时间
function formatRelativeTime(dateStr) {
    if (!dateStr) return '';
    const date = new Date(dateStr);
    const now = new Date();
    const diff = now - date;
    
    const seconds = Math.floor(diff / 1000);
    const minutes = Math.floor(seconds / 60);
    const hours = Math.floor(minutes / 60);
    const days = Math.floor(hours / 24);
    
    if (days > 0) {
        return `${days}天前`;
    } else if (hours > 0) {
        return `${hours}小时前`;
    } else if (minutes > 0) {
        return `${minutes}分钟前`;
    } else {
        return '刚刚';
    }
}

// API请求封装
async function apiRequest(url, options = {}) {
    try {
        const defaultOptions = {
            headers: {
                'Content-Type': 'application/json',
            },
        };
        
        const response = await fetch(url, {...defaultOptions, ...options});
        const result = await response.json();
        
        if (result.code === 200) {
            return result;
        } else {
            throw new Error(result.message || '请求失败');
        }
    } catch (error) {
        console.error('API请求失败:', error);
        throw error;
    }
}

// 显示消息提示
function showMessage(message, type = 'info') {
    const alertClass = {
        'success': 'alert-success',
        'error': 'alert-danger',
        'warning': 'alert-warning',
        'info': 'alert-info'
    }[type] || 'alert-info';
    
    const alertHtml = `
        <div class="alert ${alertClass} alert-dismissible fade show" role="alert" style="position: fixed; top: 20px; right: 20px; z-index: 9999; min-width: 300px;">
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    `;
    
    const container = document.createElement('div');
    container.innerHTML = alertHtml;
    document.body.appendChild(container);
    
    setTimeout(() => {
        container.remove();
    }, 3000);
}

// 确认对话框
function confirmDialog(message, callback) {
    if (confirm(message)) {
        callback();
    }
}

// 加载文章列表
async function loadArticles(url, containerId, renderFunc) {
    const container = document.getElementById(containerId);
    container.innerHTML = '<div class="loading"><div class="spinner-border" role="status"><span class="visually-hidden">加载中...</span></div></div>';
    
    try {
        const result = await apiRequest(url);
        if (result.data && result.data.records) {
            renderFunc(result.data.records, container);
        } else if (Array.isArray(result.data)) {
            renderFunc(result.data, container);
        }
    } catch (error) {
        container.innerHTML = `<div class="alert alert-danger">加载失败: ${error.message}</div>`;
    }
}

// 渲染文章卡片
function renderArticleCard(article) {
    return `
        <div class="card article-card mb-4 fade-in">
            <div class="row g-0">
                ${article.coverImage ? `
                <div class="col-md-4">
                    <img src="${article.coverImage}" class="img-fluid rounded-start" alt="${article.title}" style="height: 100%; object-fit: cover;">
                </div>
                ` : ''}
                <div class="col-md-${article.coverImage ? '8' : '12'}">
                    <div class="card-body">
                        <h5 class="card-title">
                            <a href="/article/${article.id}" class="text-decoration-none text-dark">${article.title}</a>
                        </h5>
                        <p class="card-text text-muted">${article.summary || ''}</p>
                        <div class="d-flex justify-content-between align-items-center">
                            <small class="text-muted">
                                <i class="bi bi-calendar"></i> ${formatDateOnly(article.publishTime)}
                                <i class="bi bi-eye ms-2"></i> ${article.views || 0}
                                <i class="bi bi-chat ms-2"></i> ${article.comments || 0}
                                <i class="bi bi-heart ms-2"></i> ${article.likes || 0}
                            </small>
                            <a href="/article/${article.id}" class="btn btn-sm btn-primary">阅读更多</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `;
}

// 渲染分页
function renderPagination(pageData, currentPage, callback) {
    const container = document.getElementById('pagination');
    if (!container) return;
    
    const totalPages = Math.ceil(pageData.total / pageData.size);
    let html = '<nav><ul class="pagination justify-content-center">';
    
    if (currentPage > 1) {
        html += `<li class="page-item"><a class="page-link" href="#" onclick="event.preventDefault(); ${callback}(${currentPage - 1})">上一页</a></li>`;
    }
    
    const startPage = Math.max(1, currentPage - 2);
    const endPage = Math.min(totalPages, currentPage + 2);
    
    if (startPage > 1) {
        html += `<li class="page-item"><a class="page-link" href="#" onclick="event.preventDefault(); ${callback}(1)">1</a></li>`;
        if (startPage > 2) {
            html += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
        }
    }
    
    for (let i = startPage; i <= endPage; i++) {
        html += `<li class="page-item ${i === currentPage ? 'active' : ''}">
            <a class="page-link" href="#" onclick="event.preventDefault(); ${callback}(${i})">${i}</a>
        </li>`;
    }
    
    if (endPage < totalPages) {
        if (endPage < totalPages - 1) {
            html += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
        }
        html += `<li class="page-item"><a class="page-link" href="#" onclick="event.preventDefault(); ${callback}(${totalPages})">${totalPages}</a></li>`;
    }
    
    if (currentPage < totalPages) {
        html += `<li class="page-item"><a class="page-link" href="#" onclick="event.preventDefault(); ${callback}(${currentPage + 1})">下一页</a></li>`;
    }
    
    html += '</ul></nav>';
    container.innerHTML = html;
}

