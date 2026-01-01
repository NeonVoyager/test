import dayjs from 'dayjs'
import relativeTime from 'dayjs/plugin/relativeTime'
import 'dayjs/locale/zh-cn'

dayjs.extend(relativeTime)
dayjs.locale('zh-cn')

// 格式化日期
export function formatDate(date, format = 'YYYY-MM-DD HH:mm:ss') {
  if (!date) return ''
  return dayjs(date).format(format)
}

// 格式化日期（仅日期）
export function formatDateOnly(date) {
  return formatDate(date, 'YYYY-MM-DD')
}

// 相对时间
export function formatRelativeTime(date) {
  if (!date) return ''
  return dayjs(date).fromNow()
}

// 格式化时间（简短）
export function formatShortTime(date) {
  if (!date) return ''
  const now = dayjs()
  const target = dayjs(date)
  const diff = now.diff(target, 'day')
  
  if (diff === 0) {
    return target.format('HH:mm')
  } else if (diff === 1) {
    return '昨天'
  } else if (diff < 7) {
    return `${diff}天前`
  } else {
    return target.format('MM-DD')
  }
}

