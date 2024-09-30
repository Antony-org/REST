package com.iti.jets.dbConnection;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.spi.ClassTransformer;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;
import org.hibernate.cfg.AvailableSettings;

import javax.sql.DataSource;
import java.net.URL;
import java.util.List;
import java.util.Properties;

public class CustomPersistenceUnit implements PersistenceUnitInfo {
    @Override
    public String getPersistenceUnitName() {
        return "Mysql";
    }

    @Override
    public String getPersistenceProviderClassName() {
        return "org.hibernate.jpa.HibernatePersistenceProvider";
    }

    @Override
    public PersistenceUnitTransactionType getTransactionType() {
        return PersistenceUnitTransactionType.RESOURCE_LOCAL;
    }

    @Override
    public DataSource getJtaDataSource() {
        return null;
    }

    @Override
    public DataSource getNonJtaDataSource() {
        try {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/rest");
            dataSource.setUsername("root");
            // dataSource.setPassword("root");
//           dataSource.setUsername("projectUser");
//           dataSource.setPassword("user");
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setMaximumPoolSize(50);
            dataSource.setConnectionTimeout(30000);  // 30 seconds
            dataSource.setIdleTimeout(60000);  // 1 minute
            dataSource.setMaxLifetime(180000); // 30 minutes
            dataSource.setMinimumIdle(5);  // Minimum number of idle connections in the pool

            // Log the configuration values for debugging
            System.out.println("HikariCP DataSource initialized with settings:");
            System.out.println("Max Pool Size: " + dataSource.getMaximumPoolSize());
            System.out.println("Connection Timeout: " + dataSource.getConnectionTimeout());
            System.out.println("Idle Timeout: " + dataSource.getIdleTimeout());
            System.out.println("Max Lifetime: " + dataSource.getMaxLifetime());
            System.out.println("Minimum Idle: " + dataSource.getMinimumIdle());

            return dataSource;
        } catch (Exception e) {
            throw new RuntimeException("Failed to configure HikariDataSource", e);
        }

    }

    @Override
    public List<String> getMappingFileNames() {
        return List.of();
    }

    @Override
    public List<URL> getJarFileUrls() {
        return List.of();
    }

    @Override
    public URL getPersistenceUnitRootUrl() {
        return null;
    }

    @Override
    public List<String> getManagedClassNames() {
        return List.of(

                "com.iti.jets.models.Product"


                // Add more classes as needed
        );
    }

     @Override
     public boolean excludeUnlistedClasses() {
         return false;
     }

     @Override
     public SharedCacheMode getSharedCacheMode() {
         return SharedCacheMode.ENABLE_SELECTIVE;
     }

     @Override
     public ValidationMode getValidationMode() {
         return ValidationMode.NONE;
     }

    @Override
    public  Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQLDialect");
        properties.setProperty(AvailableSettings.HBM2DDL_AUTO, "update");
        properties.setProperty(AvailableSettings.SHOW_SQL, "true");
        properties.setProperty(AvailableSettings.FORMAT_SQL, "true");
        return properties;
    }

     @Override
     public String getPersistenceXMLSchemaVersion() {
         return "2.2";
     }

     @Override
     public ClassLoader getClassLoader() {
         return Thread.currentThread().getContextClassLoader();
     }

     @Override
     public void addTransformer(ClassTransformer classTransformer) {
     }

     @Override
     public ClassLoader getNewTempClassLoader() {
         return Thread.currentThread().getContextClassLoader();
     }
 }
