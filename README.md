
# KumuluzEE Feature Flags
[![Build Status](https://img.shields.io/travis/kumuluz/kumuluzee-feature-flags/master.svg?style=flat)](https://travis-ci.org/kumuluz/kumuluzee-feature-flags)

KumuluzEE Feature Flags project for development with feature flags.

KumuluzEE Feature Flags enables you to easily manage feature flags in your application. Currenty it includes the Unleash-based implementation. The Unleash documentation can be found [here](https://github.com/Unleash/unleash/).

## Usage

You can enable KumuluzEE Feature Flags Unleash support by adding the following dependency to pom.xml:
```xml
<dependency>
    <groupId>com.kumuluz.ee.feature-flags</groupId>
    <artifactId>kumuluzee-feature-flags-unleash</artifactId>
    <version>${kumuluzee-feature-flags-unleash.version}</version>
</dependency>
```

## Installing Unleash
In order to use Unleash, you can run it in Docker. You can find Unleash docker guide [here](https://github.com/Unleash/unleash-docker).

### Configuration
To configure Unleash, create configuration in resources/config.yaml.
Here you can put your Unleash host api address, app name will be read from `kumuluzee.name`. 
```yaml
kumuluzee:
  name: String - App Name
  feature-flags:
    unleash:
      unleash-api: String - null
```
Additionally if you want more advanced configuration, you can create a custom UnleashConfig object and annotate the class with `@FFConnection`. This will override configuration in resources/config.yml
```java
@FFConnection  
@ApplicationScoped  
public class UnleashConnection {  
    UnleashConfig config = UnleashConfig  
            .builder()  
            .unleashAPI("http://localhost:4242/api")  
            .appName("feature-flags-sample-service")  
            .build();
}
```

### Creating a flag

Access the admin UI on http://localhost:4242 and create a new flag. Name it "test-feature" (or whatever you want, but don't forget to correct the flag name in the code after).

### Using feature flags
You can check if feature flags are enabled by using FeatureFlags object in the same way as you would use Unleash client object.
```java
@Inject
private FeatureFlags featureFlags;

if(featureFlags.isEnabled("test-feature"){
	//do something
} else {
	//do something else
}
```

In order to use Unleash-specific operations, inject the `UnleashFeatureFlags` object.

## Sample

You can start by using the [sample code](https://github.com/kumuluz/kumuluzee-samples/tree/master/kumuluzee-feature-flags-unleash).

## Changelog

Recent changes can be viewed on Github on the [Releases Page](https://github.com/kumuluz/kumuluzee-feature-flags/releases)

## Contribute

See the [contributing docs](https://github.com/kumuluz/kumuluzee-feature-flags/blob/master/CONTRIBUTING.md)

When submitting an issue, please follow the 
[guidelines](https://github.com/kumuluz/kumuluzee-feature-flags/blob/master/CONTRIBUTING.md#bugs).

When submitting a bugfix, write a test that exposes the bug and fails before applying your fix. Submit the test
alongside the fix.

When submitting a new feature, add tests that cover the feature.

## License

MIT
