package com.workable.hackathon.helpers.reporters;

import cucumber.runtime.StepDefinitionMatch;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.*;
import gherkin.formatter.model.Step;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.yandex.qatools.allure.Allure;
import ru.yandex.qatools.allure.annotations.*;
import ru.yandex.qatools.allure.config.AllureModelUtils;
import ru.yandex.qatools.allure.events.*;
import ru.yandex.qatools.allure.model.DescriptionType;
import ru.yandex.qatools.allure.model.SeverityLevel;
import ru.yandex.qatools.allure.utils.AnnotationManager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by manolisvlastos on 27/06/16.
 */
public class AllureReporter implements Reporter, Formatter {

    private static final String SCENARIO_OUTLINE_KEYWORD = "Scenario Outline";

    private static final String FAILED = "failed";
    private static final String SKIPPED = "skipped";
    private static final String PASSED = "passed";

    private static final Log LOG = LogFactory.getLog(AllureReporter.class);

    private final Allure lifecycle = Allure.LIFECYCLE;

    private final Pattern severityPattern = Pattern.compile("@SeverityLevel\\.(.+)");
    private final Pattern issuePattern = Pattern.compile("@Issue\\(\"+?([^\"]+)\"+?\\)");
    private final Pattern testCaseIdPattern = Pattern.compile("@TestCaseId\\(\"+?([^\"]+)\"+?\\)");
    private final Pattern descriptionPattern = Pattern.compile("@Description\\(\"+?([^\"]+)\"+?\\)");
    private final Pattern dataTablePattern = Pattern.compile("@Datatable\\(\"+?([^\"]+)\"+?\\)");

    private Feature feature;
    private StepDefinitionMatch match;

    private LinkedList<Step> gherkinSteps = new LinkedList<>();

    private String uid;
    private String currentStatus;

    //to avoid duplicate names of attachments and messages
    private long counter = 0;

    @Override
    public void syntaxError(String state, String event, List<String> legalEvents, String uri, Integer line) {

    }

    @Override
    public void uri(String uri) {

    }

    @Override
    public void feature(Feature feature) {
        this.feature = feature;

        uid = UUID.randomUUID().toString();

        TestSuiteStartedEvent event = new TestSuiteStartedEvent(uid, feature.getName());

        Collection<Annotation> annotations = new ArrayList<>();
        annotations.add(getDescriptionAnnotation(feature.getDescription()));
        annotations.add(getFeaturesAnnotation(feature.getName()));

        AnnotationManager am = new AnnotationManager(annotations);
        am.update(event);
        lifecycle.fire(event);
    }

    @Override
    public void scenarioOutline(ScenarioOutline scenarioOutline) {

    }

    @Override
    public void examples(Examples examples) {

    }

    @Override
    public void startOfScenarioLifeCycle(Scenario scenario) {

        //to avoid duplicate com.workable.hackathon.steps in case of Scenario Outline
        if(SCENARIO_OUTLINE_KEYWORD.equals(scenario.getKeyword())){
            gherkinSteps.clear();
        }

        currentStatus = PASSED;

        TestCaseStartedEvent event = new TestCaseStartedEvent(uid, scenario.getName());
        event.setTitle(scenario.getName());

        Collection<Annotation> annotations = new ArrayList<>();

        SeverityLevel level = getSeverityLevel(scenario);

        if(level != null){
            annotations.add(getSeverityAnnotation(level));
        }

        Issues issues = getIssuesAnnotation(scenario);
        if(issues != null){
            annotations.add(issues);
        }

        TestCaseId testCaseId = getTestCaseIdAnnotation(scenario);
        if(testCaseId != null){
            annotations.add(testCaseId);
        }

        annotations.add(getFeaturesAnnotation(feature.getName()));
        annotations.add(getStoriesAnnotation(scenario.getName()));
        annotations.add(getDescriptionAnnotation(scenario.getDescription()));

        AnnotationManager am = new AnnotationManager(annotations);
        am.update(event);

        event.withLabels(AllureModelUtils.createTestFrameworkLabel("CucumberJVM"));

        lifecycle.fire(event);
    }

    @Override
    public void background(Background background) {

    }

    @Override
    public void scenario(Scenario scenario) {

    }

    @Override
    public void step(Step step) {
        gherkinSteps.add(step);
    }

    @Override
    public void endOfScenarioLifeCycle(Scenario scenario) {

        while (gherkinSteps.peek() != null){
            fireCanceledStep(gherkinSteps.remove());
        }

        lifecycle.fire(new TestCaseFinishedEvent());
    }

    @Override
    public void done() {

    }

    @Override
    public void close() {

    }

    @Override
    public void eof() {
        lifecycle.fire(new TestSuiteFinishedEvent(uid));
        uid = null;
    }

    @Override
    public void before(Match match, Result result) {

    }

    @Override
    public void result(Result result) {
        if (match != null) {
            if (FAILED.equals(result.getStatus())) {
                lifecycle.fire(new StepFailureEvent().withThrowable(result.getError()));
                lifecycle.fire(new TestCaseFailureEvent().withThrowable(result.getError()));
                currentStatus = FAILED;
            } else if(SKIPPED.equals(result.getStatus())){
                lifecycle.fire(new StepCanceledEvent());
                if (PASSED.equals(currentStatus)) {
                    //not to change FAILED status to CANCELED in the report
                    lifecycle.fire(new TestCaseCanceledEvent());
                    currentStatus = SKIPPED;
                }
            }
            lifecycle.fire(new StepFinishedEvent());
            match = null;
        }
    }

    @Override
    public void after(Match match, Result result) {

    }

