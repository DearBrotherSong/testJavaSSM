package test.flyway;

import org.flywaydb.core.Flyway;
import test.infrastructure.common.CommonTool;

import java.util.concurrent.ConcurrentHashMap;

public class FlywayInit {
    public static void main(String[] args) {
        Flyway flyway = new Flyway();

        String propertiesName = "jdbc.properties";
        ConcurrentHashMap properties = CommonTool.Tools.getPropertiesMap(propertiesName);
        String url = properties.get("url").toString();
        String userNmae = properties.get("username").toString();
        String password = properties.get("password").toString();
        flyway.setDataSource(url, userNmae, password);

        flyway.baseline();
    }
}
