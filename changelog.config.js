module.exports = {
    "disableEmoji": false,
    "list": [
      "feat",
      "chore",
      "fix",
      "refactor",
      "style",
      "docs",
      "build"
    ],
    "maxMessageLength": 64,
    "minMessageLength": 3,
    "questions": [
      "type",
      "subject",
      "body",
      "issues"
    ],
    "types": {
      "feat": {
        "description": "새로운 기능 추가",
        "emoji": "✨",
        "value": "feat"
      },
      "chore": {
          "description": "자잘한 작업",
          "emoji": "🧵",
          "value": "chore"
      },
      "fix": {
          "description": "버그 수정",
          "emoji": "👾",
          "value": "fix"
        },
      "refactor": {
        "description": "성능 개선, 리팩토링",
        "emoji": "🐙",
        "value": "refactor"
      },
      "style": {
        "description": "코드에 영향을 주지 않는 UI/UX/디자인 변경사항",
        "emoji": "🎨",
        "value": "style"
      },
      "docs": {
        "description": "문서 수정",
        "emoji": "📝",
        "value": "docs"
      },
      "build": {
        "description": "시스템 또는 외부 종속성에 영향을 미치는 변경사항 (npm, gulp, yarn 레벨)",
        "emoji": "🧱",
        "value": "build"
      }
    }
  };
  