    @Override
    public void match(Match match) {

        if (match instanceof StepDefinitionMatch) {
            this.match = (StepDefinitionMatch) match;

            Step step = extractStep(this.match);

            while (gherkinSteps.peek() != null && !isEqualSteps(step, gherkinSteps.peek())) {
                fireCanceledStep(gherkinSteps.remove());
            }

            if (isEqualSteps(step, gherkinSteps.peek())) {
                gherkinSteps.remove();
            }

            String name = this.match.getStepLocation().getMethodName();
            lifecycle.fire(new StepStartedEvent(name).withTitle(name));
        }
    }

    @Override
    public void embedding(String mimeType, byte[] data) {
        lifecycle.fire(new MakeAttachmentEvent(data, "attachment" + counter++, mimeType));
    }

    @Override
    public void write(String text) {
        lifecycle.fire(new MakeAttachmentEvent(text.getBytes(), "message" + counter++, "text/plain"));
    }

    private Step extractStep(StepDefinitionMatch match) {
        try {
            Field step = match.getClass().getDeclaredField("step");
            step.setAccessible(true);
            return (Step) step.get(match);
        } catch (ReflectiveOperationException e) {
            //shouldn't ever happen
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private boolean isEqualSteps(Step step, Step gherkinStep) {
        return Objects.equals(step.getLine(), gherkinStep.getLine());
    }

    private SeverityLevel getSeverityLevel(Scenario scenario) {
        SeverityLevel level = null;
        List<SeverityLevel> severityLevels = Arrays.asList(
                SeverityLevel.BLOCKER,
                SeverityLevel.CRITICAL,
                SeverityLevel.NORMAL,
                SeverityLevel.MINOR,
                SeverityLevel.TRIVIAL);
        for (Tag tag : scenario.getTags()) {
            Matcher matcher = severityPattern.matcher(tag.getName());
            if(matcher.matches()){
                SeverityLevel levelTmp;
                String levelString = matcher.group(1);
                try {
                    levelTmp = SeverityLevel.fromValue(levelString.toLowerCase());
                } catch (IllegalArgumentException e) {
                    LOG.warn(String.format("Unexpected Severity level [%s]. SeverityLevel.NORMAL will be used instead", levelString));
                    levelTmp = SeverityLevel.NORMAL;
                }

                if(level == null || severityLevels.indexOf(levelTmp) < severityLevels.indexOf(level)){
                    level = levelTmp;
                }
            }
        }
        return level;
    }

    private void fireCanceledStep(Step unimplementedStep) {
        String name = unimplementedStep.getName();
        lifecycle.fire(new StepStartedEvent(name).withTitle(name));
        lifecycle.fire(new StepCanceledEvent());
        lifecycle.fire(new StepFinishedEvent());
        if (PASSED.equals(currentStatus)) {
            //not to change FAILED status to CANCELED in the report
            lifecycle.fire(new TestCaseCanceledEvent(){
                @Override
                protected String getMessage() {
                    return "Unimplemented com.workable.hackathon.steps were found";
                }
            });
            currentStatus = SKIPPED;
        }
    }

    private Description getDescriptionAnnotation(final String description){
        return new Description(){
            @Override
            public String value() {
                return description;
            }

            @Override
            public DescriptionType type() {
                return DescriptionType.TEXT;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return Description.class;
            }
        };
    }

    private Features getFeaturesAnnotation(final String value) {
        return new Features() {

            @Override
            public String[] value() {
                return new String[]{value};
            }

            @Override
            public Class<Features> annotationType() {
                return Features.class;
            }
        };
    }

    private Stories getStoriesAnnotation(final String value) {
        return new Stories() {

            @Override
            public String[] value() {
                return new String[]{value};
            }

            @Override
            public Class<Stories> annotationType() {
                return Stories.class;
            }
        };
    }

    private Severity getSeverityAnnotation(final SeverityLevel value) {
        return new Severity() {

            @Override
            public SeverityLevel value() {
                return value;
            }

            @Override
            public Class<Severity> annotationType() {
                return Severity.class;
            }
        };
    }

    private Issues getIssuesAnnotation(Scenario scenario) {
        List<String> issues = new ArrayList<>();
        for (Tag tag : scenario.getTags()) {
            Matcher matcher = issuePattern.matcher(tag.getName());
            if(matcher.matches()){
                issues.add(matcher.group(1));
            }
        }
        return issues.size() > 0 ? getIssuesAnnotation(issues): null;
    }

    private Issues getIssuesAnnotation(List<String> issues){
        final Issue[] values = createIssuesArray(issues);
        return new Issues() {
            @Override
            public Issue[] value() {
                return values;
            }

            @Override
            public Class<Issues> annotationType() {
                return Issues.class;
            }
        };
    }

    private Issue[] createIssuesArray(List<String> issues) {
        ArrayList<Issue> values = new ArrayList<>();
        for (final String issue : issues) {
            values.add(new Issue() {
                @Override
                public Class<Issue> annotationType() {
                    return Issue.class;
                }

                @Override
                public String value() {
                    return issue;
                }
            });
        }

        return values.toArray(new Issue[values.size()]);
    }

    private TestCaseId getTestCaseIdAnnotation(Scenario scenario) {
        for (Tag tag : scenario.getTags()) {
            Matcher matcher = testCaseIdPattern.matcher(tag.getName());
            if(matcher.matches()){
                final String testCaseId = matcher.group(1);
                return new TestCaseId() {
                    @Override
                    public String value() {
                        return testCaseId;
                    }

                    @Override
                    public Class<TestCaseId> annotationType() {
                        return TestCaseId.class;
                    }
                };
            }
        }

        return null;
    }


}