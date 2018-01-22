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