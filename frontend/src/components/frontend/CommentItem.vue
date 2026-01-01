<template>
  <div class="comment-item">
    <div class="comment-header">
      <span class="comment-author">{{ comment.nickname }}</span>
      <span class="comment-time">{{ formatRelativeTime(comment.createTime) }}</span>
    </div>
    <div class="comment-content">{{ comment.content }}</div>
    <div class="comment-actions">
      <el-button text @click="$emit('reply', comment.id)">回复</el-button>
      <el-button text @click="handleReport">举报</el-button>
    </div>
    
    <!-- 回复列表 -->
    <div v-if="replies.length > 0" class="comment-replies">
      <CommentItem
        v-for="reply in replies"
        :key="reply.id"
        :comment="reply"
        @reply="$emit('reply', reply.id)"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { formatRelativeTime } from '@/utils/date'
import { ElMessageBox } from 'element-plus'
import { reportComment } from '@/api/report'

const props = defineProps({
  comment: {
    type: Object,
    required: true
  }
})

const replies = ref([])

const handleReport = async () => {
  try {
    const { value } = await ElMessageBox.prompt('请输入举报原因', '举报评论', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      inputPlaceholder: '请输入举报原因'
    })
    
    await reportComment(props.comment.id, null, value)
    ElMessage.success('举报成功，我们会尽快处理')
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('举报失败')
    }
  }
}
</script>

<style scoped lang="scss">
.comment-item {
  padding: 15px;
  border-bottom: 1px solid #f0f0f0;
  margin-bottom: 15px;

  .comment-header {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;

    .comment-author {
      font-weight: bold;
      color: #409eff;
    }

    .comment-time {
      color: #999;
      font-size: 12px;
    }
  }

  .comment-content {
    color: #333;
    line-height: 1.6;
    margin-bottom: 10px;
  }

  .comment-actions {
    display: flex;
    gap: 10px;
  }

  .comment-replies {
    margin-left: 30px;
    margin-top: 15px;
    padding-left: 15px;
    border-left: 2px solid #f0f0f0;
  }
}
</style>

