package com.github.zzt93.syncer.config.pipeline.input;

import com.github.zzt93.syncer.common.util.RegexUtil;
import com.github.zzt93.syncer.config.pipeline.common.MysqlConnection;
import com.github.zzt93.syncer.consumer.Hashable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zzt
 */
public class Schema implements Hashable {

  private final Logger logger = LoggerFactory.getLogger(Schema.class);

  private String name;
  private List<Table> tables;

  private Pattern namePattern;
  private HashMap<String, Set<String>> nameToRows = new HashMap<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
    namePattern = RegexUtil.getRegex(name);
  }

  public List<Table> getTables() {
    return tables;
  }

  public void setTables(List<Table> tables) {
    this.tables = tables;
    for (Table table : tables) {
      nameToRows.put(table.getName(), new HashSet<>(table.getRowName()));
    }
    if (nameToRows.size() != tables.size()) {
      logger.warn("Duplicate table name definition: {}", tables);
    }
  }

  /**
   * If {@link #name} is a regex pattern, connect to default database; <p></p> Otherwise, connect to
   * that specific database.
   *
   * @return the database name used in jdbc connection string
   */
  public String getConnectionName() {
    if (noNamePattern()) {
      return name;
    }
    return MysqlConnection.DEFAULT_DB;
  }

  public Pattern getNamePattern() {
    return namePattern;
  }

  public boolean noNamePattern() {
    return namePattern == null;
  }

  @Override
  public String toString() {
    return "Schema{" +
        "name='" + name + '\'' +
        '}';
  }

  public Set<String> getTableRow(String tableSchema, String tableName) {
    if (name.equals(tableSchema) ||
        (namePattern != null && namePattern.matcher(tableSchema).find())) {
      return nameToRows.getOrDefault((tableName), null);
    }
    return null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Schema schema = (Schema) o;

    return name != null ? name.equals(schema.name) : schema.name == null;
  }

  @Override
  public int hashCode() {
    return name != null ? name.hashCode() : 0;
  }
}
