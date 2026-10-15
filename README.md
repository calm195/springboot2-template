# Spring Boot 2 模版

[English](README.en.md) | 简体中文

面向 Java 8 的 Spring Boot 后端起步工程，包含可运行的 REST API、参数校验、统一错误响应、健康检查、格式化、代码检查、测试与 Codex CLI 项目指引。无前端及 TypeScript 依赖。

> **版本说明：** Spring Boot **2.7.18** 是 2.x 最后一个公开开源稳定版本，已于 2023 年 11 月结束开源维护，并非当前仍维护的 Spring Boot 主版本。本工程按 Java 8 / Boot 2 的兼容要求选型；生产使用应评估商业支持或迁移到仍受支持的版本。[官方版本公告](https://spring.io/blog/2023/11/23/spring-boot-2-7-18-available-now/)

## 环境与启动

- JDK **8**，设置 `JAVA_HOME`；构建会强制校验 Java 8。
- Maven Wrapper 固定为 **3.9.9**；无需预装 Maven，首次运行需要联网及 `curl` / `wget`（Windows 使用 PowerShell）。

```sh
# macOS 示例，其他平台请设置为实际 JDK 8 路径
export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
./mvnw spring-boot:run
```

Windows 使用 `mvnw.cmd`。默认端口为 `8080`，可通过环境变量 `SERVER_PORT` 覆盖。

```sh
curl 'http://localhost:8080/api/v1/greetings?name=Codex'
# {"message":"Hello, Codex!"}
curl http://localhost:8080/actuator/health
# {"status":"UP"}
```

省略 `name` 时使用 `World`；仅空白或超过 80 个字符返回 HTTP 400：

```json
{"code":"INVALID_REQUEST","message":"Request validation failed"}
```

API 使用 `/api/v1` 路径版本化。只暴露 Actuator `health` / `info`；健康检查隐藏内部详情。未提供认证，接入实际业务前需按需求配置身份认证、权限及部署边界。

## 目录结构

```text
.
├── src/main/java/com/example/template/
│   ├── TemplateApplication.java   # 启动入口，位于组件扫描根包
│   ├── greeting/                 # 按业务特性组织 controller / service / DTO
│   └── shared/api/               # 公共错误响应与异常处理
├── src/main/resources/
│   └── application.yml           # 环境变量驱动的基础配置
├── src/test/java/                # HTTP 契约、校验及健康检查测试
├── config/checkstyle.xml         # 命名、导入、括号、Javadoc 等规则
├── .mvn/wrapper/                 # 固定 Maven 版本
├── .github/workflows/ci.yml      # JDK 8 质量门禁
├── AGENTS.md                     # Codex CLI 仓库规范
└── pom.xml                       # 依赖管理、打包与质量检查
```

按业务特性扩展目录；controller 负责协议和输入校验，service 承载业务规则，DTO 定义对外契约。使用构造器注入和不可变响应对象。公共类型有 Javadoc，注释说明设计原因。需要数据库时再增加实体、repository、迁移脚本和对应测试，避免空壳分层。

## 开发与质量检查

```sh
./mvnw spotless:apply                      # 自动格式化
./mvnw --batch-mode --no-transfer-progress clean verify
java -jar target/springboot2-template-0.0.1-SNAPSHOT.jar
```

`verify` 会执行 Spotless 格式校验、Checkstyle 静态规则、JUnit 5 / MockMvc 测试以及可执行 JAR 打包。GitHub Actions 使用同一命令。测试报告位于 `target/surefire-reports/`。不匹配的格式或代码规则会使构建失败。

Java 使用 Google Java Format；文本文件检查行尾空白和末尾换行，编辑器遵循 `.editorconfig`。为支持 **JDK 8 本身运行构建**，固定 Spotless 2.30.0、Google Java Format 1.7 和 Checkstyle 9.3；升级前请核实运行时要求。使用 Java 8 实际编译可防止误用更高版本 JDK 的 API。

配置从环境变量或外部 Spring 配置提供；不要提交口令、令牌或真实环境配置。新增配置项时同步更新两份 README。Boot BOM 管理运行时依赖版本，避免随意覆盖单个框架组件。

## Codex CLI

仓库已通过根目录 `AGENTS.md` 接入 Codex CLI：记录 Java 8 限制、目录约定、开发命令和检查要求，Codex 在此目录工作时会读取这些指引。不内置模型名称，不覆盖个人配置，不提交登录凭据。

如尚未安装，使用支持 Codex CLI 的 Node.js 环境执行：

```sh
npm install -g @openai/codex
codex login
codex
```

在本仓库根目录启动 `codex`。登录使用你自己的账户，工程运行和 Maven 构建不需要 Node.js 或 OpenAI API 密钥。

示例任务：`为 greeting 功能添加新的输入约束，遵循 AGENTS.md，补充测试并运行 ./mvnw clean verify。`

参考：[Codex CLI 官方文档](https://developers.openai.com/codex/cli)、[AGENTS.md 指引](https://developers.openai.com/codex/guides/agents-md)。

## 从模版开始

1. 修改 `pom.xml` 中的坐标与名称，并重命名根包及对应目录。
2. 替换示例 `greeting` 功能，补充自己的 API 和测试。
3. 按需接入数据库、认证和部署配置。
4. 执行 `./mvnw spotless:apply`、`./mvnw clean verify`，同步更新中英文文档。
