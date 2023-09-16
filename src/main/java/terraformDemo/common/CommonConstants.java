package terraformDemo.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonConstants {

    private static final Logger logger = LoggerFactory.getLogger(CommonConstants.class);

    public static final boolean LOAD_ON_START_UP = false;
    
    public static final String DEMO_KEY;

    public static final String CONFIG_FILE_NAME = "/terraformDemo.properties";
    
    public static final String test_key = "LTAI5tFWraQXkw9Mz2cb3Y98";
    
    
    public static final String test_secret = "M8Z9xzAXPWURAZN1u5HKa9FzcKsy5b";

    static {
        Properties properties = loadProperties();
        DEMO_KEY = properties.getProperty("DEMO_KEY");
        logger.info("============================CONFIG=========================");
        logger.info("DEMO_KEY:" + DEMO_KEY);
        logger.info("============================================================");
    }

    /**
     * 加载顺序:<br>
     * 1. 运行时参数,示例: java -jar -DconfigPath=/home/test/terraformDemo.properties terraformDemo.jar <br>
     * 2. terraformDemo.jar同目录下的terraformDemo.properties <br>
     * 3. 用户home/config/目录下的terraformDemo.properties <br>
     * 
     * @return
     */
    public static Properties loadProperties() {
        Properties properties = new Properties();
        try {
            File file = null;
            String configPath = System.getProperty("configPath");
            if (StringUtils.isNotBlank(configPath)) {
                file = new File(configPath);
            }
            if (StringUtils.isBlank(configPath) || !file.exists()) {
                logger.info("[LoadConfig][1]can not find config file[-D]:" + configPath);
                configPath = System.getProperty("user.dir") + CONFIG_FILE_NAME;
                file = new File(configPath);
            }
            if (!file.exists()) {
                logger.info("[LoadConfig][2]can not find config file[user.dir]:" + configPath);
                configPath = System.getProperty("user.home") + File.separator + "config" + CONFIG_FILE_NAME;
                file = new File(configPath);
            }
            if (!file.exists()) {
                logger.info("[LoadConfig][3]can not find config file[user.home]:" + configPath);
                throw new RuntimeException("can not find config file!");
            }
            InputStream ins = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(ins, "UTF-8");
            properties.load(reader);
            ins.close();
            reader.close();
            logger.info("load config file:" + file.getAbsolutePath());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            System.exit(0);
        }
        return properties;
    }

}
