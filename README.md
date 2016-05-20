
JAXB / XJC Plugin to add [Lombok](https://projectlombok.org/features/index.html) annotations in classes generated
from an XML Schema. Annotations supported:
* @Data
* @EqualsAndHashCode
* @ToString
* @AllArgsConstructor
* @NoArgsConstructor
* @Builder
* @Setter

### Usage on the Command Line

XJC Plugin options:
* -Xlombok - enable the plugin
* -Xlombok:Setter - add @Setter annotation
* -Xlombok:removeGeneratedSourceSetters - remove Setter methods from source (useful if you add @Setter annotation)
* -Xlombok:EqualsAndHashCode - add @EqualsAndHashCode annotation
* -Xlombok:ToString - add @ToString annotation
* -Xlombok:AllArgsConstructor - add @AllArgsConstructor annotation
* -Xlombok:NoArgsConstructor - add @NoArgsConstructor annotation
* -Xlombok:Builder - add @Builder annotation
* -Xlombok:Data - add @Data annotation

### Usage with Maven

```xml
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>jaxb2-maven-plugin</artifactId>
                <version>2.2</version>
                <dependencies>
                    <dependency>
                        <groupId>it.yobibit</groupId>
                        <artifactId>jaxb-lombok-plugin</artifactId>
                        <version>1.0.0</version>
                    </dependency>
                </dependencies>
                <executions>
                    <execution>
                        <id>xjc</id>
                        <goals>
                            <goal>xjc</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <packageName>com.company.model</packageName>
                    <arguments>
                        <argument>-Xlombok</argument>
                        <argument>-Xlombok:Setter</argument>
                        <argument>-Xlombok:removeGeneratedSourceSetters</argument>
                        <argument>-Xlombok:ToString</argument>
                        <argument>-Xlombok:Builder</argument>
                    </arguments>
                </configuration>
            </plugin>
```

### Inspired by:

* https://github.com/mplushnikov/xjc-lombok-plugin
* https://github.com/danielwegener/xjc-guava-plugin
