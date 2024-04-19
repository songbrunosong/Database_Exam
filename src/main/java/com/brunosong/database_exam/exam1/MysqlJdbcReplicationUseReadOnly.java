package com.brunosong.database_exam.exam1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class MysqlJdbcReplicationUseReadOnly {

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();

        // We want this for failover on the replicas
        props.put("autoReconnect", "true");

        // We want to load balance between the replicas
        props.put("roundRobinLoadBalance", "true");

        props.put("user", "song");
        props.put("password", "1234");

        Connection conn =
                DriverManager.getConnection("jdbc:mysql:replication://localhost:3306,localhost:3307/songsworld",
                        props);

        conn.setReadOnly(false);
        conn.setAutoCommit(false);
        conn.createStatement().executeUpdate("UPDATE testtable set text = 'UPDATE TEXT' WHERE text = 'test_ROW'");
        conn.commit();


        Thread.sleep(500000);

    }
}
