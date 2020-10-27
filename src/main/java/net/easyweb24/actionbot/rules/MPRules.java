/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.rules;

import java.util.List;
import net.easyweb24.actionbot.components.ApplicationContextHolder;
import net.easyweb24.actionbot.service.IndicatorsService;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseStrategy;

/**
 *
 * @author zbigniewwilgosz
 */
public abstract class MPRules {

    private final IndicatorsService indicatorsService;
    private int endIndex;
    private BarSeries series;
    private boolean shouldEnter = false;
    private boolean entryContinue = false;
    private double lastIndexTheBest = 0;
    private double lastValue = 0;
    private boolean goUp = false;
    private int timeRange = 14;
    private BaseStrategy entryStrategie;
    private BaseStrategy entryContinueStrategie;

    public MPRules(BarSeries series) {
        this.series = series;
        endIndex = series.getEndIndex();
        indicatorsService = ApplicationContextHolder.getContext().getBean(IndicatorsService.class);
        setIndicator(series);
        buildEntryRule();
        buildEntryRuleContinueInLastIndex();
        checkRules();
    }

    protected abstract void buildEntryRule();

    protected abstract void buildEntryRuleContinueInLastIndex();

    protected abstract void checkRules();

    protected abstract void setIndicator(BarSeries series);

    /**
     * @return the indicatorsService
     */
    public IndicatorsService getIndicatorsService() {
        return indicatorsService;
    }

    /**
     * @return the endIndex
     */
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * @param endIndex the endIndex to set
     */
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    /**
     * @return the series
     */
    public BarSeries getSeries() {
        return series;
    }

    /**
     * @param series the series to set
     */
    public void setSeries(BarSeries series) {
        this.series = series;
    }

    /**
     * @return the shouldEnter
     */
    public boolean isShouldEnter() {
        return shouldEnter;
    }

    /**
     * @param shouldEnter the shouldEnter to set
     */
    public void setShouldEnter(boolean shouldEnter) {
        this.shouldEnter = shouldEnter;
    }

    /**
     * @return the entryContinue
     */
    public boolean isEntryContinue() {
        return entryContinue;
    }

    /**
     * @param entryContinue the entryContinue to set
     */
    public void setEntryContinue(boolean entryContinue) {
        this.entryContinue = entryContinue;
    }

    /**
     * @return the lastIndexTheBest
     */
    public double getLastIndexTheBest() {
        return lastIndexTheBest;
    }

    /**
     * @param lastIndexTheBest the lastIndexTheBest to set
     */
    public void setLastIndexTheBest(double lastIndexTheBest) {
        this.lastIndexTheBest = lastIndexTheBest;
    }

    /**
     * @return the lastValue
     */
    public double getLastValue() {
        return lastValue;
    }

    /**
     * @param lastValue the lastValue to set
     */
    public void setLastValue(double lastValue) {
        this.lastValue = lastValue;
    }

    /**
     * @return the goUp
     */
    public boolean isGoUp() {
        return goUp;
    }

    /**
     * @param goUp the goUp to set
     */
    public void setGoUp(boolean goUp) {
        this.goUp = goUp;
    }

    /**
     * @return the timeRange
     */
    public int getTimeRange() {
        return timeRange;
    }

    /**
     * @param timeRange the timeRange to set
     */
    public void setTimeRange(int timeRange) {
        this.timeRange = timeRange;
    }

    /**
     * @return the entryStrategie
     */
    public BaseStrategy getEntryStrategie() {
        return entryStrategie;
    }

    /**
     * @param entryStrategie the entryStrategie to set
     */
    public void setEntryStrategie(BaseStrategy entryStrategie) {
        this.entryStrategie = entryStrategie;
    }

    /**
     * @return the entryContinueStrategie
     */
    public BaseStrategy getEntryContinueStrategie() {
        return entryContinueStrategie;
    }

    /**
     * @param entryContinueStrategie the entryContinueStrategie to set
     */
    public void setEntryContinueStrategie(BaseStrategy entryContinueStrategie) {
        this.entryContinueStrategie = entryContinueStrategie;
    }

    protected void checkSimplyRules(List<Double> values) {
        double topValue = 0;
        for (int i = (getEndIndex() - getTimeRange()); i <= getEndIndex(); i++) {
            //System.out.println(values.get(i));
            //System.out.println(shouldEnter);
            if (getEntryStrategie().shouldEnter(i) && !isShouldEnter()) {
                topValue = 0;
                setShouldEnter(true);
            } else if (isShouldEnter() && getEntryStrategie().shouldExit(i)) {
                topValue = 0;
                setShouldEnter(false);
            }

            if (values.get(i) > topValue && getEntryContinueStrategie().shouldEnter(i)) {
                topValue = values.get(i);
            }

        }

        setEntryContinue(getEntryContinueStrategie().shouldEnter(getEndIndex()));
        setLastIndexTheBest((values.get(getEndIndex()) / topValue) * 100);
        setGoUp(values.get(getEndIndex() - 2) < values.get(getEndIndex()-1) && values.get(getEndIndex() - 1) < values.get(getEndIndex()));
        setLastValue(values.get(getEndIndex()));
    }

}
