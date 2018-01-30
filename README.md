# GaugeSeekBar

GaugeSeekBar is an Android library for displaying round seek bar view.

How to use
----------

```gradle
allprojects {
    repositories {
        maven { url 'http://repo.aws.10clouds.com:8081/artifactory/gradle-dev-local/' }
    }
}

dependencies {
    compile(group: 'tenclouds', name: 'gaugeseekbar', version: '1.0.0', ext: 'aar')
}
```

Available XML attributes
------------------------

| Attribute name            | Format      | Description                                                                                                                                                                              |
|---------------------------|-------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| startAngleDegrees         | float       | Angle between                                                                                                                                                                            |
| thumbRadius               | dimension   | Radius of default thumb drawable, 11dp by default                                                                                                                                        |
| thumbDrawable             | reference   | Custom thumb drawable to be used instead of default thumb drawable.                                                                                                                      |
| showThumb                 | boolean     | When set to "false" thumb is not shown, "true" by default.                                                                                                                               |
| thumbColor                | color       | Default thumb drawable color,  default value is #ff6f00                                                                                                                                  |
| trackGradient             | color array | Array of colors used to drawn track, can be contain only one color. Contains only grey color by default.                                                                                 |
| trackGradientPositions    | reference   | Array of floats between 0.0 and 1.0 indicating gradient color positions in track, has to be the same size as trackGradient. If not supplied the colors would be positioned evenly.       |
| showProgress              | boolean     | If set to false progress bar will not be drawn. True by default.                                                                                                                         |
| progress                  | float       | Float ranged between 0.0 and 1.0 indicating progress shown by the view. Any larger or smaller values will be interpreted as either 1, or 0.                                              |
| interactive               | boolean     | If set to true user will be able to set the progress by touch, else progress update would be only possible from code. True by default.                                                   |
| trackWidth                | dimension   | Width of track and progress bar, 8dp by default.                                                                                                                                         |
| progressGradient          | reference   | Array of colors used to drawn track, can be contain only one color. Contains green and red colors by default.                                                                            |
| progressGradientPositions | reference   | Array of floats between 0.0 and 1.0 indicating gradient color positions in progress, has to be the same size as progressGradient. If not supplied the colors would be positioned evenly. |

Examples
--------

![alt text](https://i.imgur.com/qUrPr98.png)

```
<com.tenclouds.gaugeseekbar.GaugeSeekBar
           ...
           app:thumbRadius="18dp"
           app:trackWidth="18dp" />
```

![alt text](https://i.imgur.com/Tmw1ZHF.png)
```
<com.tenclouds.gaugeseekbar.GaugeSeekBar
        ...
        app:interactive="false"
        app:progress="0.75"
        app:progressGradient="@array/progressColor"
        app:thumbColor="@color/colorPrimary"
        app:thumbRadius="18dp"
        app:trackWidth="18dp" />
```

![alt text](https://i.imgur.com/Zs5Zdys.png)
```
<com.tenclouds.gaugeseekbar.GaugeSeekBar
        ...
        app:interactive="true"
        app:showProgress="false"
        app:startAngleDegrees="90"
        app:thumbColor="@color/colorPrimary"
        app:thumbDrawable="@drawable/custom_thumb"
        app:trackGradient="@array/progressRainbow"
        app:trackWidth="13dp" />
```