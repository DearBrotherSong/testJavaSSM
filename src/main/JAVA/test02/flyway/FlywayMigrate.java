package test02.flyway;

import org.flywaydb.core.Flyway;
import test02.Infrastructure.CommonTools.CommonTool;

import java.util.concurrent.ConcurrentHashMap;

public class FlywayMigrate {
    public static void main(String[] args) {
        Flyway flyway = new Flyway();

        String propertiesName="jdbc.properties";
        ConcurrentHashMap properties = CommonTool.Tools.GetPropertiesMap(propertiesName);
        String url = properties.get("url").toString();
        String userNmae = properties.get("username").toString();
        String password = properties.get("password").toString();
        flyway.setDataSource(url, userNmae, password);
        // 迁移
        flyway.migrate();
    }
}
