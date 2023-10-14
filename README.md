# TimeControl
Control which time is displayed for which player.

# How to use with maven?
```xml
    <dependencies>
    ...
        <dependency>
            <groupId>de.setlex</groupId>
            <artifactId>time-control</artifactId>
            <version>1.0.0</version>
        </dependency>
    ...
    </dependencies>
```

# How to access the api in your code

```java
public class Main extends JavaPlugin {

    // ...
    private TimeControl timeControlInstance;

    @Override
    public void onEnable() {
        timeControlInstance = getPlugin(TimeControl.class);
        
        // Example how to use a method:
        timeControlInstance.setDefaultMode(Mode.FROZEN(0, 6000));
    }

    public TimeControl getTimeControlInstance() {
        return timeControlInstance;
    }
    // ...
    
}
```

# Get errors?
 - Note that you specify `Time-Control` as `depend` in the `plugin.yml`
```yml
depend:
  - Time-Control
```
 - Is the [Time-Control.jar](https://github.com/TheHedgehogGamer/TimeControl/releases/tag/Plugin) plugin in the plugin folder of your server?