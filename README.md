# 交易管理 API

这是一个用于管理财务交易的简单应用程序。它提供了一个RESTful API，用于创建、检索、更新和删除交易记录。

## 关于项目

本项目是使用Spring Boot构建的简单交易管理系统示例。它展示了最佳实践，如分层架构、RESTful API设计、全面测试和容器化。

## 技术栈

* [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Maven](https://maven.apache.org/)
* [H2 数据库](https://www.h2database.com/)
* [Gatling](https://gatling.io/)
* [Docker](https://www.docker.com/)

## 入门指南

按照以下简单步骤获取并运行本地副本。

### 前提条件

* Java 17
* Maven 3.x

### 安装

1. 克隆仓库
   ```sh
   git clone https://github.com/ClintonLin/transactionmanagement.git
2. 构建项目
   ```sh
   mvn clean install
   ```

## 使用方法

使用以下命令运行应用程序：

```sh
mvn spring-boot:run
```

应用程序将在 `http://localhost:8080` 上可用。

## 运行测试

### 单元测试

要运行单元测试，请使用以下命令：

```sh
mvn test
```

### 压力测试

要运行压力测试，请使用以下命令：

```sh
mvn gatling:test
```

测试结果将在 `target/gatling` 目录中可用。

## API 参考

#### 获取所有交易

```http
  GET /api/transactions
```

| 参数 | 类型     | 描述                |
| :-------- | :------- | :------------------------- |
| `page`      | `int` | 要检索的页码。 |
| `size`      | `int` | 每页项目数。|

#### 通过ID获取交易

```http
  GET /api/transactions/{id}
```

| 参数 | 类型     | 描述                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **必需**。交易的ID。 |

#### 创建新交易

```http
  POST /api/transactions
```

| 请求体      | 类型     | 描述                       |
| :-------- | :------- | :-------------------------------- |
| `amount`    | `double` | **必需**。交易金额。 |
| `type`      | `string` | 交易类型。      |
| `description`| `string` | 交易描述。|
| `timestamp` | `string` | **必需**。交易时间戳。 |

#### 更新交易

```http
  PUT /api/transactions/{id}
```

| 参数 | 类型     | 描述                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **必需**。交易的ID。 |

| 请求体      | 类型     | 描述                       |
| :-------- | :------- | :-------------------------------- |
| `amount`    | `double` | **必需**。交易金额。 |
| `type`      | `string` | 交易类型。      |
| `description`| `string` | 交易描述。|
| `timestamp` | `string` | **必需**。交易时间戳。 |

#### 删除交易

```http
  DELETE /api/transactions/{id}
```

| 参数 | 类型     | 描述                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **必需**。交易的ID。 |

## 容器化

要构建Docker镜像，请使用以下命令：

```sh
docker build -t transaction-management .
```

要运行Docker容器，请使用以下命令：

```sh
docker run -p 8080:8080 transaction-management