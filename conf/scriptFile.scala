<configuration>

  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
      <pattern>%-5level - %msg%n</pattern>
    </encoder>
  </appender>

  <logger name="com.jolbox.bonecp" level="NONE">
    <appender-ref ref="STDOUT"/>
  </logger>

  <logger name="play" level="INFO">
    <appender-ref ref="STDOUT"/>
  </logger>

  <logger name="application" level="NONE">
    <appender-ref ref="STDOUT"/>
  </logger>

</configuration>