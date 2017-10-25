# Testing Framework incoorporating all open source technologies

### Testing approach
    BDD Testing

### Features Supported
    Cross-Browser Compatibility Testing
    Mobile Web Testing
    Mobile App Testing (IOS and Android)
    Parallel Execution

### Hubs and Grid
    SauceLabs Integration
    Gridlastic Integration
    Selenoid Integration

###  Reporting
    ReportPortal Integration
    Video Recording
    Screenshots Recording
    Logs Recording
    Code Coverage 

# There is a sample test for Amazon WebSite

## Use Maven

Open a command window and run:

    mvn clean install

This runs Cucumber features using Cucumber's JUnit runner. The `@RunWith(Cucumber.class)` annotation on the `CucumberExecutorTest`
class tells JUnit to kick off Cucumber.

## Use Gradle

Open a command window and run:
    
    gradle clean test

This runs Cucumber features using Cucumber's JUnit runner. The `@RunWith(Cucumber.class)` annotation on the `CucumberExecutorTest`
class tells JUnit to kick off Cucumber.


## Overriding options

The Cucumber runtime parses command line options to know what features to run, where the glue code lives, what plugins to use etc.
When you use the JUnit runner, these options are generated from the `@CucumberOptions` annotation on your test.

Sometimes it can be useful to override these options without changing or recompiling the JUnit class. This can be done with the
`cucumber.options` system property. The general form is:

Using Maven:

    mvn -Dcucumber.options="..." install

Let's look at some things you can do with `cucumber.options`. Try this:

    -Dcucumber.options="--help"

That should list all the available options.

*IMPORTANT*

When you override options with `-Dcucumber.options`, you will completely override whatever options are hard-coded in
your `@CucumberOptions` or in the script calling `cucumber.api.cli.Main`. There is one exception to this rule, and that
is the `--plugin` option. This will not _override_, but _add_ a plugin. The reason for this is to make it easier
for 3rd party tools to automatically configure additional plugins by appending arguments to a `cucumber.properties`
file.

### Run a subset of Features or Scenarios

Specify a particular scenario by *line* (and use the pretty plugin, which prints the scenario back)

    -Dcucumber.options="classpath:<path_to_feature_file> --plugin pretty"

You can also specify what to run by *tag*:

    -Dcucumber.options="--tags @bar --plugin pretty"

### Running only the scenarios that failed in the previous run

    -Dcucumber.options="@target/rerun.txt"

This works as long as you have the `rerun` formatter enabled.

### Specify a different formatter:

For example a JUnit formatter:

    -Dcucumber.options="--plugin junit:target/cucumber-junit-report.xml"

### Technology used 
    Java 1.8
    Cucumber 2.01
    Selenium 3.6.0
    Allure Reporting 2.6.3
    Maven 3.3+
    Gradle
    Spring 4.3.7-RELEASE
    Appium 2.6.0
