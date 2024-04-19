package com.brunosong.database_exam.exam1;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;

@Slf4j
public class MysqlJdbcReplicationNoReadOnly {

    /* ReadOnly를 사용하지 않아서 localhost:3307로 연결을 해야 한다. */
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

        //기본 상태
        System.out.println(conn);
        
        //상태 변경
        conn.setReadOnly(true);   //이때 상태가 변경된다.
        ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM testtable");

        //최종 상태
        System.out.println(conn);

        Thread.sleep(500000);

    }
}
