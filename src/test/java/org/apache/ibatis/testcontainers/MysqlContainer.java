/*
 *    Copyright 2009-2025 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.testcontainers;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public final class MysqlContainer {

  private static final String DB_NAME = "mybatis_test";
  private static final String USERNAME = "u";
  private static final String PASSWORD = "p";
  private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

  @Container
  private static final MySQLContainer<?> INSTANCE = initContainer();

  private static MySQLContainer<?> initContainer() {
    @SuppressWarnings("resource")
    MySQLContainer<?> container = new MySQLContainer<>(DockerImageName.parse("mysql").withTag("9.2"))
        .withDatabaseName(DB_NAME).withUsername(USERNAME).withPassword(PASSWORD).withUrlParam("useSSL", "false");
    container.start();
    return container;
  }

  public static DataSource getUnpooledDataSource() {
    return new UnpooledDataSource(MysqlContainer.DRIVER, INSTANCE.getJdbcUrl(), MysqlContainer.USERNAME,
        MysqlContainer.PASSWORD);
  }

  public static PooledDataSource getPooledDataSource() {
    return new PooledDataSource(MysqlContainer.DRIVER, INSTANCE.getJdbcUrl(), MysqlContainer.USERNAME,
        MysqlContainer.PASSWORD);
  }

  private MysqlContainer() {
  }
}
