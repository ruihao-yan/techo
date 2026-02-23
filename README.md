# Techo 记账

一款简洁好用的个人记账 Android 应用，支持收支记录、分类统计和预算管理。

## 功能特性

- **收支记录**：快速记录每笔支出与收入，支持备注与自定义时间
- **分类管理**：
  - 支出分类（15 类）：餐饮、交通、购物、服饰、日用品、娱乐、零食、饮料、学习、医疗、住房、水电费、通讯、人情往来、其他
  - 收入分类（9 类）：工资、奖金、借入、收债、利息附加、投资回报、二手设备、意外所得、其他
- **预算管理**：设置每月预算，实时查看剩余预算
- **隐私模式**：一键隐藏/显示金额数据
- **月度统计**：显示当月总支出、总收入及当日收支汇总
- **自定义数字键盘**：流畅的金额输入体验

## 技术栈

| 类别 | 技术 |
|------|------|
| 语言 | Java |
| UI | AndroidX + Material Design 3 |
| 数据库 | SQLite |
| 最低 SDK | Android 9（API 28） |
| 目标 SDK | Android 16（API 36） |

## 项目结构

```
app/src/main/java/cc/runyan/techo/
├── MainActivity.java          # 主界面：收支列表与月度汇总
├── RecordActivity.java        # 记账界面：支出/收入录入
├── BaseActivity.java          # Activity 基类
├── UnitApp.java               # Application 类
├── adapter/                   # 列表与分页适配器
├── db/                        # SQLite 数据库操作
├── dto/                       # 数据传输对象
├── frag_record/               # 支出/收入录入 Fragment
├── po/                        # 数据实体类
├── utils/                     # 工具类（键盘、对话框、时间）
├── constant/                  # 常量定义
└── exceptions/                # 自定义异常
```

## 开发环境

- Android Studio
- JDK 17
- Gradle 8+

## 构建与运行

1. 克隆仓库：
   ```bash
   git clone https://github.com/ruihao-yan/techo.git
   ```
2. 使用 Android Studio 打开项目根目录
3. 等待 Gradle 同步完成
4. 连接 Android 设备或启动模拟器，点击 **Run** 运行
